
${modelName} = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '姓名',dataIndex:'name',sortable:true},
					{header: '工号',dataIndex:'code',sortable:true},
					{header: '性别',dataIndex:'sex',renderer:dictRenderer},
					{header: '入职日期',dataIndex:'hireDate'},
					{header: '办公电话',dataIndex:'phone'},
					{header: '手机',dataIndex:'mobile'},
					{header: 'qq',dataIndex:'qq'},
					{header: '在职情况',dataIndex:'status',renderer:employeeStatus},
					{header: '电子邮件',dataIndex:'email',renderer:emailLink}
				]),	
				storeMapping:[
					'code', 'name', 'sex', 'phone', 'mobile', 'status', 'qq', 'hireDate', 'email'
				]
			},
			winConfig : {
				height: 330
			},
			formConfig:{
				items: [
					{xtype: 'f-text',fieldLabel: '姓名',name: 'name',emptyText: '请输入员工姓名',allowBlank: false}, 
					{xtype: 'f-text',fieldLabel: '工号',name: 'code',vtype: 'digital',allowBlank: false},
					{xtype: 'f-dict',fieldLabel: '性别',hiddenName: 'sex',kind: 'sex'}, 
					{xtype: 'f-text',fieldLabel: '电子邮件',name: 'email',vtype: 'email'},
					{xtype: 'f-text',fieldLabel: '办公电话',name: 'phone'},
					{xtype: 'f-text',fieldLabel: '手机',name: 'mobile'},
					{xtype: 'f-text',fieldLabel: 'qq',name: 'qq',vtype: 'digital'},
					{xtype: 'f-date',fieldLabel: '入职日期',name: 'hireDate'},
					{xtype: 'f-dict',fieldLabel: '在职情况',hiddenName: 'status',kind: 'employeeStatus'}
				]
			},
			url:ctx+'/${modelNameLower}'	
		});
		${modelName}.superclass.initComponent.call(this);
	}
	
});
