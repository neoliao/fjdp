Ext.ns('Myhome');

Myhome.Welcome = Ext.extend(Ext.Panel,{
	border : false,
	bodyStyle  : 'padding: 5px 10px',
	html : '<div class="msg-body"><p>欢迎您，<span class="userName">'+loginUser.userName+'</span></p></div>'
});

Myhome.Warning = Ext.extend(Ext.app.Portal,{
	id : 'warning-portal',
    title: '风险预警信息',
    privilegeCode : 'WarningPortal_view',
    autoLoad: {
    	url : ctx+'/myhome/warningList',
    	scripts : true
    },
    tag: function(bt,messageId){
    	Ext.Ajax.request({
			url: ctx+'/myhome/tagAsReaded' ,
			params: {id : messageId},
			scope:this,
			success:function(response, options) {
				Ext.get(bt).hide(true);
			},        	
			failure:function(response, options) {
				Ext.get(bt).show(true);
			}
		});
    },
    feedback: function(bt,messageId){
    	var feedWin = new Ext.app.FormWindow({
			title : '反馈意见',
			iconCls: 'pencil',
			winConfig　: {
				height : 300,
				width : 390
			},
			formConfig :{
				items : [
					{xtype: 'f-textarea',fieldLabel: '反馈意见',height: 200,width:250,
						name: 'contents',allowBlank: false,emptyText:'请输入您对此消息的反馈意见'}
				]
			},
			buttons : [{
				text : '提交反馈',
				scope : this,
				handler : function(){
					feedWin.formPanel.form.submit({
						url: ctx+'/myhome/feedback' ,
						params: {id : messageId},
						scope:this,
						success:function(response, options) {
							feedWin.close();
							Ext.get(bt).hide(true);
							if(Ext.get(bt).prev('.reading'))
								Ext.get(bt).prev('.reading').hide(true);
							App.msg('该意见已提交!');
						},        	
						failure:function(response, options) {
							//Ext.get(bt).show(true);
						}
					});
					
				}
			}]
		});
		feedWin.show();
    	
    }
});

Myhome.Feedback = Ext.extend(Ext.app.Portal,{
	id : 'feedback-portal',
	title: '反馈信息',
	privilegeCode : 'FeedbackPortal_view',
	autoLoad: {
    	url : ctx+'/myhome/feedbackList',
    	scripts : true
    },
    read: function(bt,id){
    	this.feedWin = new Ext.Window({
    		title : '阅读反馈信息',
    		iconCls : 'pencil',
    		modal : true,
    		width : 600,
    		height : 400,
    		autoScroll : true,
    		bodyStyle: 'padding: 10px;background:white;',
    		autoLoad : ctx+'/myhome/feedbackDetail?id='+id
    		
    	});
    	this.feedWin.readBt = bt;
    	this.feedWin.show();
    },
    tagAll : function(messageId){
    	Ext.Ajax.request({
			url: ctx+'/myhome/tagAllAsReaded' ,
			params: {id : messageId},
			scope:this,
			success:function(response, options) {
				this.feedWin.close();
				Ext.get(this.feedWin.readBt).hide(true);
			}
		});
    }
});

Myhome.Notice = Ext.extend(Ext.app.Portal,{
	id : 'notice-portal',
	title: '公告信息',
	privilegeCode : 'NoticePortal_view',
	autoLoad: {
    	url : ctx+'/myhome/noticeList',
    	scripts : true
    },
    tag: function(bt,messageId){
    	Ext.Ajax.request({
			url: ctx+'/myhome/tagAsReaded' ,
			params: {id : messageId},
			scope:this,
			success:function(response, options) {
				Ext.get(bt).hide(true);
			},        	
			failure:function(response, options) {
				Ext.get(bt).show(true);
			}
		});
    }
});

MyHome = Ext.extend(Ext.Panel,{
	id:'myHomePanel',
	title: '我的主页',
	iconCls:'userman',
	autoScroll:true,
	border:true,
	bodyStyle: 'padding: 10px ',
	layout: 'anchor',
	initComponent : function(){
		
		this.items = [
			new Myhome.Welcome({anchor: '-10'}),
			new Myhome.Warning({anchor: '-10'}),
			new Myhome.Feedback({anchor: '-10'}),
			new Myhome.Notice({anchor: '-10'})
		];
		
    	MyHome.superclass.initComponent.call(this);
    	
    }

});

