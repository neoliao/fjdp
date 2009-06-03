
Custom = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '企业名称',dataIndex:'name'},
					{header: '组织机构代码',dataIndex:'organizationNumber'},
					{header: '企业注册号',dataIndex:'registerNumber'},
					{header: '涉案时间',dataIndex:'caseDate'},
					{header: '处罚决定书号',dataIndex:'caseNumber'},
					{header: '罚款合计',dataIndex:'penaltyTotal'},
					{header: '追款合计',dataIndex:'recoveriesAmount'},
					{header: '补税总金额',dataIndex:'taxUnpaidAmount'},
					{header: '罚没收入',dataIndex:'penalty'},
					{header: '违法事实和证据',dataIndex:'caseDetail'},
					{header: '处理意见',dataIndex:'caseResult'}
				]),	
				storeMapping:[
					'name', 'organizationNumber', 'registerNumber', 'caseDate', 'caseNumber', 'penaltyTotal',
					'recoveriesAmount', 'taxUnpaidAmount', 'penalty', 'caseDetail', 'caseResult'
				]

			},
			winConfig : {
				height: 340
			},
			formConfig:{
				items: [
					{xtype: 'f-text',fieldLabel: '企业名称',name: 'name'}, 
					{xtype: 'f-text',fieldLabel: '组织机构代码',name: 'organizationNumber',allowBlank: false},
					{xtype:'f-text',fieldLabel:'企业注册号',name:'registerNumber'},
					{xtype:'f-text',fieldLabel:'涉案时间',name:'caseDate'},
					{xtype:'f-text',fieldLabel:'处罚决定书',name:'caseNumber'},
					{xtype:'f-text',fieldLabel:'罚款合计',name:'penaltyTotal'},
					{xtype:'f-text',fieldLabel:'追款合计',name:'recoveriesAmount'},
					{xtype:'f-text',fieldLabel:'补税总金额',name:'taxUnpaidAmount'},
					{xtype:'f-text',fieldLabel:'罚没收入',name:'penalty'},
					{xtype:'f-text',fieldLabel:'违法事实',name:'caseDetail'},
					{xtype:'f-text',fieldLabel:'处理意见',name:'caseResult'}
				]
			},
			url:ctx+'/custom'	
		});
		Custom.superclass.initComponent.call(this);
	}
	
});
