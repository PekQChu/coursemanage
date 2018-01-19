<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
         <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		 <title>我的消息</title>
		 <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	     <!--  <link rel="stylesheet" href="layui/css/layui.css"> -->
	      <style>
		       [v-cloak] {
                 display: none;
               }
		  </style>
		 <link rel="stylesheet" type="text/css" href="/TxCourse/layui/css/layui.css">
		 <script type="text/javascript" src="/TxCourse/layui/layui.js"></script>
		 <script type="text/javascript" src="/TxCourse/js/vue.min.js"></script>
		 <script type="text/javascript" src="/TxCourse/js/jquery.min.js"></script>
</head>
<body class="">
    <div class="">
	 
      <!-- 主体部分 -->
       <div class="" style="left:0px;">
       <div style="padding: 15px;">
       	<div class="layui-row" >
       		<div class="layui-col-md11">
       		<h1>我的信息</h1></div>
       		<div class="layui-col-md1">
       		<button class="layui-btn" onclick="back()">返 回</button>
       	</div>
       	<hr>
       	<!-- 所有课程部分 -->
       	<div class="">
       		<table class="layui-table" id="allmessage" v-cloak style="table-layout:fixed;">
           <colgroup>
              <col width="5%">
              <col width="15%">
              <col width="15%">
              <col width="15%">
              <col width="15%">
              <col width="20%">
              <col width="15%">
          </colgroup>
            <thead>
             <tr>
              <th>序号</th>
              <th>发送人</th>
              <th>接收人</th>
              <th>状态</th>
              <th>时间</th>
              <th>内容预览</th>
              <th>操作</th>
             </tr> 
            </thead>
           <tbody>
           <tr v-for="(message,index) in messages">
            <td>{{index+1}}</td>
            <td>{{message.senderName}}({{message.senderUid}})</td>
            <td>{{message.recipientName}}({{message.recipientUid}})</td>
            <td>{{message.state}}</td>
            <td>{{new Date(parseInt(message.date)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ")}}</td>     
            <td><div style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">{{message.mes}}</div></td>
            <td>
                <div class="layui-btn-group">
                   <button class="layui-btn  layui-btn-normal" v-on:click="messageDetail(message)"><i class="layui-icon">&#xe642;</i>详情</button>
                </div>
            </td>
        </tr>
  </tbody>
</table>
       	</div>
       </div>
      </div>
      <!-- end 主体部分 -->
	</div>
	</body>
	<script type="text/javascript">

	layui.use(['layer'], function(){
		  var layer=layui.layer;
	var app = new Vue({
		el:'#allmessage',
		data:{
			messages:[],
		},
		created:function(){
			this.load();
		},
		methods:{
		    load:function(){
				$.post("/TxCourse/teacherCourse/getMyMessage",function (result) {
					if(result.status != 0)
						layer.msg("暂时没有数据哦！");
					else
					{
						app.messages=result.result;
						window.parent.iframeLoad();
						}
				})
			},
	        messageDetail:function(message){
	        	  $.ajax({
	                    url:"/TxCourse/teacherCourse/getMessage",
	                    dataType:"json",
	                    type:"POST",
	                    data:{id:message.id},
	                    success:function(result){
	                    	if(result.status==0)
	                    		{
	                    		$.ajax({
	                    			url:"/TxCourse/teacherCourse/messageReaded",
	                    			dataType:"JSON",
	                    			type:"POST",
	                    			data:{id:message.id},
	                    			beforeSend:function(){
	                    				layer.load(1);
	                    			},
	                    			success:function(data, status) {
	                    				layer.closeAll('loading');
	                    				app.load();
	                    				window.parent.iframeLoad();
	                    			},
	                    			error:function(data){
	                    				layer.closeAll('loading');
	            	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	                    			}
	                    		});
	                    		 layer.open({
	                    		         title: '来自'+result.result[0].sendName+'的消息，时间是'+new Date(parseInt(result.result[0].date)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ")	                    		        ,area: ['390px', '260px']
	                    		        ,shade: 0
	                    		        ,area: '500px'
	                    		        ,maxmin: true
	                    		        ,closeBtn:0
	                    		        ,content: result.result[0].message
	                    		        ,btn: ['确定'] //只是为了演示
	                    		        ,yes: function(index, layero){
	                    		        	    //do something
	                    		        	    layer.close(index); //如果设定了yes回调，需进行手工关闭
	                    		        	  }
	                    		      });
	                    		}
	                    	else
	                    	{
	                    		layer.msg("没有查询到数据");
	                    		}
	                    },
	                    error:function() {
	                    	layer.msg("出了一些意外，请联系超级管理员");
	                    },
	                });
    },
	},
});
});
function back(){
	window.history.go(-1)
}
</script>
</html>	