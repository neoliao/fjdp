package net.fortunes.admin.action;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import net.fortunes.admin.AdminHelper;
import net.fortunes.admin.model.Employee;
import net.fortunes.admin.service.EmployeeService;
import net.fortunes.core.action.GenericAction;
import net.fortunes.core.service.GenericService;
import net.fortunes.util.PinYin;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component @Scope("prototype")
public class EmployeeAction extends GenericAction<Employee> {
	
	@Resource private EmployeeService employeeService;
	
	protected void setEntity(Employee employee) throws ParseException{
		employee.setCode(p("code"));
		employee.setName(p("name"));
		employee.setEmail(p("email"));
		employee.setPhone(p("phone"));
		employee.setMobile(p("mobile"));
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

}
