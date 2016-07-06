/**
 * 
 */

function checkAll(){
		var p=false;
		$(".icheckbox_square-blue").each(function(){
			//只要有一个未选中就为true，可执行全选操作
			if($(this).css("background-position")!="-48px 0px"){
				p=true;
				return false;//跳出循环
			};
		});	
		if(p){
			$(".icheckbox_square-blue").each(function(){
				$(this).attr("class","icheckbox_square-blue checked");
				$(".check").attr("checked","checked");
			});
		}else{
			$(".icheckbox_square-blue").each(function(){
				$(this).attr("class","icheckbox_square-blue");
				$(".check").removeAttr("checked");
			});
		}
	}