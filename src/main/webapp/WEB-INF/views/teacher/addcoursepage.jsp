<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page import="com.alibaba.fastjson.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>新建课程</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!--  <link rel="stylesheet" href="/txcourse/layui/css/layui.css"> -->
    <style>
		       [v-cloak] {
                 display: none;
               }
		  </style>
    <link rel="stylesheet" type="text/css" href="/TxCourse/layui/css/layui.css">
    <!-- <link rel="stylesheet" type="text/css" href="/TxCourse/layui/css/global.css"> -->
    <script type="text/javascript" src="/TxCourse/layui/layui.js"></script>
    <script type="text/javascript" src="/TxCourse/js/vue.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.2.1.js"></script>
</head>
<body class="">
<div class="">
    
    <!-- 主体部分 -->
    <div id="newCourse" v-cloak class="" style="left:0px;">
        <div style="padding: 15px;">
            <div class="layui-row">
                <div class="layui-col-md11">
                    <h1>新建课程</h1>
                </div>
                <div class="layui-col-md1">
                    <a class="layui-btn" href="/TxCourse/teacherCourse/toteacherCoursesPage">返回</a>
                </div>
                <hr>
                <!-- 所有课程部分 -->
                <div class="site-text site-block">
                    <!-- 表单部分 -->
                    <div class="layui-form" style="margin-right: 10px; ">
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">课程名称：</label>
                                <div class="layui-input-inline" style="width: 100px;">
                                    <input type="text" v-model="upload.courseName" :class="'layui-input'">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">课程号：</label>
                                <div class="layui-input-inline" style="width: 100px;">
                                    <input type="text" v-model="courseId" :class="'layui-input'">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">教师号：</label>
                                <div class="layui-input-inline" style="width: 100px;">
                                    <input type="text" v-model="teacherId" :class="'layui-input'">
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label">上课时间：</label>
                                <div class="layui-input-inline" style="width: 100px;">
                                    <input id="courseTimeStart" class="layui-input" type="text" lay-verify="courseTimeStart"  :class="'layui-input'">
                                   	<!-- <button  lay-verify="courseTimeStart" class="layui-btn"><i class="layui-icon">&#xe61a;</i> </button> -->
                                </div>
                                <div class="layui-form-mid">-</div>
                                <div class="layui-input-inline" style="width: 100px;">
                                    <input id="courseTimeEnd" class="layui-input" type="text" lay-verify="courseTimeEnd" :class="'layui-input'">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                        	<div class="layui-inline">
                        		<label class="layui-form-label">学生名单：</label>
	                            <a style="color:white;" class="layui-btn" href="/TxCourse/upload/stu.xls">
	                                <i class="layui-icon">&#xe641;</i>下载模板
	                            </a>
	                            <button class="layui-btn" id="upload" type="button" v-on:click="myUploadFile()">
	                                <i class="layui-icon">&#xe67c;</i>上传名单
	                            </button>
                        	</div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="padding-left: 0px;margin-left:0px; width: 100px;">最大学生人数：</label>
                                <div class="layui-input-inline"  style="width: 100px;">
                               		<input type="number" v-model="upload.maxStudentNum" :class="'layui-input'">
                                </div>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-inline">
                                <label class="layui-form-label">计算资源：</label>
                                <div class="layui-input-block">
                                    <input type="radio" title="VPS模板" checked>
                                    <input type="radio" title="容器模板" disable>
                                </div>
                            </div>
                            <div :class="'layui-inline'">
                            	<label class="layui-form-label">系统名称：</label>
                                <div :class="'layui-input-block'">
                                    <select lay-filter="test">
                                    	<%
                                    		String str = (String) request.getAttribute("templates");
                                    		JSONArray ja = JSONArray.parseArray(str);
                                    		for (int i = 0; i < ja.size(); i++) {
                                    			JSONObject jo = ja.getJSONObject(i);
                                    			out.print("<option value='" + jo.getString("oid")  + "'>" + jo.getString("oname") + "</option>");
                                    		}
                                    	%>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-inline">
                                <label class="layui-form-label" style="padding-left: 0px;margin-left:0px; width: 150px;">预设课程积分(分/人)：</label>
	                            <div class="layui-input-inline" style="width: 100px;">
	                                <input type="number" placeholder="0" v-model="upload.courseScore" :class="'layui-input'">
	                            </div>
	                            <div class="layui-form-mid layui-word-aux">预设积分测算公式：</div>
                            </div>
                        </div>
                        <div class="layui-form-item layui-form-text">
                            <label class="layui-form-label">课程要求：
                            </label>

                            <div class="layui-input-block" style="z-index:0">
                                <script id="container_edi" name="content" class="editor_con_rw" type="text/plain" style="width:100%; min-height:200px;"></script>
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button @click="creatNewVps" type="button" class="layui-btn">立即提交</button>
                                <button @click="emptyInput" type="reset" class="layui-btn layui-btn-primary">重置</button>
                            </div>
                        </div>
                    </div>
                    <!-- end 表单部分 -->
                </div>
            </div>
        </div>
        <!-- end 主体部分 -->
    </div>
</div>
</body>
<script type="text/javascript" charset="utf-8" src="/TxCourse/ueditor/ueditor.config.js?id=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" charset="utf-8" src="/TxCourse/ueditor/ueditor.all.js?id=<%=System.currentTimeMillis()%>"> </script>
<script>

    layui.use(['upload', 'layer','laydate', 'form'], function(){
    	
    	//ueditor
    	var ue = UE.getEditor('container_edi');
        ue.ready(function() {
            ue.setContent('请输入课程要求：');
            ue.setHeight('200');
        });
        var $ = layui.jquery;
        var layer = layui.layer;
   		var form = layui.form;
   		//laydate
   		var laydate = layui.laydate;
   		
   		form.on('select(test)', function(data){
   			app.upload.templateId = data.value;
   		});
   		
   		
        var app = new Vue({
            el: '#newCourse',
            data: {
                vpses: [],
                courseId: '',
                teacherId: '',
                courseTimeStart: '',
                courseTimeEnd: '',
                templates: [],
                checkedTemplate: '',
                upload: {
                    courseName: '',
                    courseSequence: '',
                    courseTime: '',
                    courseContent: '',
                    resourceType: 0,
                    templateId: 0,
                    courseScore: 0,
                    maxStudentNum:0
                },
                uploadFile:{},
            },
            mounted:function() {
                
              		
              	
                //常规用法
                //render 开始时间
                
                laydate.render({
                    elem: '#courseTimeStart'
                   	,trigger: 'click'
                   	,done: function(value, date, endDate){
                   		app.courseTimeStart = value;
                   	    //console.log(value); //得到日期生成的值，如：2017-08-18
                   	    //console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                   	    //console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
               		}
                });
                
                //render 结束时间
                laydate.render({
                    elem: '#courseTimeEnd'
                   	,done: function(value, date, endDate){
                   		app.courseTimeEnd = value;
                   	    //console.log(value); //得到日期生成的值，如：2017-08-18
                   	    //console.log(date); //得到日期时间对象：{year: 2017, month: 8, date: 18, hours: 0, minutes: 0, seconds: 0}
                   	    //console.log(endDate); //得结束的日期时间对象，开启范围选择（range: true）才会返回。对象成员同上。
                	}
                });
                
                layui.use('form', function(){
                    var form = layui.form;
                    form.render();
                });
            },
            methods: {
                creatNewVps: function () {
                    var editor = UE.getEditor('container_edi');
                    app.upload.courseSequence = app.courseId + '-' + app.teacherId;
                    app.upload.courseTime = app.courseTimeStart + "~" + app.courseTimeEnd;
                    app.upload.courseContent = editor.getContent();
                    $.post("/TxCourse/teacherCourse/addCoursePage", app.upload, function (result) {
                        if (0 == result.status) {
                            layer.msg(result.result, {icon: 6});
                            window.location.href='/TxCourse/teacherCourse/toteacherCoursesPage';
                        } else {
                            layer.msg(result.result, {icon: 5});
                        }
                    })
                },
                emptyInput:function () {
                    this.upload = [];
                },
                myUploadFile:function() {
                	this.uploadFile = $("<input type='file'>");
                	this.uploadFile.click();
                	this.uploadFile.change(function(){
                		var formData = new FormData();
                		formData.append('multipartFile', app.uploadFile[0].files[0]);
                		$.ajax({
                			url:"/TxCourse/teacherCourse/uploadStuExcel",
                			type: 'POST',
            			    cache: false,
            			    data: formData,
            			    processData: false,
            			    contentType: false,
            			    beforeSend:function(){
            			    	layer.load(1);
            			    },
            				success:function(data) {
            					layer.closeAll('loading');
        	  	    			if(data.status==0){
        	  	    				layer.msg(data.result, {icon: 6});
        	  	    			}else{
        	  	    				layer.msg(data.result, {icon: 5});
        	  	    			}
            				},
            				error:function(data) {
            					layer.closeAll('loading');
        	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
            				}
                		});
                	});
                }

            }
        });
    });

</script>
</html>