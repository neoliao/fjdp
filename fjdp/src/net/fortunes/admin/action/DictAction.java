package net.fortunes.admin.action;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.fortunes.admin.helper.AdminHelper;
import net.fortunes.admin.model.Dict;
import net.fortunes.admin.service.DictService;
import net.fortunes.core.action.GenericAction;
import net.fortunes.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DictAction extends GenericAction<Dict> {
	
	private DictService dictService;
	
	protected void setEntity(Dict dict) throws ParseException{
		dict.setParent(p("parentId").equals("0")?
				dictService.getRoot() : AdminHelper.toDict(p("parentId")));
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
	protected JSONArray walkTree(Dict dict) {
		JSONArray ja = new JSONArray();
		List<Dict> ds = dict.getChildren();
		for(Dict d : ds){
			JSONObject jo = new JSONObject();
			jo.put("id", d.getId());
			jo.put("text", d.getText());
			jo.put("iconCls", "dict");
			if(d.getChildren().isEmpty()){				
				jo.put("leaf", true);
			}
			ja.add(jo);
		}
		return ja;
	}
	

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

}
