<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="<s:url value="/include/image/icon/favicon.ico"/>" type="image/x-icon" />

<!-- include extJs lib -->
<s:component template="extlib.ftl">
	<s:param name="extTheme">xtheme-gray</s:param>
</s:component>

<link rel="stylesheet" type="text/css" href="<s:url value="/include/css/fort-all.css"/>" />
<link rel="stylesheet" type="text/css" href="<s:url value="/include/css/myhome.css"/>" />

<script type="text/javascript" src="<s:url value="/include/js/ext-lang-zh_CN.js"/>" charset="utf-8"></script>
<script>
	ctx = '<%=request.getContextPath()%>';
	loginUser = {
		userName : '<s:property value="#session.authedUser.displayName"/>',
		privileges : <s:property value="#session.privilegesString" escape="false" default="[]"/>,
		roles : <s:property value="#session.rolesString" escape="false" default="[]"/>,
		ownRole : function(roleName){
			return loginUser.roles.indexOf(roleName) >= 0;
		},
		ownPrivilege : function(privilegeCode){
			return loginUser.privileges.indexOf(privilegeCode) >= 0;
		}
	}
	
</script>
<script type="text/javascript" src="<s:url value="/include/js/ext-override.js"/>"></script>
<script type="text/javascript" src="<s:url value="/include/js/ext-extends.js"/>"></script>
<script type="text/javascript" src="<s:url value="/include/js/ext-app.js"/>"></script>
<script type="text/javascript" src="<s:url value="/include/js/fort-all.js"/>"></script>