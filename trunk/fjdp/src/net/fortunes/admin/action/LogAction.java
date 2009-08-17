package net.fortunes.admin.action;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.fortunes.admin.AdminHelper;
import net.fortunes.admin.model.Dict;
import net.fortunes.admin.model.Log;
import net.fortunes.admin.service.LogService;
import net.fortunes.core.action.GenericAction;
import net.fortunes.core.service.GenericService;
import net.sf.json.JSONObject;

@Component @Scope("prototype")
public class LogAction extends GenericAction<Log> {
	
	@Resource private LogService logService;
	
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
