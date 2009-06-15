package net.fortunes.admin.service;

import java.io.Reader;
import java.util.List;
import net.fortunes.admin.model.Menu;
import net.fortunes.admin.model.Privilege;
import net.fortunes.core.service.GenericService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MenuService extends GenericService<Menu>{
	
	public static final String FUNC_ELEMENT = "func";
	public static final String PRIVILEGE_ELEMENT = "privilege";
	public static final String WIDGET_JS_ROOT = "/widget";
	
	private PrivilegeService privilegeService;
	
	@Override
	public Menu add(Menu entity) {
		super.add(entity);
		if(entity.getParent() != null)
			entity.getParent().setLeaf(false);
		return entity;
	}
	
	@Override
	public void del(Menu entity) throws Exception {
		Menu parent = entity.getParent();
		super.del(entity);
		if(parent != null && parent.getChildren().size() <= 0)
			parent.setLeaf(true);
	}
	
	public void initToDb(Reader reader) throws DocumentException{
		SAXReader xmlReader = new SAXReader(); 
		Document doc = xmlReader.read(reader);
		Element root = doc.getRootElement();
		
		this.delAll();
		walkMenuTree(root.element(FUNC_ELEMENT),new Menu());
		
		privilegeService.delAll();
		
		walkPrivilegeTree(root.element(FUNC_ELEMENT),new Privilege());			
	}
	
	private void walkMenuTree(Element menuElement,Menu menu) {
		String funcType = menuElement.attributeValue("type") == null ? "node" : menuElement.attributeValue("type");
		if(!funcType.equals("myhome")){
			createMenu(menuElement,menu,funcType);
		}
		
		if (menuElement.hasContent()) {
			//遍历子菜单
			List<Element> funcElements = menuElement.elements(FUNC_ELEMENT);
			for (Element funcElement : funcElements) {
				if(!funcType.equals("myhome")){
					Menu subMenu = new Menu();
					subMenu.setParent(menu);
					walkMenuTree(funcElement,subMenu);
				}
			}		
		}
	}
	
	private void walkPrivilegeTree(Element menuElement,Privilege privilege) {
		createPrivilegeForFunc(menuElement,privilege);
		
		if (menuElement.hasContent()) {
			//遍历子菜单
			List<Element> funcElements = menuElement.elements(FUNC_ELEMENT);
			for (Element funcElement : funcElements) {
				Privilege subPrivilege = new Privilege();
				subPrivilege.setParent(privilege);
				walkPrivilegeTree(funcElement,subPrivilege);
			}
			//遍历菜单包含的权限
			List<Element> privilegeElements = menuElement.elements(PRIVILEGE_ELEMENT);
			for (Element privilegeElement : privilegeElements) {
				Privilege subPrivilege = new Privilege();
				subPrivilege.setParent(privilege);
				createPrivilege(menuElement, privilegeElement, subPrivilege);
			}	
		}
	}
	
	private void createMenu(Element menuElement,Menu menu,String funcType){
		if(funcType.equals("node")){
			menu.setUrl(getUrl(menuElement));
		}
		menu.setType(funcType);
		setMenu(menuElement, menu);
		this.add(menu);
	}
	
	private void setMenu(Element menuElement,Menu menu){
		menu.setName(menuElement.attributeValue("name"));
		menu.setText(menuElement.attributeValue("text"));
		menu.setIcon(menuElement.attributeValue("icon"));
		if(menuElement.attributeValue("icon") != null && menuElement.attributeValue("icon").equals("false"))
			menu.setDisplay(false);
		else
			menu.setDisplay(true);
	}
	
	private String getUrl(Element menuElement){
		return WIDGET_JS_ROOT+"/"+
			StringUtils.uncapitalize(menuElement.getParent().attributeValue("name"))+"/"+
			StringUtils.uncapitalize(menuElement.attributeValue("name"))+".js";
	}
	
	private void createPrivilegeForFunc(Element funcElement,Privilege p){
		p.setCode(funcElement.attributeValue("name"));
		p.setText(funcElement.attributeValue("text"));
		privilegeService.add(p);
	}
	
	private void createPrivilege(Element funcElement,Element privilegeElement,Privilege p){
		p.setCode(funcElement.attributeValue("name")+"_"+privilegeElement.attributeValue("name"));
		p.setText(privilegeElement.attributeValue("text"));
		p.setDescription(privilegeElement.attributeValue("text")+funcElement.attributeValue("text"));
		privilegeService.add(p);
	}
	

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

}
