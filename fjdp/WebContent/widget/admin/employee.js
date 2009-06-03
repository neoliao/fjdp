
Employee = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		
		var emailLink = function(v){
		    return !v? "" : String.format('<span><a href="mailto:{0}" target="_blank" class="emailLink">{0}</a></span>',v);
		}
		
		var qq = function(v){
			return !v ? "" : String.format('<span><a target=blank href=tencent://message/?uin={0}><img border="0" SRC=http://wpa.qq.com/pa?p=1:{0}:5 alt="QQ号:{0}"></a></span>',v);
		}
		
		var employeeStatus = function(v){
			var text = v['text']||'';
			var map = {
				'离职' : 'red',
				'试用' : 'blue'
			}	 
			return String.format('<span style="color:{0}">{1}</span>',map.text||'black',text);
		}
		
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
			url:ctx+'/employee'	
		});
		Employee.superclass.initComponent.call(this);
				
	}
	
});
