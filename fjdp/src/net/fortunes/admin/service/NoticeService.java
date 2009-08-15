package net.fortunes.admin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.fortunes.admin.model.Notice;
import net.fortunes.admin.model.NoticeMessage;
import net.fortunes.admin.model.User;
import net.fortunes.core.service.GenericService;

@Component
public class NoticeService extends GenericService<Notice> {
	
	@Resource private NoticeMessageService noticeMessageService;
	
	public void publishNotice(Notice notice, List<User> users) {
		getDefDao().add(notice);
		for(User user : users){
			NoticeMessage message = new NoticeMessage();
			message.setUser(user);
			message.setReaded(false);
			message.setNotice(notice);
			noticeMessageService.add(message);
		}
	}

	public void setNoticeMessageService(NoticeMessageService noticeMessageService) {
		this.noticeMessageService = noticeMessageService;
	}

	public NoticeMessageService getNoticeMessageService() {
		return noticeMessageService;
	}
	
}
