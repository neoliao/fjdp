package net.fortunes.admin.action;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.fortunes.admin.AdminHelper;
import net.fortunes.admin.model.Dict;
import net.fortunes.admin.service.DictService;
import net.fortunes.core.action.GenericAction;
import net.fortunes.core.service.GenericService;
import net.fortunes.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component @Scope("prototype")
public class DictAction extends GenericAction<Dict> {
	
	@Resource private DictService dictService;
	
	protected void setEntity(Dict dict) throws ParseException{
		////新增时
		if(dict.getId() == null){
			dict.setParent(parentId.equals("0") ? dictService.getRoot() : dictService.get(parentId));
		}
		if(StringUtils.isEmpty(id))
			dict.setId(Tools.uuid());
		dict.setText(p("text"));
		dict.setDescription(p("description"));
	}
	
	protected JSONObject toJsonObject(Dict dict) throws ParseException{
		AdminHelper record = new AdminHelper();
		record.put("id", dict.getId());
		record.put("text", dict.getText());
		record.put("description", dict.getDescription());
		record.put("iconCls", "dict");
		return record.getJsonObject();
	}

	public String getDictsByType() throws Exception {
		List<Dict> dictList = dictService.getDictsByType(p("type"));
		JSONArray ja = new JSONArray();
		for(Dict dictItem : dictList){
			JSONObject temp = new JSONObject();
			temp.put("id", dictItem.getId());
			temp.put("text", dictItem.getText());
			ja.add(temp);
		}
		jo.put("data", ja);
		return render(jo);
	}
	
	@Override
	protected JSONArray walkTree(Dict dict) throws Exception {
		JSONArray ja = new JSONArray();
		List<Dict> ds = dict.getChildren();
		for(Dict d : ds){
			JSONObject jo = toJsonObject(d);
			if(d.isLeaf()){				
				jo.put("leaf", true);
			}
			ja.add(jo);
		}
		return ja;
	}
	
	//================== setter and getter ===================
	
	@Override
	public GenericService<Dict> getDefService() {
		return dictService;
	};

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

}
