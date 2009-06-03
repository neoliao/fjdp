
Count = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '企业名称',dataIndex:'name'},
					{header: '组织机构代码',dataIndex:'organizationNumber'},
					{header: '法人代表',dataIndex:'legalPerson'},
					{header: '证件号',dataIndex:'papersNumber'},
					{header: '执行依据',dataIndex:'executeBasic'},
					{header: '执行案号名称',dataIndex:'caseName'},
					{header: '执行法院',dataIndex:'count'},
					{header: '立案日期',dataIndex:'registerDate'},
					{header: '未执行标的',dataIndex:'nonExecutedTarget'},
					{header: '承办法官',dataIndex:'judge'},
					{header: '联系电话',dataIndex:'telephone'}
				]),	
				storeMapping:[
					'name', 'organizationNumber', 'legalPerson', 'papersNumber', 'executeBasic', 'caseName',
					'count', 'registerDate', 'nonExecutedTarget', 'judge', 'telephone'
				]

			},
			winConfig : {
				height: 370,width: 385
			},
			formConfig:{
				labelWidth: 90,
				items: [
					{xtype: 'f-text',fieldLabel: '企业名称',name: 'name'}, 
					{xtype: 'f-text',fieldLabel: '组织机构代码',name: 'organizationNumber',allowBlank: false,value:'0755-01'},
					{xtype: 'f-text',fieldLabel: '法人代表',name: 'legalPerson'}, 
					{xtype: 'f-text',fieldLabel: '证件号',name: 'papersNumber'},
					{xtype: 'f-text',fieldLabel: '执行依据',name: 'executeBasic',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '执行案号名称',name: 'caseName',allowBlank: false}, 
					{xtype: 'f-text',fieldLabel: '执行法院',name: 'count'},
					{xtype: 'f-text',fieldLabel: '立案日期',name: 'registerDate',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '未执行标的',name: 'nonExecutedTarget',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '承办法官',name: 'judge'}, 
					{xtype: 'f-text',fieldLabel: '联系电话',name: 'telephone'}
				]
			},
			url:ctx+'/count'
		});
		Count.superclass.initComponent.call(this);
	}
	
});
