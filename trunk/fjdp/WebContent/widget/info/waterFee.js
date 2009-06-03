
WaterFee = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '企业名称',dataIndex:'name'},
					{header: '组织机构代码',dataIndex:'organizationNumber'},
					{header: '用户编号',dataIndex:'userNumber'},
					{header: '用水地点',dataIndex:'useLocation'},
					{header: '初装日期',dataIndex:'installDate'},
					{header: '送票地址',dataIndex:'deliverAddr'},
					{header: '用水地址',dataIndex:'useAddr'},
					{header: '联系电话',dataIndex:'tele'},
					{header: '手机号码',dataIndex:'mobile'},
					{header: '使用状态',dataIndex:'status'},
					{header: '自来水费欠费金额',dataIndex:'feeAmount'},
					{header: '自来水费欠费日期',dataIndex:'oweDate'},
					{header: '污水费欠费金额',dataIndex:'sewageFeeAmount'},
					{header: '污水费欠费日期',dataIndex:'sewageOweDate'}
				]),	
				storeMapping:[
					'name', 'organizationNumber', 'legalPerson', 'userNumber', 'useLocation', 'installDate',
					'installDate', 'deliverAddr', 'useAddr', 'tele', 'mobile', 'status', 'feeAmount', 'oweDate',
					'sewageFeeAmount', 'sewageOweDate'
				]

			},
			winConfig : {
				height: 340
			},
			formConfig:{
				items: [
					{xtype: 'f-text',fieldLabel: '企业名称',name: 'name'}, 
					{xtype: 'f-text',fieldLabel: '组织机构代码',name: 'organizationNumber',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '法人代表',name: 'legalPerson'}, 
					{xtype: 'f-text',fieldLabel: '用户编号',name: 'userNumber'}, 
					{xtype: 'f-text',fieldLabel: '用水地点',name: 'useLocation'}, 
					{xtype: 'f-text',fieldLabel: '初装日期',name: 'installDate'}, 
					{xtype: 'f-text',fieldLabel: '送票地址',name: 'deliverAddr'}, 
					{xtype: 'f-text',fieldLabel: '用水地址',name: 'useAddr'}, 
					{xtype: 'f-text',fieldLabel: '联系电话',name: 'tele'}, 
					{xtype: 'f-text',fieldLabel: '手机号码',name: 'mobile'}, 
					{xtype: 'f-text',fieldLabel: '使用状态',name: 'status'}, 
					{xtype: 'f-text',fieldLabel: '自来水费欠费金额',name: 'feeAmount'}, 
					{xtype: 'f-text',fieldLabel: '自来水费欠费日期',name: 'oweDate'}, 
					{xtype: 'f-text',fieldLabel: '污水费欠费金额',name: 'sewageFeeAmount'}, 
					{xtype: 'f-text',fieldLabel: '污水费欠费日期',name: 'sewageOweDate'}
				]
			},
			url:ctx+'/waterFee'	
		});
		WaterFee.superclass.initComponent.call(this);
	}
	
});
