package com.wentjiang.exception;
/**
 * 重复秒杀异常(运行时异常)
 * @author jiangwentao
 * @date
 */
public class RepeatKillexception extends SeckillException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2748866953891675190L;

	public RepeatKillexception(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RepeatKillexception(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	 
}
