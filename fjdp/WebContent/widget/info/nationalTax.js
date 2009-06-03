
NationalTax = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '企业名称',dataIndex:'name'},
					{header: '组织机构代码',dataIndex:'organizationNumber'},
					{header: '税务登记代码',dataIndex:'taxNumber'},
					{header: '经营地点',dataIndex:'address'},
					{header: '负责人姓名',dataIndex:'legalPerson'},
					{header: '居民身份证',dataIndex:'idCardNumber'},
					{header: '欠税税种',dataIndex:'taxKind'},
					{header: '欠税余额',dataIndex:'taxAmount'},
					{header: '本期新增欠税',dataIndex:'newTax'}
				]),	
				storeMapping:[
					'name', 'organizationNumber', 'taxNumber', 'address', 'legalPerson', 'idCardNumber',
					'taxKind', 'taxAmount', 'newTax'
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
					{xtype: 'f-text',fieldLabel: '税务登记代码',name: 'taxNumber'}, 
					{xtype: 'f-text',fieldLabel: '经营地点',name: 'address'}, 
					{xtype: 'f-text',fieldLabel: '负责人姓名',name: 'legalPerson'}, 
					{xtype: 'f-text',fieldLabel: '居民身份证',name: 'idCardNumber'}, 
					{xtype: 'f-text',fieldLabel: '欠税税种',name: 'taxKind'}, 
					{xtype: 'f-text',fieldLabel: '欠税余额',name: 'taxAmount'}, 
					{xtype: 'f-text',fieldLabel: '本期新增欠税',name: 'newTax'}
				]
			},
			url:ctx+'/nationalTax'	
		});
		NationalTax.superclass.initComponent.call(this);
	}
	
});
