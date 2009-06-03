<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="msg-body">
<p class="welcome">
	共有<span class="countNumber"><s:property value="feedbacks.size"/></span>条反馈信息！
	<a class="reading block" href="#" onclick="Ext.getCmp('feedback-portal').tagAll(<s:property value="#request.id"/>);">标记所有为已阅</a>
</p>
<s:iterator value="feedbacks" status="rowStatus">
	<div class="msg-item <s:if test="#rowStatus.even">even</s:if>">
		<p>
			<span class="feedbackMsg <s:if test="!readed">new</s:if>"><s:property value="contents"/></span>
		</p>
		<p>
			来自<span class="userName"><s:property value="creator.displayName"/></span>
			创建于<span class="date"><s:date name="createDateTime" format="yyyy-MM-dd hh:mm" /></span>
		</p>
	</div>
</s:iterator>
	
</div>
