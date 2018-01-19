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
		 <link rel="stylesheet" type="text/css" href="/txcourse/layui/css/layui.css">
		 <script type="text/javascript" src="/txcourse/layui/layui.js"></script>
</head>
<body class="">
 <div class=" ">
	 <!-- 头部 -->
        <div class="layui-header">
    <div class="layui-logo">通信云项目 学生端</div>
    <!-- 头部区域（可配合layui已有的水平导航） -->
    <ul class="layui-nav layui-layout-right">
      <li class="layui-nav-item">
        <a href="javascript:;">
               小米
        </a>
      </li>
      <li class="layui-nav-item"><a href="">退出</a></li>
    </ul>
  </div>
      <!-- /end header -->
      <!-- 左侧导航部分 -->
        <div class="layui-side layui-bg-black">
    <div class="layui-side-scroll">
      <ul class="layui-nav layui-nav-tree"  lay-filter="test">
        <li class="layui-nav-item"><a href="">课程实验</a></li>
      </ul>
    </div>
  </div>
      <!-- end 左侧导航 -->
      <!-- 主体部分 -->
       <div class="layui-body">
       <div style="padding: 15px;">
       	<div class="layui-row" >
       		<div class="layui-col-md11">
       		<h1>我的课程</h1></div>
       		<div class="layui-col-md1">
       		<a href="" style="padding-left: 10px;"><i class="layui-icon">&#xe611;</i> 我的信息</a></div>
       	</div>
       	<hr>
       	<div class="">
       		<table class="layui-table">
           <colgroup>
              <col width="150">
              <col width="150">
              <col width="150">
              <col width="150">
              <col width="150">
              <col width="150">
              <col width="400">
          </colgroup>
            <thead>
             <tr>
              <th>序号</th>
              <th>课程号</th>
              <th>课程名称</th>
              <th>加入状态</th>
              <th>课程状态</th>
              <th>分数</th>
              <th>操作</th>
             </tr> 
            </thead>
           <tbody>
             <tr>
              <td>1</td>
              <td>123456</td>
              <td>Matlab实验</td>
              <td>已加入</td>
              <td>正在进行</td>
              <td>78</td>
              <td>
                <div class="layui-btn-group">
                   <button class="layui-btn  layui-btn-normal"><i class="layui-icon">&#xe631;</i> 实验</button>
                   <button class="layui-btn  layui-btn-warm"><i class="layui-icon">&#xe6b2;</i> 留言</button>
                </div>
              </td>
             </tr>
             <tr>
              <td>1</td>
              <td>123456</td>
              <td>Matlab实验</td>
              <td>已加入</td>
              <td>正在进行</td>
              <td>78</td>
              <td>
                <div class="layui-btn-group">
                   <button class="layui-btn  layui-btn-normal"><i class="layui-icon">&#xe631;</i> 实验</button>
                   <button class="layui-btn  layui-btn-warm"><i class="layui-icon">&#xe6b2;</i> 留言</button>
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
          <form class="layui-form" action="">
          <div class="layui-form-item">
            <div class="layui-inline">
              <label class="layui-form-label">搜索条件</label>
              <div class="layui-input-inline">
                <select name="quiz">
                  <option value="">课程名</option>
                  <option value="">课程号</option>
                  <option value="">任课教师</option>
                </select>
              </div>
              <div class="layui-input-inline">
                 <input type="tel" name="phone" lay-verify="required|phone" autocomplete="off" class="layui-input">
              </div>
              <div class="layui-input-inline">
                   <button class="layui-btn"> <i class="layui-icon">&#xe615;</i> 搜索</button>
              </div>
            </div>
          </div>
          </form>
          <table class="layui-table">
           <colgroup>
              <col width="150">
              <col width="150">
              <col width="150">
              <col width="150">
              <col width="150">
              <col width="150">
              <col width="400">
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
             <tr>
              <td>1</td>
              <td>123456</td>
              <td>电磁仿真</td>
              <td>杨广立</td>
              <td>正在进行</td>
              <td>
                <div class="layui-btn-group">
                   <button class="layui-btn  layui-btn-normal"><i class="layui-icon">&#xe600;</i> 加入</button>
                </div>
              </td>
             </tr>
             <tr>
              <td>1</td>
              <td>123456</td>
              <td>电磁仿真</td>
              <td>杨广立</td>
              <td>正在进行</td>
              <td>
                <div class="layui-btn-group">
                   <button class="layui-btn  layui-btn-normal"><i class="layui-icon">&#xe600;</i> 加入</button>
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
layui.use('form', function(){
  var form = layui.form;
  
  //监听提交
  
});
</script>
	</html>	