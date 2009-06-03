
Ext.onReady(function(){

	function showNotLoginMsg(){
		Ext.MessageBox.show({
           title:'警告',
           msg: '你无权访问该系统，或者你的会话已超时<br/>点击＂确定＂回到登陆页面，并重新登陆',
           buttons: Ext.MessageBox.OK,
           fn: function goBack(){
				window.location = ctx;
			},
           icon: Ext.MessageBox.ERROR
       });
	}
	
	function showWarning(msg){
		Ext.MessageBox.show({
           title:'警告',
           msg: msg,
           buttons: Ext.MessageBox.OK,
           icon: Ext.MessageBox.WARNING
       });
	}

	//验证用户权限
	if(!loginUser ||loginUser.privileges.length == 0){
		showNotLoginMsg();
	}
	
	var viewport = new Ext.Viewport({
        layout:'border',
        items:[
	        new App.Header({
	        	id:'header'
	        }),
	        new App.NavMenu({
				region:'west',
				id:'navMenu',
				title: '功能列表',
				iconCls:'list',
				collapsible: true,
				collapseMode:'mini',
				split:true,
				width: 200,
				minSize: 175,
				maxSize: 400,
				autoScroll:true,	
				margins:'0 0 5 5',			
				root: new Ext.tree.AsyncTreeNode({
					id:'0',
					text: '功能列表',
					expend:true
				}),
				loader:new Ext.tree.TreeLoader({
					dataUrl:ctx+'/system/getMenuTree'
				})
			}),
	        new App.MainPanel({
				region:'center',
			    id:'mainPanel',
				margins:'0 5 5 0',
				activeTab:0,
				enableTabScroll : true,
				resizeTabs : true,
			    minTabWidth: 135,
			    tabWidth: 135,
			    items:[new MyHome()]
			})
	    ]
	});
	
	//全局的ajax请求处理
	Ext.Ajax.on('beforerequest', function (){
		Ext.getCmp('header-bar').showBusy();
	}, this);

	Ext.Ajax.on('requestcomplete', function (conn,response){
		Ext.getCmp('header-bar').clearStatus();
		var hasMsg = response.getResponseHeader["hasMsg"];
		if(hasMsg){
			var jo = Ext.decode(response.responseText);
			if(jo.msg){
				App.msg(jo.msg);
			}
		}
	}, this);
	
	Ext.Ajax.on('requestexception', function (conn,response,options){
		if(response.status == 403){
			showNotLoginMsg();
		}else if(response.status == 420){
			showWarning(response.responseText);
		}else{
			App.msg(response.status+' '+response.statusText,'error');
		}
		Ext.getCmp('header-bar').clearStatus();
	}, this);
	
	setTimeout(function(){
        Ext.get('loading').remove();
        Ext.get('loading-mask').fadeOut({remove:true});
    }, 500);
  
});