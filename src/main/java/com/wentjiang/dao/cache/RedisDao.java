package com.wentjiang.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.wentjiang.entity.Seckill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final JedisPool jedisPool;
	
	public RedisDao(String ip,int port){
		jedisPool=new JedisPool(ip,port);
	}
	
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
	
	public Seckill getSeckill(long seckillId){
		//redis操作逻辑
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckillId;
				//并没有实现内部序列化操作
				//get->byte[]->反序列化->object(seckill)
				//采用自定义序列化的方式
				//protostuff:pojo
				byte[] bytes = jedis.get(key.getBytes());
				if (bytes!=null) {
					//空对象
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
					//seckill被反序列
					return seckill;
				}
			} catch (Exception e) {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public String putSeckill(Seckill seckill){
		//set object(seckill)->序列化->byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, 
						LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//超时缓存
				int timeout = 60*60;//一小时
				String result  = jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			} catch (Exception e) {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
