package com.fortunes.fjdp.crm.action;

import java.io.File;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import net.fortunes.core.action.GenericAction;
import net.fortunes.core.service.GenericService;
import net.fortunes.util.PinYin;
import net.fortunes.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fortunes.fjdp.admin.AdminHelper;
import com.fortunes.fjdp.admin.model.Client;
import com.fortunes.fjdp.admin.model.Employee;
import com.fortunes.fjdp.admin.service.EmployeeService;
import com.fortunes.fjdp.crm.service.ClientService;

@Component @Scope("prototype")
public class ClientAction extends GenericAction<Client> {
	
	private static final String PHOTO_DIR = "E:/app/photo/";
	@Resource private ClientService clientService;
	private File photoFile;
	public static final String PHOTO_URL_PREFIX = "/Client/photo?photoId=";
	
	protected void setEntity(Client client) throws ParseException{
		client.setCode(p("code"));
		client.setName(p("name"));
		client.setEmail(p("email"));
		client.setPhone(p("phone"));
		client.setMobile(p("mobile"));
		client.setPhotoId(p("photoId"));
		client.setSex(AdminHelper.toDict(p("sex")));
		client.setStatus(AdminHelper.toDict(p("status")));
		client.setHireDate(AdminHelper.toDate(p("hireDate")));
	}
	
	protected JSONObject toJsonObject(Client e) throws ParseException{
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
	public String getclients() throws Exception{
		List<Client> clientList = getDefService().getListData().getList();
		JSONArray ja = new JSONArray();
		for(Client client:clientList){
			String namePy = PinYin.toPinYinString(client.getName());
			if(namePy.startsWith(getQuery().toUpperCase())
					|| client.getName().startsWith(getQuery())){
				JSONObject record = new JSONObject();
				record.put("id", client.getId());
				record.put("text", client.getName());
				record.put("code", client.getCode());
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
	public String getclientsUnAssign() throws Exception{
		List<Client> clients = clientService.getClientsUnAssign();
		JSONArray ja = new JSONArray();
		for(Client client : clients){
			JSONObject record = new JSONObject();
			record.put("id", client.getId());
			record.put("text", client.getName());
			record.put("code", client.getCode());
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
	public GenericService<Client> getDefService() {
		return this.clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public ClientService getClientService() {
		return clientService;
	}
	public File getPhotoFile() {
		return photoFile;
	}

	public void setPhotoFile(File photoFile) {
		this.photoFile = photoFile;
	}
}
