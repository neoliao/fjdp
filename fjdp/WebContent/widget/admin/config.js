

ConfigForm = Ext.extend(Ext.form.FormPanel,{
	bodyStyle : 'padding: 20px;',
	closable : true,
	border : false,
	initComponent : function(){
		
		Ext.apply(this,{
			items: [
				{xtype: 'f-text',fieldLabel: '管理员邮箱',name: 'adminEmail',emptyText: '请输入管理员邮箱',allowBlank: false},
				{xtype: 'f-select',data : [['default','缺省主题'],['gray','灰色主题']],fieldLabel: '界面主题',hiddenName: 'uiTheme',value: 'default',allowBlank: false}
			],
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				scope : this,
				handler : this.updateConfig
			}]
		});
		ConfigForm.superclass.initComponent.call(this);
	},
	loadData : function(){
		this.getForm().load({
			url : ctx + '/config/loadConfig'
		})
	},
	updateConfig : function(){
		this.getForm().submit({
			url : ctx + '/config/updateConfig',
			success: function(form,action){
				App.msg(action.result.msg);
			}
		})
	}
	
});

Config = Ext.extend(Ext.Panel,{
	layout : 'anchor',
	border : true,
	closable : true,
	initComponent : function(){
		this.items = [
			{	xtype: 'panel',
				height : 60,
				border : false,
				baseCls : 'fjdp-win-title',
				html : '<div class="fjdp-win-title-content confIcon"><h3>系统参数设置</h3><p>设置系统的各项参数</p></div>'
			},
			new ConfigForm({id : 'configForm',anchor : '0 -60'})
			
		]
		Config.superclass.initComponent.call(this);
	},
	loadData : function(){
		Ext.getCmp('configForm').loadData();
	}
});
