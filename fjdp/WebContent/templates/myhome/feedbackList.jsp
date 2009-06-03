<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="msg-body">
<p class="welcome">
	您共有<span class="countNumber"><s:property value="infos.size"/></span>条预警信息的反馈未读！
	<a id="myhome-warning-refresh" class="block refreshBt" href="#" 
		onclick="Ext.getCmp('feedback-portal').refresh();">点击刷新</a>
</p>
<s:iterator value="infos" status="rowStatus">
	<div class="msg-item <s:if test="#rowStatus.even">even</s:if>">
		<p>关于<span class="company"><s:property value="enterprise.name"/></span>
			<span class="message"><s:property value="contents"/></span>这一信息
		</p>
		<p>共有<span class="countNumber"><s:property value="feedbacks.size"/></span>条反馈信息！
			<a class="reading block" href="#" onclick="Ext.getCmp('feedback-portal').read(this,<s:property value="id"/>);">点击阅读</a>
		</p>
	</div>
</s:iterator>
	
</div>
