package net.fortunes.admin.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import net.fortunes.admin.model.NoticeMessage;
import net.fortunes.admin.model.User;
import net.fortunes.core.service.GenericService;

@Component
public class NoticeMessageService extends GenericService<NoticeMessage> {

	public List<NoticeMessage> getNotReadedNoticesByUser(User authedUser) {
		return getDefDao().findByCriteria(DetachedCriteria.forClass(NoticeMessage.class)
				.add(Restrictions.eq("user", authedUser))
				.add(Restrictions.eq("readed", false)));
	}
	
}
