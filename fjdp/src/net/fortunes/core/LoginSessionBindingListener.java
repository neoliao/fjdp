package net.fortunes.core;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import net.fortunes.admin.model.User;
import net.fortunes.admin.service.UserService;
import net.fortunes.util.Tools;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class LoginSessionBindingListener implements HttpSessionBindingListener {
	
	private static final String USER_SERVICE_NAME = "userService";
	
	private User user;

	public LoginSessionBindingListener(User authedUser) {
		this.setUser(authedUser);
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		User userInDb = loginOrLogOut(event, true);
		Tools.println(Tools.date2String(new Date())+" 用户<"+userInDb.getDisplayName()+">登陆;");
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		User userInDb = loginOrLogOut(event, false);
		Tools.println(Tools.date2String(new Date())+" 用户<"+userInDb.getDisplayName()+">注销;");
	}
	
	private User loginOrLogOut(HttpSessionBindingEvent event,boolean flag){
		HttpSession session = event.getSession();
		UserService userService = lookupService(session);
		User userInDb = userService.get(String.valueOf(user.getId()));
		userService.updateLoginSession(userInDb, flag);
		return userInDb;
	}
	
	private UserService lookupService(HttpSession session){
		WebApplicationContext wac =
			WebApplicationContextUtils.getRequiredWebApplicationContext(session.getServletContext());
		return (UserService) wac.getBean(USER_SERVICE_NAME, UserService.class);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
