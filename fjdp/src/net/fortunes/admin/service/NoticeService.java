package net.fortunes.admin.service;

import java.util.List;
import net.fortunes.admin.model.Notice;
import net.fortunes.admin.model.NoticeMessage;
import net.fortunes.admin.model.User;
import net.fortunes.core.service.GenericService;

public class NoticeService extends GenericService<Notice> {
	
	private NoticeMessageService noticeMessageService;
	
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
	
}
