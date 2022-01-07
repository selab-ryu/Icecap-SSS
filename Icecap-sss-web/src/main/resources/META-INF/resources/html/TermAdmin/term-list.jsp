
<%@page import="com.liferay.portal.kernel.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="osp.icecap.sss.web.taglib.clay.TermVerticalCard"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="osp.icecap.sss.web.display.context.TermManagementToolbarDisplayContext"%>
<%@page import="com.liferay.portal.kernel.dao.search.SearchContainer"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portal.kernel.dao.search.RowChecker"%>
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

	String viewStyle = ParamUtil.getString(
											renderRequest, 
											IcecapSSSWebKeys.DISPLAY_STYLE, 
											IcecapSSSConstants.VIEW_TYPE_TABLE);
%>

<portlet:renderURL var="addTermURL">
    <portlet:param 
    		name="<%= IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME %>" 
    		value="<%= MVCCommandNames.RENDER_ADMIN_TERM_EDIT %>"/>
    <portlet:param 
    		name="<%= IcecapSSSWebKeys.REDIRECT %>" 
    		value="<%= currentURL %>"/>
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

<clay:management-toolbar
	displayContext="<%=termAdminManagementToolbarDisplayContext%>"
/>
	
<div class="closed container-fluid container-fluid-max-xl sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-frontend:sidebar-panel
		searchContainerId="<%= IcecapSSSConstants.SEARCH_CONTAINER_ID %>"
	>	</liferay-frontend:sidebar-panel>
	
	<div class="sidenav-content">
		<aui:form action="" method="get" name="fm">
			<aui:input name="cmd" type="hidden"></aui:input>
			<aui:input name="redirect" type="hidden"></aui:input>
		
	 	<liferay-ui:search-container 
	 		id="<%= IcecapSSSConstants.SEARCH_CONTAINER_ID %>"
		    searchContainer="<%= termAdminManagementToolbarDisplayContext.getSearchContainer() %>" >
		    
		        <liferay-ui:search-container-row
							className="osp.icecap.sss.model.Term" 
							keyProperty="termId" 
							modelVar="term"
							 
							escapedModel="<%=true%>">
							
					<%
					Map<String, Object> rowData = new HashMap<>();

					// rowData.put("actions", StringUtil.merge(termAdminManagementToolbarDisplayContext.getAvailableActions(term)));

					row.setData(rowData);
					//row.setCssClass("col-md-12");

					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setParameter(IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_ADMIN_TERM_VIEW);
					rowURL.setParameter(IcecapSSSWebKeys.REDIRECT, currentURL);
					rowURL.setParameter(IcecapSSSWebKeys.TERM_ID, String.valueOf(term.getTermId()));
					
					%>
					<c:choose>
						<c:when test="<%= viewStyle.equals(IcecapSSSConstants.VIEW_TYPE_CARDS) %>">
							<%
								row.setCssClass("lfr-asset-item col-md-3 col-sm-4 col-xs-6");
								RowChecker rowChecker = searchContainer.getRowChecker();
								rowChecker.setData( rowData );
								TermVerticalCard termVerticalCard = new TermVerticalCard(
										term, 
										renderRequest, 
										renderResponse, 
										rowChecker, 
										rowURL.toString(),
										termAdminManagementToolbarDisplayContext.getTermActionDropdownItems(term.getTermId()));
							%>
							<liferay-ui:search-container-column-text>
								<clay:vertical-card
										verticalCard="<%= termVerticalCard %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:when test="<%= viewStyle.equals(IcecapSSSConstants.VIEW_TYPE_LIST) %>">
							<liferay-ui:search-container-column-text href="<%=rowURL.toString() %>" >
								<h5 class=“text-default”><%= term.getDisplayName(locale) %></h5>
								<h5><%= term.getTermVersion() %></h5>
							</liferay-ui:search-container-column-text>
							<liferay-ui:search-container-column-text  colspan="3" >
	  							<h6 class=“text-default”>
	    							<%= term.getTermName() %>
	  							</h6>
	  							<h6>
	  								<%= term.getDefinition(locale) %>
	  							</h6>
							</liferay-ui:search-container-column-text>
							<liferay-ui:search-container-column-text>
								<clay:dropdown-actions
									dropdownItems="<%= termAdminManagementToolbarDisplayContext.getTermActionDropdownItems(term.getTermId()) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:when>
						<c:otherwise>
							<%
								row.setCssClass("col-md-12 "+row.getCssClass() );
							%>
							<liferay-ui:search-container-column-text
							 			name="display-name"
							 			href="<%=rowURL.toString() %>"
										value="<%=term.getDisplayName(locale)%>"/>
							<liferay-ui:search-container-column-text 
										name="parameter-name" 
										property="termName" />
							<liferay-ui:search-container-column-text 
										name="version" 
										property="termVersion"/>
							<liferay-ui:search-container-column-text 
										name="type" 
										property="termType" />
			
							<liferay-ui:search-container-column-text 
										name="definition" 
										value="<%= term.getDefinition(locale)%>" />
							
							<liferay-ui:search-container-column-text 
										name="modified-date"
										value="<%= DateUtil.getDate(term.getModifiedDate(), "yyyy-MM-dd", locale) %>"/>
							
							<liferay-ui:search-container-column-status 
										name="status" 
										property="status" />
			
							<liferay-ui:search-container-column-text name="actions">
								<clay:dropdown-actions
									dropdownItems="<%= termAdminManagementToolbarDisplayContext.getTermActionDropdownItems(term.getTermId()) %>"
								/>
							</liferay-ui:search-container-column-text>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-row>
				
				<c:choose>
					<c:when test="<%= viewStyle.equals(IcecapSSSConstants.VIEW_TYPE_TABLE) %>">
						<liferay-ui:search-iterator
								markupView="lexicon" />
					</c:when>
					<c:otherwise>
						<liferay-ui:search-iterator
								displayStyle = "<%= viewStyle %>"
								markupView="lexicon" />
					</c:otherwise>
				</c:choose>
		</liferay-ui:search-container>
		</aui:form>
	</div>
</div>

<script type="text/javascript">
Liferay.componentReady('termAdminManagementToolbar').then(function(
		managementToolbar
	) {

		managementToolbar.on('actionItemClicked', function(event) {
			confirm('confirm...');
			console.log('Data CMD: ', event.data.item.data.cmd );
			var form = document.getElementById('<portlet:namespace />fm');

			Liferay.Util.postForm(form, {
				data:{
					cmd: event.data.item.data.cmd,
					redirect: '<%= currentURL %>'
				},
				url: '<portlet:actionURL name="<%=MVCCommandNames.ACTION_ADMIN_BULK_ACTIONS %>" />'
			});
		});
	});
</script>
