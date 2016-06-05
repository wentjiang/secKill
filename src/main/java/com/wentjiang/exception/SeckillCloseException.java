package com.wentjiang.exception;
/**
 * 秒杀关闭异常
 * @author jiangwentao
 * @date
 */
public class SeckillCloseException extends SeckillException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1599701448918339432L;

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillCloseException(String message) {
		super(message);
	}
	
	
}
