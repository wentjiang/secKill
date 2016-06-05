package com.wentjiang.service;

import java.util.List;

import com.wentjiang.dto.Exposer;
import com.wentjiang.dto.SeckillExecution;
import com.wentjiang.entity.Seckill;
import com.wentjiang.exception.RepeatKillexception;
import com.wentjiang.exception.SeckillException;

/**
 * 业务接口:站在"使用者"的角度设计接口
 * 三个方面:方法定义粒度,参数,返回类型(return 类型/异常)
 * @author jiangwentao
 * @date
 */
public interface SeckillService {
	/**
	 * 查询所有秒杀记录
	 * @return
	 * List<Seckill>
	 *
	 */
	List<Seckill> getSeckillList();
	/**
	 * 查询单个秒杀记录
	 * @param seckillId
	 * @return
	 * Seckill
	 *
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 秒杀开启时,输出秒杀接口地址,否则输出系统时间和秒杀时间
	 * @param seckillId
	 * void
	 */
	Exposer exportSeckillUrl(long seckillId);
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * void
	 *
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5) 
	throws SeckillException,RepeatKillexception,SeckillException;
	/**
	 * 存储过程执行秒杀
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 * SeckillExecution
	 *
	 */
	SeckillExecution executeSeckillProcedure(long seckillId,long userPhone,String md5);
}
