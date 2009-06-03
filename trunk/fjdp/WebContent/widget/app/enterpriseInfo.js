
EnterpriseList = Ext.extend(Ext.app.BaseFuncPanel,{
	paging:false,
	initComponent : function(){
		Ext.apply(this, {								
			gridConfig: {
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '企业名称',dataIndex:'name',sortable:true},
					{header: '组织机构代码',dataIndex:'organizationNumber'}
				]),	
				storeMapping:[
					'id','name','organizationNumber'
				]
			},
			buttonConfig : ['->','查询: ',{
				width : 180,
				xtype : 'f-search',
				emptyText : '输入企业名称或组织机构代码'
			}],
			url : ctx+'/enterprise',
			listUrl : '/listByOrganization'
		});		
    	EnterpriseList.superclass.initComponent.call(this);
    	
    	this.getSelectionModel().on('rowselect',function(sm,rowIndex,record){
			Ext.getCmp('EnterpriseInfoPanel').loadInfo(this.selectedId);
		},this); 
    }
});

WarningInfo = Ext.extend(Ext.Panel,{
	initComponent : function(){
		Ext.apply(this,{
			title : '预警信息',
			bodyStyle: 'padding: 20px ',
			tbar : ['年份：',{
				xtype : 'f-year'
			}]
		});
		
		WarningInfo.superclass.initComponent.call(this);
		
		this.on('activate',function(p){
			if(Ext.getCmp('EnterpriseInfo-EnterpriseList').selectedId)
				p.loadInfo();
		},this);
	},
	loadInfo : function(){
		this.getUpdater().update({
			url : ctx+'/enterpriseInfo/warningInfo',
			params : {id : Ext.getCmp('EnterpriseInfo-EnterpriseList').selectedId}
		});
    }
});

AppraisalReport = Ext.extend(Ext.Panel,{
	initComponent : function(){
		Ext.apply(this,{
			title : '评估报告',
			bodyStyle: 'padding: 20px '
		});
		AppraisalReport.superclass.initComponent.call(this);
		this.on('activate',function(p){
			if(Ext.getCmp('EnterpriseInfo-EnterpriseList').selectedId)
				p.loadInfo();
		},this);
	},
	loadInfo : function(){
		this.getUpdater().update({
			url : ctx+'/enterpriseInfo/appraisalReport',
			params : {id : Ext.getCmp('EnterpriseInfo-EnterpriseList').selectedId}
		});
    }
});

FinanceData = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			title : '财务数据',
			closable: false,
			style : 'padding:0px',
			paging : false,
			gridConfig: {
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{dataIndex:'year',header:'年份',width:70,sortable : true},
					{dataIndex:'totalAssets',header:'总资产',width:100,sortable : true},
					{dataIndex:'totalLiabilities',header:'负债总额',width:100,sortable: true},
					{dataIndex:'longTermLiabilities',header:'长期负债',width:100,sortable: true},
					{dataIndex:'money',header:'货币资金',width:100,sortable: true},
					{dataIndex:'otherReceivables',header:'其他应收款',width:100,sortable: true},
					{dataIndex:'currentLiabilities',header:'流动负债',width:100,sortable: true},
					{dataIndex:'capitalFund',header:'资本公积金',width:100,sortable: true},
					{dataIndex:'surplusFund',header:'盈余公积金',width:100,sortable: true},
					{dataIndex:'undistributedProfits',header:'未分配利润',width:100,sortable: true},
					{dataIndex:'shareholders',header:'股东权益',width:100,sortable: true},
					{dataIndex:'assetLiabilityRatio',header:'资产负债率',width:100,sortable: true},
					{dataIndex:'liquidityRatio',header:'流动比率',width:100,sortable: true},
					{dataIndex:'quickRatio',header:'速动比率',width:100,sortable: true},
					{dataIndex:'accountsReceivable',header:'应收账款',width:100,sortable: true},
					{dataIndex:'inventory',header:'存货',width:100,sortable: true},
					{dataIndex:'accountsReceivableRurnoverRatio',header:'应收账款周转率',width:100,sortable: true},
					{dataIndex:'inventoryRurnover',header:'存货周转率',width:100,sortable: true},
					{dataIndex:'totalAssetTurnoverRatio',header:'总资产周转率',width:100,sortable: true},
					{dataIndex:'operatingIncome',header:'营业收入',width:100,sortable: true},
					{dataIndex:'operatingProfit',header:'营业利润',width:100,sortable: true},
					{dataIndex:'totalProfits',header:'利润总额',width:100,sortable: true},
					{dataIndex:'netProfit',header:'净利润',width:100,sortable: true},
					{dataIndex:'investmentIncome',header:'投资收益',width:100,sortable: true},
					{dataIndex:'operatingProfitMargin',header:'营业利润率',width:100,sortable: true},
					{dataIndex:'roe',header:'净资产收益率',width:100,sortable: true},
					{dataIndex:'operatingNetCashFlow',header:'经营性活动现金净流量',width:130,sortable: true},
					{dataIndex:'investmentNetCashFlow',header:'投资性活动现金净流量',width:130,sortable: true},
					{dataIndex:'financingNetCashFlow',header:'筹资性活动现金净流量',width:130,sortable: true},
					{dataIndex:'mainBusinessIncomeCashRate',header:'主营业务收入现金率',width:130,sortable: true},
					{dataIndex:'operatingNetCashFlow_currentLiabilities',header:'经营性活动现金净流量/流动负债',width:150,sortable: true},
					{dataIndex:'netCashFlow',header:'现金净流量',width:100}
				]),	
				storeMapping:[
					'id','year','totalAssets','totalLiabilities','longTermLiabilities','money','otherReceivables','currentLiabilities','capitalFund',
					'surplusFund','undistributedProfits','shareholders','assetLiabilityRatio','liquidityRatio','quickRatio',
					'accountsReceivable','inventory','accountsReceivableRurnoverRatio','inventoryRurnover','totalAssetTurnoverRatio',
					'operatingIncome','operatingProfit','totalProfits','netProfit','investmentIncome','operatingProfitMargin','roe',
					'operatingNetCashFlow','investmentNetCashFlow','financingNetCashFlow','mainBusinessIncomeCashRate',
					'operatingNetCashFlow_currentLiabilities','netCashFlow'
				],
				viewConfig : {}
			},
			buttonConfig : [],
			url: ctx+'/enterpriseInfo',
			listUrl : '/financeData'
		});	
		FinanceData.superclass.initComponent.call(this);
		this.on('activate',function(p){
			if(Ext.getCmp('EnterpriseInfo-EnterpriseList').selectedId)
				p.loadInfo();
		},this);
	},
	loadInfo : function(){
		this.loadData({id : Ext.getCmp('EnterpriseInfo-EnterpriseList').selectedId});
    }
});

IndustryRank = Ext.extend(Ext.app.BaseFuncPanel,{
	initComponent : function(){
		Ext.apply(this,{
			title : '行业排名',
			closable: false,
			style : 'padding:0px',
			paging : false,
			gridConfig: {
				cm:new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{dataIndex:'name',header:'公司名称',width:150,sortable : true},
					{dataIndex:'totalAssets',header:'总资产',width:100,sortable : true},
					{dataIndex:'totalLiabilities',header:'负债总额',width:100,sortable: true},
					{dataIndex:'longTermLiabilities',header:'长期负债',width:100,sortable: true},
					{dataIndex:'money',header:'货币资金',width:100,sortable: true},
					{dataIndex:'otherReceivables',header:'其他应收款',width:100,sortable: true},
					{dataIndex:'currentLiabilities',header:'流动负债',width:100,sortable: true},
					{dataIndex:'capitalFund',header:'资本公积金',width:100,sortable: true},
					{dataIndex:'surplusFund',header:'盈余公积金',width:100,sortable: true},
					{dataIndex:'undistributedProfits',header:'未分配利润',width:100,sortable: true},
					{dataIndex:'shareholders',header:'股东权益',width:100,sortable: true},
					{dataIndex:'assetLiabilityRatio',header:'资产负债率',width:100,sortable: true},
					{dataIndex:'liquidityRatio',header:'流动比率',width:100,sortable: true},
					{dataIndex:'quickRatio',header:'速动比率',width:100,sortable: true},
					{dataIndex:'accountsReceivable',header:'应收账款',width:100,sortable: true},
					{dataIndex:'inventory',header:'存货',width:100,sortable: true},
					{dataIndex:'accountsReceivableRurnoverRatio',header:'应收账款周转率',width:100,sortable: true},
					{dataIndex:'inventoryRurnover',header:'存货周转率',width:100,sortable: true},
					{dataIndex:'totalAssetTurnoverRatio',header:'总资产周转率',width:100,sortable: true},
					{dataIndex:'operatingIncome',header:'营业收入',width:100,sortable: true},
					{dataIndex:'operatingProfit',header:'营业利润',width:100,sortable: true},
					{dataIndex:'totalProfits',header:'利润总额',width:100,sortable: true},
					{dataIndex:'netProfit',header:'净利润',width:100,sortable: true},
					{dataIndex:'investmentIncome',header:'投资收益',width:100,sortable: true},
					{dataIndex:'operatingProfitMargin',header:'营业利润率',width:100,sortable: true},
					{dataIndex:'roe',header:'净资产收益率',width:100,sortable: true},
					{dataIndex:'operatingNetCashFlow',header:'经营性活动现金净流量',width:130,sortable: true},
					{dataIndex:'investmentNetCashFlow',header:'投资性活动现金净流量',width:130,sortable: true},
					{dataIndex:'financingNetCashFlow',header:'筹资性活动现金净流量',width:130,sortable: true},
					{dataIndex:'mainBusinessIncomeCashRate',header:'主营业务收入现金率',width:130,sortable: true},
					{dataIndex:'operatingNetCashFlow_currentLiabilities',header:'经营性活动现金净流量/流动负债',width:150,sortable: true},
					{dataIndex:'netCashFlow',header:'现金净流量',width:100}
				]),	
				storeMapping:[
					'id','name','totalAssets','totalLiabilities','longTermLiabilities','money','otherReceivables','currentLiabilities','capitalFund',
					'surplusFund','undistributedProfits','shareholders','assetLiabilityRatio','liquidityRatio','quickRatio',
					'accountsReceivable','inventory','accountsReceivableRurnoverRatio','inventoryRurnover','totalAssetTurnoverRatio',
					'operatingIncome','operatingProfit','totalProfits','netProfit','investmentIncome','operatingProfitMargin','roe',
					'operatingNetCashFlow','investmentNetCashFlow','financingNetCashFlow','mainBusinessIncomeCashRate',
					'operatingNetCashFlow_currentLiabilities','netCashFlow'
				],
				viewConfig : {}
			},
			buttonConfig : [],
			url: ctx+'/enterpriseInfo',
			listUrl : '/industryRank'
		});	

		IndustryRank.superclass.initComponent.call(this);
		this.on('activate',function(p){
			if(Ext.getCmp('EnterpriseInfo-EnterpriseList').selectedId)
				p.loadInfo();
		},this);
	},
	loadInfo : function(){
		this.loadData({id : Ext.getCmp('EnterpriseInfo-EnterpriseList').selectedId});
    }
});

EnterpriseInfoPanel = Ext.extend(Ext.TabPanel,{
    activeTab: 0,
	defaults : {
		border : true,
		autoScroll : true
		//bodyStyle: 'padding: 20px '
	},
	initComponent : function(){
		
		this.items = [
			new WarningInfo(),
			new AppraisalReport(),
			new FinanceData(),
			new IndustryRank()
		]
		
    	EnterpriseInfoPanel.superclass.initComponent.call(this);
    	
    	Ext.getCmp('EnterpriseInfo-EnterpriseList').getSelectionModel().on('rowselect',function(){
    		this.getUpdater().refresh();
    	},this);
					
    },
    loadInfo : function(){
		this.getActiveTab().loadInfo();
    }
});



EnterpriseInfo = Ext.extend(Ext.Panel,{
	layout:'border',
	closable: true,
	initComponent : function(){
		this.right = new EnterpriseList({
			id:'EnterpriseInfo-EnterpriseList',
			funcCode: this.funcCode,
			region:'west',
			title: '企业列表',
			split:true,
			collapsible: true,
			//collapseMode:'mini',
			width: 250,
			minSize: 175,
			maxSize: 400
		});
		this.left = new EnterpriseInfoPanel({
			id:'EnterpriseInfoPanel',
			funcCode: this.funcCode,
			region:'center',			
			title: '综合信息'
		});
		this.items = [this.right,this.left];
    	EnterpriseInfo.superclass.initComponent.call(this);
			
    },
	loadData:function(){
		this.right.loadData();
	}
		
});

