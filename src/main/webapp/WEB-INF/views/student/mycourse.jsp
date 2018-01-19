<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
         <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		 <title>课程管理</title>
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
</head>
	<!DOCTYPE html>
	<html>
	<head>
	     <meta charset="utf-8">
         <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		 <title>课程详情</title>
		 <link rel="stylesheet" type="text/css" href="../layui/css/layui.css">	
     <link rel="stylesheet" type="text/css" href="../layui/css/global.css">
	</head>
	<body class="">
 <div class="">
	 
      <!-- 主体部分 -->
       <div class=""  id="coursedetail" v-cloak style="left:0px;">
       <div style="padding: 15px;">
       	<div class="layui-row" >
       		<div class="layui-col-md11">
       		  <h1>课程详情</h1>
          </div>
       		<div class="layui-col-md1">
       		<button class="layui-btn" style="margin-bottom: 5px;"><a href="/TxCourse/studentCourse/toStudentCourse" style="color:white;">返 回</a></button>
       	</div>
        </div>
       	<hr>
       	<!-- 所有课程部分 -->
       	<div class="site-text site-block"> 
       		<!-- 表格部分 -->
          <div class="layui-form" style="margin-right: 0px;">
           <table class="layui-table" lay-skin="nob" style="table-layout:fixed;">
              <colgroup>
                <col width="15%">
                <col width="35%">
                <col width="15%">
                <col width="35%">
              </colgroup>
              <tbody>
                <tr>
                  <td>课程名称：</td>
                  <td>{{course.courseName}}</td>
                  <td>课程号：</td>
                  <td>{{course.courseSequence}}</td>
                </tr>
                <tr>
                  <td>上课时间：</td>
                  <td>{{course.courseTime}}</td>
                  <td></td>
                  <td></td>
                </tr>
                <tr>
                  <td>课程要求：</td>
                  <td colspan="3">
                    <div v-html="course.content" style="word-wrap:break-word"></div>
                  </td>
                </tr>
                <tr>
                  <td>实验报告：</td>
                  <td>
                        <button class="layui-btn" id="upload" v-bind:data-id="''+course.id"><i class="layui-icon">&#xe62f;</i>上传</button>
                        <span style="color:red">注：若与已上传报告同名，将会被覆盖</span>
                   </td>
                   <td colspan="2">
                   		<div v-if="course.reportUrl" >已上传：<a :href="course.reportUrl">{{getReportName(course.reportUrl)}}</a></div>
                   </td>
                </tr>
                <tr>
                  <td>计算资源：</td>
                  <td colspan="3">
                  		<button v-if="course.status || course.status == -1" class="layui-btn " id="create" v-on:click="create()"><i class="layui-icon">&#xe630;</i>创建</button>
                  		<span v-else style="color:red">注：已创建资源，资源详情如下</span>
                  </td>
                </tr>
              </tbody>
           </table> 
            <table v-if="!(course.status || course.status == -1)" class="layui-table" >
               <colgroup>
               	<col width="5%">
                <col width="10%">
                <col width="10%">
                <col width="10%">
                <col width="15%">
                <col width="10%">
                <col width="10%">
                <col width="30%">
              </colgroup>
              <thead>
                <tr>
                	<td>序号</td>
                  <td>ip地址</td>
                  <td>密码</td>
                  <td>CPU/内存</td>
                  <td>系统</td>
                  <td>状态</td>
                  <td>积分</td>
                  <td>操作</td>
                </tr>
              </thead>
              <tbody>
              	<td>1</td>
                <td>{{course.ip}}</td>
                  <td>
					<button class="layui-btn layui-btn-sm" v-on:click="showPwd(course, $event)">查看密码</button>
					</td>
                  <td>{{course.vpsCpu}}核/{{course.vpsMem}}MB</td>
                  <td>{{course.osname}}</td>
                  <td>{{course.vpsstatus}}</td> 
                  <td>{{course.workTime}}</td>
                  <td>
                    <div class="layui-btn-group">
                                <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="refresh(course)">
                                <i class="layui-icon">&#x1002;</i> 刷新状态</button>
                                <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="resetPwd(course)">
                                <i class="layui-icon"> &#xe857;</i>重置密码</button>
                                <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="boot(course)">
                                <i class="layui-icon">&#xe6af;</i> 开机</button>
                                <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="shutdown(course)">
                                <i class="layui-icon">&#xe69c;</i> 关机</button>
                                <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="reboot(course)">
                                <i class="layui-icon">&#xe756;</i> 重启</button>
                                <button class="layui-btn layui-btn-sm " v-on:click="">
                                <i class="layui-icon">&#xe756;</i> VNC</button>
                          </div> 
                  </td> 
              </tbody>
            </table>
          </div> 
          <!-- end 表格部分 -->
       	</div>
       </div>
      </div>
      <!-- end 主体部分 -->
	</div>
	</body>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script>

layui.use(['form','layer','upload'], function(){
	var layer = layui.layer;
	var $ = layui.jquery;
	var form = layui.form;
	var upload = layui.upload;
	
	var courseApp = new Vue({
		el:'#coursedetail',
		data:{
			course:{}
		},
		created:function () {
            this.getMyCourse();
        },
        mounted:function() {
	  		form.render();
	  	},
		methods:{
			getMyCourse:function(){ 
	            $.post("/TxCourse/studentCourse/getCourseDetial",{},function (obj) {
	            	courseApp.course = obj.result[0];
	            	if(courseApp.course.vpsstatus==='-1'){
	            	   courseApp.course.vpsstatus='异常';
	            	}else if(courseApp.course.vpsstatus==='running'){
	            	   courseApp.course.vpsstatus='正在运行';
	            	}else if(courseApp.course.vpsstatus==='halted'){
	            	   courseApp.course.vpsstatus='已关机';
	            	}
	            	window.parent.iframeLoad();
	            })
			},
			getReportName:function(reportUrl) {
				//console.log(reportUrl);
            	var pattern = /[^\/\\]+$/;
            	return reportUrl.match(pattern)[0];
            	//return reportUrl;
            },
            refresh:function(student) {
				$.ajax({
	        		url:'/TxCourse/studentCourse/refreshExperimentStatus',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
	        				layer.closeAll('loading');
							layer.msg(data.result, {icon: 6});
							courseApp.getMyCourse();
							window.parent.iframeLoad();
						} else {
							layer.closeAll('loading');
							layer.msg(data.result, {icon: 5});
						}
	        		},
	        		error:function(data) {
	        			layer.closeAll('loading');
	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	        		}
	        	});
			},
			create:function(course){
            	$.ajax({
	        		url:'/TxCourse/studentCourse/createResources',
	        		dataType:'JSON',
	        		data:{cid:courseApp.course.id 

},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
	        				layer.closeAll('loading');
							layer.msg(data.result, {icon: 6});
	        				courseApp.getMyCourse();
	        				//window.parent.iframeLoad();
						} else {
							layer.closeAll('loading');
							layer.msg(data.result, {icon: 5});
						}
	        		},
	        		error:function(data) {
	        			layer.closeAll('loading');
	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	        		}
	        	});
            },
            resetPwd:function(student) {
            	$.ajax({
	        		url:'/TxCourse/studentCourse/resetPwd',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {console.log("0");
							$.post("/TxCourse/studentCourse/resetPwd", {vpsid:student.vpsid}, function(stuData) {
								console.log("resetPwd-0");
							});
						} else if (data.status == -1) {console.log("-1");
							layer.closeAll('loading');
							layer.msg(data.result, {icon: 5});
							courseApp.getMyCourse();
						} else if (data.status == 1) {
							console.log("1");
							layer.open({
						        type: 1
						        ,title: "新密码" //不显示标题栏
						        ,closeBtn: false
						        ,area: '300px;'
						        ,shade: 0.8
						        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
						        ,btn: ['确认', '取消']
						        ,btnAlign: 'c'
						        ,moveType: 1 //拖拽模式，0或者1
						        ,content: "<div style='padding:20px;'>请输入新密码：（至少6位）<br><br><input type='text' /><div>"
						        ,yes: function(index, layero){
						        	$.ajax({
						        		url:'/TxCourse/studentCourse/resetPwd',
						        		dataType:'JSON',
						        		data:{vpsid:student.vpsid},
						        		type:'POST',
						        		beforeSend:function(){
						        			layer.load(1);
						        		},
						        		success:function(data, status) {
						        			layer.closeAll('loading');
						        			var newpwd = $.trim(layero.find("input").val());
						        			if (newpwd.length < 6) {
						        				layer.msg("密码长度不能少于6位", {icon: 5});
						        			} else {
						        				layer.load(1);
						        				$.post("/TxCourse/studentCourse/resetPwd", {vpsid:student.vpsid,newPassword:newpwd}, function(resetData) {
						        					layer.closeAll('loading');
						        					layer.close(index);
						        					layer.msg(resetData.result, {icon: 6});
						        					courseApp.getMyCourse();
						        					window.parent.iframeLoad();
						        				});
						        			}
						        		},
						        		error:function(data) {
						        			console.log("error");
						        		}
						        	});
						        }
							});
						} else if (data.status == 2) {
							//密码长度不够重新输入
						}
	        		},
	        		error:function(data) {
	        			layer.closeAll('loading');
	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	        		}
	        	});
            },
            boot:function(student) {
            	$.ajax({
	        		url:'/TxCourse/studentCourse/openVps',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
	        				courseApp.getMyCourse();
	        				layer.closeAll('loading');
							layer.msg(data.result, {icon: 6});
							window.parent.iframeLoad();
						} else {
							layer.closeAll('loading');
							layer.msg(data.result, {icon: 5});
						}
	        		},
	        		error:function(data) {
	        			layer.closeAll('loading');
	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	        		}
	        	});
            },
            shutdown:function(student) {
            	$.ajax({
	        		url:'/TxCourse/studentCourse/shutdownVps',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
	        				courseApp.getMyCourse();
	        				layer.closeAll('loading');
							layer.msg(data.result, {icon: 6});
							window.parent.iframeLoad();
						} else {
							layer.closeAll('loading');
							layer.msg(data.result, {icon: 5});
						}
	        		},
	        		error:function(data) {
	        			layer.closeAll('loading');
	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	        		}
	        	});
            },
            reboot:function(student) {
            	$.ajax({
	        		url:'/TxCourse/studentCourse/restartVps',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
	        				courseApp.getMyCourse();
	        				layer.closeAll('loading');
							layer.msg(data.result, {icon: 6});
							window.parent.iframeLoad();
						} else {
							layer.closeAll('loading');
							layer.msg(data.result, {icon: 5});
						}
	        		},
	        		error:function(data) {
	        			layer.closeAll('loading');
	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	        		}
	        	});
            },
            showPwd:function(vps, event) {
            	var curNode = event.currentTarget;
            	var parentNode = $(curNode).parent();
            	parentNode.empty();
            	console.log(vps.passwd);
            	if (typeof(vps.passwd)=="undefined" ) {
            		parentNode.append("无密码");
            	} else {
            		parentNode.append(vps.passwd);
            	}
            },
            create:function(course){
            	$.ajax({
	        		url:'/TxCourse/studentCourse/createResources',
	        		dataType:'JSON',
	        		data:{cid:courseApp.course.id},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
	        				layer.closeAll('loading');
							layer.msg(data.result, {icon: 6});
	        				courseApp.getMyCourse();
	        				//window.parent.iframeLoad();
						} else {
							layer.closeAll('loading');
							layer.msg(data.result, {icon: 5});
						}
	        		},
	        		error:function(data) {
	        			layer.closeAll('loading');
	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	        		}
	        	});
            },
		}
	});
	
	upload.render({
		elem:'#upload',
		accept: 'file' ,
		url:'/TxCourse/studentCourse/uploadReport',
		data:{
			id:courseApp.course.id,
		},
		done:function(res){
			layer.closeAll('loading');
     		if (res.status == 0) {
        		layer.msg(res.result, {icon: 6});
        		layer.close(index);
        	} else{
        	   	layer.msg(res.result, {icon: 5}); 
           	}
		},
		error:function(data){
			layer.closeAll('loading');
			layer.msg(data.status + " " + data.statusText, {icon: 5});
		}
	});
	
});


</script>
</html>	