ProcessDefinition = Ext.extend(Ext.app.BaseFuncPanel, {
	region: 'north',
	title: '流程定义列表',
	height: 200,
	split: true,
	
	initComponent: function() {
		Ext.apply(this, {
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '流程定义名称',dataIndex:'name'},
					{header: '流程定义key',dataIndex:'key'},
					{header: '流程版本',dataIndex:'version'},
					{header: '流程导入时间',dataIndex:'date'}
				]),	
				storeMapping:[
					'id', 'name', 'key', 'version', 'date'					
				]				
			},			
			buttonConfig: [{
				id: 'btProcessDefinition',
				xtype: 'button',
				enableOnEmpty : true,
				text: '导入流程定义',
				iconCls: 'importFile',
				scope : this,
				handler: this.importFile
			}],
			paging: false,
			url: ctx+'/processManager',
			listUrl: '/getDefinitions'
		});
		ProcessDefinition.superclass.initComponent.call(this);
		
		this.getSelectionModel().on('selectionchange', function(model) {
			if(model.hasSelection()) {
				Ext.getCmp('ProcessInstance').store.baseParams = {id: model.getSelected().get('id'), limit: this.itemSize};
				Ext.getCmp('ProcessInstance').store.reload();
			}			
		}, this)
	},	
	
	importFile: function() {
		var importWin = new Ext.app.FormWindow({
			title: '导入流程定义',
			winConfig: {height: 220, width: 400, title: '导入流程定义'},
			formConfig: {
				fileUpload: true,
				items: [{
					xtype: 'f-upload', fieldLabel: '流程定义文件', name: 'importFile'
				}, {
					xtype: 'panel', border:false, bodyStyle :'padding:10 0 0 25;color:grey;',
					html:'<p>流程定义文件名需要以".jpdl.xml"为后缀</p>'
				}]
			},
			buttons: [{
				id : 'enterprise-importBt',
				text: '确认',
				scope: this,
				handler: function() {
					importWin.formPanel.getForm().submit({
						scope: this,
						waitMsg:'提交中...',
						url:this.url+'/importProcessDefinition',
						success:function(form, action) {
							importWin.close();
							App.msg('数据成功上传');
							this.loadData();
			            },        	
			            failure:function(form, action) {
			            	Ext.getCmp('enterprise-importBt').enable();
			            	App.msg('数据上传失败!','error');
			            }
					}) 
				}
			}]
		});
		importWin.show();
	}
});

ProcessInstance = Ext.extend(Ext.app.BaseFuncPanel, {
	region: 'center',
	title: '流程实例列表',
	
	initComponent: function() {
		Ext.apply(this, {
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '发起时间',dataIndex:'date'},
					{header: '目前状态',dataIndex:'state'}
				]),	
				storeMapping:[
					'date', 'state'			
				]				
			},	
			buttonConfig: ['查看实例状态'],
						
			url: ctx+'/processManager',
			listUrl: '/getInstances'
		});
		ProcessInstance.superclass.initComponent.call(this);
	}
})

ProcessManager = Ext.extend(Ext.Panel, {
	layout: 'border',
	closable: true,
	
	initComponent: function() {
		this.definitionPanel = new ProcessDefinition();
		this.instancePanel = new ProcessInstance({id: 'ProcessInstance'});
		
		this.items = [
			this.definitionPanel,
			this.instancePanel
		];		
		ProcessManager.superclass.initComponent.call(this);
	},
	
	loadData: function() {
		this.definitionPanel.loadData();
	}
});