<%@page import="osp.icecap.sss.constants.IcecapSSSClassNames"%>
<%@page import="osp.icecap.sss.constants.IcecapSSSTermTypes"%>
<%@page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.SelectOption"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="osp.icecap.sss.constants.IcecapSSSWebKeys"%>
<%@page import="osp.icecap.sss.model.Term"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ include file="../init.jsp" %>

<script src="<%= renderRequest.getContextPath()%>/js/plugins/mustache/jquery.mustache.js"></script>
<script src="<%= renderRequest.getContextPath()%>/js/plugins/mustache/mustache.min.js"></script>
<script type="text/javascript" src="<%= renderRequest.getContextPath()%>/js/sss-constants.js"></script>
<script type="text/javascript" src="<%= renderRequest.getContextPath()%>/js/sss-term.js"></script>

<%
	Term term = (Term)renderRequest.getAttribute(IcecapSSSWebKeys.TERM); 
%>

<liferay-asset:asset-categories-error />
<liferay-asset:asset-tags-error />

<portlet:actionURL var="updateTermUrl">
</portlet:actionURL>

<aui:container id="formContainer">
</aui:container>

<script type="text/javascript">

console.log('SSS.Term:', SSS.Term );
$('#<portlet:namespace/>formContainer').append( SSS.Term.getEditForm(
							'<%=updateTermUrl.toString()%>',
							'<portlet:namespace/>termEditForm',
							'<portlet:namespace/>termEditForm',
							'POST'
							) );

</script>

