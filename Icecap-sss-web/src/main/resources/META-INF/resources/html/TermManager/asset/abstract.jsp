<%@page import="osp.icecap.sss.constants.MVCCommandNames"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="osp.icecap.sss.service.TermLocalServiceUtil"%>
<%@page import="osp.icecap.sss.model.Term"%>
<%@ include file="../../init.jsp" %>

<%
	long termId = ParamUtil.getLong(renderRequest, "termId");

	Term term = null;

	term = TermLocalServiceUtil.getTerm(termId);
%>

<liferay-portlet:renderURL varImpl="viewTermURL">
	<portlet:param name="mvcRenderCommandName" value="<%= MVCCommandNames.RENDER_TERM_VIEW %>" />
	<portlet:param name="termId" value="<%=String.valueOf(termId)%>" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL varImpl="viewURL">
	<portlet:param name="mvcRenderCommandName" value="<%= MVCCommandNames.RENDER_TERM_LIST %>" />
</liferay-portlet:renderURL>

<liferay-ui:header
		backURL="<%=viewURL.toString()%>"
		title="<%=term.getDisplayTitle(locale)%>" 
/>

<dl>
	<dt>Term</dt>
	<dd><%=term.getDisplayTitle(locale)%></dd>
	<dt>Parameter Name</dt>
	<dd><%=term.getName()%></dd>
	<dt>Type</dt>
	<dd><%=term.getType()%></dd>
	<dt>Definition</dt>
	<dd><%=term.getDefinition(locale, true) %></dd>
	<dt>Definition</dt>
	<dd><%=term.getDefinition(locale) %></dd>
</dl>