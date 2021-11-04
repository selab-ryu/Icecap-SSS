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

	String cmd = ParamUtil.getString(renderRequest, "CMD", IcecapSSSActionKeys.ADD_TERM);
	String submitButtonLabel = "";
	
	if( cmd.equals(IcecapSSSActionKeys.ADD_TERM) ){
		submitButtonLabel = LanguageUtil.get(locale, "add-term", "Add Term");
	}
	else{
		submitButtonLabel = LanguageUtil.get(locale, "update-term", "Update Term");
	}
	
	Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(); 	
	List<String>  availableLanguageIds = new ArrayList<>();
	for( Locale availableLocale : availableLocales ){
		availableLanguageIds.add("\""+LanguageUtil.getLanguageId(availableLocale)+"\"");
	}
	
	System.out.println( availableLanguageIds.toString() );

%>
<style>
.hide {
	display:none;
}

.show {
	display:block;
}
</style>
<liferay-asset:asset-categories-error />
<liferay-asset:asset-tags-error />

<aui:container>
	<aui:form >
		<aui:row>
			<aui:col md="3" cssClass="term-type-section">
				<%@include file="jspf/parameter-type.jspf" %>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col md="12" cssClass="term-definition-section">
				<%@include file="jspf/term-definition.jspf" %>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col md="12">
				<%@include file="jspf/categorization.jspf" %>
			</aui:col>
		</aui:row>
		<aui:button-row>
			<aui:button type="submit" name="submit" value="<%=submitButtonLabel %>"></aui:button>
			<aui:button type="reset" name="clear" value="<%= LanguageUtil.get(locale, "clear", "Clear") %>"></aui:button>
		</aui:button-row>
	</aui:form> 
</aui:container>


<script>
var term = new SSS.Term();

$('#<portlet:namespace/>termType').val("<%= IcecapSSSTermTypes.NUMERIC%>");
$('#<portlet:namespace/>termType').trigger('change');

</script>
