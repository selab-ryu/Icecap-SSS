<%@page import="osp.icecap.sss.util.term.IcecapSSSTermAttributeUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.kernel.util.Validator"%>
<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@page import="osp.icecap.sss.constants.MVCCommandNames"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="osp.icecap.sss.constants.IcecapSSSActionKeys"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Set"%>
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

<script type="text/javascript" src="<%= renderRequest.getContextPath()%>/js/sss-constants.js"></script>
<script type="text/javascript" src="<%= renderRequest.getContextPath()%>/js/sss-term.js"></script>

<%
	Term term = (Term)renderRequest.getAttribute(IcecapSSSWebKeys.TERM);

	String cmd = ParamUtil.getString(renderRequest, Constants.CMD, Constants.ADD);
	
	String defaultTermType = ParamUtil.getString(renderRequest, IcecapSSSWebKeys.TERM_TYPE, IcecapSSSTermTypes.STRING);

	String submitButtonLabel = "";
	
	System.out.println("CMD: "+cmd);
	if( cmd.equals(Constants.ADD) ){
		submitButtonLabel = LanguageUtil.get(locale, "add-term", "Add Term");
	}
	else{
		submitButtonLabel = LanguageUtil.get(locale, "update-term", "Update Term");
		defaultTermType = term.getTermType();
	}
	
	Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(); 	
	List<String>  availableLanguageIds = new ArrayList<>();
	for( Locale availableLocale : availableLocales ){
		availableLanguageIds.add("\""+LanguageUtil.getLanguageId(availableLocale)+"\"");
	}
	
	System.out.println("Default Term Type: "+defaultTermType);
%>
<style>
.hide {
	display:none;
}

.show {
	display:block;
}
</style>

<liferay-portlet:actionURL name="<%= MVCCommandNames.ACTION_ADMIN_TERM_ADD %>" var="addTermURL">
</liferay-portlet:actionURL>

<portlet:actionURL name="<%= MVCCommandNames.ACTION_ADMIN_TERM_UPDATE %>" var="updateTermURL">
</portlet:actionURL>

<portlet:actionURL name="<%= MVCCommandNames.ACTION_ADMIN_LOAD_TERM_ATTRIBURES %>" var="loadTermAttributesURL"/>

<div class="container-fluid container-fluid-max-xl main-content-body">

<liferay-asset:asset-categories-error />
<liferay-asset:asset-tags-error />

<aui:row>
	<aui:col md="3" cssClass="card-horizontal">
		<aui:form name="termTypeForm" method="POST" action="<%= loadTermAttributesURL %>" markupView="lexicon">
					<aui:select name="termType" label="term-type" helpMessage="term-type-select-help">
						<%
						final String[] parameterTypes = IcecapSSSTermTypes.getTypes();
						for( String parameterType : parameterTypes ){
							if( parameterType.equals(IcecapSSSTermTypes.GROUP) || parameterType.equals(IcecapSSSTermTypes.COMMENT) ){
								continue;
							}
							if( parameterType.equals( defaultTermType )){
						%>
							<aui:option label="<%=parameterType %>" value="<%=parameterType %>" selected="<%= true %>"/>
						<%
							}
							else {
						%>
								<aui:option label="<%=parameterType %>" value="<%=parameterType %>"/>
						<%
							}
						}
						%>
					</aui:select>
					
					<input type="hidden" name="<portlet:namespace/>mvcRenderCommandName" value="<%= MVCCommandNames.RENDER_ADMIN_TERM_EDIT %>" >
					<input type="hidden" name="<portlet:namespace/>termListViewURL" value="<%= redirect %>" >
		</aui:form>
	</aui:col>
	<aui:col md="7">
	</aui:col>
	<aui:col md="2" >
		<clay:link href="<%=redirect %>" icon="list" label="view-term-list" />
	</aui:col>
</aui:row>

<%
	String actionURL;
	
	if( cmd.equals(Constants.ADD) ){
		actionURL = addTermURL.toString();
	}
	else{
		actionURL = updateTermURL.toString();
	}
	
	JSONObject jsonDedicatedAttrs = null;
	if( Validator.isNotNull(term) ){
		jsonDedicatedAttrs = IcecapSSSTermAttributeUtil.toJsonObject(term.getAttributesJSON());
	}
%>
<aui:form name="termDefForm" method="POST" action="<%= actionURL %>">
	<aui:row>
		<aui:col>
			<aui:input type="hidden" name="termId" value="<%= Validator.isNotNull(term)?term.getTermId():StringPool.BLANK %>"></aui:input>
			<aui:input type="hidden" name="selectedTermType" value="<%= defaultTermType %>"></aui:input>
			<%@include file="jspf/term-definition.jspf" %>
		</aui:col>
	</aui:row>
	<aui:row>
		<aui:col id="dedicatedAttributes">
			<c:choose>
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.STRING) %>">
					<%@include file="jspf/string-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.NUMERIC) %>">
					<%@include file="jspf/numeric-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.LIST) %>">
					<%@include file="jspf/list-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.LIST_ARRAY) %>">
					<%@include file="jspf/list-array-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.BOOLEAN) %>">
					<%@include file="jspf/boolean-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.ARRAY) %>">
					<%@include file="jspf/array-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.MATRIX) %>">
					<%@include file="jspf/matrix-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.OBJECT) %>">
					<%@include file="jspf/object-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.OBJECT_ARRAY) %>">
					<%@include file="jspf/object-array-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.DATA_LINK) %>">
					<%@include file="jspf/data-link-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.DATA_LINK_ARRAY) %>">
					<%@include file="jspf/data-link-array-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.FILE) %>">
					<%@include file="jspf/file-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.FILE_ARRAY) %>">
					<%@include file="jspf/file-array-attributes.jspf" %>
				</c:when> 
				<c:when test="<%= defaultTermType.equals(IcecapSSSTermTypes.PHONE) %>">
					<%@include file="jspf/phone-attributes.jspf" %>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
		</aui:col>
	</aui:row>
	<aui:row>
		<aui:col>
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset collapsed="<%= false %>" collapsible="<%= true %>" label="categorization" helpMessage="term-categories-help">
					<liferay-asset:asset-categories-selector 
								className="<%=Term.class.getName()%>" 
								classPK="<%= Validator.isNotNull(term)?term.getTermId():0 %>"/>
				</aui:fieldset>
			</aui:fieldset-group>
		</aui:col>
	</aui:row>
	<aui:button-row>
		<aui:button name="submit" type="submit" value="<%= submitButtonLabel %>"></aui:button>
		<aui:button name="clear" type="reset" value="clear"></aui:button>
		<aui:button name="cancel" type="cancel" value="cancel" href="<%= redirect %>"></aui:button>
	</aui:button-row>

</aui:form>
<div class="container-fluid container-fluid-max-xl main-content-body">
<script>
$(document).ready(function(){
	var hasDedicatedAttributes = function( termType ){
		if( termType === '<%= IcecapSSSTermTypes.EMAIL%>' ||
			 termType === '<%= IcecapSSSTermTypes.DATE %>' )
			return false;
		
		return true;
	};
	
	$('#<portlet:namespace/>termType').change(function(){
		var termType = $('#<portlet:namespace/>termType').val();
		console.log('termType: '+termType);
		$('#<portlet:namespace/>selectedTermType').val(termType);
		
		if( hasDedicatedAttributes( termType ) ){
			$('#<portlet:namespace/>termTypeForm').submit();
		}
		else{
			$('#<portlet:namespace/>dedicatedAttributes').empty();
		}
	});
	
});



</script>
