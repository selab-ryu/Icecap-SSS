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

<%
	Term term = (Term)renderRequest.getAttribute(IcecapSSSWebKeys.TERM); 
	SelectOption option = new SelectOption("Option 1", "1"); 
%>
<h1>Edit Term</h1>
<aui:container>
	<aui:form >
	<aui:row>
		<aui:col md="2">
		<div class="row term-type-section">
			<%@include file="jspf/parameter-type.jspf" %>
		</div>
		</aui:col>
	</aui:row>
	<aui:row>
		<aui:col md="12">
			<div class="term-name-section">
				<aui:fieldset-group markupView="lexicon">
					<aui:fieldset label="term-name-definition">
			<aui:row>
		<aui:col md="2">
					<aui:input name="name" label="parameter-name" inlineField="<%=true %>" required="true"></aui:input>
		</aui:col>
		<aui:col md="3">
					<aui:input name="displayName" label="term-display-name" localized="true" required="true" model="<%= Term.class %>"></aui:input>
		</aui:col>
		<aui:col md="2">
					<aui:input name="version" label="term-version" inlineField="<%=true %>" required="true"></aui:input>
		</aui:col>
			</aui:row>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
		</aui:col>
	</aui:row>
	
	</aui:form>
</aui:container>

