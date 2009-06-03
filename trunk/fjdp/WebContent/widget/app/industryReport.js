IndustryReport = Ext.extend(Ext.Panel,{
	bodyStyle: 'padding: 20px ',
	initComponent : function(){
		Ext.apply(this,{
			tbar : ['行业：',{
				id : 'IndustryReport-dict',
				xtype : 'f-dict',
				kind:'industry',
				emptyText:'请选择一个行业',
				listeners : {
					'select' : this.loadData,
					scope: this
				}
			},'->',{
				text : '导入报告',
				iconCls : 'importFile',
				scope : this,
				privilegeCode : this.funcCode+'_import',
				handler : this.importData
			}],
			border: true,
			closable: true
		});
		IndustryReport.superclass.initComponent.call(this);
	},
	loadData : function(){
		var industry = Ext.getCmp('IndustryReport-dict').getValue();
		this.getUpdater().update({
			url : ctx +'/industryReport/reportList',
			params : industry ? { query : industry } : {}
		});
	
	},
	importData : function(){
		var importWin = new Ext.app.FormWindow({
			title : '数据导入',
			winConfig　: {
				height : 200,width : 390
			},
			formConfig :{
				fileUpload : true,
				items : [
					{xtype:'f-year',fieldLabel:'年份',name:'year',allowBlank: false},
					{xtype: 'f-dict',fieldLabel: '行业类别',hiddenName: 'industry',kind: 'industry',allowBlank: false,emptyText:'请选择一个行业'},
					{xtype:'f-upload',fieldLabel:'报告文件',name:'importFile',allowBlank: false},
					{xtype:'panel',border:false,bodyStyle :'padding:10 0 0 25;color:grey;',
						html:'<p>该功能导入行业报告，导入文件类型是一个Word文件</p>'}
				]
			},
			buttons : [{
				id : 'industry-importBt',
				text : '确认',
				scope : this,
				handler : function(){
					Ext.getCmp('industry-importBt').disable();
					importWin.formPanel.getForm().submit({           
			            waitMsg:'提交中...',
						url: ctx+'/industryReport/add',
						params: {id : this.selectedId },
						scope:this,
						success:function(form, action) {
							importWin.close();
							App.msg('数据成功上传');
			            },        	
			            failure:function(form, action) {
			            	Ext.getCmp('industry-importBt').enable();
			            	App.msg('数据上传失败!','error');
			            }
			        });
				}
			}]
		});
		importWin.show();
	}
	
});