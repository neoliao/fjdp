/*
CommunityList = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '楼盘名称',dataIndex:'name'}
				]),	
				storeMapping:[
					'id', 'name'
				]				
			},
			buttonConfig: ['all','-', '->',{
				xtype: 'f-search',
				width: 130,
				emptyText: '请输入楼盘名称'
			}],
			winConfig : {
				height: 200,
				title : '楼盘信息管理',
				desc : '新增，修改和楼盘信息',
				bigIconClass : 'projectBigIcon'
			},
			formConfig:{
				items: [
					{xtype: 'f-text', fieldLabel: '楼盘名称',name: 'name', allowBlank: false}
				]
			},
			url:ctx+'/community'
		});
		CommunityList.superclass.initComponent.call(this);
		
		this.getSelectionModel().on('rowselect', function(model,index,record) {			
			Ext.getCmp('CommunityProject').parentId = record.id;	
			Ext.getCmp('CommunityProject').loadData({communityId: record.id});
		}, this)
	}	
});

CommunityProject = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '项目名称',dataIndex:'name',width:150},
					{header: '镇(街)名称', dataIndex: 'area',renderer:dictRenderer,width:100},
					{header: '项目地址',dataIndex:'address',width:200},
					{header: '开发商', dataIndex: 'developer',renderer:dictRenderer,width:150},
					{header: '物业管理公司', dataIndex: 'propertyCompany',renderer:dictRenderer,width:150}
				]),	
				storeMapping:[
					'name', 'area', 'address', 'developer', 'propertyCompany'
				],
				viewConfig: { emptyText : '没有可用的数据'}
			},
			buttonConfig: ['all'],
			winConfig : {
				height: 330,
				title : '项目信息管理',
				desc : '新增，修改和项目信息',
				bigIconClass : 'projectBigIcon'
			},
			formConfig:{
				items: [
					{xtype: 'f-text',fieldLabel: '项目名称',name: 'name', allowBlank: false},
					{xtype: 'f-area',fieldLabel: '镇(街)',hiddenName: 'area',emptyText:'请输入镇(街)名称或者名称拼音缩写',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '项目地址',name: 'address'},					
					{xtype: 'f-text',fieldLabel: '楼栋总数',name: 'buildingCount'},
					{xtype: 'f-property',fieldLabel: '物管公司',hiddenName: 'propertyCompany',emptyText:'请输入物业公司名称或者名称拼音缩写',allowBlank: false},
					{xtype: 'f-developer',fieldLabel: '开发商',hiddenName: 'developer',emptyText:'请输入开发商名称或者名称拼音缩写',allowBlank: false}
				]
			},
			
			paging: false,
			listUrl : '/listByComnunity',
			url:ctx+'/project'
		});
		CommunityProject.superclass.initComponent.call(this);
	}	
});

Community = Ext.extend(Ext.Panel, {
	layout: 'border',
	closable: true,
	initComponent: function() {
		this.left = new CommunityList({
			id: 'CommunityList',
			funcCode: this.funcCode,
			region: 'west',
			title: '楼盘列表',
			split: true,
			collapsible: true,
			width: 350,
			minSize: 175,
			maxSize: 500
		});
		this.right = new CommunityProject({
			id: 'CommunityProject',
			funcCode: this.funcCode,
			region: 'center',
			title: '项目列表'			
		});
		this.items = [this.left, this.right];
		Community.superclass.initComponent.call(this);
	},
	loadData: function() {
		this.left.loadData();
	}
});*/

Community = Ext.extend(Ext.app.BaseFuncPanel,{
	closable: true,
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '项目名称',dataIndex:'name',width:150},
					{header: '镇(街)名称', dataIndex: 'area',renderer:dictRenderer,width:100},
					{header: '项目地址',dataIndex:'address',width:200},
					{header: '开发商', dataIndex: 'developer',renderer:dictRenderer,width:150},
					{header: '物业管理公司', dataIndex: 'propertyCompany',renderer:dictRenderer,width:150}
				]),	
				storeMapping:[
					'name', 'area', 'address', 'developer', 'propertyCompany'
				]
			},
			buttonConfig: ['all','-', '->',{
				xtype: 'f-search',
				emptyText: '请输入项目名称'
			}],
			winConfig : {
				height: 330,
				title : '项目信息管理',
				desc : '新增，修改和项目信息',
				bigIconClass : 'projectBigIcon'
			},
			formConfig:{
				items: [
					{xtype: 'f-text',fieldLabel: '项目名称',name: 'name', allowBlank: false},
					{xtype: 'f-area',fieldLabel: '镇(街)',hiddenName: 'area',emptyText:'请输入镇(街)名称或者名称拼音缩写',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '项目地址',name: 'address'},					
					{xtype: 'f-text',fieldLabel: '楼栋总数',name: 'buildingCount'},
					{xtype: 'f-property',fieldLabel: '物管公司',hiddenName: 'propertyCompany',emptyText:'请输入物业公司名称或者名称拼音缩写'},
					{xtype: 'f-developer',fieldLabel: '开发商',hiddenName: 'developer',emptyText:'请输入开发商名称或者名称拼音缩写'}
				]
			},
			url:ctx+'/project'
		});
		Community.superclass.initComponent.call(this);
	}	
});