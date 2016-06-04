-- 数据库初始化脚本
-- 创建数据库

create database seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
create table seckill(
	`seckill_id` bitint not null auto_increment comment '商品库存id',
	`name` varchar(120) not null comment '商品名称',
	`number` int not null comment '库存数量',
	`start_time` timestamp not null comment '秒杀开始时间',
	`end_time` timestamp not null comment '秒杀结束时间',
	`create_time` timestamp not null default current_timestamp comment '创建时间',
	primary_key(seckill_id),
	key idx_start_time(start_time),
	key idx_end_time(end_time),
	key idx_create_time(create_time)
	)engine=InnoDB auto_increment=1000 default charset=utf8 comment='秒杀数据库';

-- 初始化数据
	insert into seckill(name,number,start_time,end_time)
	values
		("1000元秒杀iphone",100,"2015-11-01 00:00:00","2015-11-02 00:00:00"),
		("1元秒杀ipad",100,"2015-11-01 00:00:00","2015-11-02 00:00:00"),
		("200元秒杀三星g7",100,"2015-11-01 00:00:00","2015-11-02 00:00:00"),
		("20元秒杀iwatch",100,"2015-11-01 00:00:00","2015-11-02 00:00:00"),
		("300元秒杀红米note2",100,"2015-11-01 00:00:00","2015-11-02 00:00:00"),
	
-- 秒杀成功明细表
-- 用户登录认证相关信息
create table success_killed(
	`seckill_id` bitint not null comment '秒杀商品id',
	`user_phone` bitint not null comment '用户手机号',
	`state` tinyint not null default -1 comment 
	'状态标识:-1:无效 0:成功 1:已付款 2:已发货',
	`create_time` timestamp not null comment '创建时间',
	primary key(seckill_id,user_phone),/* 联合主键 */
	key idx_create_time(create_time)
)engine=InnoDB auto_increment=1000 default charset=utf8 comment='秒杀成功明细';


		
		
		
		
		
		
	
	