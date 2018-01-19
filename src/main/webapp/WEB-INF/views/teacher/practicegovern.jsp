<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>实验管理</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!--  <link rel="stylesheet" href="layui/css/layui.css"> -->
    <style>
		       [v-cloak] {
                 display: none;
               }
		  </style>
    <link rel="stylesheet" type="text/css" href="/TxCourse/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="/TxCourse/layui/css/global.css">
    <script type="text/javascript" src="/TxCourse/layui/layui.js"></script>
    <script src="/TxCourse/js/vue.min.js"></script>
</head>
<body class="">
<div class="layui-layout">
    
    <!-- 主体部分 -->
    <div id="exManage" v-cloak class="" style="left:0px;">
        <div style="padding: 15px;">
            <div class="layui-row">
                <div class="layui-col-md11">
                    <h1>实验管理</h1></div>
                <div class="layui-col-md1">
                    <a href="/TxCourse/home?uid=10004660" class="layui-btn" style="margin-bottom: 5px;">返回</a>
                </div>
                <hr>
                <!-- 所有课程部分 -->
                <table class="layui-hide" id="exManageTable"></table>
                <div>
                    <div>
                        <button class="layui-btn layui-btn-normal" v-on:click="openAllVps()">批量开机</button>
                        <button class="layui-btn layui-btn-normal" v-on:click="restartAllVps()">批量重启</button>
                        <button class="layui-btn layui-btn-normal" v-on:click="shutdownAllVps()">批量关机</button>
                    </div>
                    <table class="layui-table" style="table-layout:fixed;">
                        <colgroup>
                            <col width="3%">
                            <col width="5%">
                            <col width="7%">
                            <col width="6%">
                            <col width="8%">
                            <col width="8%">
                            <col width="8%">
                            <col width="7%">
                            <col width="12%">
                            <col width="6%">
                            <col width="30%">
                        </colgroup>
                        <thead>
                        <tr>
                            <th><input type="checkbox" v-on:click="checkAll()"></th>
                            <th>序号</th>
                            <th>学号</th>
                            <th>姓名</th>
                            <th>IP地址</th>
                            <th>密码</th>
                            <th>状态</th>
                            <th>云机状态</th>
                            <th>实验报告</th>
                            <th>成绩</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
	                        <tr v-for="(vps, index) in vpses">
	                            <td><input type="checkbox" v-bind:value="vps.vpsid" v-model="chosenVps"></td>
	                            <td>{{index + 1}}</td>
	                            <td>{{vps.snum}}</td>
	                            <td>{{vps.sname}}</td>
	                            <td>{{vps.ip}}</td>
	                            <td>
	                            	<button class="layui-btn layui-btn-sm" v-on:click="showPwd(vps, $event)">查看密码</button>
	                            </td>
	                            <td>{{vps.experimentStatus}}</td>
	                           	<td>{{vps.runningstat}}</td>
	                            <td>
	                            	<div v-if="vps.reportStatus == '未上传'" 
	                            		style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">
	                            			{{vps.reportStatus}}
                           			</div>
                           			<a v-if="vps.reportStatus != '未上传'"
                           				v-bind:href="vps.reportUrl"
                           				style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;display:block;">
                           					{{getReportName(vps.reportUrl)}}
                         			</a>
	                            </td>
	                            <td>{{vps.score}}</td>
	                            <td>
	                                <div class="layui-btn-group">
	                                    <button class="layui-btn layui-btn-sm layui-btn-danger" v-on:click="checkscore(vps)"><i class="layui-icon">&#xe735;</i>打分
                                        </button>
	                                    <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="refresh(vps)">
	                                        <i class="layui-icon">&#x1002;</i> 刷新状态
	                                    </button>
	                                    <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="resetPwd(vps)">
	                                        <i class="layui-icon"> &#xe857;</i>重置密码
	                                    </button>
	                                    <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="boot(vps)">
	                                        <i class="layui-icon">&#xe6af;</i> 开机
	                                    </button>
	                                    <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="shutdown(vps)">
	                                        <i class="layui-icon">&#xe69c;</i> 关机
	                                    </button>
	                                    <button class="layui-btn layui-btn-sm layui-btn-normal" v-on:click="reboot(vps)">
	                                        <i class="layui-icon">&#xe756;</i> 重启
	                                    </button>
	                                    <button class="layui-btn layui-btn-sm " v-on:click="">
                                        <i class="layui-icon">&#xe756;</i> VNC</button>
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
</div>
</body>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script>

layui.use(['layer','table'], function(){
	var layer = layui.layer;
	
    var app = new Vue({
        el: '#exManage',
        data: {
            vpses: [],
            chosenVps:[],
            isAllCheck:0,
        },
        created:function () {
            this.getCourseDetail();
        },
        methods: {
        	checkAll:function() {
        		if (this.isAllCheck == 0) {
        			this.isAllCheck = 1;
        			this.chosenVps = [];
        			for (var i = 0; i < this.vpses.length; i++) {
            			this.chosenVps[i] = this.vpses[i].vpsid;
            		}
        		} else {
        			this.isAllCheck = 0;
        			this.chosenVps = [];
        		}
            },
            openAllVps:function(){
            	if (this.chosenVps.length >0) {
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
				        		url:'/TxCourse/teacherCourse/openAllVps',
				        		dataType:'JSON',//courseApp.chosenVps
				        		data: {vpsids:app.chosenVps},
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
            	} else {
            		layer.msg("未选择任何云主机", {icon: 5});
            	}
            },
            shutdownAllVps:function(){
            	if (this.chosenVps.length >0) {
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
				        		url:'/TxCourse/teacherCourse/shutdownAllVps',
				        		dataType:'JSON',//courseApp.chosenVps
				        		data: {vpsids:app.chosenVps},
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
            	} else {
            		layer.msg("未选择任何云主机", {icon: 5});
            	}
            },
            restartAllVps:function(){
            	console.log(this.chosenVps);
            	if (this.chosenVps.length >0) {
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
				        		url:'/TxCourse/teacherCourse/restartAllVps',
				        		dataType:'JSON',//courseApp.chosenVps
				        		data: {vpsids:app.chosenVps},
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
            	} else {
            		layer.msg("未选择任何云主机", {icon: 5});
            	}
            },
            getCourseDetail: function () {
                $.post("/TxCourse/teacherCourse/getpracticeGovern",{ cid: "${cid}" }, function (result) {
                	if (result.status == 0) {
                		app.vpses = result.result;
                		//window.parent.iframeLoad();
                	} else {
                		app.vpses = [];
                		layer.msg(result.result, {icon: 5});
                	}
                });
            },
            getReportName:function(reportUrl) {
            	var pattern = /[^\/\\]+$/;
            	return reportUrl.match(pattern)[0];
            },
            checkscore:function(student){
				  layer.prompt({title: '给 '+student.sname+" 打分：", formType: 0}, function(text, index){
					  layer.close(index);
					  $.post("/TxCourse/teacherCourse/checkscore",{id:student.id,score:text},function(result,status){
					  		if(result.status == 0){
					  			 layer.msg(result.result, {icon: 6});
					  		}else{
								 layer.alert(result.result, {icon: 5});
					  		}
					  	}); 
					});  
			  },
            refresh:function(student) {
				$.ajax({
	        		url:'/TxCourse/teacherCourse/refreshExperimentStatus',
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
	        				app.getCourseDetail();
	        			// window.parent.iframeLoad();
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
	        		url:'/TxCourse/teacherCourse/resetPwd',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {console.log("0");
							$.post("/TxCourse/teacherCourse/resetPwd", {vpsid:student.vpsid}, function(stuData) {
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
						        		url:'/TxCourse/teacherCourse/resetPwd',
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
						        				$.post("/TxCourse/teacherCourse/resetPwd", {vpsid:student.vpsid,newPassword:newpwd}, function(resetData) {
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
	        		url:'/TxCourse/teacherCourse/openVps',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
	        				app.getCourseDetail();
	        				layer.closeAll('loading');
							layer.msg(data.result, {icon: 6});
							// window.parent.iframeLoad();
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
	        		url:'/TxCourse/teacherCourse/shutdownVps',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
	        				app.getCourseDetail();
	        				layer.closeAll('loading');
							layer.msg(data.result, {icon: 6});
							// window.parent.iframeLoad();
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
	        		url:'/TxCourse/teacherCourse/restartVps',
	        		dataType:'JSON',
	        		data:{vpsid:student.vpsid},
	        		type:'POST',
	        		beforeSend:function(){
	        			layer.load(1);
	        		},
	        		success:function(data, status) {
	        			if (data.status == 0) {
	        				app.getCourseDetail();
	        				layer.closeAll('loading');
							layer.msg(data.result, {icon: 6});
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
            	console.log(document.getElementById("123"));
            	var curNode = event.currentTarget;
            	var parentNode = $(curNode).parent();
            	parentNode.empty();
            	if (typeof(vps.password)=="undefined" ) {
            		parentNode.append("无密码");
            	} else {
            		parentNode.append(vps.password);
            	}
            },
        }
    });
});
</script>
</html>