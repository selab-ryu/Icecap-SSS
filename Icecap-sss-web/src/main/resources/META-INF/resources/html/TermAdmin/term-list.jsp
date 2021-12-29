
<%@page import="osp.icecap.sss.constants.IcecapSSSConstants"%>
<%@page import="osp.icecap.sss.web.display.context.term.admin.TermAdminManagementToolbarDisplayContext"%>
<%@page import="osp.icecap.sss.constants.IcecapSSSJsps"%>
<%@page import="osp.icecap.sss.constants.MVCCommandNames"%>
<%@page import="osp.icecap.sss.constants.IcecapSSSWebKeys"%>
<%@ include file="../init.jsp" %>

<%
	TermAdminManagementToolbarDisplayContext termAdminManagementToolbarDisplayContext = 
				(TermAdminManagementToolbarDisplayContext)renderRequest.getAttribute(
						TermAdminManagementToolbarDisplayContext.class.getName());
%>

<portlet:renderURL var="addTermURL">
    <portlet:param 
    		name="<%= IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME %>" 
    		value="<%= MVCCommandNames.RENDER_ADMIN_TERM_EDIT %>"/>
    <portlet:param 
    		name="<%= IcecapSSSWebKeys.REDIRECT %>" 
    		value="<%= curentURL %>"/>
</portlet:renderURL>

<portlet:renderURL var="searchViewURL">
    <portlet:param name="mvcPath" 
    value="<%= IcecapSSSJsps.ADMIN_VIEW_SEARCH_TERMS_JSP %>" />
</portlet:renderURL>

<portlet:renderURL var="searchTermsURL">
    <portlet:param name="<%= IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME %>" 
    value="<%= MVCCommandNames.RENDER_ADMIN_SEARCH_TERMS %>" />
</portlet:renderURL>

<portlet:actionURL name="<%= MVCCommandNames.ACTION_ADMIN_SEARCH_TERMS %>"  var="searchActionURL">
</portlet:actionURL>


<div class="container-fluid container-fluid-max-xl main-content-body">
	<clay:management-toolbar
		displayContext="<%=termAdminManagementToolbarDisplayContext%>"
	/>
 	<liferay-ui:search-container 
 		id="searchedTermsContainer" 
	    searchContainer="<%= termAdminManagementToolbarDisplayContext.getSearchContainer() %>" >
	        <liferay-ui:search-container-row
						className="osp.icecap.sss.model.Term"
						keyProperty="termId" modelVar="term" escapedModel="<%=true%>">
				<liferay-ui:search-container-column-text 
				 			name="display-name"
							value="<%=term.getDisplayName(locale)%>" />
				<liferay-ui:search-container-column-text property="termName" />
				<liferay-ui:search-container-column-text property="termVersion"/>
				<liferay-ui:search-container-column-text property="termType" />

				<liferay-ui:search-container-column-text name="definition" value="<%= term.getDefinition(locale)%>" />
				
				<liferay-ui:search-container-column-text 
						property="modifiedDate"
						cssClass="table-column-text-end" />
				
				<liferay-ui:search-container-column-text property="status" />

				<liferay-ui:search-container-column-text name="actions">
					<clay:dropdown-actions
						dropdownItems="<%= termAdminManagementToolbarDisplayContext.getTermActionDropdownItems(term.getTermId()) %>"
					/>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>
			
			<liferay-ui:search-iterator />
	</liferay-ui:search-container>
 
</div>

