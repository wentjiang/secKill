<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<!-- 静态包含,在一个servlet中 -->
<%@include file="common/head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>秒杀详情页</title>

</head>
<body>
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="pannel-heading">
				<h1>${seckill.name }</h1>
			</div>
				<h2 class="text-danger">
				<!-- 显示time图标 -->
				<span class="glyphicon glyphicon-time"></span>
				<!-- 展示倒计时 -->
				<span class="glyphicon" id="seckill-box"></span>
				</h2>
			</div>
		</div>
	</div>
	<!-- 弹出登录框,输入电话 -->
	<div id="killPhoneModal" class="modal fade">
		<div class="modal-dialog">
		
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"></span>秒杀电话:
					</h3>
				</div>
				<div class="modal-body">
					<div class="row"></div>
					<div class="col-xs-8 col-xs-offset-2">
						<input type="text" name="killPhone" id="killPhoneKey"
							placeholder="填写手机号" class="form-control">
					</div>
				</div>
				<div class="modal-footer">
				<!-- 验证信息 -->
					<span id="killPhoneMessage" class="glyphicon"></span><br>
					<button type="button" id="killPhoneBtn" class="btn btn-success">
						<span class="glyphicon glyphicon-phone"></span>
						Submit
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
	<script src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
	<script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	
	<!-- cookie操作插件 -->
	<script src="http://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
	<!-- 倒计时插件 -->
	<script src="http://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
	<!-- 编写交互逻辑 -->
	<script src="/resources/seckill.js"></script>
	<script type="text/javascript">
		$(function(){
			seckill.detail.init({
				seckillId : ${seckill.seckillId},
				startTime : ${seckill.startTime.time},//毫秒
				endTime : ${seckill.endTime.time}
			});
		});
			//使用EL表达式传入参数
	</script>
</html>