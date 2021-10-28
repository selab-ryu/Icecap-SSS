// form button event binder.
;(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define( ["jquery"], factory );
	} else if (typeof module === "object" && module.exports) {
		module.exports = factory( require( "jquery" ) );
	} else {
		factory( jQuery );
	}
}(function($){
	'use strict'
	/*
		portletKey : target portlet key,
		actionKey : target portlet render command name
		modalTitle : modal title that opened,
		modalId : modal id that opened,
		paramObj : parameters to send request,
		confirmBtnCallback : function when click 
	*/
	/*
		example : 
			$('#inputNameWrapper').find('[data-form-type="link"]').each(function(i, $el){
				$($el).auiModalBtn(
						'ICECAP_WEB_datatype_personal_ListDataTypeManagerPortlet',
						'',// no command name.
						'test title',
						'TESTMODAL',
						{
							key : '703',
							sde : true,
							popup : true,
							form : false,
							formNamespace : 'testFormNs',
							inputName : 'testIn',
							disabled : true,
							link : true
						},
						function(){
							console.log("HELLO");
							Liferay.Util.getWindow('TESTMODAL');// close modal.
						}
					);
			});
	*/
	$.fn.auiModalBtn = function(portletKey, actionKey, modalTitle, modalId, paramObj, confirmBtnCallback){
		var $btn = this,
			portlet = portletKey || '',
			action = actionKey || '';
		
		var isArray = Array.isArray($btn);
		
		if(portlet === ''){
			toastr.error('check button s parameter( button id : '+$btn.attr('id')+' ).');
			return false;
		}
		
		if(confirmBtnCallback && typeof confirmBtnCallback !== 'function'){
			toastr.error('callback must be function( callback type : '+typeof confirmBtnCallback +' ).');
			return false;
		}
		
		if(isArray){
			$btn.each(function(i, $el){
				$($el).on('click', function(){
					_addAuiModalEvent(portlet, action, modalTitle, modalId, paramObj, confirmBtnCallback);
				});
			})
		}else{
			$btn.on('click', function(){
				_addAuiModalEvent(portlet, action, modalTitle, modalId, paramObj, confirmBtnCallback);
			});
		}
	}
	
	const _addAuiModalEvent = function(portlet, action, modalTitle, modalId, paramObj, confirmBtnCallback){
		AUI().use("liferay-portlet-url", function(a){
			var portletURL = Liferay.PortletURL.createRenderURL();
			portletURL.setPortletMode("view");
			portletURL.setWindowState("pop_up");
			portletURL.setPortletId(portlet);
			portletURL.setParameter('mvcRenderCommandName', action);
			
			for(var key in paramObj){
				portletURL.setParameter(key, paramObj[key]);
			}
			
			var modalProps = {
				dialog: {
					width:1024,
					height:900,
					cache: false,
					draggable: false,
					resizable: false,
					modal: true,
					centered : true,
					destroyOnClose: true,
					cssClass: 'modal-xl modal-position-over',
					after: {
						render: function(event) {
							$('#' + modalId).css("z-index", "1500");
						},
					}
				},
				id: modalId,
				uri: portletURL.toString(),
				title: modalTitle
			};
			
			if(confirmBtnCallback){
				modalProps.dialog.toolbars = {
					footer: [
						{
							label: 'Close',
							cssClass: 'btn-default',
							on : {
								click : function(){
									Liferay.Util.getWindow(modalId).destroy();
								}
							}
						},
						{
							label : 'Confirm',
							cssClass: 'btn-primary',
							on: {
								click : confirmBtnCallback
							}
						}
					]
				}
			}
			Liferay.Util.openWindow(modalProps);
		});
	}
}));
