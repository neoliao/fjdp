
Area = Ext.extend(Ext.app.BaseFuncPanel,{
	paging : false,
	initComponent : function(){
		Ext.apply(this,{
			gridConfig:{
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '镇(街)编码',dataIndex:'code'},
					{header: '镇(街)名称',dataIndex:'name'}
				]),	
				storeMapping:[
					'code','name'
				]
			},
			winConfig : {
				height: 220,
				desc : '新增，修改和镇(街)信息',
				bigIconClass : 'areaBigIcon'
			},
			formConfig:{
				items: [
					{xtype: 'f-text',fieldLabel: '镇(街)编码',name: 'code',allowBlank: false},
					{xtype: 'f-text',fieldLabel: '镇(街)名称',name: 'name',allowBlank: false}
 
				]
			},
			url:ctx+'/area'	
		});
		Area.superclass.initComponent.call(this);
	}
	
});
