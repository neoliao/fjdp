CodeGenerateFieldGrid = Ext.extend(Ext.grid.EditorGridPanel,{
    clicksToEdit:1,
    frame : true,
    title : '字段列表',
    style : 'padding : 20px;',
    height : 300,
    initComponent : function(){
    	
    	this.FieldItem = Ext.data.Record.create([
           {name: 'type'},
           {name: 'label'},
           {name: 'name'},
           {name: 'extend'},
           {name: 'allowBlank'}
		]);
		
		Ext.apply(this,{
			store : new Ext.data.JsonStore({
				root: 'root',
		        fields: this.FieldItem,
		        data : {root : []}
			}),
    		cm: new Ext.grid.ColumnModel([{
	        	id:'type',header: "字段类型",dataIndex: 'type',width: 150,
	        	editor: new Ext.app.SelectField({
	        		data : [['text','text'],['dict','dict'],['date','date'],
	        				['time','time'],['dataTime','dataTime'],['textArea','textArea'],
	        				['select','select'],['number','number'],['upload','upload']],
	           		allowBlank: false
	           })
	        },{
	        	header: "扩展属性 kind/data/maxLength",dataIndex: 'extend',width: 200,
	        	editor: new Ext.app.TextField()
	        },{
	        	header: "字段标签",dataIndex: 'label',width: 150,
	        	editor: new Ext.app.TextField({allowBlank: false})
	        },{
	        	header: "字段名",dataIndex: 'name',width: 150,
	        	editor: new Ext.app.TextField({allowBlank: false})
	        },{
	        	header: "能否为空",dataIndex: 'allowBlank',width: 150,
	        	editor: new Ext.app.SelectField({
	        		data : [['yes','可以为空'],['no','不能为空']],allowBlank: false
	           })
	        }]),
	        tbar: [{
	            text: '新增',
	            iconCls : 'add',
	            scope : this,
	            handler : this.add
	        },
	        {
	            text: '删除',
	            iconCls : 'remove',
	            scope : this,
	            handler : this.del
	        }]
		});
		CodeGenerateFieldGrid.superclass.initComponent.call(this);
	},
	add : function(){
        var item = new this.FieldItem({
        	type : 'text',
        	name : 'name',
        	label : '标签',
        	extend : '20',
        	allowBlank : 'yes'
        });
        this.stopEditing();
        this.store.insert(0, item);
        //this.startEditing(0, 0);
    }
	
});

CodeGenerate = Ext.extend(Ext.form.FormPanel,{
	bodyStyle : 'padding: 20px;',
	closable : true,
	border : true,
	frame:true,
	initComponent : function(){
		
		Ext.apply(this,{
			items: [
				{xtype: 'f-text',fieldLabel: '包前缀',name: 'packagePrefix',value: 'com.fortunes',allowBlank: false},
				{xtype: 'panel', border : false, bodyStyle :'padding:7 0 10 10;color:grey;',
					html: '包前缀　：com.fortunes.+项目名+.子模块名    例如com.fortunes.levws.info 如果是小项目，可以不分子模块,如 com.fortunes.levws'},
				{xtype: 'f-text',fieldLabel: '模型名',name: 'modelName',allowBlank: false},
				{xtype: 'panel', border : false, bodyStyle :'padding:7 0 10 10;color:grey;',
					html: '实体名称,对应数据库中的一个表，如Employee,User等'},
				{xtype: 'f-select',data : [['Long','Long'],['String','String']],fieldLabel: '实体ID类型',hiddenName: 'idType',value:'Long',allowBlank: false},
				{xtype: 'panel', border : false, bodyStyle :'padding:7 0 15 10;color:grey;',
					html: '一个实体Id的类型,long对于数据库中的int型，String对于varchar'},
				new CodeGenerateFieldGrid({id : 'CodeGenerateFieldGrid' })
			],
			buttonAlign : 'center',
			buttons : [{
				text : '生成',
				scope : this,
				handler : this.generate
			}]
		});
		CodeGenerate.superclass.initComponent.call(this);
	},
	loadData : function(){
		
	},
	generate : function(){
		var grid = Ext.getCmp('CodeGenerateFieldGrid');
		grid.stopEditing();
		grid.store.commitChanges();
		var formParmas =  {
			fieldTypes : [],
			fieldNames : [],
			fieldLabels : [],
			fieldExtend : [],
			fieldAllowBlank : []
		}
		grid.store.each(function(record){
			formParmas['fieldTypes'].push(record.data['type']);
			formParmas['fieldNames'].push(record.data['name']);
			formParmas['fieldLabels'].push(record.data['label']);
			formParmas['fieldExtend'].push(record.data['extend']);
			formParmas['fieldAllowBlank'].push(record.data['allowBlank']);
		});
		this.getForm().submit({
			url : ctx + '/codeGenerate/generate',
			params : formParmas,
			success: function(form,action){
				//App.msg(action.result.msg);
			}
		})
	}
	
});
