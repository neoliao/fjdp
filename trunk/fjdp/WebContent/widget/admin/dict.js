
Dict = Ext.extend(Ext.app.BaseFuncTree,{
	initComponent : function(){
		Ext.apply(this,{
			winConfig : {
				height: 230
			},
			formConfig:{
				items: [
					{xtype:'f-text',fieldLabel:'名称',name: 'text',emptyText:'请输入字典名称',allowBlank:false},
					{xtype:'f-textarea',fieldLabel:'描述',name: 'description'}
				]
			},
			rootConfig: { id:'0' },
			url:ctx+'/dict'
		});
		Dict.superclass.initComponent.call(this);
	}
	
});


