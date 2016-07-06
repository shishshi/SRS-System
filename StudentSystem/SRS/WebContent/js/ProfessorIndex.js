/**
 * 
 */

$(document).ready(function(){
		$.getJSON("GetAllProfessors",function(json){
			var html="";
			for(var i=0;i<json.length;i++){
				html+="<tr><td><input type='checkbox' name='professor' id='professor' value='"+json[i].Pssn+"' /></td>";
				html+="<td>"+json[i].Pssn+"</td><td><a href='updateProfessor.html?Pssn="+json[i].Pssn+"'>"+json[i].name+"</a></td><td>"+json[i].title+"</td><td>"+json[i].department+"</td></tr>";
			}
			$("#professorList").append(html);
			$('input').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue',
				increaseArea: '20%'
			});
		});
	});

	function deleteProfessors(){
		var count=0;
		var pssn=new Array();
		$(".icheckbox_square-blue").each(function(){
			if($(this).css("background-position")=="-48px 0px"){
				count++;
				pssn[count]=$(this).children("input").val();
			};
		});
		if(count==0){
			alert("请至少选择一名教师");
		}else{
			if(confirm("操作不可恢复，确认要删除选中的教师吗？")==true){
				alert("删除成功！");
				window.location.href="DeleteProfessors?count="+count+"&pssn="+pssn; 
			}
		}
	}