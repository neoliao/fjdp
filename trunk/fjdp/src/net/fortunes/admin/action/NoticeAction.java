package net.fortunes.admin.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.fortunes.admin.model.Organization;
import net.fortunes.admin.model.Employee;
import net.fortunes.admin.model.User;
import net.fortunes.admin.service.OrganizationService;
import net.fortunes.core.action.GenericAction;
import net.sf.json.JSONObject;

import com.fortunes.helper.AppHelper;
import net.fortunes.admin.model.Notice;
import net.fortunes.admin.service.NoticeService;

public class NoticeAction extends GenericAction<Notice> {
	
	private NoticeService noticeService;
	private OrganizationService departmentService;
	
	private String[] organizations;
	
	@Override
	public String add() throws Exception {
		Notice notice = new Notice();
		setEntity(notice);
		List<User> users = new ArrayList<User>();
		for(String id : organizations){
			Organization d = departmentService.get(id);
			for(Employee e : d.getEmployees()){
				if(e.getUser() != null){
					users.add(e.getUser());
				}
			}
		}
		noticeService.publishNotice(notice,users);
		jo.put("success", true);
		return render(jo);
	}
	
	@Override
	public String update() throws Exception {
		Notice notice = noticeService.get(id);
		setEntity(notice);
		noticeService.update(notice);
		jo.put("success", true);
		return render(jo);
	}

	@Override
	protected void setEntity(Notice e) throws ParseException {
		e.setCreateDateTime(new Date());
		e.setCreator(authedUser);
		e.setContents(p("contents"));
		e.setLevel(AppHelper.toDict(p("level")));
	}

	@Override
	protected JSONObject toJsonObject(Notice e) throws ParseException {
		AppHelper record = new AppHelper();
		record.put("id", e.getId());
		record.put("contents", e.getContents());
		record.put("createUser", e.getCreator());
		record.put("createDateTime", e.getCreateDateTime());
		record.put("level", e.getLevel());
		return record.getJsonObject();
	}

	public void setOrganizations(String[] organizations) {
		this.organizations = organizations;
	}

	public String[] getOrganizations() {
		return organizations;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public OrganizationService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(OrganizationService departmentService) {
		this.departmentService = departmentService;
	}

}
