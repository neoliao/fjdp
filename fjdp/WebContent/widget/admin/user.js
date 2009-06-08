
/**
 * 根据用户选择角色
 * 
 */
Ext.app.RoleSelect = Ext.extend(Ext.app.MultiSelectField, {
	initComponent : function(){	
		
		this.store = new Ext.data.JsonStore({
		    url: ctx+'/role/getRolesByUser',
			root:'data',
		    fields: ['id', 'text','checked']			
		});
		
		Ext.app.RoleSelect.superclass.initComponent.call(this);
		
		this.store.on('beforeload',function(store,o){
			this.store.baseParams['id'] = Ext.getCmp('User').saveId;
		},this);
	}
});
Ext.reg('f-roleByUser', Ext.app.RoleSelect);

User = Ext.extend(Ext.app.BaseFuncPanel,{
	
	initComponent : function(){
		var rolesRender = function(v){
			var re = [];
			for(var r in v){
				if(v[r].text){
					re.push(v[r].text);
				}		
			}
			return re.join(',');
		}
		
		var lockedRender = function(v){		
			return v == true ?'<span style="color:red">已锁定</span>' : '';
		}
		Ext.apply(this,{
			url:ctx+'/user',
			gridConfig: {
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '用户名',dataIndex:'userName',sortable:true},
					{header: '用户显示名',dataIndex:'userDisplayName',sortable:true},
					{header: '员工姓名',dataIndex:'employee',renderer : dictRenderer},
					{header: '锁定',dataIndex:'locked',renderer:lockedRender},
					{header: '所属角色',dataIndex:'roles',renderer:rolesRender,width:400}
				]),	
				storeMapping:[
					'id','userName','userDisplayName','employee','roles','locked'
				]
			},
			winConfig : {
				height: 370, width : 390
			},
			formConfig:{
				items: [
					{xtype:'fieldset',title: '对应员工',autoHeight:true,
						items :[
							{xtype: 'f-select',dataUrl:'/employee/getEmployeesUnAssign',storeFields:['id','text','code'],fieldLabel: '员工姓名',hiddenName: 'employee',id:'employeeSelect',allowBlank: false,emptyText: '请选择一个员工',listeners : {}}
						]
					},
					{xtype:'fieldset',title: '登陆信息',autoHeight:true,
						items :[
							{xtype: 'f-text',fieldLabel: '用户名',id: 'userName',name: 'userName',allowBlank: false},
							{xtype: 'f-text',fieldLabel: '用户显示名',id: 'userDisplayName',name: 'userDisplayName',allowBlank: false},
							{xtype:'panel',id:'passwordPanel',autoHeight:true,border:false,layout:'form',
								 items :[
									{xtype: 'f-text',fieldLabel: '密码',id:'pswd',name: 'password',inputType:'password',allowBlank: false},
									{xtype:'f-text',fieldLabel:'确认密码',id:'pswdComfirm',name:'password2',inputType:'password',vtype: 'password',initialPassField: 'pswd',allowBlank: false}
								]
							},
							{xtype:'panel',id:'resetPanel',autoHeight:true,border:false, buttonAlign:'center',hidden:true,
								 buttons :[
									{xtype:'f-button',text: '重设密码',iconCls:'key',scope:this,handler:this.resetPassword}			
								]
							}
						]
					},
					{xtype:'fieldset',title: '选择用户角色',autoHeight:true,
						items :[
							{xtype:'f-roleByUser',fieldLabel: '用户角色',hiddenName:'roles',emptyText: '请选择一个或多个用户角色',allowBlank: false}						
						]
					}
				]
			},
			buttonConfig : ['all',{
				text:'锁定',
				iconCls:'lock',
				id:'lockUserBt',
				prililegeCode:this.funcCode+'_lock',
				scope:this,
				handler:this.lockUser
			},{
				text:'解锁',
				iconCls:'key',
				id:'unlockUserBt',
				prililegeCode:this.funcCode+'_lock',
				scope:this,
				handler:this.lockUser,
				hidden:true
			}]
		});
		User.superclass.initComponent.call(this);

		this.getSelectionModel().on('rowselect',function(sm,rowIndex,record){
			var flag = sm.getSelected().data.locked;
			Ext.getCmp('lockUserBt').setVisible(!flag);
			Ext.getCmp('unlockUserBt').setVisible(flag);
		},this); 
		
		this.on('winshow',function(grid){
			if(this.saveType == 'update'){
				Ext.getCmp('passwordPanel').setVisible(false).setDisabled(true);
				Ext.getCmp('resetPanel').setVisible(true).setDisabled(false);
				Ext.getCmp('pswd').setDisabled(true);
				Ext.getCmp('pswdComfirm').setDisabled(true);
				Ext.getCmp('employeeSelect').setReadOnly();
				Ext.getCmp('userName').setReadOnly();
			}
			Ext.getCmp('employeeSelect').on('select',function(combo,record,index){
				Ext.getCmp('userName').setValue(record.data.code);
				Ext.getCmp('userDisplayName').setValue(record.data.text);
			},this);
		},this);
	},
	resetPassword : function(){
		this.resetWin = new Ext.app.FormWindow({
			iconCls : 'key',
			title : '重设密码',
			winConfig : {
				height : 150,
				width : 395
			},
			formConfig : {
				items : [
		 			{xtype: 'f-text',fieldLabel: '密码',id:'pswd2',name: 'password',inputType:'password',allowBlank: false},
					{xtype:'f-text',fieldLabel:'确认密码',id:'pswdComfirm2',name:'password2',inputType:'password',vtype: 'password',initialPassField: 'pswd2',allowBlank: false}
				]
			},
			buttons : [{
				text: '确定',
				scope:this,
				handler : function(){
					this.resetWin.formPanel.getForm().submit({           
			            waitMsg:'保存中...',
						url:this.url+'/resetPassword',
						params: { id :this.getSelectionModel().getSelected().id },
						scope:this,
						success:function(form, action) {
							this.resetWin.close();
							App.msg("密码设置成功！");
			            }
			        });
				}
			}]
		});
		this.resetWin.show();
	},
	lockUser : function(){
		Ext.Ajax.request({
			url:this.url+'/lockUser',
			params: { id :this.getSelectionModel().getSelected().id },
			scope:this,
			success:function(response, options) {
				this.loadData();
			}
		});
	}
});