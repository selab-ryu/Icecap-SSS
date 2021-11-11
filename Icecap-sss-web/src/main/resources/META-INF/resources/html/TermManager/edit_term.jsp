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
			<aui:col md="3" cssClass="term-type-section">
				<%@include file="jspf/parameter-type.jspf" %>
			</aui:col>
		</aui:row>
		<aui:row>
			<aui:col md="12" cssClass="term-name-section">
				<%@include file="jspf/term-definition.jspf" %>
			</aui:col>
		</aui:row>
		<aui:row>
		</aui:row>
	
	</aui:form>
</aui:container>

<script>
$(function() {
	var TERM_TYPE = '<portlet:namespace/>termType',
		TERM_CONTAINER = '<portlet:namespace/>termContainer';

	var TERM_TYPE_ID = '#'+TERM_TYPE,
		TERM_CONTAINER_ID = '#'+TERM_CONTAINER;

	$(TERM_TYPE_ID).on('change', function() {
		var typeElem = $(this);
		var typeVal = typeElem.val() || '';

		console.log(typeVal);

		// type is not selected
		if(typeVal === '') {
			
		} else { // type selected

		}

		$(TERM_CONTAINER_ID).find('[data-row-type]').each(function(i, termWrapper) {
			var rowType = $(termWrapper).data('rowType');
			console.log(rowType);
			switch(rowType) {
				case "common" :
				case typeVal :
					$(termWrapper).show();
					
					// loading term data to UI

					break;
				default:
					$(termWrapper).hide();
					break;
			}
		});
	});

	$(TERM_TYPE_ID).trigger('change');
});
</script>