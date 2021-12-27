<%@page import="osp.icecap.sss.constants.MVCCommandNames"%>
<%@page import="com.liferay.portal.kernel.service.ServiceContextFunction"%>
<%@page import="com.liferay.portal.kernel.comment.CommentManagerUtil"%>
<%@page import="com.liferay.portal.kernel.comment.Discussion"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="com.liferay.asset.kernel.service.AssetTagLocalServiceUtil"%>
<%@page import="com.liferay.asset.kernel.model.AssetTag"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil"%>
<%@page import="com.liferay.asset.kernel.model.AssetEntry"%>
<%@page import="osp.icecap.sss.service.TermLocalServiceUtil"%>
<%@page import="osp.icecap.sss.model.Term"%>
<%@ include file="../../init.jsp"%>

<%
	long termId = ParamUtil.getLong(renderRequest, "termId");

	Term term = null;

	term = TermLocalServiceUtil.getTerm(termId);
	
	List<Term> history = TermLocalServiceUtil.getTermsByName(term.getTermName());

	term = term.toEscapedModel();

	AssetEntry assetEntry = 
				AssetEntryLocalServiceUtil.getEntry(Term.class.getName(), termId);

	String currentURL = PortalUtil.getCurrentURL(request);
	PortalUtil.addPortletBreadcrumbEntry(request, term.getDisplayTitle(locale), currentURL);
	
	PortalUtil.setPageSubtitle(term.getDisplayTitle(locale), request);
	PortalUtil.setPageDescription(term.getDisplayTitle(locale), request);

	List<AssetTag> assetTags = 
			AssetTagLocalServiceUtil.getTags(Term.class.getName(), termId);
	PortalUtil.setPageKeywords(ListUtil.toString(assetTags, "name"), request);
%>

<liferay-portlet:renderURL varImpl="viewTermListURL">
	<portlet:param name="mvcRenderCommandName" value="<%= MVCCommandNames.RENDER_ADMIN_TERM_LIST %>" />
</liferay-portlet:renderURL>

<liferay-ui:header
		backURL="<%=redirect%>"
		title="view-term-list" 
/>

<dl>
	<dt>Term</dt>
	<dd><%=term.getDisplayTitle(locale)%></dd>
	<dt>Parameter Name</dt>
	<dd><%=term.getTermName()%></dd>
	<dt>Type</dt>
	<dd><%=term.getTermType()%></dd>
	<dt>Definition</dt>
	<dd><%=term.getDefinition(locale, true) %></dd>
</dl>

<liferay-ui:ratings 
		className="<%=Term.class.getName()%>"
		classPK="<%=termId%>" type="stars" />
<% 
	Discussion discussion = 
	CommentManagerUtil.getDiscussion(
			user.getUserId(), 
			scopeGroupId, 
			Term.class.getName(), 
			termId, 
			new ServiceContextFunction(request));
%>

<c:if test="<%= discussion != null %>">
	<h2>
		<strong>
			<liferay-ui:message 
					arguments="<%= discussion.getDiscussionCommentsCount() %>" 
					key='<%= (discussion.getDiscussionCommentsCount() == 1) ? "x-comment" : "x-comments" %>' />
		</strong>
	</h2>
</c:if>
<c:if test="<%= themeDisplay.isSignedIn() %>">
	<liferay-comment:discussion
			className="<%= Term.class.getName() %>"
			classPK="<%= termId %>"
			discussion="<%= discussion %>"
			formName="fm2"
			ratingsEnabled="true"
			redirect="<%= currentURL %>"
			userId="<%= term.getUserId() %>"
	/>
</c:if>
