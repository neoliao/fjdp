<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<SCRIPT  LANGUAGE="vbscript">
<!--

Sub window_onload
	Dim lReturn
		Dim obj_mfsst_util
		Dim dzhdtest
		 dzhdtest=1
		 document.getElementById("dzhdtest").value = dzhdtest
		Set obj_mfsst_util = CreateObject( "mfsst.util" )
		 dzhdtest=0
		 document.getElementById("dzhdtest").value = dzhdtest
 End Sub
-->
</script>

<body>

<form name="input" action="html_form_action.jsp" method="post">
Male: 
<input type="radio" name="Sex" value="Male" checked="checked">
<br>
Female: 
<input type="radio" name="Sex" value="Female">
<br>
<input type ="submit" value ="Submit">
<input type = "text" id="dzhdtest" name="dzhdtest">
</form> 

<p>如果您点击 "Submit" 按钮，您将把输入传送到名为 html_form_action.asp 的新页面。</p>

</body>
</html>

