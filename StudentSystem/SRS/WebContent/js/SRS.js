/**
 * 
 */
$(document).ready(function(){
	getAllSchedules();
	getEnrolledSections();
});

function getAllSchedules(){
	$.getJSON("GetAllSchedules",function(json){
		var html="";
		for(var i=0;i<json.length;i++){
			html+="<tr><td><input type='radio' id='schedule' name='schedule' value='"+json[i].sectionNo+"' /></td><td>"+json[i].courseName+"</td><td>"+json[i].professor+"</td>";
			html+="<td>"+json[i].day+" - "+json[i].time+"</td><td>"+json[i].room+"</td><td>"+json[i].sCapacity+"</td></tr>";
		}
		$("#scheduleList").append(html);
		$("input[id='schedule']").iCheck({
			checkboxClass: 'icheckbox_square-blue',
			radioClass: 'iradio_square-blue',
			increaseArea: '20%'
		});
	});
}

function getEnrolledSections(){
	$("#registeredList").find("tr:gt(0)").each(function(){
		$(this).remove();
	});
	$.getJSON("GetEnrolledSections",function(json){
		var html="";
		for(var i=0;i<json.length;i++){
			html+="<tr><td><input type='radio' id='registered' name='registered' value='"+json[i].sectionNo+"' /></td><td>"+json[i].courseName+"</td><td>"+json[i].professor+"</td>";
			html+="<td>"+json[i].day+" - "+json[i].time+"</td><td>"+json[i].room+"</td><td>"+json[i].sCapacity+"</td></tr>";
		}
		$("#registeredList").append(html);
		$("input[id='registered']").iCheck({
			checkboxClass: 'icheckbox_square-blue',
			radioClass: 'iradio_square-blue',
			increaseArea: '20%'
		});
	});
}

function enroll(){
	var sectionNo=$("#schedule:checked").val();
	$.getJSON("EnrollSectionServlet",{sectionNo:sectionNo},function(json){
		alert(json.warning);
		if(json.result=="success"){
			getEnrolledSections();
		}
	});
}

function drop(){
	var sectionNo=$("#registered:checked").val();
	if(sectionNo!="undefined"){
		if(confirm("确定退选该课程吗？")==true){
			$("#form2").ajaxSubmit();
			getEnrolledSections();
		}
	}
}