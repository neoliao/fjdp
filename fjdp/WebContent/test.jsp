<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<title>用户登陆</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="<s:url value="/include/image/icon/favicon.ico"/>" type="image/x-icon" />

<!-- include extJs lib -->
<s:component template="extlib.ftl">
	<s:param name="extTheme">xtheme-gray</s:param>
</s:component>

<link rel="stylesheet" type="text/css" href="<s:url value="/include/css/fjdp-all.css"/>" />
<link rel="stylesheet" type="text/css" href="<s:url value="/include/css/app-all.css"/>" />

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
<script type="text/javascript" src="<s:url value="/include/js/fjdp-all.js"/>"></script>
<script type="text/javascript" src="<s:url value="/include/js/app-all.js"/>"></script>
</head>
<body>
<script>
Ext.onReady(function(){
	
	var banner = new Ext.Panel({
		id:'loginimg',
        height:150,
        html: '<img src="include/image/theme/login.png"/>'  
    });
	
	var loginform = new Ext.FormPanel({
		id:'loginform',		
	    baseCls: 'x-plain',
	    labelWidth: 60, 
	   	bodyStyle:'padding:15px 10px 0 90px',
	    defaults: {width: 230},
	    defaultType: 'textfield',
	
	    items: [{
	            fieldLabel: '用户名',
	            name: 'userName',
	            id:'loginUser',
	            validateOnBlur:false,
	            blankText:'用户名不能为空,请输入您的用户名',
	            value:'01',
	            allowBlank:false
	        },{
	            fieldLabel: '密码',
	            id:'loginPswd',
	            name: 'password',
	            validateOnBlur:false,
	            blankText:'密码不能为空',
	            allowBlank:false,
	            value:'neo',
	            inputType:'password'		
	        }
	    ]
	});   
	
	function submit(){
		loginform.getForm().submit({
             url: ctx+'/system/login',
             waitTitle: '请稍候...',   
             waitMsg: '正在登陆',
             success: function (form, action) {
                 document.location = ctx+'/system/viewport';
             },
             failure:function(form, action) {
                 if(action.failureType == action.SERVER_INVALID)
             		Ext.MessageBox.alert('信息', '登陆错误,请检查你的用户名和密码是否正确以及用户是否被锁定!');
             }
         });
	}
	
	var win = new Ext.Window({		
		title:'用户登陆',
		iconCls: 'userman',
	    layout:'fit',
	    draggable:false,
	    resizable:false,
	    constrain:true,
	    closable:false,
	    plain:true,
	    buttonAlign:'center',
	    width:510, 
	    height:300,
	    closeAction:'hide',
	    plain: true,
	    layout:'anchor', 
		items:[banner,loginform],
	
	    buttons: [{
	        text:'登陆',
	        name:'login',
	        handler: submit
	    }]
	});
	
	win.show();
	win.alignTo(document,'c-c',[0,-50]);  
	
	var map = new Ext.KeyMap(document, {
	    key: Ext.EventObject.ENTER,
	    fn: submit,
	    scope: this
	});
});
</script>

</body>
</html>

