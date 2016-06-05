package com.wentjiang.dao.cache;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wentjiang.dao.SeckillDao;
import com.wentjiang.entity.Seckill;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class RedisDaoTest{
	
	private long id = 1001;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SeckillDao seckillDao;
	@Test
	public void testSeckill() throws Exception{
		//get and put
		Seckill seckill = redisDao.getSeckill(id);
		if (seckill==null) {
			seckill = seckillDao.queryById(id);
			String result = redisDao.putSeckill(seckill);
			System.out.println(result);
			seckill = redisDao.getSeckill(id);
			System.out.println(seckill);
		}
		
	}


}
