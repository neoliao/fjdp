package net.fortunes.admin.action;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fortunes.fjdp.AppHelper;

import net.fortunes.admin.AdminHelper;
import net.fortunes.admin.model.Log;
import net.fortunes.admin.model.Role;
import net.fortunes.admin.model.User;
import net.fortunes.admin.service.LogService;
import net.fortunes.core.ListData;
import net.fortunes.core.action.GenericAction;
import net.fortunes.core.service.GenericService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component @Scope("prototype")
public class LogAction extends GenericAction<Log> {
	
	@Resource private LogService logService;
	
	@Override
	public String list() throws Exception{
		User user = (User)getSessionMap().get(AppHelper.AUTHED_USER);
		List<Role> roles = user.getRoles();
		String strRoles = "";
		for(Role element: roles) {
			strRoles += element.getName();
		}		
		queryMap.put("roles", strRoles);
		queryMap.put("userDisplayName", user.getDisplayName());
		
		ListData<Log> listData = getDefService().getListData(query, queryMap, start, limit);
		JSONArray ja = new JSONArray();
		for(Log entity:listData.getList()){
			ja.add(toJsonObject(entity));
		}
		jo.put(DATA_KEY, ja);
		jo.put(TOTAL_COUNT_KEY, listData.getTotal());
		return render(jo);  
	}
	
	protected void setEntity(Log log) throws ParseException{

	}
	
	protected JSONObject toJsonObject(Log log) throws ParseException{	
		AdminHelper record = new AdminHelper();
		record.put("id", log.getId());		
		record.put("opType", log.getOpType());
		record.put("opUser", log.getOpUser());
		record.put("createTime", log.getCreateTime());
		record.put("contents", log.getContents());
		return record.getJsonObject();
	}
	
	//================== setter and getter ===================
	
	@Override
	public GenericService<Log> getDefService() {
		return logService;
	}
	
	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
}
