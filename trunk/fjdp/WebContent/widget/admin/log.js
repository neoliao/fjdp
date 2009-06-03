Log = Ext.extend(Ext.app.BaseFuncPanel,{

	initComponent : function(){
		Ext.apply(this,{
			url:ctx+'/log',
			gridConfig:{
				sm:new Ext.grid.RowSelectionModel({singleSelect:true}),
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '操作时间',dataIndex:'createTime'},
					{header: '用户',dataIndex:'opUser',renderer:dictRenderer},
					{header: '操作类型',dataIndex:'opType'},
					{header: '内容',dataIndex:'contents',width:400}
				]),	
				storeMapping:[
					'createTime', 
					'opUser',
					'contents',
					'opType'
				]
			},
			buttonConfig : [
				'->','查询 :',{
					xtype : 'f-search',
					emptyText : '请输入日期，用户名，操作类型等查询条件'						
				}
			]
		});
		
		Log.superclass.initComponent.call(this);
	}
});