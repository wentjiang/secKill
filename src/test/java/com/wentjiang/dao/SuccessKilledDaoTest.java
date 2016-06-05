package com.wentjiang.dao;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wentjiang.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class SuccessKilledDaoTest {
	
	@Resource
	private SuccessKilledDao successKilledDao;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testInsertSuccessKilled() {
		int count  = successKilledDao.insertSuccessKilled(1000, 17004951042L);
		System.out.println(count==1);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1000L, 17004951042L);
		System.out.println(successKilled);
	}

}
