<%@ page language="java" contentType="text/html; charset=utf-8"%>
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
		 <script type="text/javascript" src="/TxCourse/js/jquery.min.js"></script>
		 <script type="text/javascript" src="/TxCourse/js/vue.min.js"></script>
</head>
<body class="">
	 <div class="" style="overflow: hidden;">
	 
      <!-- 主体部分 -->
       <div class="" id="coursemanage" v-cloak style="left:0px;">
       <div style="padding: 15px;">
       	<div class="layui-row" >
       		<div class="layui-col-md11">
       			<h1>课程管理（点击课程可查看学生）</h1>
       		</div>
       		<div class="layui-col-md1"></div>
       	</div>
       	<hr>
       	<div class="">
       		<table class="layui-table" style="table-layout:fixed;">
	           <colgroup>
	              <col width="5%">
	              <col width="15%">
	              <col width="15%">
	              <col width="15%">
	              <col width="10%">
	              <col width="15%">
	              <col width="25%">
	          </colgroup>
            <thead>
             <tr>
              <th>序号</th>
              <th>课程号</th>
              <th>课程名称</th>
              <th>任课老师</th>
              <th>课程状态</th>
              <th>课程时间</th>
              <th>操作</th>
             </tr> 
            </thead>
           <tbody>
  
             <tr v-for="(course, index) in allcourses" class="course" data-id="{{course.id}}" v-on:click="showStudents(course)">
              <td>{{index+1}}</td>
              <td>{{course.courseSequence}}</td>
              <td :title="course.courseName" style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">{{course.courseName}}</td>
              <td>{{course.name}}</td>
              <td>{{course.status}}</td>
              <td>{{course.date}}</td>
              <td>
                <div class="layui-btn-group">
                   <button :class="{'layui-btn-disabled':disableAuthorize(course.status)}" class="layui-btn  layui-btn-normal" data-id="{{course.id}}" v-on:click="authorize(course)"><i class="layui-icon">&#xe631;</i> 审核</button>
                   <button :class="{'layui-btn-disabled':disableCourseStatusOpen(course.status)}" class="layui-btn  layui-btn-warm" data-id="{{course.id}}" v-on:click="openCourse(course)"><i class="layui-icon">&#xe6b2;</i> 开课</button>
                   <button :class="{'layui-btn-disabled':disableCourseStatusClose(course.status)}" class="layui-btn  layui-btn-warm" data-id="{{course.id}}" v-on:click="closeCourse(course)"><i class="layui-icon">&#xe6b2;</i> 关课</button>
                </div>
              </td>
             </tr>
          </tbody>
          </table>
       	</div>
        <!-- 所有课程部分 -->
          <div class="layui-row" >
          <div class="layui-col-md11">
          <h1>课程成员</h1></div>
          <div class="layui-col-md1">
          </div>
        </div>
        <hr>
        <div class="">
			<div>
		    	<button class="layui-btn layui-btn-normal" v-on:click="openAllVps()">批量开机</button>
		    	<button class="layui-btn layui-btn-normal" v-on:click="restartAllVps()">批量重启</button>
		    	<button class="layui-btn layui-btn-normal" v-on:click="shutdownAllVps()">批量关机</button>
		   		<button class="layui-btn layui-btn-normal" v-on:click="destroyAllVps()">批量回收</button>
			</div>
          <table class="layui-table" style="table-layout:fixed;">
           <colgroup>
              <col width="3%">
              <col width="5%">
              <col width="8%">
              <col width="8%">
              <col width="8%">
              <col width="8%">
              <col width="8%">
              <col width="12%">
              <col width="10%">
              <col width="30%">
          </colgroup>
            <thead>
             <tr >
              <th><input type="checkbox" v-model="isAllCheck" v-on:click="checkAll()"></th>
              <th>序号</th>
              <th>学号</th>
              <th>姓名</th>
              <th>课程</th>
              <th>任课教师</th>
              <th>ip地址</th>
              <th>密码</th>
              <th>状态</th>
              <th>操作</th>
             </tr> 
            </thead>
           <tbody>
             <tr v-for="(student, index) in allstudents">
              <td><input type="checkbox" v-bind:value="student.vpsid" v-model="chosenVps"></td>
              <td>{{index+1}}</td>
              <td>{{student.uid}}</td>
              <td>{{student.stuName}}</td>
              <td :title="student.courseName" style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">{{student.courseName}}</td>
              <td>{{student.teaName}}</td>
              <td>{{student.ip}}</td>
              <td>
              		<button class="layui-btn layui-btn-sm" v-on:click="showPwd(student, $event)">查看密码</button>
              </td>
              <td>{{student.runningStat}}</td>
              <td>
                 <div class="layui-btn-group">
                      <button :class="{'layui-btn-disabled':student.vpsid == 0}" class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="refresh(student)">
                      <i class="layui-icon">&#x1002;</i> 刷新状态</button>
                      <button :class="{'layui-btn-disabled':student.vpsid == 0}" class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="resetPwd(student)">
                      <i class="layui-icon"> &#xe857;</i>重置密码</button>
                      <button :class="{'layui-btn-disabled':student.vpsid == 0}" class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="boot(student)">
                      <i class="layui-icon">&#xe6af;</i> 开机</button>
                      <button :class="{'layui-btn-disabled':student.vpsid == 0}" class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="shutdown(student)">
                      <i class="layui-icon">&#xe69c;</i> 关机</button>
                      <button :class="{'layui-btn-disabled':student.vpsid == 0}" class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="reboot(student)">
                      <i class="layui-icon">&#xe756;</i> 重启</button>
                      <button class="layui-btn layui-btn-sm " v-on:click=" ">
                       <i class="layui-icon">&#xe756;</i> VNC</button>
                </div> 
              </td>
             </tr>
          </tbody>
          </table>
        </div>
        <!-- end 所有课程 -->
       </div>
      </div>
      <!-- end 主体部分 -->
	</div>
	</body>

  <script>
//Demo

layui.use(['layer','table'], function(){
	//var layer = layui.form;
	var layer = layui.layer;
	
	var courseApp = new Vue({
		el:'#coursemanage',
		data:{
			allcourses:${AllCourse },
			allstudents:[],
			chosenVps:[],
			currentCourse:{},
			isAllCheck:[],
		},
		methods:{
			disableAuthorize:function(authStatus){
				if (authStatus != "未审核") {
					return true;
				} else {
					return false
				}
			},
			disableCourseStatusOpen:function(courseStatus){
				if (courseStatus != "课程关闭" && courseStatus != "审核通过") {
					return true;
				} else {
					return false;
				}
			},
			disableCourseStatusClose:function(courseStatus){
				if (courseStatus != "课程进行中") {
					return true;
				} else {
					return false;
				}
			},
			authorize:function(course) {
				if (course.status == "未审核") {
					$.post("/TxCourse/admin/authorizeCourse", {courseId:course.courseId}, function(data) {
						if (data.status == 0) {
							var courseDetail = data.result[0];
							var detailStr = "<div style='padding-left:20px'>创建时间：" + courseDetail.courseTime + "<br>" 
								+ "课程内容：<br>" + courseDetail.courseContent
								+ "</div>";
							layer.open({
						        type: 1
						        ,title: courseDetail.courseName //不显示标题栏
						        ,closeBtn: false
						        ,area: '600px;'
						        ,shade: 0.8
						        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
						        ,btn: ['通过', '取消']
						        ,btnAlign: 'c'
						        ,moveType: 1 //拖拽模式，0或者1
						        ,content: detailStr
						        ,yes: function(index, layero){
						            $.post("/TxCourse/admin/passCourse", {courseId:course.courseId}, function(passResult){
						            	if (passResult.status == 0) {
						            		layer.msg(passResult.result, {icon: 6});
						            	} else {
						            		layer.msg(passResult.result, {icon: 5});
						            	}
						            	layer.close(index);
						            	location.reload();
						            });
						        }
							});
						} else {
							layer.msg(data.result, {icon: 5});
						}
					});
				}
			},
			showStudents:function(course) {
				this.isAllCheck = [];
				this.chosenVps = [];
				this.currentCourse = course;
				$.post("/TxCourse/admin/getAllStudentVps", {id:course.courseId}, function(data) {
					if (data.status == 0) {
						courseApp.allstudents = data.result;
						window.parent.iframeLoad();
					} else {
						courseApp.allstudents = [];
					}
				});
			},
			openCourse:function(course){
				if (course.status == "审核通过" || course.status == "课程关闭") {
					var detailStr = "<div style='padding:20px;'>您确定要开启" + course.courseName +  "课程吗？</div>";
					layer.open({
				        type: 1
				        ,title: "开课确认" //不显示标题栏
				        ,closeBtn: false
				        ,area: '300px;'
				        ,shade: 0.8
				        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
				        ,btn: ['确认', '取消']
				        ,btnAlign: 'c'
				        ,moveType: 1 //拖拽模式，0或者1
				        ,content: detailStr
				        ,yes: function(index, layero){
				        	$.post("/TxCourse/admin/openCourse", {courseId:course.courseId}, function(data) {
								if (data.status == 0) {
									layer.msg(data.result, {icon: 6});
									layer.close(index);
					            	location.reload();
								} else {
									layer.msg(data.result, {icon: 5});
								}
							});
				        }
					});
				}
				
			},
			closeCourse:function(course) {
				if (course.status == "课程进行中") {
					var detailStr = "<div style='padding:20px;'>您确定要关闭" + course.courseName +  "课程吗？</div>";
					layer.open({
				        type: 1
				        ,title: "关课确认" //不显示标题栏
				        ,closeBtn: false
				        ,area: '300px;'
				        ,shade: 0.8
				        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
				        ,btn: ['确认', '取消']
				        ,btnAlign: 'c'
				        ,moveType: 1 //拖拽模式，0或者1
				        ,content: detailStr
				        ,yes: function(index, layero){
				        	$.ajax({
				        		url:'/TxCourse/admin/closeCourse',
				        		dataType:'JSON',
				        		data:{courseId:course.courseId},
				        		type:'POST',
				        		beforeSend:function(){
				        			//layer.load(1);
				        		},
				        		success:function(data, status) {
				        			//layer.closeAll('loading');
				        			if (data.status == 0) {
										layer.msg(data.result, {icon: 6});
										layer.close(index);
						            	//location.reload();
										setTimeout(function () { location.reload(); }, 500);
									} else {
										layer.msg(data.result, {icon: 5});
									}
				        		},
				        		error:function(data) {
				        			console.log("error");
				        		}
				        	});
				        }
					});
				}
			},
			refresh:function(student) {
				$.ajax({
	        		url:'/TxCourse/admin/refreshExperimentStatus',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
							$.post("/TxCourse/admin/getAllStudentVps", {id:courseApp.currentCourse.courseId}, function(stuData) {
								if (data.status == 0) {
									console.log("reloading");
									layer.closeAll('loading');
									layer.msg(data.result, {icon: 6});
									courseApp.allstudents = stuData.result;
									window.parent.iframeLoad();
								} else {
									layer.closeAll('loading');
									layer.msg(data.result, {icon: 5});
								}
							});
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
	        		url:'/TxCourse/admin/resetPwd',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {console.log("0");
							$.post("/TxCourse/admin/resetPwd", {vpsid:student.vpsid}, function(stuData) {
								console.log("resetPwd-0");
							});
						} else if (data.status == -1) {console.log("-1");
							layer.closeAll('loading');
							layer.msg(data.result, {icon: 5});
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
						        		url:'/TxCourse/admin/resetPwd',
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
						        				$.post("/TxCourse/admin/resetPwd", {vpsid:student.vpsid,newPassword:newpwd}, function(resetData) {
						        					layer.closeAll('loading');
						        					layer.close(index);
						        					layer.msg(resetData.result, {icon: 6});
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
	        		url:'/TxCourse/admin/bootExperimentVp',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
							$.post("/TxCourse/admin/getAllStudentVps", {id:courseApp.currentCourse.courseId}, function(stuData) {
								if (data.status == 0) {
									console.log("reloading");
									layer.closeAll('loading');
									layer.msg(data.result, {icon: 6});
									courseApp.allstudents = stuData.result;
									window.parent.iframeLoad();
								} else {
									layer.closeAll('loading');
									layer.msg(data.result, {icon: 5});
								}
							});
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
	        		url:'/TxCourse/admin/shutdownVps',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
							$.post("/TxCourse/admin/getAllStudentVps", {id:courseApp.currentCourse.courseId}, function(stuData) {
								if (data.status == 0) {
									console.log("reloading");
									layer.closeAll('loading');
									layer.msg(data.result, {icon: 6});
									courseApp.allstudents = stuData.result;
									window.parent.iframeLoad();
								} else {
									layer.closeAll('loading');
									layer.msg(data.result, {icon: 5});
								}
							});
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
	        		url:'/TxCourse/admin/restartVps',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
							$.post("/TxCourse/admin/getAllStudentVps", {id:courseApp.currentCourse.courseId}, function(stuData) {
								if (data.status == 0) {
									console.log("reloading");
									layer.closeAll('loading');
									layer.msg(data.result, {icon: 6});
									courseApp.allstudents = stuData.result;
									window.parent.iframeLoad();
								} else {
									layer.closeAll('loading');
									layer.msg(data.result, {icon: 5});
								}
							});
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
            checkAll:function() {
            	if (this.isAllCheck.length == 1) {
            		this.chosenVps = [];
            		for (var i = 0; i < this.allstudents.length; i++) {
            			this.chosenVps[i] = this.allstudents[i].vpsid;
            		}
            	} else {
            		this.chosenVps = [];
            	}
            },
            openAllVps:function(){
            	console.log(this.chosenVps);
            	if (this.chosenVps.length >0) {
            		console.log(courseApp.chosenVps.toString());
            		var detailStr = "<div style='padding:20px;'>您确定要开启" + this.chosenVps.length +  "台机器吗？</div>";
            		layer.open({
				        type: 1
				        ,title: "开机确认" //不显示标题栏
				        ,closeBtn: false
				        ,area: '300px;'
				        ,shade: 0.8
				        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
				        ,btn: ['确认', '取消']
				        ,btnAlign: 'c'
				        ,moveType: 1 //拖拽模式，0或者1
				        ,content: detailStr
				        ,yes: function(index, layero){
				        	$.ajax({
				        		url:'/TxCourse/admin/openAllVps',
				        		dataType:'JSON',//courseApp.chosenVps
				        		data: {Vpsid:courseApp.chosenVps.toString()},
				        		type:'POST',
				        		beforeSend:function(){
				        			layer.load(1);
				        		},
				        		success:function(data, status) {
				        			layer.closeAll('loading');
				        			if (data.status == 0) {
										layer.msg(data.result, {icon: 6});
										layer.close(index);
						            	//location.reload();
										setTimeout(function () { location.reload(); }, 500);
									} else {
										layer.msg(data.result, {icon: 5});
									}
				        		},
				        		error:function(data) {
				        			console.log("error");
				        		}
				        	});
				        	
				        }
            	});
            	}else {
					layer.msg("未选择任何云机", {icon: 5});
				}
            },
            shutdownAllVps:function(){
            	console.log(this.chosenVps);
            	if (this.chosenVps.length >0) {
            		console.log(courseApp.chosenVps.toString());
            		var detailStr = "<div style='padding:20px;'>您确定要关闭" + this.chosenVps.length +  "台机器吗？</div>";
            		layer.open({
				        type: 1
				        ,title: "关机确认" //不显示标题栏
				        ,closeBtn: false
				        ,area: '300px;'
				        ,shade: 0.8
				        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
				        ,btn: ['确认', '取消']
				        ,btnAlign: 'c'
				        ,moveType: 1 //拖拽模式，0或者1
				        ,content: detailStr
				        ,yes: function(index, layero){
				        	$.ajax({
				        		url:'/TxCourse/admin/shutdownAllVps',
				        		dataType:'JSON',//courseApp.chosenVps
				        		data: {Vpsid:courseApp.chosenVps.toString()},
				        		type:'POST',
				        		beforeSend:function(){
				        			layer.load(1);
				        		},
				        		success:function(data, status) {
				        			layer.closeAll('loading');
				        			if (data.status == 0) {
										layer.msg(data.result, {icon: 6});
										layer.close(index);
						            	//location.reload();
										setTimeout(function () { location.reload(); },500);
									} else {
										layer.msg(data.result, {icon: 5});
									}
				        		},
				        		error:function(data) {
				        			console.log("error");
				        		}
				        	});
				        	
				        }
            	});
            	}else {
					layer.msg("未选择任何云机", {icon: 5});
				}
            },
            destroyAllVps:function(){
            	console.log(this.chosenVps);
            	if (this.chosenVps.length >0) {
            		console.log(courseApp.chosenVps.toString());
            		var detailStr = "<div style='padding:20px;'>您确定要回收" + this.chosenVps.length +  "台机器吗？</div>";
            		layer.open({
				        type: 1
				        ,title: "回收确认" //不显示标题栏
				        ,closeBtn: false
				        ,area: '300px;'
				        ,shade: 0.8
				        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
				        ,btn: ['确认', '取消']
				        ,btnAlign: 'c'
				        ,moveType: 1 //拖拽模式，0或者1
				        ,content: detailStr
				        ,yes: function(index, layero){
				        	$.ajax({
				        		url:'/TxCourse/admin/destroyAllVps',
				        		dataType:'JSON',//courseApp.chosenVps
				        		data: {Vpsid:courseApp.chosenVps.toString()},
				        		type:'POST',
				        		beforeSend:function(){
				        			layer.load(1);
				        		},
				        		success:function(data, status) {
				        			layer.closeAll('loading');
				        			if (data.status == 0) {
										layer.msg(data.result, {icon: 6});
										layer.close(index);
						            	//location.reload();
										setTimeout(function () { location.reload(); }, 500);
									} else {
										layer.msg(data.result, {icon: 5});
									}
				        		},
				        		error:function(data) {
				        			console.log("error");
				        		}
				        	});
				        	
				        }
            	});
            	}else {
					layer.msg("未选择任何云机", {icon: 5});
				}
            },
            restartAllVps:function(){
            	console.log(this.chosenVps);
            	if (this.chosenVps.length >0) {
            		console.log(courseApp.chosenVps.toString());
            		var detailStr = "<div style='padding:20px;'>您确定要重启" + this.chosenVps.length +  "台机器吗？</div>";
            		layer.open({
				        type: 1
				        ,title: "重启确认" //不显示标题栏
				        ,closeBtn: false
				        ,area: '300px;'
				        ,shade: 0.8
				        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
				        ,btn: ['确认', '取消']
				        ,btnAlign: 'c'
				        ,moveType: 1 //拖拽模式，0或者1
				        ,content: detailStr
				        ,yes: function(index, layero){
				        	$.ajax({
				        		url:'/TxCourse/admin/restartAllVps',
				        		dataType:'JSON',//courseApp.chosenVps
				        		data: {Vpsid:courseApp.chosenVps.toString()},
				        		type:'POST',
				        		beforeSend:function(){
				        			layer.load(1);
				        		},
				        		success:function(data, status) {
				        			layer.closeAll('loading');
				        			if (data.status == 0) {
										layer.msg(data.result, {icon: 6});
										layer.close(index);
						            	//location.reload();
										setTimeout(function () { location.reload(); }, 500);
									} else {
										layer.msg(data.result, {icon: 5});
									}
				        		},
				        		error:function(data) {
				        			console.log("error");
				        		}
				        	});
				        	
				        }
            		});
            	}else {
					layer.msg("未选择任何云机", {icon: 5});
				}
            },
            showPwd:function(student, event) {
            	var curNode = event.currentTarget;
            	var parentNode = $(curNode).parent();
            	parentNode.empty();
            	if (typeof(student.dpass)=="undefined" ) {
            		parentNode.append("无密码");
            	} else {
            		parentNode.append(student.dpass);
            	}
            },
		}
	});

});
</script>
</html>