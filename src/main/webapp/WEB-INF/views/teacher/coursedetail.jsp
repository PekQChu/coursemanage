<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
         <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		 <title>课程详情</title>
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
       <div id="courseDetail" v-cloak class="" style="left:0px;">
       <div style="padding: 15px;">
       	<div class="layui-row" >
       		<div class="layui-col-md10">
       		  <h1>课程详情</h1>
          </div>
            <div class="layui-col-md1">
                <button class="layui-btn" v-on:click="editCommand" >编辑</button>
            </div>
       		<div class="layui-col-md1">
                <button class="layui-btn" style="margin-bottom: 5px; color: white" v-on:click="goBack" >返回</button>
       	    </div>
       	<hr>
       	<!-- 所有课程部分 -->
       	<div class="site-text site-block"> 
       		<!-- 表格部分 -->
          <div class="layui-form">
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
                  <td>{{courseDetail.courseName}}</td>
                  <td>课程号：</td>
                  <td>{{courseDetail.courseSequence}}</td>
                </tr>
                <tr>
                  <td>上课时间：</td>
                  <td>{{courseDetail.courseTime}}</td>
                  <td></td>
                  <td></td>
                </tr>
                <tr>
                  <td>计算资源：</td>
                  <td>{{courseDetail.osname}}</td>
                  <td></td>
                  <td></td>
                </tr>
                <tr>
                  <td>课程要求：</td>
                    <td  colspan="3" ></p>
                    	<div id="commandDisplay" v-html="courseDetail.courseContent" style="word-wrap:break-word"></div>
                    	<div style="width: 100%;" id="commandEditor" hidden="true">
                      		<script id="container_edi" name="content" class="editor_con_rw" type="text/plain" style="width: 100%; max-height:600px;"></script>
                      		<button id="clickToUpload" style="" v-on:click="uploadCommand" class="layui-btn">保存修改</button>
                      	</div>
                    </td>
                  <td >
                  </td>
                </tr>
              </tbody>
           </table> 
          </div> 
          <!-- end 表格部分 -->
                  

       	</div>
       </div>
      </div>
      <!-- end 主体部分 -->
	</div>
 </div>
	</body>
<script src="https://code.jquery.com/jquery-3.2.1.js"></script>
<script type="text/javascript" charset="utf-8" src="/TxCourse/ueditor/ueditor.config.js?id=<%=System.currentTimeMillis()%>"></script>
<script type="text/javascript" charset="utf-8" src="/TxCourse/ueditor/ueditor.all.js?id=<%=System.currentTimeMillis()%>"> </script>
  <script>

layui.use(['form', 'layer'], function(){

  var form = layui.form;
    var ue = UE.getEditor('container_edi');
    ue.ready(function() {
        ue.setContent(app.courseDetail.courseContent);
		ue.setHeight('200px');
    });
    var layer = layui.layer;
    var app = new Vue({
        el: '#courseDetail',
        data: {
            courseDetail: {}
        },
        created:function () {
            this.getCourseDetail();
        },
        methods: {
            statusToText:function(status) {
                if (1 == status) {
                    return "正在进行"
                } else {
                    return "已结课"
                }
            },
            getCourseDetail: function () {
                $.post("/TxCourse/teacherCourse/toTCourseDetailPage",{ id: "${cid}" }, function (result) {
                    app.courseDetail = result.result[0];                  
                    window.parent.iframeLoad();
                });
            },
            editCommand:function () {
                $("#commandDisplay").attr("hidden", "hidden");
                $("#commandEditor").removeAttr("hidden");
            },
            uploadCommand:function () {
                var command = UE.getEditor('container_edi').getContent();
                $("#commandEditor").attr("hidden", "hidden");
                $("#commandDisplay").removeAttr("hidden");
                $.ajax({
                    url: '/TxCourse/teacherCourse/UpdateCourseDetailPage',
                    dataType: 'JSON',
                    data: { id: '${cid}',content:command },
                    type: 'POST',
                    beforeSend:function () {
                        layer.load(1);
                    },
                    success:function (result) {
                        if(0 == result.status) {
                            layer.msg(result.result, {icon: 6})
                        } else {
                            layer.msg(result.result, {icon: 5})
                        }
                        layer.closeAll('loading');
                    },
                    error:function () {
                        layer.msg("上传失败", {icon: 5});
                        layer.closeAll('loading');
                    }
                });
            },
            goBack:function () {
                window.history.go(-1);
            }

        }
    })
});
</script>
</html>