package net.fortunes.admin.action;

import java.text.ParseException;
import java.util.List;

import net.fortunes.admin.helper.AdminHelper;
import net.fortunes.admin.model.Organization;
import net.fortunes.admin.model.Employee;
import net.fortunes.admin.service.OrganizationService;
import net.fortunes.admin.service.DictService;
import net.fortunes.admin.service.EmployeeService;
import net.fortunes.core.action.GenericAction;
import net.fortunes.util.PinYin;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class OrganizationAction extends GenericAction<Organization> {
	
	private EmployeeService employeeService;
	private OrganizationService organizationService;
	private DictService dictService;

	private int[] checkedId;


	protected void setEntity(Organization department) throws ParseException{
		department.setName(p("name"));
		department.setCode(p("code"));
		department.setShortName(p("shortName"));
		department.setAddress(p("address"));
		department.setTel(p("tel"));
		department.setType(AdminHelper.toDict(p("type")));
		if(p("parentId").equals("0")){
			department.setParent(getOrganizationService().getRoot());
		}else{
			department.setParent(AdminHelper.toDepartment(getParentId()));
		}		
	}
	
	protected JSONObject toJsonObject(Organization department) throws ParseException{
		AdminHelper record = new AdminHelper();
		record.put("id", department.getId());
		record.put("name", department.getName());
		record.put("code",department.getCode());
		record.put("shortName", department.getShortName());
		record.put("fullName", department.getFullName());
		record.put("address", department.getAddress());
		record.put("tel", department.getTel());
		record.put("type", department.getType());
		return record.getJsonObject();
	}

	public JSONArray walkTree(Organization department){
		JSONArray ja = new JSONArray();
		if(department != null){
			List<Organization> ds = department.getChildren();		
			for(Organization d : ds){
				JSONObject jo = new JSONObject();
				jo.put("id", d.getId());
				jo.put("text", d.getShortName());
				jo.put("iconCls", "department");
				if(d.getChildren().isEmpty()){				
					jo.put("leaf", true);
				}else{
					//异步load
					//jo.put("children", walkTree(d));
				}
				ja.add(jo);
			}
		}
		return ja;
	}	
	
	//所有部门（用于下拉菜单）
	public String getDepartments() throws Exception{  
		List<Organization> departmentList = getOrganizationService().getListData().getList();
		JSONArray ja = new JSONArray();
		JSONObject record = null;
		for(Organization department:departmentList){
			if(department.getParent() != null ){
				record = new JSONObject();
				record.put("id", department.getId());
				record.put("text", department.getName());
				record.put("code", department.getCode());
				ja.add(record);
			}
		}
		jo.put("data", ja);
		
		return render(jo);  
	}
	
	//列出某个部门所拥有的所有员工
	public String ListEmployees() throws Exception{
		List<Employee> employees = getOrganizationService().get(p("organizationId")).getEmployees();
		JSONArray ja = new JSONArray();
		for(Employee e : employees){
			JSONObject record = new JSONObject();
			record.put("id", e.getId());
			record.put("text", e.getName());
			record.put("qtip", "工号："+e.getCode());
			record.put("iconCls", 
					(e.getSex()!=null && e.getSex().getText().equals("女"))? "employee_female" : "employee");
			record.put("leaf", true);
			ja.add(record);
		}
		return render(ja);  
	}
	
	//列出某个部门未拥有的员工(根据关键字过滤)
	public String ListEmployeesUnassign() throws Exception{
		List<Employee> employeeList = getOrganizationService().getUnassignEmployeesByOrganizationId(p("organizationId"));
		JSONArray ja = new JSONArray();
		for(Employee employee:employeeList){
			String namePy = PinYin.toPYString(employee.getName());
			System.out.println(namePy);
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
	
	//加入一个员工到一个部门
	public String addEmployee() throws Exception{
		getOrganizationService().addEmployee(p("organizationId"), p("employeeId"));
		return render(jo);
	}
	
	//从一个部门移除某一个员工
	public String removeEmployee() throws Exception{
		getOrganizationService().removeEmployee(p("organizationId"), p("employeeId"));
		return render(jo);
	}
	
	//========================================== setter and getter ============================================
	

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}
	
	public int[] getCheckedId() {
		return checkedId;
	}

	public void setCheckedId(int[] checkedId) {
		this.checkedId = checkedId;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public OrganizationService getOrganizationService() {
		return organizationService;
	}
	
	
}