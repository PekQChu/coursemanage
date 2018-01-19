<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>课程详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <style>
		       [v-cloak] {
                 display: none;
               }
		  </style>
    <link rel="stylesheet" type="text/css" href="/TxCourse/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="/TxCourse/layui/css/global.css">
    
</head>
<body class="">
<div class="">
    
    <!-- 主体部分 -->
    <div class=""id="memberapp" v-cloak style="left:0px;">
        <div style="padding: 15px;">
            <div class="layui-row">
                <div class="layui-col-md11">
                    <h1>成员管理</h1></div>
                <div class="layui-col-md1">
                    <button class="layui-btn" style="margin-bottom: 5px;"><a style="color: white;" href="/TxCourse/teacherCourse/toteacherCoursesPage">返 回</a></button>
                </div>
                <hr>
                <!-- 所有课程部分 -->
                <div class="">
                    <div class="layui-tab layui-tab-brief"  lay-filter="courseStuMan">
                        <ul class="layui-tab-title">
                            <li class="layui-this" data-url="/TxCourse/teacherCourse/getChoosenMember">已加入</li>
                            <li data-url="/TxCourse/teacherCourse/getUnchoosenMember">未加入</li>
                        </ul>
                        <div class="layui-tab-content">
                            <div class="layui-tab-item layui-show">
                                <table class="layui-table">
                                    <colgroup>
                                        <col width="150">
                                        <col width="150">
                                        <col width="150">
                                        <col width="150">
                                        <col width="150">
                                        <col width="150">
                                        <col width="300">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>学号</th>
                                        <th>姓名</th>
                                        <th>实验状态</th>
                                        <th>实验成绩</th>
                                        <th>积分</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="(m,index) in member">
                                        <td>{{index + 1}}</td>
                                        <td>{{m.uid}}</td>
                                        <td>{{m.username}}</td>
                                        <td>{{m.experimentStatus}}</td>
                                        <td>{{m.score}}</td>
                                        <td>{{m.worktime}}</td>
                                        <td>
                                            <div class="layui-btn-group">
                                                <button class="layui-btn  layui-btn-normal" v-on:click="leaveMesage(m)"><i class="layui-icon">&#xe642;</i>留言
                                                </button>
                                                
                                            </div>
                                        </td>
                                    </tr>

                                    </tbody>
                                </table>
                            </div>
                            <div class="layui-tab-item">
                             <div class="layui-btn-group">
                                   <button class="layui-btn" v-on:click="passAll()">批量同意</button>
                             </div>
                              <table class="layui-table">
                                    <colgroup>
                                        <col width="50">
                                        <col width="150">
                                        <col width="150">
                                        <col width="150">
                                        <col width="150">
                                        <col width="150">
                                        <col width="150">
                                        <col width="300">
                                    </colgroup>
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" v-on:click="checkAll()"></th>
                                        <th>序号</th>
                                        <th>学号</th>
                                        <th>姓名</th>
                                        <th>实验状态</th>
                                        <th>实验成绩</th>
                                        <th>积分</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr v-for="(m,index) in member">
                                        <th><input type="checkbox" v-bind:value="m.id" v-model="chosenStu"></th>
                                        <td>{{index + 1}}</td>
                                        <td>{{m.uid}}</td>
                                        <td>{{m.username}}</td>
                                        <td>{{m.experimentStatus}}</td>
                                        <td>{{m.score}}</td>
                                        <td>{{m.worktime}}</td>
                                        <td>
                                            <div class="layui-btn-group">
                                                <button class="layui-btn  layui-btn-normal" v-on:click="leaveMesage(m)"><i class="layui-icon">&#xe642;</i>留言
                                                </button>
                                                <button class="layui-btn " v-on:click="pass(m)"><i class="layui-icon">&#xe618;</i>同意
                                                </button>
                                            </div>
                                        </td>
                                    </tr>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <script type="text/javascript" src="/TxCourse/layui/layui.js"></script>
    <script type="text/javascript" src="/TxCourse/js/vue.min.js"></script>
   <script>

   layui.use(['element','layer'], function(){
	   var $ = layui.jquery
	   var layer = layui.layer;
	   var app = new Vue({
		  el:"#memberapp",
		  data:{
			  member:[],
			  chosenStu:[],
			  isAllCheck:0,
		  },
		  methods:{
			  checkAll:function() {
	        		if (this.isAllCheck == 0) {
	        			this.isAllCheck = 1;
	        			this.chosenStu = [];
	        			for (var i = 0; i < this.member.length; i++) {
	            			this.chosenStu[i] = this.member[i].id;
	            		}
	        		} else {
	        			this.isAllCheck = 0;
	        			this.chosenStu = [];
	        		}
	            },
			  tabChange:function(url){
				  	$.post(url,{id:"${cID}"},function(result,status){
				  		if(result.status == 0){
				  			app.member = result.result;
				  			// window.parent.iframeLoad();
				  		}else{
				  			app.member = [];
							 layer.alert(result.result, {icon: 5});
				  		}
				  	});
			  },
			  leaveMesage:function(i){
				  layer.prompt({title: '给 '+i.username+" 留言：", formType: 2}, function(text, index){
					  layer.close(index);
					  $.post("/TxCourse/teacherCourse/sendMessage",{receiveUserId:i.id,content:text},function(result,status){
					  		if(result.status == 0){
					  			 layer.msg(result.result, {icon: 6});
					  		}else{
								 layer.alert(result.result, {icon: 5});
					  		}
					  	}); 
					});  
			  },
			  //同意加入课程
			  pass: function(i){
				  
			  },
			  //同意所有学生
			  passAll:function(){
	            	console.log(this.chosenStu);
	            	if (this.chosenStu.length >0) {
	            		var detailStr = "<div style='padding:20px;'>您确定要同意" + this.chosenStu.length +  "多学生吗？</div>";
	            		layer.open({
					        type: 1
					        ,title: "同意确认" //不显示标题栏
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
					        		url:'/TxCourse/teacherCourse/addMembersToCourse',
					        		dataType:'JSON',//courseApp.chosenVps
					        		data: {number:app.chosenStu,cid:1},
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
		  },
		  created:function(){
			  this.tabChange("/TxCourse/teacherCourse/getChoosenMember")
		  }
	   });
	   
	   var element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
	   element.on('tab(courseStuMan)', function(data){
			app.tabChange($(this).attr("data-url"));
			// window.parent.iframeLoad();
		  });
	   
   });
   </script>
</body>
</html>