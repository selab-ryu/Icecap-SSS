<%@page import="osp.icecap.sss.constants.MVCCommandNames"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="osp.icecap.sss.service.TermLocalServiceUtil"%>
<%@page import="osp.icecap.sss.model.Term"%>
<%@ include file="../../init.jsp" %>

<%
	Term term = (Term)renderRequest.getAttribute("term");
%>

<liferay-portlet:renderURL varImpl="viewTermURL">
	<portlet:param name="mvcRenderCommandName" value="<%= MVCCommandNames.RENDER_ADMIN_TERM_VIEW %>" />
	<portlet:param name="termId" value="<%=String.valueOf(term.getTermId())%>" />
</liferay-portlet:renderURL>

<liferay-portlet:renderURL varImpl="viewURL">
	<portlet:param name="mvcRenderCommandName" value="<%= MVCCommandNames.RENDER_ADMIN_TERM_LIST %>" />
</liferay-portlet:renderURL>

<dl>
	<dt>Parameter Name</dt>
	<dd><%=term.getTermName()%></dd>
	<dt>Type</dt>
	<dd><%=term.getTermType()%></dd>
	<dt>Definition</dt>
	<dd><%=term.getDefinition(locale, true) %></dd>
</dl>