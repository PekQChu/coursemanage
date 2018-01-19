<%@page import="com.txcourse.DAOImpl.MessageDAOImpl"%>
<%@page import="com.txcourse.DAO.MessageDAO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ page import="com.shu.model.*" %>
<%@ page import="com.txcourse.DAO.*" %>
<%@ page import="com.txcourse.DAOImpl.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>我的课程</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!--  <link rel="stylesheet" href="layui/css/layui.css"> -->
     <style>
		       [v-cloak] {
                 display: none;
               }
		  </style>
    <link rel="stylesheet" type="text/css" href="/TxCourse/layui/css/layui.css">
    <script type="text/javascript" src="/TxCourse/layui/layui.js"></script>
    <script src="/TxCourse/js/vue.min.js"></script>
</head>
<body class="">
<div class="">
    
    <!-- 主体部分 -->
    <div class="" style="left:0px;">
        <div id="courseTable" v-cloak style="padding: 15px;">
            <div class="layui-row">
                <div class="layui-col-md10">
                    <h1>我的课程</h1>
                </div>
                <div class="layui-col-md2">
                    <a href="/TxCourse/teacherCourse/toAddCoursePage" class="layui-btn">新建实验</a>
                    <a href="/TxCourse/teacherCourse/toMyMessage" style="padding-left: 10px;">我的信息
                    <%
                    	MessageDAO mDao = new MessageDAOImpl();
                    	User user = (User)session.getAttribute(User.CUR_USER);
                    	int msgNum = mDao.getNewMsgNum(user.getId());
                    %>
                    	<span v-if="<%=msgNum %> != 0" class="layui-badge"><%=msgNum %></span>
                   	</a>
                </div>
            </div>
            <hr>
            <!-- 所有课程部分 -->
            <div>
                <table class="layui-table">
                    <colgroup>
                        <col width="150">
                        <col width="150">
                        <col width="150">
                        <col width="150">
                        <col width="150">
                        <col width="500">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>课程号</th>
                        <th>课程名称</th>
                        <th>学生人数</th>
                        <th>课程状态</th>
                        <th>管理面板</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(course, index) in courses" >
                        <td>{{index + 1}}</td>
                        <td>{{course.courseSequence}}</td>
                        <td>{{course.courseName}}</td>
                        <td>{{course.studentNum}}</td>
                        <td>{{statusToText(course.status)}}</td>
                        <td>
                            <div class="layui-btn-group">
                                <a :href="'/TxCourse/teacherCourse/toCourseDetail?id=' + course.courseId" class="layui-btn  layui-btn-normal">课程详情</a>
                                <a :href="'/TxCourse/teacherCourse/tomemberManagePage?id=' + course.courseId" class="layui-btn  layui-btn-warm">成员管理</a>
                                <a :href="'/TxCourse/teacherCourse/topracticeGovern?cid=' + course.courseId" class="layui-btn layui-btn-danger">实验管理</a>
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
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script>

    var app = new Vue({
        el: '#courseTable',
        data: {
            courses: []
        },
        mounted: function() {
            // 动态设置背景图的高度为浏览器可视区域高度

            // 首先在Virtual DOM渲染数据时，设置下背景图的高度．
        	 window.parent.iframeLoad();
            
        },
        created:function () {
            this.getAllCourse();
        },
        methods: { 
            statusToText:function(status) {
                if ( status==0) {
                    return "尚未审核"
                } else if(status==1) {
                    return "审核通过"
                }else if(status==2){
                	return "课程已结束"
                }else if(status==3){
                	return "正在进行"
                }
            },
            getAllCourse: function () {
                $.post("/TxCourse/teacherCourse/getTeacherCourses",{}, function (result) {
                    app.courses = result.result;
                    window.parent.iframeLoad();
                });
            },

        }
    })
</script>
</body>
</html>