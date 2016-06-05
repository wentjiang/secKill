package com.wentjiang.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.wentjiang.dao.SeckillDao;
import com.wentjiang.dao.SuccessKilledDao;
import com.wentjiang.dao.cache.RedisDao;
import com.wentjiang.dto.Exposer;
import com.wentjiang.dto.SeckillExecution;
import com.wentjiang.entity.Seckill;
import com.wentjiang.entity.SuccessKilled;
import com.wentjiang.enums.SeckillStateEnum;
import com.wentjiang.exception.RepeatKillexception;
import com.wentjiang.exception.SeckillCloseException;
import com.wentjiang.exception.SeckillException;
import com.wentjiang.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RedisDao redisDao;
	@Autowired //@resource,@inject
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	//md5盐值字符串,用语混淆
	private final String slat = "jiangwentao";
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 10);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		//优化点:缓存优化:超时的基础上维护一致性
		
		// 1:访问redis
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill==null) {
			//2.访问数据库
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			}else{
				//3.放入redis
				redisDao.putSeckill(seckill);
			}
		}
		
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		//获取系统当前时间
		Date nowTime = new Date();
		
		if (nowTime.getTime()<startTime.getTime()||nowTime.getTime()>endTime.getTime()) {
			return new Exposer(false, seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
		}
		//转化特定字符串的过程,不可逆
		String md5 = getMD5(seckillId);
		
		return new Exposer(true, md5,seckillId);
	}

	@Override
	@Transactional
	/**
	 * 使用注解控制事务方法的优点
	 * 1:开发团队达成一致的约定,明确标注事务方法的编程风格
	 * 2:保证事务方法的执行时间尽可能短,不要穿插其他的网络操作rpc/http请求,需要的话剥离到方法外
	 * 3:不是所有的方法都需要事务,如只有一条修改操作,或者只读操作,不需要事务控制
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, RepeatKillexception, SeckillException {
		if (md5==null||!md5.equals(getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		try{
		//执行秒杀逻辑:减库存+记录购买行为
		Date nowTime = new Date();
		int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
		if (updateCount<=0) {
			//没有更新到记录,秒杀结束
			throw new SeckillCloseException("seckill is closed");
		}else{
			//记录购买行为
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			//唯一:主键
			if(insertCount<=0){
				//重复秒杀
				throw new RepeatKillexception("seckill repeated");
			}else{
				//秒杀成功
				SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
			}
		}
		}catch(SeckillCloseException e1){
			throw e1;
		}catch(RepeatKillexception e2){
			throw e2;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			//所有编译起异常,转化为运行期异常
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}

	private String getMD5(long seckillId){
		String base = seckillId + "/" +slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
}
