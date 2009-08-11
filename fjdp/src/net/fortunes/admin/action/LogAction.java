package net.fortunes.admin.action;

import java.text.ParseException;

import net.fortunes.admin.AdminHelper;
import net.fortunes.admin.model.Log;
import net.fortunes.admin.service.LogService;
import net.fortunes.core.action.GenericAction;
import net.sf.json.JSONObject;

public class LogAction extends GenericAction<Log> {
	
	private LogService logService;
	
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

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
}
