
RelativeOrganization = Ext.extend(Ext.app.GridSelect,{
	initComponent : function(){
		Ext.apply(this,{
			cm:new Ext.grid.ColumnModel([
				new Ext.grid.CheckboxSelectionModel(),
				{header: '机构名称',dataIndex:'name',width: 250,sortable:true},
				{header: '机构代码',dataIndex:'code'}
			]),	
			storeMapping:[
				'name', 'code','checked'
			],
			url:ctx+'/enterprise/listRelatives'
		});
		RelativeOrganization.superclass.initComponent.call(this);
		
	}
});


Enterprise = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '企业名称',dataIndex:'name',width: 250},
					{header: '组织机构代码',dataIndex:'organizationNumber'},
					{header: '法人代表',dataIndex:'legalPerson'},
					{header: '贷款卡号',dataIndex:'loanCardNumber'},
					{header: '注册资本',dataIndex:'registerCaptital'},
					{header: '所属行业',dataIndex:'industry',renderer:dictRenderer,width: 160}
				]),	
				storeMapping:[
					'name', 'organizationNumber', 'legalPerson', 'loanCardNumber', 'registerCaptital', 'industry'
				]
			},
			buttonConfig : ['all',{
				iconCls : 'importFile',
				text: '导入数据',
				privilegeCode : this.funcCode+'_import',
				scope : this,
				handler : this.importFile
			},'-',{
				text: '相关机构',
				privilegeCode : this.funcCode+'_relative',
				iconCls : 'collapse-all',
				scope : this,
				handler : this.setRelative
			}],
			winConfig : {
				height: 340,width: 385
			},
			formConfig:{
				labelWidth: 90,
				items: [
					{xtype: 'f-text',fieldLabel: '企业名称',name: 'name'},  
					{xtype: 'f-text',fieldLabel: '组织机构代码',name: 'organizationNumber',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '法人代表',name: 'legalPerson'}, 
					{xtype: 'f-text',fieldLabel: '贷款卡号',name: 'loanCardNumber'},
					{xtype: 'f-text',fieldLabel: '注册资本',name: 'registerCaptital',allowBlank: false},
					{xtype: 'f-dict',fieldLabel: '所属行业',hiddenName: 'industry',kind:'industry',allowBlank: false}
				]
			},
			url:ctx+'/enterprise'	
		});
		Enterprise.superclass.initComponent.call(this);
		
	},
	importFile : function(){
		var importWin = new Ext.app.FormWindow({
			title : '数据导入',
			winConfig　: {
				height : 300,width : 390
			},
			formConfig :{
				fileUpload : true,
				items : [
					{xtype: 'f-radiogroup',fieldLabel: '数据类型',id:'dataType',columns: 1,
						items: [
							{boxLabel: '非银行信息', id:'warningData',name:'dataType',inputValue: 'warningData' ,checked: true},
						    {boxLabel: '财务数据', name:'dataType',inputValue: 'fiancialData'},
						    {boxLabel: '评估报告', name:'dataType',inputValue: 'appraisalReport'}
						]
					},
					{xtype:'f-upload',fieldLabel:'数据文件',name:'importFile'},
					{xtype:'panel',border:false,bodyStyle :'padding:10 0 0 25;color:grey;',
						html:'<p>该功能导入跟这个企业相关的数据，请选择相应的数据类型和导入文件，导入文件类型是一个Excel文件</p>'}
				]
			},
			buttons : [{
				id : 'enterprise-importBt',
				text : '确认',
				scope : this,
				handler : function(){
					Ext.getCmp('enterprise-importBt').disable();
					importWin.formPanel.getForm().submit({           
			            waitMsg:'提交中...',
						url:this.url+'/import'+Ext.getCmp('warningData').getGroupValue(),
						params: {id : this.selectedId },
						scope:this,
						success:function(form, action) {
							importWin.close();
							App.msg('数据成功上传');
			            },        	
			            failure:function(form, action) {
			            	Ext.getCmp('enterprise-importBt').enable();
			            	App.msg('数据上传失败!','error');
			            }
			        });
				}
			}]
		});
		importWin.show();
	},
	setRelative : function(){
		this.relativeWin = new Ext.Window({
			title : '相关机构',
			height : 300,
			width : 400,
			layout : 'fit',
			modal : true,
			items : new RelativeOrganization({
				id : 'enterprise-relatives'
			})
		});
		this.relativeWin.show();
		var grid = Ext.getCmp('enterprise-relatives');
		grid.loadData({id : this.selectedId});
		grid.on('savedata',function(grid){
			this.saveRelatives();
		},this);
	},
	saveRelatives : function(){
		Ext.Ajax.request({
		   url: this.url + '/saveRelatives',
		   params: { id : this.selectedId,checkedIds : Ext.getCmp('enterprise-relatives').checkedData},
		   scope : this,
		   success: function(){
		   		this.relativeWin.close();
		   		App.msg('保存成功');
		   }
		});
	}
	
});
