
Ext.app.GridSelect = Ext.extend(Ext.grid.GridPanel, {
	saveText : '确定',
	buttonAlign : 'center',
	autoScroll:true,
	loadMask: true,
	showSaveButton : true,
	initComponent : function(){
		this.store = new Ext.data.JsonStore({
		    url: this.url,
			root: 'data',
	        totalProperty: 'totalCount',
	        id: 'id',
	        fields: Ext.data.Record.create(this.storeMapping)
		});
		
		if(this.showSaveButton){
			this.saveBt = new Ext.app.Button({
				text : this.saveText,
				minWidth:75,
				disabled : true,
				scope : this,
				handler : this.saveData
			});
			this.buttons = [this.saveBt];
		}
		
		Ext.applyIf(this, {    
	        sm: new Ext.grid.CheckboxSelectionModel(),        
	        viewConfig: { forceFit : true ,emptyText : '没有可用的数据'},
	        store : this.store
		});
		
		Ext.app.GridSelect.superclass.initComponent.call(this);
		
		//添加事件
		this.addEvents(
			'savedata'
        );
        
		this.store.on('load',function(store,records,options){
			var records = [];
			this.store.each(function(record){
				if(record.data.checked){
					records.push(record);
				}
			});
			this.getSelectionModel().selectRecords(records,false);
			this.getSelectionModel().on('selectionchange',function(){
				if(this.saveBt)
					this.saveBt.enable();
				var checkedIds = [];
				var checkedRecords = this.getSelectionModel().getSelections();
				for(var i = 0;i < checkedRecords.length; i++){
					checkedIds.push(checkedRecords[i].id);
				}
				this.checkedData = checkedIds;
			},this);
		},this); 
		
    },
	loadData : function(o){
		if(o)
			Ext.apply(this.store.baseParams,o);
		this.store.load();
	},
	saveData : function(){
		this.fireEvent('savedata', this);
	}
});