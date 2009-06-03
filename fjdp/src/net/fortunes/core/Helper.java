package net.fortunes.core;

import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import net.fortunes.admin.model.User;
import net.fortunes.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 助手类,提供有关json和date的快捷操作
 * @author Neo.Liao
 *
 */
public class Helper {
	
	protected JSONObject jsonObject;
	
	public static boolean DEBUG = true;
	public static final String AUTHED_USER = "authedUser";//User
	public static final String PRIVILEGES = "privileges";//(String)Object []
	public static final String PRIVILEGES_STRING = "privilegesString";//"['role'],['role_view']"
	public static final String ROLES_STRING = "rolesString";//String "['系统管理员'],['会计']"
	public static final String WIDGET_URLS = "widgets";//String "['/admin/menu.js'],['/admin/role.js']"
	
	private static final ThreadLocal<HttpSession> HTTP_SESSION_IN_THREAD = new ThreadLocal<HttpSession>();
	
	public static void setHttpSessionInThread(HttpSession httpSession) {
		Helper.HTTP_SESSION_IN_THREAD.set(httpSession);
	}

	public static HttpSession getHttpSessionInThread(){
		return Helper.HTTP_SESSION_IN_THREAD.get();
	}
	
	public static void removeHttpSessionInThread(){
		Helper.HTTP_SESSION_IN_THREAD.remove();
	}
	
	public static User getUser(){
		if(Helper.HTTP_SESSION_IN_THREAD.get()!=null){
			return (User)Helper.HTTP_SESSION_IN_THREAD.get().getAttribute(AUTHED_USER);
		}else
			return null;
	}
	
	public static boolean userHasPrivilege(String privilegeCode){
		Object[] privileges;
		if(Helper.HTTP_SESSION_IN_THREAD.get()!=null){
			privileges = (Object[])Helper.HTTP_SESSION_IN_THREAD.get().getAttribute(PRIVILEGES);
		}else
			return false;
		if(privileges == null)
			return false;
		for(Object p : privileges){
			if(((String)p).equals(privilegeCode)) 
				return true;
		}
		return false;		
	}
	
	
	public Helper(){
		jsonObject = new JSONObject();
	}
	
	public Object put(String key,JSONArray o){	
		return jsonObject.put(key, o);
	}
	
	public Object put(String key,String o){	
		return jsonObject.put(key, o);
	}
	
	public Object put(String key,int o){	
		return jsonObject.put(key, o);
	}
	
	public Object put(String key,double o){	
		return jsonObject.put(key, o);
	}
	
	public Object put(String key,long o){	
		return jsonObject.put(key, o);
	}
	
	public Object put(String key,boolean o){	
		return jsonObject.put(key, o);
	}
	
	public Object put(String key,JSONObject o){	
		return jsonObject.put(key, o);
	}
	
	public Object put(String key,Date o){	
		return jsonObject.put(key, Tools.date2String(o));
	}
	
	public static Date toDate(String dateString) throws ParseException{
		return Tools.string2Date(dateString);		
	}
	
	public static String toDateString(Date date) throws ParseException{
		return Tools.date2String(date);
	}
	
	
	public String toString(int indentFactor){
		return jsonObject.toString(indentFactor);
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

}
