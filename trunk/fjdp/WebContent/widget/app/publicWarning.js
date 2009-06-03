
PublicWarning = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '重要程度',dataIndex:'level',renderer:dictRenderer,width:100},
					{header: '报警内容',dataIndex:'contents',width:300},
					{header: '创建人',dataIndex:'createUser',renderer:dictRenderer},
					{header: '创建时间',dataIndex:'createDateTime'}
				]),	
				storeMapping:[
					'level','contents', 'createUser', 'createDateTime'
				]

			},
			winConfig : {
				height: 340,width: 385
			},
			formConfig:{
				items: [
					{xtype: 'f-textarea',fieldLabel: '报警内容',name: 'contents',height:200,allowBlank: false},
					{xtype: 'f-dict',fieldLabel: '重要程度',hiddenName: 'level',kind:'level',allowBlank: false}
				]
			},
			url:ctx+'/publicWarning'
		});
		PublicWarning.superclass.initComponent.call(this);
	}
	
});

