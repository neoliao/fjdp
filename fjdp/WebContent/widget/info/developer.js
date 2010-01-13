
Developer = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '名称',dataIndex:'name',width :200},
					{header: '组织机构代码',dataIndex:'organizationCode',width :150},
					{header: '负责人',dataIndex:'chargeMan',width :150},
					{header: '联系人',dataIndex:'linkMan',width :150},
					{header: '联系电话',dataIndex:'telphone',width :150},
					{header: '营业执照号码',dataIndex:'businessLicenceNumber',width :150},
					{header: '营业执照有效期',dataIndex:'licenceExpiryDate',width :150}
				]),	
				storeMapping:[
					'name','organizationCode','chargeMan','linkMan','telphone',
					'businessLicenceNumber','licenceExpiryDate','address'
				],
				viewConfig: { emptyText : '没有可用的数据'}
			},
			winConfig : {
				height: 410,
				desc : '新增，修改和开发商信息',
				bigIconClass : 'companyBigIcon'
			},
			formConfig:{
				items: [
					{xtype: 'f-text',fieldLabel: '名称',name: 'name',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '组织机构代码',name: 'organizationCode'},
					{xtype: 'f-text',fieldLabel: '负责人',name: 'chargeMan'},
					{xtype: 'f-text',fieldLabel: '联系人',name: 'linkMan'},
					{xtype: 'f-text',fieldLabel: '联系电话',name: 'telphone'},
					{xtype: 'f-text',fieldLabel: '营业执照号码',name: 'businessLicenceNumber'},
					{xtype: 'f-text',fieldLabel: '营业执照有效期',name: 'licenceExpiryDate'},
					{xtype: 'f-textarea',fieldLabel: '地址',name: 'address'}

				]
			},
			url:ctx+'/developer'	
		});
		Developer.superclass.initComponent.call(this);
	}
	
});
