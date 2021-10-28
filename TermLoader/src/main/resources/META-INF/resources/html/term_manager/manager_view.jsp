<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ include file="../init.jsp"%>
<!-- database css -->
<link rel="stylesheet" type="text/css" href="${contextPath}/css/database/database.css"/>

<!-- mustache -->
<script src="${contextPath}/js/plugins/mustache/jquery.mustache.js"></script>
<script src="${contextPath}/js/plugins/mustache/mustache.min.js"></script>

<script src="${contextPath}/js/stdt/std_basic_object.js"></script>
<script src="${contextPath}/js/stdt/std_super_class.js"></script>
<script src="${contextPath}/js/stdt/osp_datatype.js"></script>

<!-- dataetimePicker -->
<link rel="stylesheet" type="text/css" href="${contextPath}/css/jquery.datetimepicker.css"/>
<script src="${contextPath}/js/plugins/jquery.datetimepicker.js"></script>
<script src="${contextPath}/js/datatypeform/form.js"></script>

<style type="text/css">
	#listItemsDiv{
		border-top : 0;
	}
	
	#activateTBLWrapper{
		max-height : 40rem;
		overflow : auto;
	}
	
	#activatorThead > tr > th{
		position : sticky;
		top : 0;
		z-index : 10;
	}
	#activateTBLWrapper table > tbody > tr > td > div{
		width : 12rem;
		margin-left : auto;
		margin-right : auto;
	}
</style>
<div class="clone-data">
<div class="theme-form-title">
	<h5 class="database-title">
		<liferay-ui:message key="DATATYPE-STRUCTURE-SUBTITLE-STRUCTURE"/>
	</h5>
</div>
</div>
<aui:form name="parameterFrm" onSubmit="return false;">
	<div class="theme-form-column">
		<div class="theme-main-title">
			<div class="main-title-button">
				<h4 class="main-title"><liferay-ui:message key="DATATYPE-STRUCTURE-SUBTITLE-PARAMETER-TITLE"/></h4>
			</div>
		</div>
		<div class="theme-portlet-descript mb-4">
			<liferay-ui:message key="DATATYPE-STRUCTURE-SUBTITLE-PARAMETER-MSG"/>
		</div>
		
		<!-- parameter list. -->
		<div class="structure-list-nav">
			<a href="#" class="list-move-button">
				<i class="icon-chevron-left"></i>
			</a>
			<ul id="parameterList" class="list-nav">
			</ul>
			<a href="#" class="list-move-button">
				<i class="icon-chevron-right"></i>
			</a>
		</div>
		
		<!-- common data s -->
		<div class="structure-list-content">
			<div class="theme-form-area" data-col-type="common">
				<!-- type -->
				<aui:select name="type_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-TYPE" required="true" data-col-name="type_" data-col-result="string">
					<aui:option>--- Select Type ---</aui:option>
					<aui:option value="numeric">Numeric</aui:option>
					<aui:option value="string">String</aui:option>
					<aui:option value="boolean">Boolean</aui:option>
					<aui:option value="array">Array</aui:option>
					<aui:option value="list">List</aui:option>
					<aui:option value="matrix">Matrix</aui:option>
					<aui:option value="group">Group</aui:option>
					<aui:option value="comment">Comment</aui:option>
					<aui:option value="multiString">MultiString</aui:option>
					<aui:option value="file">File</aui:option>
					<aui:option value="phoneNumber">Phone Number</aui:option>
					<aui:option value="email">EMail</aui:option>
					<aui:option value="date">Date</aui:option>
					<aui:option value="object">Object</aui:option>
					<aui:option value="objectArray">Object Array</aui:option>
					<aui:option value="dataLink">DataLink</aui:option>
					<aui:option value="dataLinkArray">DataLink Array</aui:option>
					<aui:option value="filePath">File Path</aui:option>
				</aui:select>
			</div>
			
			<!-- name -->
			<div class="theme-form-area" data-col-type="common">
				<aui:input name="name_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NAME" type="text" required="true" data-col-type="common" data-col-name="name_" data-col-result="string">
					<aui:validator name="custom" errorMessage="DATATYPE-EDIT-VALIDATION-NAME-NOSPACE">
							function (val) {
								if (val.split(" ").length > 1){
									return false;
								}else{
									return true;
								}
							}
						</aui:validator>
						<aui:validator name="custom" errorMessage="DATATYPE-EDIT-VALIDATION-NAME-FORMAT">
							function (val) {
								if (!/^[A-Za-z0-9\-\\_]*$/.test(val)){
									return false;
								}else{
									return true;
								}
							}
						</aui:validator>
				</aui:input>
			</div>
			
			<!-- alias -->	
			<div class="theme-form-area" data-col-type="common">
				<label for="<portlet:namespace/>nameText__" class="control-label">
					<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-COLUMN-ALIAS"/>
					<span class="reference-mark text-warning" id="seii">
						<svg class="lexicon-icon lexicon-icon-asterisk" focusable="false" role="presentation" viewBox="0 0 512 512">
							<path class="lexicon-icon-outline" d="M323.6,190l146.7-48.8L512,263.9l-149.2,47.6l93.6,125.2l-104.9,76.3l-96.1-126.4l-93.6,126.4L56.9,435.3l92.3-123.9L0,263.8l40.4-122.6L188.4,190v-159h135.3L323.6,190L323.6,190z"></path>
						</svg>
					</span>
				</label>
				
				<liferay-ui:input-localized xml="${defaultAliasXml}" id="nameText_" name="nameText_" data-col-result="localization" data-col-name="nameText_"/>
			</div>
			
			<!-- required -->
			<div class="theme-form-area" data-col-type="common">
				<label for="<portlet:namespace/>isRequired_" class="control-label">
					<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-COLUMN-REQUIRED"/>
				</label>
				<div class="form-check form-switch">
					<aui:input type="checkbox" id="isRequired_" label="" value="" name="isRequired_" data-col-result="boolean" data-boolean-type="checked" data-col-name="isRequired_"/>
				</div>
			</div>
			
			
			<!-- tooltip -->
			<div class="theme-form-area" data-col-type="common">
				<label for="<portlet:namespace/>tooltip__" class="control-label">
					<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-COLUMN-TOOLTIP"/>
				</label>
				<liferay-ui:input-localized xml="${defaultTooltipXml}" id="tooltip_" name="tooltip_" data-col-result="localization" data-col-name="tooltip_"/>
			</div>
			
			<!-- Description -->
			<div class="theme-form-area" data-col-type="common">
				<label for="<portlet:namespace/>description__" class="control-label">
					<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-COLUMN-DESCRIPTION"/>
				</label>
				<liferay-ui:input-localized type="textarea" xml="${defaultDescriptionXml}" id="description_" name="description_" data-col-result="localization" data-col-name="description_"/>
			</div>
			
			<!-- numeric parameter -->
			<div class="theme-form-area" data-col-type="numeric">
				<!-- unit -->
				<aui:input name="unit_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NUMERIC-UNIT" type="text" data-col-result="string" data-col-name="unit_">
				</aui:input>
				
				<!-- uncertainty -->
				<label for="<portlet:namespace/>uncertainty_" class="control-label">
					<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NUMERIC-UNCERTAINTY"/>
				</label>
				<div class="form-check form-switch">
					<aui:input type="checkbox" id="uncertainty_" label="" value="" name="uncertainty_" data-col-result="boolean" data-boolean-type="checked" data-col-name="uncertainty_"/>
				</div>
				
				<div class="row">
					<div class="col-md-2 col-lg-2 col-sm-12">
						<!-- minimum value -->
						<aui:input label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NUMERIC-MIN" name="lowerBoundary_" type="text" data-col-result="object" data-col-name="lowerBoundary_" data-obj-type="number" data-obj-name="range_">
							<aui:validator name="number"/>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-NUMERIC-MINIMUM-GREATER-DEFAULT">
								function(val){
									var lowBoundary = $('#<portlet:namespace/>lowOperrand').val() || '=',
										defaultVal = $('#<portlet:namespace/>numericValue').val() || '';
										
									if(defaultVal !== '' && !isNaN(Number(defaultVal))){
										if(lowBoundary === '='){
											return val <= defaultVal;
										}else{
											return val < defaultVal;
										}
									}else{
										return true;
									}
								}
							</aui:validator>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-NUMERIC-MINIMUM-GREATER-MAXIMUM">
								function(val){
									var maxVal = $('#<portlet:namespace/>upperBoundary_').val() || '';

									if(maxVal !== '' && !isNaN(Number(maxVal))){
										return val < Number(maxVal);
									}else{
										return true;
									}
								}
							</aui:validator>
						</aui:input>
					</div>
					
					<div class="col-md-2 col-lg-2 col-sm-12">
						<!-- minimum select -->
						<aui:select label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NUMERIC-MINBOUNDARY" id="lowOperrand" name="operrand_" data-col-result="object" data-col-name="operrand_" data-obj-type="string" data-obj-name="range_">
							<aui:option value="="><=</aui:option>
							<aui:option value=">"><</aui:option>
						</aui:select>
					</div>
					<div class="col-md-4 col-lg-4 col-sm-12">
						<!-- default value -->
						<aui:input name="numericValue_" id="numericValue" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-VALUE" type="text" data-obj-type="number" data-obj-name="value_">
							<aui:validator name="number"/>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-NUMERIC-DEFAULT-LESS-MINIMUM">
								function(val){
									var lowBoundary = $('#<portlet:namespace/>lowOperrand').val() || '=',
										lowVal = $('#<portlet:namespace/>lowerBoundary_').val() || '';
									
									if(lowVal !== '' && !isNaN(Number(lowVal))){
										if(lowBoundary === '='){
											return val >= lowVal;
										}else{
											return val > lowVal;
										}
									}else{
										return true;
									}
								}
							</aui:validator>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-NUMERIC-DEFAULT-GREATER-MAXIMUM">
								function(val){
									var upBoundary = $('#<portlet:namespace/>upOperrand').val() || '=',
										upVal = $('#<portlet:namespace/>upperBoundary_').val() || '';
									if(upVal !== '' && !isNaN(Number(upVal))){
										if(upBoundary === '='){
											return Number(val) <= Number(upVal);
										}else{
											return val < upVal;
										}
									}else{
										return true;
									}
								}
							</aui:validator>
						</aui:input>
					</div>
					
					<div class="col-md-2 col-lg-2 col-sm-12">
						<!-- maximum select -->
						<aui:select label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NUMERIC-MAXBOUNDARY" id="upOperrand" name="operrand_"  data-col-result="object" data-col-name="operrand_" data-obj-type="string" data-obj-name="range_">
							<aui:option value="="><=</aui:option>
							<aui:option value="<"><</aui:option>
						</aui:select>
					</div>
					
					<div class="col-md-2 col-lg-2 col-sm-12">
						<!-- maximum value -->
						<aui:input label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NUMERIC-MAX" name="upperBoundary_" type="text" data-col-result="object" data-col-name="upperBoundary_" data-obj-type="number" data-obj-name="range_">
							<aui:validator name="number"/>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-NUMERIC-MAXIMUM-LESS-DEFAULT">
								function(val){
									var upBoundary = $('#<portlet:namespace/>upOperrand').val() || '=',
										defaultVal = $('#<portlet:namespace/>numericValue').val() || '';
									
									if(defaultVal !== '' && !isNaN(Number(defaultVal))){
										if(upBoundary === '='){
											<%-- Liferay.Util.focusFormField(document.<portlet:namespace/>parameterFrm.<portlet:namespace/>numericValue_);
											Liferay.Util.focusFormField(document.<portlet:namespace/>parameterFrm.<portlet:namespace/>upperBoundary_);
											Liferay.Util.focusFormField(''); --%>
											return Number(val) >= Number(defaultVal);
										}else{
											return val > defaultVal;
										}
									}else{
										return true;
									}
									<%-- Liferay.Util.focusFormField('#<portlet:namespace/>numericValue'); --%>
								}
							</aui:validator>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-NUMERIC-MAXIMUM-LESS-MINIMUM">
								function(val){
									var minVal = $('#<portlet:namespace/>lowerBoundary_').val() || '';
									if(minVal !== '' && !isNaN(Number(minVal))){
										return val > Number(minVal);
									}else{
										return true;
									}
								}
							</aui:validator>
						</aui:input>
					</div>
				</div>
			</div>
			
			<!-- String parameter data -->
			<div class="theme-form-area" data-col-type="string">
				<div class="row">
					<div class="col-md-6 col-sm-12">
						<aui:input name="stringMinSize_" id="stringMinSize_" type="text" data-col-result="number" data-col-name="minSize_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MIN-LENGTH">
							<aui:validator name="number"/>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-SIZE">
								function(val){
									var maxSize = $('#<portlet:namespace/>stringMaxSize_').val() || '';

									if(maxSize === '' || isNaN(Number(maxSize))){
										return true;
									}else{
										return Number(val) < Number(maxSize);
									}
								}
							</aui:validator>
						</aui:input>
					</div>
					<div class="col-md-6 col-sm-12">
						<aui:input name="stringMaxSize_" id="stringMaxSize_" type="text" data-col-result="number" data-col-name="maxSize_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MAX-LENGTH">
							<aui:validator name="number"/>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-SIZE">
								function(val){
									var mixSize = $('#<portlet:namespace/>stringMinSize_').val() || '';
									
									if(mixSize === '' || isNaN(Number(mixSize))) return true;
									else return Number(mixSize) < Number(val);
								}
							</aui:validator>
						</aui:input>
					</div>
				</div>
				
				<aui:input type="text" name="validationRule_" label="DATATYPE-STRUCTURE-PARAMETER-VALIDATIONRULE" data-col-result="string" data-col-name="validationRule_"
					prefix="/" suffix="/"
				/>
				
				<aui:input name="stringValue_" id="stringValue" type="text" data-col-result="string" data-col-name="value_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-VALUE">
					<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-STRING-VALUE">
						function(val){
							var validationRule = $('#<portlet:namespace/>validationRule_').val() || '';
							var reg = new RegExp(validationRule);
							
							if(reg.test(val) === '') return false;
							else{
								return reg.test(val);
							}
						}
					</aui:validator>
				</aui:input>
			</div>
			
			<!-- MultiString parameter data -->
			<div class="theme-form-area" data-col-type="multiString">
				<aui:input name="value_" id="multiStringValue" type="textarea" data-col-result="string" data-col-name="value_">
				</aui:input>
			</div>
			
			<!-- array parameter -->
			<div class="theme-form-area" data-col-type="array">
				<div class="row">
					<div class="col-md-6 col-sm-12">
						<aui:input name="arrayMinSize_" type="text" data-col-result="number" data-col-name="minSize_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MIN-LENGTH">
							<aui:validator name="number"/>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-SIZE">
								function(val){
									var maxSize = $('#<portlet:namespace/>arrayMaxSize_').val() || '';

									if(maxSize === '' || isNaN(Number(maxSize))){
										return true;
									}else{
										return Number(val) < Number(maxSize);
									}
								}
							</aui:validator>
						</aui:input>
					</div>
					<div class="col-md-6 col-sm-12">
						<aui:input name="arrayMaxSize_" type="text" data-col-result="number" data-col-name="maxSize_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MAX-LENGTH">
							<aui:validator name="number"/>
							<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-SIZE">
								function(val){
									var mixSize = $('#<portlet:namespace/>arrayMinSize_').val() || '';
									
									if(mixSize === '' || isNaN(Number(mixSize))) return true;
									else return Number(mixSize) < Number(val);
								}
							</aui:validator>
						</aui:input>
					</div>
				</div>
				<aui:select name="childrenType_" label="DATATYPE-STRUCTURE-PARAMETER-SUB-TYPE" data-col-result="string" data-col-name="childrenType_">
					<aui:option value="string">String</aui:option>
					<aui:option value="numeric">Numeric</aui:option>
				</aui:select>
				<script type="text/javascript">
					$('#<portlet:namespace/>childrenType_').on('change', function(){
						var child = $(this).val() || '';
						
						if(child === 'string'){
							$('#numericChildren').hide();
// 							$('#stringChildren').show();
						}else if(child === 'numeric'){
							$('#numericChildren').show();
// 							$('#stringChildren').hide();
						}
					});
				</script>
				
				<div class="row" id="numericChildren">
					<!-- unit -->
					<div class="col-md-6">
						<aui:input name="unit_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NUMERIC-UNIT" type="text" data-col-result="string" data-col-name="unit_">
						</aui:input>
					</div>
					<!-- uncertainty -->
					<div class="col-md-6">
						<label for="<portlet:namespace/>uncertainty_" class="control-label">
							<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NUMERIC-UNCERTAINTY"/>
						</label>
						<div class="form-check form-switch">
							<aui:input type="checkbox" id="uncertainty_" label="" value="" name="uncertainty_" data-col-result="boolean" data-boolean-type="checked" data-col-name="uncertainty_"/>
						</div>
					</div>
				</div>
<!-- 				<div class="row" id="stringChildren"> -->
<!-- 				</div> -->
			</div>
			
			
			<div class="theme-form-area" data-col-type="list">
				<!--  add list item part. -->
				<div class="theme-sub-title">
					<h4 class="sub-title"><liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-LIST-MANAGE"/></h4>
				</div>
				<div class="label-button-group">
					<label class="control-label">
						<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-LIST-ITEM-CATEGORY"/>
					</label>
				</div>
				<div class="row mb-3">
					<div class="col-5">
						<aui:input type="text" id="listValue_" name="value_" placeholder="" label="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-TABLE-VALUE" data-save-result="string" inlineLabel="left">
							<aui:validator name="number"/>
						</aui:input>
					</div>
					<div class="col-1">
						<!-- localized inline label 적용 -->
						<label for="" class="control-label mt-2">
							<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-LIST-NICKNAME"/>
						</label>
					</div>
					<div class="col-5">
						<liferay-ui:input-localized xml="${defaultListTextXml}" label="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-LIST-NICKNAME" inlineLabel="left" id="text_" name="text_" data-save-result="localization"/>
					</div>
					<div class="col-1">
						<i class="icon-plus btn-outline-light-dark button icon-plus" data-list-event="save">
						</i>
					</div>
				</div><!-- end of add list item part. -->
				
				<!-- show listItems part -->
				<div id="listItemTitle" class="theme-form-title label-button-group sub-title cursor-pointer" data-event-type="accordion" data-event-target="#listItemsDiv">
					<label for="#listItemsDiv" class="control-label database-title">
						<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST"/>
						<small class="ml-3">
							<i class="icon-question-sign text-purple"><liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-MSG"/></i>
						</small>
					</label>
					<div class="button-group-right">
						<i class="icon-chevron-down">
						</i>
					</div>
				</div>
				<div id="listItemsDiv" name="listItems_" data-col-type="list" data-col-result="list" class="theme-table theme-table-gray" data-info="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-DATA-INFO">
					<div class="searchcontainer">
						<div class="searchcontainer-content">
							<div class="table-responsive">
								<table class="table mt-2">
									<thead>
										<tr>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-TABLE-INDEX"/></th>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-TABLE-KOREAN"/></th>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-TABLE-ENGLISH"/></th>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-TABLE-VALUE"/></th>
											<th scope="col"></th>
										</tr>
									</thead>
									<tbody id="listItemsTbody" class="text-center">
										<tr>
											<td colspan="5" class="text-center"><liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-TABLE-NO-DATA"/></td>
										</tr>
									</tbody>
								</table>
							</div>		
						</div>
					</div>
				</div>
			</div><!-- end of listItems table part -->
			<!-- end of showing listItems part -->
			<!-- group part -->
			<div class="theme-form-area" data-col-type="group">
				<div class="theme-sub-title">
					<h4 class="sub-title"><liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-GROUP-CHOOSE-VAULES"/></h4>
				</div>
				<div data-event-target="true" class="theme-form-radio-group row">
					<div class="form-check-group">
					</div>
				</div>
			</div>
			
			<!-- matrix -->
			<div class="theme-form-area" id="dimensionWrapper" data-col-type="matrix">
				<div class="row">
					<aui:select label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MATRIX-ROW"
						name="dimension_"
						id="xDimension_"
						data-col-result="array"
						wrapperCssClass="col-6"
					>
						<aui:option value="2">2</aui:option>
						<aui:option value="3">3</aui:option>
						<aui:option value="4">4</aui:option>
					</aui:select>
					<aui:select label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MATRIX-COLUMN"
						name="dimension_"
						id="yDimension_"
						data-col-result="array"
						wrapperCssClass="col-6"
					>
						<aui:option value="1">1</aui:option>
						<aui:option value="2">2</aui:option>
						<aui:option value="3">3</aui:option>
						<aui:option value="4">4</aui:option>
					</aui:select>
				</div>
			</div>
			<div class="theme-form-area" id="matrixParamsDiv" data-col-type="matrix" data-col-result="array" data-col-name="matrixValue_">
				<label for="" class="control-label">
						Value
				</label>
				<div class="row" data-col-result="matrix" data-col-name="matrixValue_">
					<aui:input name="matrixValue" label="" type="text" wrapperCssClass="col-md-12">
					</aui:input>
				</div>
				<div class="row" data-col-result="matrix" data-col-name="matrixValue_">
					<aui:input name="matrixValue" label="" type="text" wrapperCssClass="col-md-12">
					</aui:input>
				</div>
			</div>
			
			<script type="text/javascript">
				$(function(){
					var DIMENSION = '#dimensionWrapper',
						X_DIMENSION = '#<portlet:namespace/>xDimension_',
						Y_DIMENSION = '#<portlet:namespace/>yDimension_',
						MATRIX_PARAMS_DIV = '#matrixParamsDiv';
					
					$.Mustache.addFromDom();
					
					$(DIMENSION).on('change', 'select', function(){

						var xDimension = $(X_DIMENSION).val(),
							yDimension = $(Y_DIMENSION).val();

						$(MATRIX_PARAMS_DIV).find('[data-col-result]').remove();
						
						var xLengthArr = new Array(Number(xDimension));
	
						$(xLengthArr).each(function(i, arr){
							xLengthArr[i] = {};
							xLengthArr[i].colNum = Math.floor(12/Number(xDimension));
						})
						for(let i = 0 ; i < yDimension ; i++){
							var html = Mustache.render($('#dimension-template').html(), {
																						'xLength' : xLengthArr
																					});
							$(MATRIX_PARAMS_DIV).append(html);
						}
					});
				});
			</script>
			<script id="dimension-template" type="text/x-mustache">
				<div class="row" data-col-result="matrix" data-col-name="matrixValue_">
				{{#xLength}}
					<aui:input name="matrixValue" label="" type="text" wrapperCssClass="col-md-{{colNum}}">
					</aui:input>
				{{/xLength}}
				</div>
			</script>
		
			<!-- phone number parameter -->
			<div class="theme-form-area" data-col-type="phoneNumber">
				<aui:input id="phoneNumberValue" name="pnValue_" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-VALUE" type="text" data-col-result="string" data-col-name="value_">
					<aui:validator name="custom" errorMessage="DATATYPE-STRUCTURE-PARAMETER-VALIDATION-PHONENUMBER">
						function(val){
							var pass = /(^\+d{0}[.-][1-9]\d?[.-]|^\(?0[1-9]\d?\)?[.-]?)?[1-9]\d{2,3}[.-]\d{4}$/.test(val) || /^\d{2,3}-\d{3,4}-\d{4}$/.test(val);
							
							return pass;
						}
					</aui:validator>
				</aui:input>
			</div>
			<!-- file paramter  -->s
			<div class="theme-form-area" data-col-type="file">
				<div class="label-button-group">
					<label class="control-label">
						참조 데이터타입 설정
						<span class="reference-mark text-warning" id="seii">
							<svg class="lexicon-icon lexicon-icon-asterisk" focusable="false" role="presentation" viewBox="0 0 512 512">
								<path class="lexicon-icon-outline" d="M323.6,190l146.7-48.8L512,263.9l-149.2,47.6l93.6,125.2l-104.9,76.3l-96.1-126.4l-93.6,126.4L56.9,435.3l92.3-123.9L0,263.8l40.4-122.6L188.4,190v-159h135.3L323.6,190L323.6,190z"></path>
							</svg>
						</span>
						<i class="icon-question-sign text-purple"></i>
					</label>
					<div class="button-group-right">
						<button type="button" class="button button-sm button-static btn-table" 
								data-button-event="popup"
								data-button-type="dataType"
								data-button-target="#file">검색</button>
					</div>
				</div>
				<div class="theme-form-area">
					<aui:input type="text" disabled="true" data-col-result="string" data-col-name="dataTypeName_" name="fileDataTypeName_" placeholder="" label="참조 데이터타입명" inlineLabel="left" />
					<aui:input type="text" disabled="true" data-col-result="string" data-col-name="dataTypeVersion_" name="fileDataTypeVersion_" placeholder="" label="참조 데이터타입 버전" inlineLabel="left" />
				</div>
			</div>
			
			<!-- Email parameter -->
			<div class="theme-form-area" data-col-type="email">
				<aui:input name="value_" id="mailValue" type="text" data-col-result="string" data-col-name="value_">
					<aui:validator name="email"/>
				</aui:input>
			</div>
			
			<!-- date parameter -->
			<div class="theme-form-area" data-col-type="date">
				<aui:select name="format_" data-col-result="string" data-col-name="format_">
					<aui:option value="YMD">YYYY-MM-DD</aui:option>
					<aui:option value="YMDHMS">YYYY-MM-DD HH:mm:ss</aui:option>
				</aui:select>
				
				<aui:input name="dateValue_" id="dateValue" type="text" autocomplete="off" data-col-result="string" data-col-name="value_" >
					<aui:validator name="date"/>
				</aui:input>
				<script type="text/javascript">
					$(function(){
						var DATE_INPUT = '<portlet:namespace/>dateValue',
							DATE_FORMAT = '<portlet:namespace/>format_';
						
						$('#'+DATE_INPUT).dateInput('<portlet:namespace/>');
						
						$('#' + DATE_FORMAT).on('change', function(e){
							var $option = $(this).find('option:selected');
							var format = $option.text(),
								viewMode = $option.val();

							var value = $('#' + DATE_INPUT).val();
							
							if(value !== ''){
								var values = value.split(' ');
								if(viewMode === 'YMDHMS'){
									if(values.length == 1){
										value = value + ' 00:00:00';
									}
								}else{
									if(values.length > 1){
										value = value.split(' ')[0];
									}
								}
							}
							
							$('#' + DATE_INPUT)
											.val(value)
											.data({
												dateFormat : format,
												dateViewmode : viewMode
											});
						});
						$('#' + DATE_FORMAT).trigger('change');
					});
				</script>
			</div>
			<!-- object -->
			<div class="theme-form-area" data-col-type="object">
				<div class="label-button-group">
					<label class="control-label">
						참조 데이터타입 설정
						<span class="reference-mark text-warning" id="seii">
							<svg class="lexicon-icon lexicon-icon-asterisk" focusable="false" role="presentation" viewBox="0 0 512 512">
								<path class="lexicon-icon-outline" d="M323.6,190l146.7-48.8L512,263.9l-149.2,47.6l93.6,125.2l-104.9,76.3l-96.1-126.4l-93.6,126.4L56.9,435.3l92.3-123.9L0,263.8l40.4-122.6L188.4,190v-159h135.3L323.6,190L323.6,190z"></path>
							</svg>
						</span>
						<i class="icon-question-sign text-purple"></i>
					</label>
					<div class="button-group-right">
						<button type="button" class="button button-sm button-static btn-table" 
								data-button-event="popup"
								data-button-type="dataType"
								data-button-target="#dataTypeInput">검색</button>
					</div>
				</div>
				<div class="theme-form-area">
					<aui:input type="text" disabled="true" data-col-result="string" data-col-name="dataTypeName_" name="dataTypeName_" placeholder="" label="참조 데이터타입명" inlineLabel="left" />
					<aui:input type="text" disabled="true" data-col-result="string" data-col-name="dataTypeVersion_" name="dataTypeVersion_" placeholder="" label="참조 데이터타입 버전" inlineLabel="left" />
				</div>
			</div>
			
			<!-- object_array parameter -->
			<div class="theme-form-area" data-col-type="objectArray">
				<div class="label-button-group">
					<label class="control-label">
						<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-OBJECT-ARRAY-REF-DATATYPE-LIST"/>
					</label>
				</div>
				<div>
					<aui:button value="DATATYPE-STRUCTUTE-PARAMETER-OBJECT-ARRAY-REF-DATATYPE-SEARCH" icon="icon-search" cssClass="button btn-outline-light-dark button-block" data-button-event="popup" data-button-type="dataType" data-button-target="#dataTypeTbody">
					</aui:button>
				</div>
				<!-- 등록 데이터타입 -->
				<div class="theme-table theme-table-gray" data-info="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-DATA-INFO">
					<div class="searchcontainer">
						<div class="searchcontainer-content">
							<div class="table-responsive">
								<table class="table mt-2">
									<thead>
										<tr>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-OBJECT-ARRAY-REF-DATATYPE-LIST-INDEX"/></th>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-OBJECT-ARRAY-REF-DATATYPE-LIST-NAME"/></th>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-OBJECT-ARRAY-REF-DATATYPE-LIST-VERSION"/></th>
											<th scope="col"></th>
										</tr>
									</thead>
									<tbody id="dataTypeTbody" class="text-center" data-col-result="objectArray" data-col-Name="dataTypes_">
										<tr>
											<td colspan="4" class="text-center"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-OBJECT-ARRAY-REF-DATATYPE-NO-REF-DATA"/></td>
										</tr>
									</tbody>
								</table>
							</div>		
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 col-sm-12">
						<aui:input name="numberMinSize_" id="stringMinSize" type="text" data-col-result="number" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MIN-LENGTH" data-col-name="minSize_">
							<aui:validator name="number"/>
						</aui:input>
					</div>
					<div class="col-md-6 col-sm-12">
						<aui:input name="numberMaxSize_" id="stringMaxSize_" type="text" data-col-result="number" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MAX-LENGTH" data-col-name="maxSize_">
							<aui:validator name="number"/>
						</aui:input>
					</div>
				</div>
			</div>
			<!-- dataLink parameter -->
			<div class="theme-form-area" data-col-type="dataLink">
				<div class="label-button-group">
					<label class="control-label">
						참조 데이터베이스 설정
						<span class="reference-mark text-warning" id="seii">
							<svg class="lexicon-icon lexicon-icon-asterisk" focusable="false" role="presentation" viewBox="0 0 512 512">
								<path class="lexicon-icon-outline" d="M323.6,190l146.7-48.8L512,263.9l-149.2,47.6l93.6,125.2l-104.9,76.3l-96.1-126.4l-93.6,126.4L56.9,435.3l92.3-123.9L0,263.8l40.4-122.6L188.4,190v-159h135.3L323.6,190L323.6,190z"></path>
							</svg>
						</span>
						<i class="icon-question-sign text-purple"></i>
					</label>
					<div class="button-group-right">
						<button type="button" class="button button-sm button-static btn-table" 
								data-button-event="popup"
								data-button-type="databaseLink"
								data-button-target="databaseInput">검색</button>
					</div>
				</div>
				<div class="theme-form-area">
					<aui:input type="text" disabled="true" data-col-result="string" data-col-name="databaseName_" name="databaseName_" placeholder="" label="참조 데이터베이스명" inlineLabel="left" />
					<aui:input type="text" disabled="true" data-col-result="string" data-col-name="databaseVersion_" name="databaseVersion_" placeholder="" label="참조 데이터베이스 버전" inlineLabel="left" />
				</div>
			</div>
			
			<!-- filePath paramter  -->
			<div class="theme-form-area" data-col-type="filePath">
				<div class="label-button-group">
					<label class="control-label">
						참조 데이터타입 설정
						<span class="reference-mark text-warning" id="seii">
							<svg class="lexicon-icon lexicon-icon-asterisk" focusable="false" role="presentation" viewBox="0 0 512 512">
								<path class="lexicon-icon-outline" d="M323.6,190l146.7-48.8L512,263.9l-149.2,47.6l93.6,125.2l-104.9,76.3l-96.1-126.4l-93.6,126.4L56.9,435.3l92.3-123.9L0,263.8l40.4-122.6L188.4,190v-159h135.3L323.6,190L323.6,190z"></path>
							</svg>
						</span>
						<i class="icon-question-sign text-purple"></i>
					</label>
					<div class="button-group-right">
						<button type="button" class="button button-sm button-static btn-table" 
								data-button-event="popup"
								data-button-type="dataType"
								data-button-target="#filePath">검색</button>
					</div>
				</div>
				<div class="theme-form-area">
					<aui:input type="text" disabled="true" data-col-result="string" data-col-name="dataTypeName_" name="fileDataTypeName_" placeholder="" label="참조 데이터타입명" inlineLabel="left" />
					<aui:input type="text" disabled="true" data-col-result="string" data-col-name="dataTypeVersion_" name="fileDataTypeVersion_" placeholder="" label="참조 데이터타입 버전" inlineLabel="left" />
				</div>
			</div>
			
			<!-- datalink_array parameter -->
			<div class="theme-form-area" data-col-type="dataLinkArray">
				<div class="label-button-group">
					<label class="control-label">
						<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-DATALINK-REF-DATABASE-LIST" />
					</label>
				</div>
				<div>
					<aui:button value="DATATYPE-STRUCTUTE-PARAMETER-DATALINK-REF-DATABASE-SEARCH" icon="icon-search" cssClass="button btn-outline-light-dark button-block" data-button-event="popup" data-button-type="databaseLink" data-button-target="#databaseTbody">
					</aui:button>
				</div>
				<!-- 등록 데이터타입 -->
				<div class="theme-table theme-table-gray" data-info="모바일에서 표를 좌우로 스크롤 할 수 있습니다.">
					<div class="searchcontainer">
						<div class="searchcontainer-content">
							<div class="table-responsive">
								<table class="table mt-2">
									<thead>
										<tr>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-OBJECT-ARRAY-REF-DATATYPE-LIST-INDEX"/></th>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-DATALINK-REF-DATABASE-LIST-NAME"/></th>
											<th scope="col"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-DATALINK-REF-DATABASE-LIST-VERSION"/></th>
											<th scope="col"></th>
										</tr>
									</thead>
									<tbody id="databaseTbody" class="text-center" data-col-result="dataLinkArray" data-col-Name="databases_">
										<tr>
											<td colspan="4" class="text-center"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-DATALINK-REF-DATABASE-NO-REF-DATA"/></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="row">
					<div class="col-md-6 col-sm-12">
						<aui:input name="numberMinSize_" id="stringMinSize" type="text" data-col-result="number" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MIN-LENGTH" data-col-name="minSize_">
							<aui:validator name="number"/>
						</aui:input>
					</div>
					<div class="col-md-6 col-sm-12">
						<aui:input name="numberMaxSize_" id="stringMaxSize_" type="text" data-col-result="number" label="DATATYPE-STRUCTURE-PARAMETER-COLUMN-MAX-LENGTH" data-col-name="maxSize_">
							<aui:validator name="number"/>
						</aui:input>
					</div>
				</div>
			</div>
			
			<!-- boolean parameter -->
			<div class="theme-form-area" data-col-type="boolean">
				<label for="<portlet:namespace/>boolean_" class="control-label">
					<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-COLUMN-BOOLEAN"/>
				</label>
				<div class="form-check form-switch">
					<aui:input type="checkbox" id="boolean_" label="" value="" name="boolean_" data-col-result="boolean" data-boolean-type="checked"/>
				</div>
			</div>
			
			<div class="button-group">
				<div class="button-group-left">
					<aui:button type="button" value="DATATYPE-STRUCTURE-PARAMETER-NEW-PARAMETER" data-button-event="new" cssClass="button button-round btn-common button-sm button-static-sm"/>
				</div>
				<div class="button-group-right">
					<aui:button type="button" value="delete" data-button-event="delete" cssClass="button button-round btn-outline-common button-sm button-static-sm"/>
					<aui:button type="submit" value="save" data-button-event="submit" cssClass="button button-round btn-common button-sm button-static-sm"/>
				</div>
			</div>
			
		</div>
	</div>
</aui:form>
<%-- parameter definition end. --%>


<%-- Activator part start. --%>
<div id="activatorTitle" class="theme-form-title label-button-group sub-title cursor-pointer" data-event-type="accordion" data-event-target="#<portlet:namespace/>activatorFrm">
	<label for="activatorFrm" class="control-label database-title">
		<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-ACTION-CONDITION-SETTING"/>
	</label>
	<div class="button-group-right">
		<i class="icon-chevron-down">
		</i>
	</div>
</div>
<aui:form name="activatorFrm" onSubmit="return false;">
	<div class="theme-form-column">
		<div class="theme-main-title">
			<div class="main-title-button">
				<h4 class="main-title"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-ACTION-CONDITION-SETTING"/></h4>
			</div>
		</div>
		<div class="theme-portlet-descript mb-4">
			<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-ACTION-CONDITION-SETTING-INFO"/>
			<br/>
			<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-ACTION-CONDITION-SETTING-INFO-FIRST"/>
			<br/>
			<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-ACTION-CONDITION-SETTING-INFO-SECOND"/>
		</div>
		<div class="theme-form-area">
			<div class="theme-sub-title">
				<h4 class="sub-title"><liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-LIST-PARAMETER"/></h4>
				<div class="structure-list-nav">
					<a href="#" class="list-move-button">
						<i class="icon-chevron-left"></i>
					</a>
					<ul id="listParameterList" class="list-nav">
					</ul>
					<a href="#" class="list-move-button">
						<i class="icon-chevron-right"></i>
					</a>
				</div>
			</div>
		</div>
		
		<div id="activatorWrapper" class="thme-form-area">
		</div>
		
		<div class="button-group">
			<div class="button-group-right">
				<aui:button type="submit" value="save" data-button-event="submit" cssClass="button button-round btn-common button-sm button-static-sm"/>
			</div>
		</div>
	</div>
</aui:form>



<!-- dataType structure btns -->

<aui:form name="frm" onSubmit="return false;">
	<aui:input name="key" type="hidden" value="${dataTypeId}"></aui:input>
	<aui:input name="sKey" type="hidden" value="${structureId}"></aui:input>
	<div class="button-group">
		<div class="button-group-left">
			<aui:button id="listBtn" type="button" value="list" cssClass="button button-round btn-outline-process button-lg button-static"/>
		</div>
		<div class="button-group-right">
			<aui:button type="submit" value="save" cssClass="button button-lg button-round btn-process button-static"/>
		</div>
	</div>
</aui:form>


<script id="parametericon-template" type="text/x-mustache">
	{{#icons}}
		<li class="list-nav-item" data-parameter-name="{{name}}">
			<a class="list-nav-link">
				<span class="list-nav-icon"><i class="{{icon}}"></i></span>
				<span class="list-nav-text">{{name}}</span>
			</a>
		</li>
	{{/icons}}
</script>

<script id="parameter-list-empty-template" type="text/x-mustache">
	<tr>
		<td colspan="5" class="text-center">
			<liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-TABLE-NO-DATA" />
		</td>
	</tr>
</script>

<script id="parameter-list-table-template" type="text/x-mustache">
	{{#listItems}}
		<tr class="cursor-pointer" data-list-event="update" data-list-key="{{value_}}">
			<td>
				{{index}}
			</td>
			{{#text_}}
			<td>
				{{ko_KR}}
			</td>
			<td>
				{{en_US}}
			</td>
			{{/text_}}
			<td>
				{{value_}}
			</td>
			<td>
				<aui:button
					icon="icon-trash"
					data-list-event="remove"
					data-list-key="{{value_}}"
				/>
			</td>
		</tr>
	{{/listItems}}
</script>

<script id="object-array-table-template" type="text/x-mustache">
	{{#dataTypes}}
		<tr>
			<td>
				{{index}}
			</td>
			<td>
				{{dataTypeName}}
			</td>
			<td>
				{{dataTypeVersion}}
			</td>
			<td>
				<aui:button
					icon="icon-trash"
					data-event-type="remove"
					data-event-target="dataType"
					data-datatype-name="{{dataTypeName}}"
					data-datatype-version="{{dataTypeVersion}}"
				/>
			</td>
		</tr>
	{{/dataTypes}}
</script>
<script id="object-array-table-empty-template" type="text/x-mustache">
	<tr>
		<td colspan="4">
			<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-OBJECT-ARRAY-REF-DATATYPE-NO-REF-DATA" />
		</td>
	</tr>
</script>
<script id="database-array-table-template" type="text/x-mustache">
	{{#databases}}
		<tr>
			<td>
				{{index}}
			</td>
			<td>
				{{databaseName}}
			</td>
			<td>
				{{databaseVersion}}
			</td>
			<td>
				<aui:button
					icon="icon-trash"
					data-event-type="remove"
					data-event-target="database"
					data-database-name="{{databaseName}}"
					data-database-version="{{databaseVersion}}"
				/>
			</td>
		</tr>
	{{/databases}}
</script>
<script id="database-array-table-empty-template" type="text/x-mustache">
	<tr>
		<td colspan="4">
			<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-DATABASE-REF-DATATYPE-NO-REF-DATA" />
		</td>
	</tr>
</script>

<script id="structure-view-template" type="text/x-mustache">
	<div class="overflow-auto">
		<pre>
{{structure}}
		</pre>
	</div>
</script>

<script id="group-parameter-template" type="text/x-mustache">
{{#names}}
<div class="form-check-group col">
	<div class="form-group form-inline input-checkbox-wrapper">
		<input class="field"
			id="<portlet:namespace/>{{name}}"
			name="<portlet:namespace/>parameters_"
			type="checkbox"
			value="{{name}}"
			data-col-result="array"
			data-col-check="true"
			{{#checked}}
				checked="{{checked}}"
			{{/checked}}
			/> {{name}}
	</div>
</div>
{{/names}}
</script>

<script id="activator-template" type="text/x-mustache">
<div class="theme-table theme-table-gray" data-info="DATATYPE-STRUCTURE-PARAMETER-LIST-MSG-VIEWLIST-DATA-INFO">
	<div class="searchcontainer">
		<div class="searchcontainer-content">
			<div id="activateTBLWrapper" class="table-responsive">
				<table class="table mt-2" data-list-key="{{listKey}}">
					<thead id="activatorThead">
						<tr>
							<th class="fixed-header"><liferay-ui:message key="DATATYPE-STRUCTURE-PARAMETER-COLUMN-NAME" /></th>
							{{#column}}
								<th class="fixed-header" data-key="{{key}}">
									{{text}}
								</th>
							{{/column}}
						</tr>
					</thead>
					<tbody class="text-center">
							{{#row}}
							<tr data-col-key="{{key}}">
								<th class="text-center">
									<b>{{key}}</b>
									<%-- <i class="icon-check-minus cursor-pointer"></i> --%>
								</th>
								{{#values}}
									<td data-col-idx="{{cIndex}}">
										<div class="theme-form-radio-group">
											<div class="form-check-group">
												<div class="radio">
													<label for="<portlet:namespace/>_type_{{rIndex}}_{{cIndex}}_m">
														<input class="field" id="<portlet:namespace/>_type_{{rIndex}}_{{cIndex}}_m" name="<portlet:namespace/>_type_{{rIndex}}_{{cIndex}}" type="radio" value="m" data-col-value="type_"
														{{#mType}}
															checked="true">
														{{/mType}}
														{{^mType}}
															>
														{{/mType}}
														<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-LIST-PARAMETER-REQUIRED"/>
													</label>
												</div>
												<div class="radio">
													<label for="<portlet:namespace/>_type_{{rIndex}}_{{cIndex}}_o">
														<input class="field" id="<portlet:namespace/>_type_{{rIndex}}_{{cIndex}}_o" name="<portlet:namespace/>_type_{{rIndex}}_{{cIndex}}" type="radio" value="o" data-col-value="type_"
														{{#oType}}
															checked="true">
														{{/oType}}
														{{^oType}}
															>
														{{/oType}}
														<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-LIST-PARAMETER-CHOOSE"/>
													</label>
												</div>
												<div class="radio">
													<label for="<portlet:namespace/>_type_{{rIndex}}_{{cIndex}}_none">
														<input class="field" id="<portlet:namespace/>_type_{{rIndex}}_{{cIndex}}_none" name="<portlet:namespace/>_type_{{rIndex}}_{{cIndex}}" type="radio" value="false" data-col-value="type_"
														{{#none}}
															checked="true">
														{{/none}}
														{{^none}}
															>
														{{/none}}
														<liferay-ui:message key="DATATYPE-STRUCTUTE-PARAMETER-LIST-PARAMETER-NOT-CONSIDERED"/>
													</label>
												</div>
											</div>
										</div>
									</td>
								{{/values}}
							</tr>
							{{/row}}
					</tbody>
				</table>
			</div>		
		</div>
	</div>
</div>
</script>