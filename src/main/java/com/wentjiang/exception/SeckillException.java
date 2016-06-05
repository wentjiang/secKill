package com.wentjiang.exception;
/**
 * 秒杀相关异常
 * @author jiangwentao
 * @date
 */
public class SeckillException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2662954426207673975L;

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillException(String message) {
		super(message);
	}
	
}
