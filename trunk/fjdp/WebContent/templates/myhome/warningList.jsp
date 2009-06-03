<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="msg-body">
	<p class="welcome">
		您共有<span class="countNumber"><s:property value="warningMessages.size"/></span>条预警信息未读！
		<s:if test="warningMessages.size>0"><span class="alertImage"></span></s:if>
		<a id="myhome-warning-refresh" class="block refreshBt" href="#" 
			onclick="Ext.getCmp('warning-portal').refresh();">点击刷新</a>
	</p>
	<s:iterator value="warningMessages" status="rowStatus">
		<div class="msg-item <s:if test="#rowStatus.even">even</s:if>">
			<p><span class="company"><s:property value="info.enterprise.name"/></span>
				<span class="message"><s:property value="info.contents"/></span>
				<s:if test="!info.mustFeedback">
					<a class="block reading" href="#" onclick="Ext.getCmp('warning-portal').tag(this,<s:property value="id"/>);">标记为已阅</a>
				</s:if>
					<a class="block feedback" href="#" onclick="Ext.getCmp('warning-portal').feedback(this,<s:property value="id"/>);">已阅及反馈</a>
				
				
			</p>
		</div>
	</s:iterator>	
</div>
<script type="text/javascript">

</script>
