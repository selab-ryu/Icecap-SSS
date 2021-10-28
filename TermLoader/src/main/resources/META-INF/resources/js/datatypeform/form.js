

(function (factory) {
	/* global define */
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as an anonymous module.
		define(['jquery'], factory);
	} else {
		// Browser globals: jQuery
		factory(window.jQuery);
	}
}(function ($) {
	'use strict'

	// activator
	$.fn.activator = function(namespace, formId){
		if($(this).prop('tagName') !== 'SELECT')  return false;
		
		var $select = $(this),
			NAMESPACE = namespace,
			FORM_ID = formId;
		
		$($select).on('change', function(){
			var $option = $($select).find('option:selected'),
				$form = $('#'+ FORM_ID);
			
			var nonActiveNames = $option.data('activateNone') || '',
				mActiveNames = $option.data('activateM') || '',
				oActiveNames = $option.data('activateO') || '';
			try{
				var formValidator = Liferay.Form.get($form.attr('id')).formValidator;
				if(nonActiveNames !== ''){
					nonActiveNames.split(',').forEach(name => {
						var $target = $form.find('[data-parameter-key="'+name+'"');
						$target.prop('disabled', true);
						$target.attr('data-validation-required', false);
						
						if($target.prop('tagName') === 'SELECT'){
							$target.find('option:eq(0)').prop('selected', true);
						}else if($target.prop('tagName') === 'INPUT' || $target.prop('tagName') === 'TEXTAREA'){
							$target.val('');
						}
						
						var $wrapper = $target.closest('.theme-form-area');
						$wrapper.hide();
						
						var isGrouped = $target.closest('[data-group-name]').length > 0;
						if(isGrouped){
							var $groupWrapper = $target.closest('[data-group-name]');
							
							if($groupWrapper.find('.theme-form-area:visible').length == 0) $groupWrapper.hide();
						}
						
						var rules = formValidator.get('rules');
						if(rules[$target.attr('id')]){
							rules[$target.attr('id')].required = false;
						}
						
					});
				}
				
				if(mActiveNames !== ''){
					mActiveNames.split(',').forEach(name => {
						var $target = $form.find('[data-parameter-key="'+name+'"');
						
						$target.prop('disabled', false);
						$target.attr('data-validation-required', true);
						if($target.prop('tagName') !== 'BUTTON'){
							var rules = formValidator.get('rules');
							if(!rules[$target.attr('id')]) rules[$target.attr('id')] = {};
							
							rules[$target.attr('id')].required = true;
						}
						
						var $wrapper = $target.closest('.theme-form-area');
						$wrapper.show();
						
						var isGrouped = $target.closest('[data-group-name]').length > 0;
						if(isGrouped){
							$target.closest('[data-group-name]').show();
						}
						
						if($wrapper.find('label > span.reference-mark').length == 0){
							if($wrapper.find('label > span.taglib-icon-help')){
								$wrapper.find('label > span.taglib-icon-help').before(
										$(
												`<span class="reference-mark text-warning mr-1">
												<svg class="lexicon-icon lexicon-icon-asterisk" focusable="false" role="presentation" viewBox="0 0 512 512">
												<path class="lexicon-icon-outline" d="M323.6,190l146.7-48.8L512,263.9l-149.2,47.6l93.6,125.2l-104.9,76.3l-96.1-126.4l-93.6,126.4L56.9,435.3l92.3-123.9L0,263.8l40.4-122.6L188.4,190v-159h135.3L323.6,190L323.6,190z"></path>
												</svg>
												</span>`
										)
								);
							}
							else{
								$wrapper.find('label:eq(0)').append(
										`<span class="reference-mark text-warning mr-1">
										<svg class="lexicon-icon lexicon-icon-asterisk" focusable="false" role="presentation" viewBox="0 0 512 512">
										<path class="lexicon-icon-outline" d="M323.6,190l146.7-48.8L512,263.9l-149.2,47.6l93.6,125.2l-104.9,76.3l-96.1-126.4l-93.6,126.4L56.9,435.3l92.3-123.9L0,263.8l40.4-122.6L188.4,190v-159h135.3L323.6,190L323.6,190z"></path>
										</svg>
										</span>`
								)
							}
						}
					});
				}
				
				if(oActiveNames !== ''){
					oActiveNames.split(',').forEach(name => {
						var $target = $form.find('[data-parameter-key="'+name+'"');
						$target.prop('disabled', false);
						$target.attr('data-validation-required', false);
						if($target.prop('tagName') !== 'BUTTON'){
							var rules = formValidator.get('rules');
							
							if(!rules[$target.attr('id')]) rules[$target.attr('id')] = {};
							
							rules[$target.attr('id')].required = false;
						}
						
						var isGrouped = $target.closest('[data-group-name]').length > 0;
						if(isGrouped){
							$target.closest('[data-group-name]').show();
						}
						
						var $wrapper = $target.closest('.theme-form-area');
						$wrapper.show();
						if($wrapper.find('label > span.reference-mark').length > 0) $wrapper.find('label > span.reference-mark').remove();
					});
				}
			}catch(e){
				// do nothing.
				setActivator($select, NAMESPACE, FORM_ID);
			}
		});
	}
	function setActivator($el, namespace, formId){
		if(!$el.activator){
			$($el).activator(namespace, formId);
		}
	}
	
	
	// array parameter
	$.fn.arrayEditor = function(namespace, formId){
		var NAMESPACE = namespace,
			FORM_ID = formId,
			$this = $(this),
			canvasId = $this.data('arrInput'),
			childrenType = $this.data('arrChildren');
		
		$this.on('click', function(){
			var $wrapper = $this.closest('.theme-form-area'),
				arrValues = $this.data('arrValues') || [],
				inputs = $wrapper.find('[data-arr-type]');
			
			
			if(inputs.length === 1){
				var $input = inputs.eq(0),
					childrenType = $input.data('arrType');
				
				if(($input.val() || '') === ''){
					globalErrorMsg('값을 입력 후 추가해주십시오.')
					$input.focus();
					return false;
				}
				
				if(childrenType === 'numeric'){
					$this.data('arrValues', [
						...arrValues,
						Number($input.val())
					]);
				}else{
					$this.data('arrValues', [
						...arrValues,
						$input.val()
					]);
				}
				$input.val('');
			}else{
				var obj = {},
					pass = true;
				inputs.each(function(i, input){
					var key = $(input).data('arrName'),
						value = $(input).val() || '';
					
					if(value === ''){
						globalErrorMsg('값을 입력 후 추가해주십시오.')
						$input.focus();
						pass = false;
						return false;
					}
					
					obj[key] = value;
				});
				if(!pass){
					return false;
				}else{
					$this.data('arrValues', [
						...arrValues,
						obj
					]);
				}
			}
			drawArrayData();
		});
		
		var drawArrayData = function(){
			var $canvas = $('#'+canvasId);
			var datas = $this.data('arrValues') || [];
			if(!Array.isArray(datas)){
				try{
					var test = JSON.parse(datas);
				}catch(e){
					var mData = datas.substring(1, datas.length-1);
					datas = mData.split(',');
					$this.data('arrValues', datas)
				}
			}
			if(datas.length === 0){
				$canvas.empty();
				$($canvas).append($(`
						<tr>
						<td colspan="2">no data</td>
						</tr>	
				`));
				return false;
			}
			
			$canvas.empty();
			
			if(Array.isArray(datas)){
				datas.forEach((obj,i) => {
					var prettyData = JSON.stringify(JSON.parse(JSON.stringify(obj)),null,2)
					
					$($canvas).append($(`
							<tr>
							<td>${prettyData}</td>
							<td class="text-center">
								<button type="button" value="delete" data-button-event="delete" class="button button-round btn-outline-common button-sm"
								data-index="${i}"
								>
									<i class="icon-trash"></i>
								</button>
							</td>
							</tr>	
					`));
				});
			}
			$this.trigger('change');
		}
		
		$('#'+canvasId).on('click', '[data-button-event="delete"]', function(e){
			var index = $(this).data('index');
			try{
				var datas = $this.data('arrValues') || [];
				var spedData = datas.splice(index, 1);
				$this.data('arrValues', datas);
				drawArrayData();
			}catch(e){
				console.log("e : ", e)
			}
		})
		drawArrayData();
	}
	
	$.fn.objectEditor = function(namespace, formId){
		var NAMESPACE = namespace,
			FORM_ID = formId,
			$this = $(this),
			canvasId = $this.data('oaInput') || '';
		var $form = $('#'+FORM_ID);
		
		var drawObjectData = function(){
			var type = $this.data('parameterType'), 
				$canvas = $('#'+canvasId);
			
			if(type === 'objectArray'){
				var datas = $this.data('objectValue') || [];
				
				if(datas.length === 0){
					$canvas.empty();
					$($canvas).append($(`
							<tr>
							<td colspan="3">no data</td>
							</tr>	
					`));
					return false;
				}
				
				$canvas.empty();
				
				if(Array.isArray(datas)){
					datas.forEach((obj,i) => {
						var prettyData = JSON.stringify(JSON.parse(JSON.stringify(obj)),null,2)
						
						$($canvas).append($(`
								<tr>
								<td>${prettyData}</td>
								<td class="text-center">
									<button type="button" value="view" data-button-event="view" class="button button-round btn-outline-common button-sm"
									data-object-index="${i}"
									>
										<i class="icon-search"></i>
									</button>
								</td>
								<td class="text-center">
									<button type="button" value="delete" data-button-event="delete" class="button button-round btn-outline-common button-sm"
									data-object-index="${i}"
									>
										<i class="icon-trash"></i>
									</button>
								</td>
								</tr>	
						`));
					});
				}
				
			}else{
				var datas = $this.data('objectValue') || {};
				
				if(Object.keys(datas).length === 0){
					$canvas.empty();
					$($canvas).append($(`
							<tr>
								<td colspan="2">no data</td>
							</tr>	
					`));
					return false;
				}
				
				$canvas.empty();
				var prettyData = JSON.stringify(JSON.parse(JSON.stringify(datas)),null,2)
				
				$($canvas).append($(`
						<tr>
							<td>${prettyData}</td>
							<td class="text-center">
								<button type="button" value="view" data-button-event="view" class="button button-round btn-outline-common button-sm"
									data-object-index="0"
								>
									<i class="icon-search"></i>
								</button>
							</td>
						</tr>
				`));
			}
			$this.trigger('change');
		};
		
		drawObjectData();
		
		$('#'+canvasId).on('click', '[data-button-event="delete"]', function(e){
			var objectIndex = $(this).data('objectIndex');
			try{
				var datas = $this.data('objectValue') || [];
				var spedData = datas.splice(objectIndex, 1);
				$this.data('objectValue', datas);
				drawObjectData();
			}catch(e){
				console.log("e : ", e)
			}
		}).on('click', '[data-button-event="view"]', function(e){
			var objectIndex = $(this).data('objectIndex');
			try{
				var datas = $this.data('objectValue');
				if(datas){
					var prettyData = '';
					if(Array.isArray(datas)){
						var spedData = datas[objectIndex];
						prettyData = JSON.stringify(JSON.parse(JSON.stringify(spedData)),null,2)
					}else{
						prettyData = JSON.stringify(JSON.parse(JSON.stringify(datas)),null,2)
					}
					$.ospModal({
						title : '<liferay-ui:message key="DATATYPE-STRUCTURE-BUTTON-STRUCTURE-VIEW"/>',
						body : `<pre>${prettyData}</pre>`,
						modalDialogClass : 'modal-lg',
						isScrollable : true
					})
				}else{
					globalErrorMsg('No Data for parameter "'+$this.data('parameterKey')+'".');
					return false;
				}
			}catch(e){
				console.log("e : ", e)
			}
		});
		
		
		$this.on('click', function(e){
			var targetId = $this.data('oaTarget') || '',
				type = $this.data('parameterType');
			var $target = $form.find('[data-oa-target="' + targetId+'"]'),
				dataTypeName = '',
				dataTypeVersion = '';
			
			
			if(type ==='objectArray'){
				var $selected = $target.find('option:selected');
				
				dataTypeName = $selected.data('oaName');
				dataTypeVersion = $selected.data('oaVersion');
			}else{
				dataTypeName = $this.data('oaName');
				dataTypeVersion = $this.data('oaVersion');
			}
			
			AUI().use("liferay-portlet-url", function(a){
				// set portlet & popup properties
				var portletURL = Liferay.PortletURL.createRenderURL();
				portletURL.setPortletMode("view");
				portletURL.setWindowState("pop_up");
				portletURL.setPortletId("ICECAP_WEB_datatype_personal_DataTypeFormPortlet");
				portletURL.setParameter('mvcRenderCommandName', 'DATATYPE_FORM_RENDER');
				
				portletURL.setParameter('step', 1);
				
				// set parameters to portletURL.
				portletURL.setParameter('dataTypeName', dataTypeName);
				portletURL.setParameter('dataTypeVersion', dataTypeVersion);
				
				portletURL.setParameter('popup', true);
				portletURL.setParameter('sde', true);
				
				// set form view parameters.
				portletURL.setParameter('wrap', true);
				portletURL.setParameter('buttonType', '');
				portletURL.setParameter('title', 'dataType');
				portletURL.setParameter('disabled', false);
				portletURL.setParameter('form', true);
				portletURL.setParameter('formNamespace', 'form');
				portletURL.setParameter('inputName', 'input');
// 				if(popup){// page change with backpage btn.
// 					portletURL.setParameter('backURL', window.location.href);
// 					location.href = portletURL.toString();
// 				}else{// open modal and print form.
					Liferay.Util.openWindow(
							{
								dialog: {
									cache: false,
									draggable: false,
									resizable: false,
									modal: true,
									centered : true,
									destroyOnClose: true,
									cssClass: 'modal-position-over',
									after: {
										render: function(event) {
											$('#' + 'dataTypeList').css("z-index", "1500");
										},
									},
									toolbars : {
										footer : [
											{
												label : 'Choose',
												cssClass: 'btn-common button-static button-lg ml-auto',
												on : {
													click : function(){
														var contentWindow = Liferay.Util.getWindow("dataTypeFormModal").iframe.node._node.contentWindow;
														var document = $(contentWindow.document);
														
														var $form = document.find('form');

														var formValidator = contentWindow.Liferay.Form.get($form.attr('id')).formValidator;
														
														formValidator.validate();
														if(Object.keys(formValidator.errors).length > 0) return false;
														
														var datas = {},
															fso = $form.serializeObject();
														for(var k in fso){
															if(k == '_ICECAP_WEB_datatype_personal_DataTypeFormPortlet_formDate') continue;
															datas[k.replace('_ICECAP_WEB_datatype_personal_DataTypeFormPortlet_', '')] = fso[k];
														};
														
														
														if(type === 'objectArray'){
															var objectValue = $this.data('objectValue') || [];
															objectValue.push(datas);
															$this.data('objectValue', objectValue);
														}else{
															$this.data('objectValue', datas);
														}
														
														drawObjectData(canvasId);
														
//														$($canvas).text(JSON.stringify(JSON.parse(JSON.stringify(objectValue)),null,2));
														Liferay.Util.getWindow('dataTypeFormModal').destroy();
													}
												}
											},
											{
												label: 'Close',
												cssClass: 'button-static button-lg',
												on : {
													click : function(){
														Liferay.Util.getWindow('dataTypeFormModal').destroy();
													}
												}
											}
										]
									}
								},
								id: "dataTypeFormModal",
								uri: portletURL.toString(),
								title: 'DataType Form'
							}
						);
// 				}
			});
		});
	}
	
	// data link and data link array
	$.fn.dataLinkArrayEditor = function(namespace, formId){
		var NAMESPACE = namespace,
			FORM_ID = formId,
			$this = $(this),
			canvasId = $this.data('dlaInput') || '';
		
		var $form = $('#'+FORM_ID);

		$this.on('click', function(e){
			var type = $this.data('parameterType'),
				targetId = $this.data('dlaTarget') || '';
				
			
			var $target = $form.find('[data-dla-target="' + targetId+'"]'),
				$canvas = $form.find('#'+canvasId).find('pre');

			var databaseName = '',
				databaseVersion = '';

			if(type === 'dataLinkArray'){
				var $selected = $target.find('option:selected');
				
				databaseName = $selected.data('dlaName');
				databaseVersion = $selected.data('dlaVersion');
			}else if(type === 'dataLink'){
				databaseName = $this.data('dlaName');
				databaseVersion = $this.data('dlaVersion');
			}
			
			AUI().use("liferay-portlet-url", function(a){
				var portletURL = Liferay.PortletURL.createResourceURL();
				portletURL.setPortletId("com_osp_icecap_personal_DatabaseManagerPortlet");
				portletURL.setResourceId('DATABASE_STRUCTURE_TREE_VIEW_RESOURCE');
				portletURL.setParameter('databaseName', databaseName);
				portletURL.setParameter('databaseVersion', databaseVersion);
				portletURL.setParameter('isForm', true);
				
				$.ospModal({
					title : 'Select DataPack',
					modalDialogClass : 'modal-md',
					onCreate : function(){
						var fancyTree = $('<div/>').attr('id', 'treeDiv').fancytree({
							minExpandLevel: 1,
							clickFolderMode : 4,
							icon : function(event, data){
								return $.structureTreeIcon(data.node.data.resource_type, 'w-100');
							},
							source: {
								url: portletURL.toString(),
								toggleEffect: { effect: 'slideToggle', duration: 200 },
								async : false,
								global: false
							}
						});
						this.bodyTmpl = $(fancyTree);
					},
					isScrollable : true,
					btnGroup : [
						{
							addClass : 'btn-common button button-lg',
							text : 'Confirm',
							callback : {
								'click' : function(e){
									var tree = $(document).find('.modal #treeDiv:eq(0)').fancytree('getTree');
									
									var node = tree.getActiveNode();
									if(node){
										var resourceType = node.data.resource_type;
										var structureKey = "";
										var isEntry = false;
										if(resourceType == "entry"){
											structureKey = node.data.resource_ref_structure_key;
											isEntry = true;
										}
										$(document).find('.modal').modal('hide');// close modal.
										
										openDataRefPopup(node.data.resource_ref_key, 102202302, isEntry, structureKey);
									}else{
										globalErrorMsg('Choose DataPack.');
										return false;
									}
								}
							}
						},
						{
							addClass : 'btn-outline-common button button-lg',
							text : 'Close',
							callback : {
								'click' : function(e){
									$(document).find('.modal').modal('hide');// close modal.
								}
							}
						}
					]
				});
			});
		});
		
		const openDataRefPopup = function(refDatabaseId, refDivCd, isEntry, structureKey){
			AUI().use("liferay-portlet-url", function(a){
				// set portlet & popup properties
				var portletURL = Liferay.PortletURL.createRenderURL();
				portletURL.setPortletMode("view");
				portletURL.setWindowState("pop_up");
				portletURL.setPortletId("com_osp_icecap_personal_RefInputDataInformationPortlet");
				
				// set parameters to portletURL.
				portletURL.setParameter('refDatabaseId', refDatabaseId);
				portletURL.setParameter('refDatabaseStructureId', structureKey);
				portletURL.setParameter('refDivCd', refDivCd);
				
				Liferay.Util.openWindow(
						{
							dialog: {
								cache: false,
								draggable: false,
								resizable: false,
								modal: true,
								centered : true,
								destroyOnClose: true,
								cssClass: 'modal-position-over',
								after: {
									render: function(event) {
										$('#' + 'databaseFormModal').css("z-index", "1500");
									},
								},
								toolbars : {
									footer : [
										{
											label : 'Choose',
											cssClass: 'btn-common button-static button-lg ml-auto',
											on : {
												click : function(){
													var contentWindow = Liferay.Util.getWindow("databaseFormModal").iframe.node._node.contentWindow;
													var $cDocument = $(contentWindow.document),
														type = $this.data('parameterType');
													
													
													var inputDataId = $cDocument.find("[data-event='tab']").eq(0).data("inputDataId");
													if(inputDataId == "" || typeof inputDataId == "undefined"){
														globalErrorMsg('<liferay-ui:message key="select-require-msg" />');
														return false;
													}
													
													var dataLinkJsonObj = new Object();
													dataLinkJsonObj["databaseId"] = refDatabaseId;
													dataLinkJsonObj["inputDataId"] = inputDataId;
													$cDocument.find("[data-qa-id='row']").each(function(index, item){
														var isSelectedData = $(this).hasClass("on");
														if(isSelectedData){
															var $title = $(this).find("[data-event='info']").data("title");
															dataLinkJsonObj["title"] = $title;
														}
													})
													if(isEntry){
														dataLinkJsonObj["databaseStructureId"] = structureKey;
													}

													if(type === 'dataLinkArray'){
														var data = $this.data('parameterData') || [];
														$this.data('parameterData', [...data, dataLinkJsonObj])
													}else if(type === 'dataLink'){
														$this.data('parameterData', dataLinkJsonObj);
													}
													appendDataLinkTr(dataLinkJsonObj.title);
													Liferay.Util.getWindow('databaseFormModal').destroy();
												}
											}
										},
										{
											label: 'Close',
											cssClass: 'button-static button-lg',
											on : {
												click : function(){
													Liferay.Util.getWindow('databaseFormModal').destroy();
												}
											}
										}
									]
								}
							},
							id: "databaseFormModal",
							uri: portletURL.toString(),
							title: 'Database Form'
						}
					);
			});
		}
		
		const getDataTypeFormParameter = function (dataLinkJsonData){// change to data draw event.
			
			var dataLinkStrData = JSON.stringify(dataLinkJsonData[0]);
			var resultData = "";
			AUI().use("liferay-portlet-url", function(a){
				// set portlet & popup properties
				var portletURL = Liferay.PortletURL.createResourceURL();
				portletURL.setPortletId("ICECAP_WEB_datatype_personal_DataTypeFormPortlet");
				portletURL.setResourceId('DATATYPE_FORM_PARAMETER_RESOURCE');
				
				// set parameters to portletURL.
				portletURL.setParameter('purpose', 'DATATYPE_FORM_PARAMETER_RESOURCE');
				portletURL.setParameter('dataLinkStrData', dataLinkStrData);
				
				$.ajax({
					type : 'post',
					url: portletURL.toString(),
					dataType : 'json',
					async : false,
					global: false,
					success : function(data) {
						resultData = data
					},
					error : function(err) {
						console.log("err : ", err);
						return false;
					}
				});
			});
			return resultData;
		}
		
		const appendDataLinkTr = function(dataLinkJsonData){
			var type = $this.data('parameterType'),
				$canvas = $('#' + canvasId),
				prettyData = JSON.stringify(JSON.parse(JSON.stringify(dataLinkJsonData)),null,2);
			
			if(type === 'dataLink'){
				$canvas.empty();
				$canvas.append(
						$(`
								<tr>
									<td>${prettyData}</td>
									<td class="text-center">
										<button type="button" value="view" data-button-event="view" class="button button-round btn-outline-common button-sm"
											data-object-index="0"
										>
											<i class="icon-search"></i>
										</button>
									</td>
								</tr>
						`)
				);
			}else if(type === 'dataLinkArray'){
				var i = 0;
				if($canvas.find('tr')){
					i = $canvas.find('tr').length - 1;
				}

				$canvas.append(
						$(`
								<tr>
								<td>${i+2}</td>
								<td>${prettyData}</td>
								<td class="text-center">
								<button type="button" value="view" data-button-event="view" class="button button-round btn-outline-common button-sm"
								data-datalink-index="${i+1}"
								>
								<i class="icon-search"></i>
								</button>
								</td>
								<td class="text-center">
								<button type="button" value="view" data-button-event="delete" class="button button-round btn-outline-common button-sm"
								data-datalink-index="${i}"
								>
								<i class="icon-trash"></i>
								</button>
								</td>
								</tr>
						`)
				);
			}
		}
		
		$('#'+canvasId).on('click', '[data-button-event="delete"]', function(e){
			e.preventDefault();
			e.stopPropagation();
			try{
				var index = $(this).data('datalinkIndex');
				try{
					var datas = $this.data('parameterData') || [];
					$this.data('parameterData', datas.splice(index, 1));
					$(this).closest('tr').remove();
				}catch(e){
					console.log("e : ", e)
				}
			}catch(e){
				console.log("e : ", e)
			}
		}).on('click', '[data-button-event="view"]', function(e){
			try{
				var tabStructureData = getDataTypeFormParameter($this.data("parameterData"));
//				console.log("$$$",JSON.stringify(tabStructureData).replace(/\\/g,'\\'))
				var prettyData = JSON.stringify(JSON.parse(JSON.stringify(tabStructureData)),null,2)
				
				$.ospModal({
					title : $this.data("parameterData")[0].title,
					body : `<pre>${prettyData}</pre>`,
					modalDialogClass : 'modal-lg',
					isScrollable : true
				})
			}catch(e){
				console.log("e : ", e)
			}
		});
	}
	
	
	// date input parser.
	$.fn.dateInput = function(namespace){
		if($(this).prop('tagName') !== 'INPUT') return false;
		
		var $input = $(this),
			DATE_TIME_PICKER = namespace + 'datetimePicker',
			NAMESPACE = namespace;
		
		$input.attr('autocomplete', 'off')
		
		$input.on('keydown', function(e){
			e.preventDefault();
			e.stopPropagation();
		});
		
		$input.on('mouseup', function(e){
			e.preventDefault();
			e.stopPropagation();
			
			$('#'+DATE_TIME_PICKER, $('body')).remove();
			
			var format = $input.data('dateFormat') || 'YYYY-MM-DD',
				viewMode = $input.data('dateViewmode') || 'YMD';
			
			var value = new Date($input.val()) != "Invalid Date" ? new Date($input.val()) : new Date();
			
			var yPosition = $($input).offset().top + $($input).outerHeight(),
				xPosition = $($input).offset().left;
		
			var $dropdown = $('<div/>')
								.attr('id', DATE_TIME_PICKER)
								.addClass('dropdown position-absolute')
								.css({
									zIndex : 100,
									top : yPosition,
									left : xPosition
								})
								.appendTo('body');
			
			$dropdown.datetimepicker({
				date: value,
				viewMode: viewMode,
				onOk : function(e){
					$($input).val(this.getText(format));
					$($input.trigger('change'));
					$dropdown.remove();
				},
				onDateChange : function(e){
					$($input).val(this.getText(format));
					$($input.trigger('change'));
				}
			})

			// Event that remove dateTimePicker when click other field.
			$(document).on('mouseup', function(e){
				if($dropdown.has(e.target).length == 0
					&& e.target != $input){
					$($dropdown).remove();
					$(document).off('mouseup');
				}
			});
		});
	}
	
	
	
	
	function setDateInput($el, namespace){
		if(!$el.dataInput) $($el).dateInput(namespace);
	}
	
	// export functions.
	$.extend({
		dateInput : $.fn.dateInput,
		setDateInput : setDateInput,
		activator : $.fn.activator,
		setActivator : setActivator
	});
}));