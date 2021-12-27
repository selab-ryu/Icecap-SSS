
<%@page import="osp.icecap.sss.constants.MVCCommandNames"%>
<%@page import="osp.icecap.sss.constants.IcecapSSSWebKeys"%>
<%@ include file="../init.jsp" %>

<portlet:renderURL var="addTermURL">
    <portlet:param 
    		name="<%= IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME %>" 
    		value="<%= MVCCommandNames.RENDER_ADMIN_TERM_EDIT %>"/>
    <portlet:param 
    		name="<%= IcecapSSSWebKeys.REDIRECT %>" 
    		value="<%= curentURL %>"/>
</portlet:renderURL>

<div class="container-fluid container-fluid-max-xl main-content-body">
	<aui:button-row>
	    <aui:button value="add-term" onClick="<%= addTermURL %>"></aui:button>
	</aui:button-row>
</div>

