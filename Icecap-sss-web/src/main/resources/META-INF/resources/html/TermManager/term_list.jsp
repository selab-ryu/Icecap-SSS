<%@page import="com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.asset.kernel.model.AssetEntry"%>
<%@page import="osp.icecap.sss.web.taglib.clay.TermVerticalCard"%>
<%@page import="osp.icecap.sss.constants.IcecapSSSConstants"%>
<%@page import="osp.icecap.sss.web.util.TermActionDropdownItemsProvider"%>
<%@page import="com.liferay.portal.kernel.security.permission.ActionKeys"%>
<%@page import="osp.icecap.sss.web.security.permission.resource.TermModelPermission"%>
<%@page import="com.liferay.petra.string.StringUtil"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.trash.TrashHelper"%>
<%@page import="osp.icecap.sss.web.display.context.TermManagementToolbarDisplayContext"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="osp.icecap.sss.model.Term"%>
<%@page import="com.liferay.portal.kernel.dao.search.SearchContainer"%>
<%@page import="osp.icecap.sss.constants.IcecapSSSWebKeys"%>
<%@page import="osp.icecap.sss.web.display.context.TermDisplayContext"%>
<%@ include file="../init.jsp" %>


<%
	TermDisplayContext termDisplayContext = 
	(TermDisplayContext)renderRequest.getAttribute(TermDisplayContext.class.getName());

String displayStyle = termDisplayContext.getDisplayStyle();
SearchContainer<Term> termSearchContainer = termDisplayContext.getSearchContainer();
PortletURL portletURL = termSearchContainer.getIteratorURL();
TrashHelper trashHelper = termDisplayContext.getTrashHelper();

TermManagementToolbarDisplayContext termManagementToolbarDisplayContext =
	new TermManagementToolbarDisplayContext(
			liferayPortletRequest, liferayPortletResponse, request, termSearchContainer, trashHelper, displayStyle
			);
%>


<clay:management-toolbar
	displayContext="<%=termManagementToolbarDisplayContext%>"
	searchContainerId="termSearchContainer"
/>

<div class="container-fluid container-fluid-max-xl main-content-body">
	<aui:form action="<%=portletURL.toString()%>" method="get" name="fm">
		<aui:input name="<%=Constants.CMD%>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%=portletURL.toString()%>" />
		<aui:input name="deleteTermIds" type="hidden" />

		<liferay-ui:search-container
			id="termSearchContainer"
			searchContainer="<%=termSearchContainer%>"
		>
			<liferay-ui:search-container-row
				className="osp.icecap.sss.model.Term"
				escapedModel="<%=true%>"
				keyProperty="termId"
				modelVar="term"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/html/TermManager/edit_term" />
					<portlet:param name="redirect" value="<%=portletURL.toString()%>" />
					<portlet:param name="termId" value="<%=String.valueOf(term.getTermId())%>" />
				</liferay-portlet:renderURL>

				<%
					Map<String, Object> rowData = new HashMap<>();

				//				rowData.put("actions", StringUtil.merge(termDisplayContext.getAvailableActions(term)));

						row.setData(rowData);
				%>

				<c:choose>
					<c:when test='<%=displayStyle.equals("descriptive")%>'>
						<liferay-ui:search-container-column-user
							showDetails="<%=false%>"
							userId="<%=term.getUserId()%>"
						/>
				
						<liferay-ui:search-container-column-text
							colspan="<%=2%>"
						>
				
							<h2 class="h5">
								<c:choose>
									<c:when test="<%=TermModelPermission.contains(permissionChecker, term, ActionKeys.UPDATE)%>">
										<aui:a href="<%=rowURL.toString()%>">
											<%=term.getDisplayName(locale)%>
										</aui:a>
									</c:when>
									<c:otherwise>
										<%=term.getDisplayName(locale)%>
									</c:otherwise>
								</c:choose>
							</h2>
				
							<span class="text-default">
								<aui:workflow-status markupView="lexicon" showIcon="<%=false%>" showLabel="<%=false%>" status="<%=term.getStatus()%>" />
							</span>
						</liferay-ui:search-container-column-text>
				
						<liferay-ui:search-container-column-text>
				
							<%
												TermActionDropdownItemsProvider termActionDropdownItemsProvider = 
																			new TermActionDropdownItemsProvider(
																					term, 
																					renderRequest, 
																					renderResponse, 
																					permissionChecker, 
																					trashHelper);
											%>
				
							<clay:dropdown-actions
								defaultEventHandler="<%=IcecapSSSConstants.TERM_ELEMENTS_DEFAULT_EVENT_HANDLER%>"
								dropdownItems="<%=termActionDropdownItemsProvider.getActionDropdownItems()%>"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:when test='<%=displayStyle.equals("icon")%>'>
				
						<%
											row.setCssClass("entry-card lfr-asset-item");
										%>
				
						<liferay-ui:search-container-column-text>
							<clay:vertical-card
								verticalCard="<%=new TermVerticalCard(term, renderRequest, renderResponse, searchContainer.getRowChecker(), trashHelper, rowURL.toString(), permissionChecker)%>"
							/>
						</liferay-ui:search-container-column-text>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand table-cell-minw-200 table-title"
							href="<%=TermModelPermission.contains(permissionChecker, term, ActionKeys.UPDATE) ? rowURL : null%>"
							name="display-name"
							orderable="<%= false %>"
							value="<%= term.getDisplayName(locale) %>"
						/>
				
						<liferay-ui:search-container-column-text
							cssClass="table-cell-expand-smallest table-cell-minw-150"
							name="parameter-name"
							orderable="<%= false %>"
							property="name"
						/>
				
						<liferay-ui:search-container-column-date
							cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
							name="version"
							orderable="<%= false %>"
							property="version"
						/>
				
						<liferay-ui:search-container-column-date
							cssClass="table-cell-expand-smallest table-cell-ws-nowrap"
							name="definition"
							orderable="<%= false %>"
							property="definition"
						/>
				
						<%
						AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(Term.class.getName(), term.getTermId());
						%>
				
						<liferay-ui:search-container-column-text
							cssClass="table-column-text-end"
							name="views"
							orderable="<%= false %>"
							value="<%= String.valueOf(assetEntry.getViewCount()) %>"
						/>
				
						<liferay-ui:search-container-column-status
							name="status"
						/>
				
						<liferay-ui:search-container-column-text>
				
							<%
							TermActionDropdownItemsProvider termActionDropdownItemsProvider = 
									new TermActionDropdownItemsProvider(term, renderRequest, renderResponse, permissionChecker, trashHelper);
							%>
				
							<clay:dropdown-actions
								defaultEventHandler="<%= IcecapSSSConstants.TERM_ELEMENTS_DEFAULT_EVENT_HANDLER %>"
								dropdownItems="<%= termActionDropdownItemsProvider.getActionDropdownItems() %>"
							/>
						</liferay-ui:search-container-column-text>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				displayStyle="<%= displayStyle %>"
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>