
<%@page import="osp.icecap.sss.constants.IcecapSSSJsps"%>
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

<portlet:renderURL var="searchRenderURL">
    <portlet:param name="mvcPath" 
    value="<%= IcecapSSSJsps.ADMIN_VIEW_SEARCH_TERMS_JSP %>" />
</portlet:renderURL>

<div class="container-fluid container-fluid-max-xl main-content-body">
	<aui:button-row>
	    <aui:button value="add-term" onClick="<%= addTermURL %>"></aui:button>
	</aui:button-row>
	
	<aui:form action="${searchRenderURL}" name="fm">

    <div class="row">
        <div class="col-md-8">
            <aui:input inlineLabel="left" label="" name="keywords" placeholder="search-entries" size="256" />
        </div>

        <div class="col-md-4">
            <aui:button type="submit" value="search" />
        </div>
    </div>

 </aui:form>
</div>

