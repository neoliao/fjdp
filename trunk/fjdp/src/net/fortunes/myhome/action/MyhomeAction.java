package net.fortunes.myhome.action;

import java.util.List;
import net.fortunes.admin.model.NoticeMessage;
import net.fortunes.admin.model.User;
import net.fortunes.admin.service.NoticeMessageService;
import net.fortunes.admin.service.NoticeService;
import net.fortunes.admin.service.UserService;
import net.fortunes.core.action.BaseAction;

public class MyhomeAction extends BaseAction{
	
	private NoticeService noticeService;
	private NoticeMessageService noticeMessageService;
	private UserService userService;
	
	private List<NoticeMessage> noticeMessages;
	private List<User> onlineUsers;

	public String noticeList()throws Exception{
		noticeMessages = noticeMessageService.getNotReadedNoticesByUser(authedUser);
		return TEMPLATE;
	}
	
	public String loginStat()throws Exception{
		onlineUsers = userService.getOnlineUsers();
		return TEMPLATE;
	}
	
	
	
	
	//====================== setter and getter ======================
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}


	public NoticeService getNoticeService() {
		return noticeService;
	}


	public void setNoticeMessages(List<NoticeMessage> noticeMessages) {
		this.noticeMessages = noticeMessages;
	}


	public List<NoticeMessage> getNoticeMessages() {
		return noticeMessages;
	}

	public void setNoticeMessageService(NoticeMessageService noticeMessageService) {
		this.noticeMessageService = noticeMessageService;
	}

	public NoticeMessageService getNoticeMessageService() {
		return noticeMessageService;
	}

	public void setOnlineUsers(List<User> onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

	public List<User> getOnlineUsers() {
		return onlineUsers;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}
	
}
