package net.fortunes.admin.action;

import java.io.File;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import net.fortunes.admin.AdminHelper;
import net.fortunes.admin.model.Employee;
import net.fortunes.admin.service.EmployeeService;
import net.fortunes.core.action.GenericAction;
import net.fortunes.core.service.GenericService;
import net.fortunes.util.PinYin;
import net.fortunes.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component @Scope("prototype")
public class EmployeeAction extends GenericAction<Employee> {
	
	private static final String PHOTO_DIR = "E:/app/photo/";
	@Resource private EmployeeService employeeService;
	private File photoFile;
	public static final String PHOTO_URL_PREFIX = "/employee/photo?photoId=";
	
	protected void setEntity(Employee employee) throws ParseException{
		employee.setCode(p("code"));
		employee.setName(p("name"));
		employee.setEmail(p("email"));
		employee.setPhone(p("phone"));
		employee.setMobile(p("mobile"));
		employee.setPhotoId(p("photoId"));
		employee.setSex(AdminHelper.toDict(p("sex")));
		employee.setStatus(AdminHelper.toDict(p("status")));
		employee.setHireDate(AdminHelper.toDate(p("hireDate")));
	}
	
	protected JSONObject toJsonObject(Employee e) throws ParseException{
		AdminHelper record = new AdminHelper();
		record.put("id", e.getId());
		record.put("code", e.getCode());		
		record.put("sex", e.getSex());
		record.put("status", e.getStatus());
		record.put("name", e.getName());
		record.put("phone", e.getPhone());
		record.put("mobile", e.getMobile());
		record.put("email", e.getEmail());
		record.put("hireDate", e.getHireDate());
		record.put("photoId",e.getPhotoId());
		return record.getJsonObject();
	}
	
	/**
	 * 用于雇员选择的下拉菜单,可以用于拼音首字母和关键字查询
	 * @return　json
	 * @throws Exception
	 */
	public String getEmployees() throws Exception{
		List<Employee> employeeList = getDefService().getListData().getList();
		JSONArray ja = new JSONArray();
		for(Employee employee:employeeList){
			String namePy = PinYin.toPinYinString(employee.getName());
			if(namePy.startsWith(getQuery().toUpperCase())
					|| employee.getName().startsWith(getQuery())){
				JSONObject record = new JSONObject();
				record.put("id", employee.getId());
				record.put("text", employee.getName());
				record.put("code", employee.getCode());
				record.put("pinyin", namePy);
				ja.add(record);
			}	
		}
		jo.put("data", ja);
		return render(jo); 
	}
	
	/**
	 * 查询没有被分配用户的雇员
	 * @return json
	 * @throws Exception
	 */
	public String getEmployeesUnAssign() throws Exception{
		List<Employee> employees = employeeService.getEmployeesUnAssign();
		JSONArray ja = new JSONArray();
		for(Employee employee : employees){
			JSONObject record = new JSONObject();
			record.put("id", employee.getId());
			record.put("text", employee.getName());
			record.put("code", employee.getCode());
			ja.add(record);			
		}
		jo.put("data", ja);
		return render(jo);
	}
	
	public String setupPhoto() throws Exception {
		String uuid = Tools.uuid();
		//final String photoDir = configService.get(ConfigKey.PHOTO_DIR);
		FileUtils.copyFile(photoFile, new File(PHOTO_DIR+uuid+".jpg"));
		jo.put("photoId", uuid);
		setJsonMessage(true,"设置人员相片成功!");
		return render(jo.toString());
	}
	
	public String photo() throws Exception{
		//final String photoDir = configService.get(ConfigKey.PHOTO_DIR);
		return render(FileUtils.readFileToByteArray(new File(PHOTO_DIR+p("photoId")+".jpg")), "image/jpeg");
	}
	//================== setter and getter ===================
	
	@Override
	public GenericService<Employee> getDefService() {
		return this.employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	public File getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(File photoFile) {
		this.photoFile = photoFile;
	}
}
