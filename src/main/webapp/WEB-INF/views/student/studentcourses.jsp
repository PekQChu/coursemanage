<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="com.shu.model.*" %>
<%@ page import="com.txcourse.DAO.*" %>
<%@ page import="com.txcourse.DAOImpl.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
         <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		 <title>课程管理</title>
		 <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	     <!--  <link rel="stylesheet" href="layui/css/layui.css"> -->
		 <link rel="stylesheet" type="text/css" href="/TxCourse/layui/css/layui.css">
		 <script type="text/javascript" src="/TxCourse/layui/layui.js"></script>
		 <script type="text/javascript" src="/TxCourse/js/jquery.min.js"></script>
		  <script src="https://unpkg.com/vue"></script>
		  <style>
		       [v-cloak] {
                 display: none;
               }
		  </style>
		 <!-- <script type="text/javascript" src="/TxCourse/js/vue.min.js"></script>  -->
</head>
<body class="">
 <div class="">
      <!-- 主体部分 -->
      
       <div class="" id="mycourses" v-cloak  style="left:0px;">
       <div style="padding: 15px;">
       	<div class="layui-row" >
       		<div class="layui-col-md11">
       		<h1>我的课程</h1></div>
       		<div class="layui-col-md1">
       		<a href="/TxCourse/studentCourse/toMyMessage" style="padding-left: 10px;"><i class="layui-icon">&#xe611;</i> 我的信息</a>
       			<%
                  	MessageDAO mDao = new MessageDAOImpl();
                  	User user = (User)session.getAttribute(User.CUR_USER);
                	int msgNum = mDao.getNewMsgNum(user.getId());
            	%>
            	<span v-if="<%=msgNum %> != 0" class="layui-badge"><%=msgNum %></span>
       		</div>
       	</div>
       	<hr>
       	<div class="">
       		<table class="layui-table">
           <colgroup>
               <col width="5%">
              <col width="10%">
              <col width="15%">
              <col width="10%">
              <col width="10%">
              <col width="10%">
              <col width="10%">
              <col width="30%">
          </colgroup>
            <thead>
             <tr >
              <th>序号</th>
              <th>课程号</th>
              <th>课程名称</th>
              <th>任课教师</th>
              <th>加入状态</th>
              <th>课程状态</th>
              <th>分数</th>
              <th>操作</th>
             </tr> 
            </thead>
           <tbody>
             <tr v-for="(course ,index) in mycourse">
              <td>{{index+1}}</td>
              <td>{{course.courseSequence}}</td>
              <td :title="course.courseName" style="overflow:hidden;white-space:nowrap;text-overflow:ellipsis;">{{course.courseName}}</td>
              <td>{{course.teacherName}}</td>
              <td>{{(course.chooseStatus==0)?'未加入':'已加入'}}</td>
              <td>{{(course.courseStatus<=2)?'未开课':'已开课'}}</td>
              <td>{{course.score}}</td>
              <td>
                <div class="layui-btn-group">
                   <a :href="'/TxCourse/studentCourse/toCourseDetial?id='+course.courseId" class="layui-btn  layui-btn-normal"><i class="layui-icon">&#xe631;</i> 实验</a>
                   <button class="layui-btn  layui-btn-warm" v-on:click="sendMsg(course)"><i class="layui-icon">&#xe6b2;</i> 留言</button>
                </div>
              </td>
             </tr>
          </tbody>
          </table>
       	</div>
        <!-- 所有课程部分 -->
          <div class="layui-row" >
          <div class="layui-col-md11">
          <h1>所有课程</h1></div>
          <div class="layui-col-md1">
          </div>
        </div>
        <hr>
        <div class="">
          <form class="layui-form" >
          <div class="layui-form-item">
            <div class="layui-inline">
              <label class="layui-form-label">搜索条件</label>
              <div class="layui-input-inline">
                <select lay-filter="selectCondition">
                	<option value=0>请选择条件</option>
                  <option value=1>课程名</option>
                  <option value=2>任课教师</option>
                </select>
              </div>
              <div class="layui-input-inline">
                 <input type="text" name="sql" v-model="searchCondition" class="layui-input">
              </div>
              <div class="layui-input-inline">
                   <button class="layui-btn" type="button" v-on:click="getAllCourse()"> <i class="layui-icon">&#xe615;</i> 搜索</button>
              </div>
            </div>
          </div>
          </form>    
          <table class="layui-table" > 
           <colgroup>
              <col width="5%">
              <col width="10%">
              <col width="15%">
              <col width="20%">
              <col width="15%">
              <col width="35%">
          </colgroup>
            <thead>
             <tr>
             <th>序号</th>
              <th>课程号</th>
              <th>课程名称</th>
              <th>任课教师</th>
              <th>课程状态</th>
              <th>操作</th>
             </tr> 
            </thead>
           <tbody>
             <tr v-for="(course, index) in allcourse">
              <td>{{index+1}}</td>
              <td>{{course.courseSequence}}</td>
              <td>{{course.courseName}}</td>
              <td>{{course.teacherName}}</td>
              <td>{{(course.courseStatus<=2)?'未开课':'已开课'}}</td> <!--  -->
              <td>
                <div class="layui-btn-group">
                   <button class="layui-btn  layui-btn-normal join" v-on:click="joinCourse(course)"><i class="layui-icon">&#xe600;</i> 加入</button>
                   <button class="layui-btn  layui-btn-default join" v-on:click="seeCourseDetail(course)"><i class="layui-icon">&#xe705;</i> 查看</button>
                </div>
              </td>
             </tr>
          </tbody>
          </table>
        </div>
			<!-- end 所有课程 -->
			</div>
			<!-- layui弹出层 -->
	       	<div id="courseDetail" hidden="hidden" style="margin: 20px;max-height:400px;">
	       		<strong>上课教师：</strong>{{currentCourse.teacherName}}<br><br>
	       		<strong>课程要求：</strong><br><br>
	       		<div v-html="currentCourse.courseContent" style="max-width: 100%;word-wrap:break-word"></div>
	       	</div>
		</div>
		<!-- end 主体部分 -->
	</div>
	       	
</body>
  
<script>
//Demo

layui.use(['form','layer'], function(){
	var form = layui.form;
	var layer = layui.layer;
	var $ = layui.jquery;

  
	//vue
	var app=new Vue({
  		el:'#mycourses',
	  	data:{
	  		mycourse:[],   //我的课程
	  		allcourse:[],   //所有课程
	  		searchType:0,
	  		searchCondition:'',
	  		currentCourse:{},
	  	},
	  	created:function() {
	  		this.getMyCourse();
	  		this.getAllCourse();
	  	},
	  	mounted:function() {
	  		form.render();
	  	},
	  	methods:{
	  	    getMyCourse: function () {
	  	    	$.ajax({
	  	    		type:'POST',
	  	    		url:'/TxCourse/studentCourse/getStudentCourses',
	  	    		data:{},
	  	    		dataType:'JSON',
	  	    		beforeSend:function() {
	  	    			layer.load(1);
	  	    		},
	  	    		success:function(result){
	  	    			layer.closeAll('loading');
	  	    			if(result.status==0){
	  	    				app.mycourse = result.result;
	  	    				window.parent.iframeLoad();
	  	    			}else{
	  	    				layer.msg(result.result, {icon: 6});
	  	    			}
	  	    		},
	  	    		error:function(data){
	  	    			layer.closeAll('loading');
	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	  	    		}
	  	    	})
			},
			getAllCourse: function () {
				$.ajax({
	  	    		type:'POST',
	  	    		url:'/TxCourse/studentCourse/getAllCourses',
	  	    		data:{
	  	    			type:this.searchType,
	  	    			sql:this.searchCondition
  	    			},
	  	    		dataType:'JSON',
	  	    		beforeSend:function() {
	  	    			layer.load(1);
	  	    		},
	  	    		success:function(result){
	  	    			layer.closeAll('loading');
	  	    			if(result.status==0){
	  	    				app.allcourse = result.result;
	  	    				window.parent.iframeLoad();
	  	    			}else{
	  	    				layer.msg(result.result, {icon: 6});
	  	    			}
	  	    		},
	  	    		error:function(data){
	  	    			layer.closeAll('loading');
	        			layer.msg(data.status + " " + data.statusText, {icon: 5});
	  	    		}
	  	    	});
			},
			joinCourse:function(course) {
				if(course.courseStatus==3){
					$.ajax({
				        type:'POST',
				        url:'/TxCourse/studentCourse/joinCourse',
				        data:{
				        	id:course.courseId
				        },
				        dataType:'JSON',
				        beforeSend:function() {
				        	layer.load(1);
				        },
				        success:function(res){
				        	if (res.status == 0) {
				        		layer.closeAll('loading');
				        		layer.msg(res.result, {icon: 6});
								app.getMyCourse();
								window.parent.iframeLoad();
				        	} else{
				        		layer.closeAll('loading');
				        	   	layer.msg(res.result, {icon: 5}); 
				           	}
				        },
				        error:function(data){
				        	layer.closeAll('loading');
		        			layer.msg(data.status + " " + data.statusText, {icon: 5});
				        }
					})
				}else{
					layer.msg("此课尚未开放，不能加入",{icon: 5});
				}
				 
			},
			seeCourseDetail:function(course) {
				this.currentCourse = course;
				layer.open({
			        type: 1
			        ,title: course.courseName + "(" + course.courseSequence + ")" //不显示标题栏
			        ,closeBtn: false
			        ,area: '600px'
			        ,shade: 0
			        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
			        ,btn: ['确定']
			        ,btnAlign: 'c'
			        ,moveType: 1 //拖拽模式，0或者1
			        ,content: $("#courseDetail")
				});
			},
			sendMsg:function(course) {
				layer.open({
		        	type:1,//页面层
		        	title:'课程留言：' + course.courseName + '(' + course.courseSequence + ')',
		       		closeBtn:false,
		   			content: "<div style='max-width:100%;padding-left:20px;padding-right:20px;'><p style='padding:5px 0px;'>任课老师：" + course.teacherName + "</p><textarea class='layui-textarea' type='text'></textarea></div>",//使用html格式的文本
		   			area: '400px',
		           	btn:['提交','取消'],
		           	yes:function(index, layero){
                		var msg = layero.find("textarea").val();
		                $.ajax({
		                	type:'POST',
		                	url:'/TxCourse/studentCourse/sendMessage',  //要改
		                	data:{
		                   		tid:course.teacherId,
		                   		me:msg
		                 	},
		                 	dataType:'JSON',
		                 	beforeSend:function(){
		                 		layer.load(1);
		                 	},
		                 	success:function(res){
		                 		layer.closeAll('loading');
		                 		if (res.status == 0) {
					        		layer.msg(res.result, {icon: 6});
					        		layer.close(index);
					        	} else{
					        	   	layer.msg(res.result, {icon: 5}); 
					           	}
		                 	},
		                 	error:function(data) {
		                 		layer.closeAll('loading');
			        			layer.msg(data.status + " " + data.statusText, {icon: 5});
		                 	}
		            	});
		           	},
		           	btn2:function(index,othis){
		               	layer.close(index);
		        	}
	        	});
			}
		}
	});
	
	form.on('select(selectCondition)', function(data){
		app.searchType = data.value;
		window.parent.iframeLoad();
	});
});




</script>
	</html>	