package com.wentjiang.dao;

import org.apache.ibatis.annotations.Param;

import com.wentjiang.entity.SuccessKilled;

public interface SuccessKilledDao {
	 /**
     * 插入购买明细,可过滤重复(数据库有联合主键)
     *
     * @param seckilledId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckilledId") long seckilledId, @Param("userPhone") long userPhone);

    /**
     * 根据ID查询SuccessKilled并携带秒杀产品对象实体
     *
     * @param seckilledId
     * @param userPhone
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckilledId") long seckilledId, @Param("userPhone") long userPhone);

}
