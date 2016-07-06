/**
 * 
 */

$(document).ready(function(){
		$.getJSON("GetAllCourses",function(json){
			var html="";
			var str="";
			for(var i=0;i<json.length;i++){
				html+="<tr><td><input type='checkbox' name='course' id='course' value='"+json[i].courseNo+"' /></td>";
				html+="<td>"+json[i].courseNo+"</td><td><a href='updateCourse.html?courseNo="+json[i].courseNo+"'>"+json[i].courseName+"</a></td><td>"+json[i].credits+"</td>";
				html+="<td><button class='btn btn-default' id='modal-858342' href='#modal-container-858342' role='button' data-toggle='modal' onclick=\"showSections('"+json[i].courseNo+"','"+json[i].courseName+"');\">";
				html+="<span class='glyphicon glyphicon-tasks'></span>展开班次</button></td></tr>";
				str+="<option value='"+json[i].courseNo+"'>"+json[i].courseName+"</option>"
			}
			$("#courseList").append(html);
			$("#Prerequisites").append(str);
			$('input').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue',
				increaseArea: '20%'
			});
		});
	});
	
	function deleteCourses(){
		var count=0;
		var courseNo=new Array();
		$(".icheckbox_square-blue").each(function(){
			if($(this).css("background-position")=="-48px 0px"){
				count++;
				courseNo[count]=$(this).children("input").val();
			};
		});
		if(count==0){
			alert("请至少选择一门课程");
		}else{
			if(confirm("操作不可恢复，确认要删除选中的课程吗？")==true){
				alert("删除成功！");
				window.location.href="DeleteCourses?count="+count+"&courseNo="+courseNo; 
			}
		}
	}
	
	function showSections(courseNo,courseName){
		$("#sectionList").find("tr:gt(0)").each(function(){
			$(this).remove();
		});
		$("#studentList").find("tr:gt(0)").each(function(){
			$(this).remove();
		});
		$("#cName").empty();
		$("#cName").append(courseName);
		$.getJSON("GetAllSections",{courseNo:courseNo},function(json){
			var html="";
			if(json.length==0){
				html+="<tr><td colspan='7' class='bg-warning' style='text-align:center;'>NO SECTION</td></tr>";
			}else{
				for(var i=0;i<json.length;i++){
					html+="<tr><td>"+json[i].sectionNo+"</td><td>"+json[i].professor+"</td><td>"+json[i].day+"</td><td>"+json[i].time+"</td><td>"+json[i].room+"</td><td>"+json[i].sCapacity+"</td>";
					html+="<td><button class='btn btn-default' onclick=\"showStudents("+json[i].sectionNo+");\">查看选课情况</button></td></tr>";
				}
			}
			$("#sectionList").append(html);
		});
	}
	
	function showStudents(sectionNo){
		$("#studentList").find("tr:gt(0)").each(function(){
			$(this).remove();
		});
		$.getJSON("GetEnrolledStudents",{sectionNo:sectionNo},function(json){
			var html="";
			if(json.length==0){
				html+="<tr><td colspan='4' class='bg-warning' style='text-align:center;'>NO STUDENT ENROLLED</td></tr>";
			}else{
				for(var i=0;i<json.length;i++){
					html+="<tr><td>"+json[i].sssn+"</td><td>"+json[i].name+"</td><td>"+json[i].major+"</td><td>"+json[i].degree+"</td></tr>";
				}
			}
			$("#studentList").append(html);
		});
	}
	
	function addCourse(){
		var courseNo=$("#courseNo").val();
		var courseName=$("#courseName").val();
		var credits=$("#credits").val();
		var prerequisite=$("#Prerequisites").find("option:selected").val();
		$.getJSON("AddCourse",{courseNo:courseNo,courseName:courseName,credits:credits,prerequisite:prerequisite},function(json){
			alert(json.warning);
			if(json.result==true){
				window.location.reload();
			}
		});
	}