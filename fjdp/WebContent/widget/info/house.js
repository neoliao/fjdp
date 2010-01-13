ProjectList = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '项目名称',dataIndex:'name'},
					{header: '镇(街)名称', dataIndex: 'area',renderer:dictRenderer},
					{header: '开发商', dataIndex: 'developer',renderer:dictRenderer,hidden:true},
					{header: '物业管理公司', dataIndex: 'propertyCompany',renderer:dictRenderer,hidden:true},
					{header: '项目地址',dataIndex:'address',hidden:true}
				]),	
				storeMapping:[
					'name', 'area', 'address', 'developer', 'propertyCompany'
				]
			},
			buttonConfig: ['->',{
				xtype: 'f-search',
				width: 200,
				emptyText: '请输入项目名称'
			}],					
			url:ctx+'/project'
		});
		ProjectList.superclass.initComponent.call(this);
		
		this.getSelectionModel().on('rowselect', function(model,index,record) {	
			Ext.getCmp('BuildingList').parentId = record.id;
			Ext.getCmp('BuildingList').loadData({parentId: record.id});
		}, this)
	}	
});

BuildingList = Ext.extend(Ext.app.BaseFuncPanel,{
	paging : false,
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '幢名称',dataIndex:'name'}
				]),	
				storeMapping:[
					'name'
				]				
			},
			winConfig : {
				title : '幢信息管理',
				desc : '新增，修改幢信息',
				bigIconClass : 'buildingBigIcon'
			},
			buttonConfig: [
				'all'
			],
			formConfig:{
				items: [
					{xtype: 'f-text',fieldLabel: '幢名称',name: 'name',allowBlank: false}
				]
			},
			listUrl : '/listBuildings',
			url:ctx+'/building'
		});
		BuildingList.superclass.initComponent.call(this);
		
		this.store.on('load', function(store, records, options) {
			var selected = this.getSelectionModel().getSelected();
			
			this.delBt.setDisabled(selected.id == 'all');
			this.editBt.setDisabled(selected.id == 'all');
		}, this),
		
		this.getSelectionModel().on('rowselect', function(model,index,record) {	
			this.delBt.setDisabled(record.id == 'all');
			this.editBt.setDisabled(record.id == 'all');
			
			Ext.getCmp('HouseInfo').projectId = this.parentId;
			Ext.getCmp('HouseInfo').buildingId = record.id;
			Ext.getCmp('HouseInfo').loadData({buildingId: record.id,projectId : this.parentId});
		}, this);
		
		this.on('beforesave',function(){
			this.ajaxParams['projectId'] = this.parentId;
		},this);
	}	
});

HouseInfo = Ext.extend(Ext.app.BaseFuncPanel,{
	itemSize : 100,
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '房号', dataIndex:'houseName'},
					{header: '业主姓名', dataIndex:'ownerName'},
					{header: '住宅类型', dataIndex:'houseType', renderer:dictRenderer},
					{header: '建筑面积', dataIndex:'buildingArea', align:'right'},
					{header: '公摊面积', dataIndex:'publicArea', align:'right'},
					{header: '套内面积', dataIndex:'innerArea', align:'right'},					
					{header: '证件类型', dataIndex:'paperType', renderer:dictRenderer},
					{header: '证件号码', dataIndex:'paperNumber'},
					{header: '联系电话', dataIndex:'telphone'},
					{header: '合同号', dataIndex:'contractNumber'},
					{header: '产权号', dataIndex:'propertyNumber'},
					{header: '维修资金账号', dataIndex:'accountId'},
					{header: '账户余额', dataIndex:'balance'},
					{header: '是否曾足额', dataIndex:'specFlag', renderer: function(value) {
						if(value == 'on') {
							return '是';
						}
					}},
					{header: '地址', dataIndex:'location', width : 350}
				]),	
				storeMapping:[
					'houseName','houseType','buildingArea','publicArea', 'innerArea', 'project', 'building',
					'contractNumber','linkman', 'otherNumber', 'ownerName',
					'paperNumber', 'paperType', 'propertyNumber', 'telephone', 
					'accountId','balance','specFlag', 'location', 'specFlag'
				],
				viewConfig: { emptyText : '没有可用的数据'}
			},
			buttonConfig: ['add', 'edit', '-', '->',{
				xtype: 'f-search',
				width: 200,
				emptyText: '请输入房号'
			}],
			winConfig : {				
				height: 500,width: 860,
				title : '物业信息管理',
				desc : '新增，修改和查看物业信息',
				bigIconClass : 'organizationIcon'
			},
			formConfig:{
				labelWidth: 80,
				autoScroll : true,
				items: [{
					layout: 'column',border: false,
					items : [{
						columnWidth:.45,layout: 'form',border: false,
						items : [
							{xtype:'fieldset',title: '住宅信息',height:290,
								items :[
									{xtype: 'f-project',fieldLabel: '楼盘',id: 'projectId',hiddenName: 'project', allowBlank: false, listeners: {
										change: function(field, newValue, oldValue){
											Ext.getCmp('buildingId').clearValue();
											Ext.getCmp('buildingId').setValue({id: 'all', text: ''});
										}
									}},
									{xtype: 'f-building',fieldLabel: '幢',id: 'buildingId',hiddenName: 'building',relativeField : 'projectId'},
									{xtype: 'f-text', fieldLabel: '房号',id: 'houseNameField', name: 'houseName', allowBlank: false},
									{xtype: 'f-dict', fieldLabel: '住宅类型', hiddenName: 'houseType', kind : 'houseType', allowBlank: false},
									{xtype: 'f-number', fieldLabel: '建筑面积', name: 'buildingArea', allowBlank: false, decimalPrecision: 3},	
									{xtype: 'f-number', fieldLabel: '套内面积', name: 'innerArea', decimalPrecision: 3},
									{xtype: 'f-number', fieldLabel: '公摊面积', name: 'publicArea', decimalPrecision: 3},
									{xtype: 'f-textarea' ,fieldLabel: '地址', name: 'location', allowBlank: false}
								]
							}
						] 
					},{
						columnWidth:.45,layout: 'form',border: false,style : 'margin-left:10px;',
						items : [
						{xtype:'fieldset',title: '产权及资金信息',height:290,
								items :[
									{xtype: 'f-text', fieldLabel: '业主姓名', name: 'ownerName'},
									{xtype: 'f-dict', fieldLabel: '证件类型', hiddenName: 'paperType',kind : 'paperType'},	
									{xtype: 'f-text', fieldLabel: '证件号码', name: 'paperNumber'},
									{xtype: 'f-text', fieldLabel: '联系电话', name: 'telephone'},
									{xtype: 'f-text', fieldLabel: '合同号', name: 'contractNumber'},
									{xtype: 'f-text', fieldLabel: '产权号', name: 'propertyNumber'},
									{xtype: 'checkbox', fieldLabel: '是否曾足额', name: 'specFlag'},
									{xtype: 'f-text', fieldLabel: '维修资金账号', name: 'accountId', readOnly: true},
									{xtype: 'f-text', fieldLabel: '账户余额', name: 'balance',readOnly: true}
								]
							}
						] 
					}]
				}]
			},
			url:ctx+'/house'
		});
		HouseInfo.superclass.initComponent.call(this);
		
		this.on('winshow',function(){
			var r = new this.recordConfig({
				project: this.getProject(),
				building: this.getBuilding()				
			});
			this.win.formPanel.getForm().loadRecord(r);
		},this);
		
		this.getProject = function() {
			return {
				id : Ext.getCmp('ProjectList').getSelectionModel().getSelected().id,
				text : Ext.getCmp('ProjectList').getSelectionModel().getSelected().data.name
			}
		}
		
		this.getBuilding = function() {
			if(this.saveType == 'edit') {
				return {
					id: this.getSelectionModel().getSelected().id,
					text: this.getSelectionModel().getSelected().data.name
				}
			}
			else {
				var buildingName = Ext.getCmp('BuildingList').getSelectionModel().getSelected().data.name;
				return {
					id: Ext.getCmp('BuildingList').getSelectionModel().getSelected().id,
					text: buildingName == '全部' ? '' : buildingName
				}
			}
		}	
	}
});

House = Ext.extend(Ext.Panel, {
	layout: 'border',
	closable: true,
	initComponent: function() {
		this.left = new Ext.Panel({
			funcCode: this.funcCode,
			region: 'west',
			split: true,
			width: 370,
			minSize: 175,
			maxSize: 500,
			border : false,
			collapsible: true,
			collapseMode:'mini',
			layout: 'border',
			items : [
				new ProjectList({
					id: 'ProjectList',
					funcCode: this.funcCode,
					region: 'west',
					title: '项目列表',
					region: 'north',
					split: true,
					collapsible: true,
					height: 350,
					minSize: 175,
					maxSize: 600
				}),
				new BuildingList({
					id: 'BuildingList',
					funcCode: this.funcCode,
					region: 'center',
					title: '幢列表'
				})
			]
		
		});
		this.right = new HouseInfo({
			id: 'HouseInfo',
			funcCode: this.funcCode,
			region: 'center',
			title: '物业信息'			
		});
		this.items = [this.left, this.right];
		House.superclass.initComponent.call(this);
	},
	loadData: function() {
		Ext.getCmp('ProjectList').loadData();
	}
});