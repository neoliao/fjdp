
Salary = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '企业名称',dataIndex:'name'},
					{header: '组织机构代码',dataIndex:'organizationNumber'},
					{header: '法人',dataIndex:'legalPerson'},
					{header: '主要负责人',dataIndex:'chargePerson'},
					{header: '垫付月份',dataIndex:'payMonth'},
					{header: '欠薪垫付金额',dataIndex:'payAmount'}
				]),	
				storeMapping:[
					'name', 'organizationNumber', 'legalPerson', 'chargePerson', 'payMonth', 'payAmount'
				]
			},
			winConfig : {
				height: 245,width: 385
			},
			formConfig:{
				labelWidth: 90,
				items: [
					{xtype: 'f-text',fieldLabel: '企业名称',name: 'name'}, 
					{xtype: 'f-text',fieldLabel: '组织机构代码',name: 'organizationNumber',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '法人',name: 'legalPerson'}, 
					{xtype: 'f-text',fieldLabel: '主要负责人',name: 'chargePerson'},
					{xtype: 'f-text',fieldLabel: '垫付月份',name: 'payMonth',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '欠薪垫付金额',name: 'payAmount',allowBlank: false}
				]
			},
			url:ctx+'/salary'	
		});
		Salary.superclass.initComponent.call(this);
		
		
	}
	
});
