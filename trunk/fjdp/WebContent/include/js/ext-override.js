
Ext.BLANK_IMAGE_URL = ctx+'/struts/resources/images/default/s.gif'; 

var urlPostPrefix = '';

Ext.QuickTips.init();

// turn on validation errors beside the field globally
Ext.form.Field.prototype.msgTarget = 'side';

Ext.Updater.defaults.disableCaching = true;

Ext.ns('App');

/**
 * override tree
 * 
 */
Ext.override(Ext.tree.TreePanel, {
	rootVisible: false,
	lines:false,
	useArrows:true,
	autoScroll:true,
	loadRoot : function(o){
		if(o)
			Ext.apply(this.getLoader().baseParams,o);
		this.getLoader().load(this.root);
	}
});

Ext.override(Ext.form.FormPanel, {
    bindHandler : function(){
        var valid = true;
        if(!this.getForm().isDirty())
        	valid = false;
        this.form.items.each(function(f){
            if(!f.isValid(true)){
                valid = false;
                return false;
            }
        });
        if(this.fbar){
            var fitems = this.fbar.items.items;
            for(var i = 0, len = fitems.length; i < len; i++){
                var btn = fitems[i];
                if(btn.formBind === true && btn.disabled === valid){
                    btn.setDisabled(!valid);
                }
            }
        }
        this.fireEvent('clientvalidation', this, valid);
    }
});

Ext.override(Ext.form.BasicForm, {
    trackResetOnLoad : true
});

/**
 * 字段验证
 * 
 */
Ext.apply(Ext.form.VTypes, { 
	digital: function() {                                 
		var numericRe = /^\d+$/;;  
		return function(v) { return numericRe.test(v); }                                
	}(),
	digitalText : '您输入的字符有误,只能输入数字,如01,67等',
	digitalcMask : /[0-9]/ ,
	password: function(val, field) {
	    if (field.initialPassField) {
	      var pwd = Ext.getCmp(field.initialPassField);
	      return (val == pwd.getValue());
	    }
	    return true;
	}, 
  	passwordText: '您输入的两个密码不匹配，请重新输入',
  	daterange : function(val, field) {
        var date = field.parseDate(val);

        if(!date){
            return;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = Ext.getCmp(field.startDateField);
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } 
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = Ext.getCmp(field.endDateField);
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        /*
         * Always return true since we're only using this vtype to set the
         * min/max allowed values (these are tested for after the vtype test)
         */
        return true;
    },
    datetimerange : function(val, field) {

        /*
         * Always return true since we're only using this vtype to set the
         * min/max allowed values (these are tested for after the vtype test)
         */
        return true;
    }

});


/**
 * 给不为空field前面的lable加上小红点
 * 
 */
Ext.apply(Ext.layout.FormLayout.prototype, {
	 renderItem : function(c, position, target){
        if(c && !c.rendered && (c.isFormField || c.fieldLabel) && c.inputType != 'hidden'){
        	if(c.allowBlank==false){
                c.fieldLabel = "<span style=\"color:red;font-weight:bold;\" ext:qtip=\"该字段不能为空\"> * </span>"+c.fieldLabel;
            }
            var args = this.getTemplateArgs(c);
            if(typeof position == 'number'){
                position = target.dom.childNodes[position] || null;
            }
            if(position){
                this.fieldTpl.insertBefore(position, args);
            }else{
                this.fieldTpl.append(target, args);
            }
            c.render('x-form-el-'+c.id);
        }else {
            Ext.layout.FormLayout.superclass.renderItem.apply(this, arguments);
        }
    }
}); 

var dictRenderer = function(v){
    return v? v['text'] : "";
}

/**
 * 给翻页工具栏加上加两个方法,nextPage,previousPage,如何不能翻页,则返回false
 * 
 */
Ext.override(Ext.PagingToolbar, {
	nextPage : function(){
		if(this.getPageData().activePage == this.getPageData().pages){
			return false;
		}else{
			this.doLoad(this.cursor+this.pageSize);
			return true;
		}
	},
	previousPage : function(){
		if(this.getPageData().activePage == 1){
			return false;
		}else{
			this.doLoad(this.cursor-this.pageSize);
			return true;
		}		
	}
});



