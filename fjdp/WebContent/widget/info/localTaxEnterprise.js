
LocalTaxEnterprise = Ext.extend(Ext.app.BaseFuncPanel,{
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
					{header: '营业税',dataIndex:'salesTax'},
					{header: '城建税',dataIndex:'constructionTax'},
					{header: '房产税',dataIndex:'buildingTax'},
					{header: '企业所得税',dataIndex:'enterpriseIncomeTax'},
					{header: '个人所得税',dataIndex:'personalIncomeTax'},
					{header: '印花税',dataIndex:'stampTax'},
					{header: '其他',dataIndex:'otherTax'},
					{header: '欠税合计',dataIndex:'total'},
					{header: '本期新增欠税',dataIndex:'newTax'}

				]),	
				storeMapping:[
					'name','organizationNumber','taxNumber','address','legalPerson','idCardNumber','salesTax','constructionTax',
					'buildingTax','enterpriseIncomeTax','personalIncomeTax','stampTax','otherTax','total','newTax'
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
					{xtype: 'f-text',fieldLabel: '税务登记代码',name:'taxNumber'}, 
					{xtype: 'f-text',fieldLabel: '经营地点',name:'address'}, 
					{xtype: 'f-text',fieldLabel: '负责人姓名',name:'legalPerson'}, 
					{xtype: 'f-text',fieldLabel: '居民身份证',name:'idCardNumber'}, 
					{xtype: 'f-text',fieldLabel: '营业税',name:'salesTax'}, 
					{xtype: 'f-text',fieldLabel: '城建税',name:'constructionTax'}, 
					{xtype: 'f-text',fieldLabel: '房产税',name:'buildingTax'}, 
					{xtype: 'f-text',fieldLabel: '企业所得税',name:'enterpriseIncomeTax'}, 
					{xtype: 'f-text',fieldLabel: '个人所得税',name:'personalIncomeTax'}, 
					{xtype: 'f-text',fieldLabel: '印花税',name:'stampTax'}, 
					{xtype: 'f-text',fieldLabel: '其他',name:'otherTax'}, 
					{xtype: 'f-text',fieldLabel: '欠税合计',name:'total'}, 
					{xtype: 'f-text',fieldLabel: '本期新增欠税',name:'newTax'}

				]
			},
			url:ctx+'/localTaxEnterprise'	
		});
		LocalTaxEnterprise.superclass.initComponent.call(this);
	}
	
});
