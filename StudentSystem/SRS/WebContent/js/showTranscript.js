/**
 * 
 */

$(document).ready(function(){
	$.getJSON("GetTranscript",function(jsonData){
		var html="";
		for(var i=0;i<jsonData.length;i++){
			html+="<tr><td>"+jsonData[i].sectionNo+"</td><td>"+jsonData[i].courseNo+"</td><td>"+jsonData[i].courseName+"</td>";
			html+="<td>"+jsonData[i].professorName+"</td><td>"+jsonData[i].credits+"</td><td>"+jsonData[i].grade+"</td></tr>";
		}
		$("#transcript").append(html);
	})
});