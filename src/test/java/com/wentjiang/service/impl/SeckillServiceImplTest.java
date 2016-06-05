package com.wentjiang.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wentjiang.dto.Exposer;
import com.wentjiang.dto.SeckillExecution;
import com.wentjiang.entity.Seckill;
import com.wentjiang.exception.RepeatKillexception;
import com.wentjiang.exception.SeckillCloseException;
import com.wentjiang.service.SeckillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/*.xml" })
public class SeckillServiceImplTest {
	@Resource
	private SeckillService seckillService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("list={}",list);
		System.out.println(list.size());
		for (Seckill seckill : list) {
			System.out.println(seckill);
		}
	}

	@Test
	public void testGetById() {
		Seckill seckill = seckillService.getById(1000L);
		System.out.println(seckill);
	}

	//测试秒杀逻辑,注意可重复执行
	@Test
	public void testSeckillLogic() throws Exception{
		long id = 1001;
		Exposer exposer = seckillService.exportSeckillUrl(id);
		String md5 = exposer.getMd5();
		if(exposer.isExposed()){
			logger.debug("exposer={}", exposer);
			try{
				SeckillExecution seckillExecution = seckillService.executeSeckill(1000L, 17004951042L, md5);
				System.out.println(seckillExecution);
			}catch(RepeatKillexception e){
				logger.error(e.getMessage());
			}catch(SeckillCloseException e){
				logger.error(e.getMessage());
			}
		}else{
			//秒杀未开启
			logger.warn("exposer={}",exposer);
		}
		
		
		//md5=fd584413eba87f953d96f994b1b184ca
	}

	@Test
	public void testExecuteSeckillProcedure() {
		long seckillId = 1001L;
		long phone = 12311111111L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if (exposer.isExposed()) {
			String md5 = exposer.getMd5();
			SeckillExecution seckillExecution = seckillService
					.executeSeckillProcedure(seckillId, phone, md5);
			logger.info(seckillExecution.getStateInfo());
		}
	}

}
