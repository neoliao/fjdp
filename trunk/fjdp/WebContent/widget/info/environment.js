
Environment = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '企业名称',dataIndex:'name'},
					{header: '组织机构代码',dataIndex:'organizationNumber'},
					{header: '法人',dataIndex:'legalPerson'},
					{header: '处罚日期',dataIndex:'executedDate'},
					{header: '违法行为',dataIndex:'illegalBehavior'},
					{header: '处罚结果',dataIndex:'dealResults'}
				]),	
				storeMapping:[
					'name', 'organizationNumber', 'legalPerson', 'executedDate', 'illegalBehavior', 'dealResults'
				]

			},
			winConfig : {
				height: 340,width: 385
			},
			formConfig:{
				labelWidth: 90,
				items: [
					{xtype: 'f-text',fieldLabel: '企业名称',name: 'name'}, 
					{xtype: 'f-text',fieldLabel: '组织机构代码',name: 'organizationNumber',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '法人',name: 'legalPerson'}, 
					{xtype: 'f-date',fieldLabel: '处罚日期',name: 'executedDate'},
					{xtype: 'f-textarea',fieldLabel: '违法行为',name: 'illegalBehavior',allowBlank: false},
					{xtype: 'f-textarea',fieldLabel: '处罚结果',name: 'dealResults',allowBlank: false}
				]
			},
			url:ctx+'/environment'	
		});
		Environment.superclass.initComponent.call(this);
		
		
	}
	
});
