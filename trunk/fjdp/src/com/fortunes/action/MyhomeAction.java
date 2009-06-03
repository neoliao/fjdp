package com.fortunes.action;

import java.util.List;
import net.fortunes.admin.model.NoticeMessage;
import net.fortunes.admin.service.NoticeMessageService;
import net.fortunes.admin.service.NoticeService;
import net.fortunes.core.action.BaseAction;

public class MyhomeAction extends BaseAction{
	private NoticeService noticeService;
	private NoticeMessageService noticeMessageService;
	
	private List<NoticeMessage> noticeMessages;

	public String noticeList()throws Exception{
		setNoticeMessages(noticeMessageService.getNotReadedNoticesByUser(authedUser));
		return TEMPLATE;
	}

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
	
}
