(function(w) {
    'use strict';
    if (w.SSS ) {
        if (w.SSS.TermTypes) return;
    } else{
    	w.SSS = {};
    }

    SSS.TermTypes = {
            VERSION: '2021-10-15',
            STRING: 'String',
            NUMERIC: 'Numeric',
            BOOLEAN : 'Boolean',
            LIST: 'List',
            LIST_ARRAY: 'ListArray',
            MATRIX: 'Matrix',
            FILE : 'File',
            FILE_ARRAY: 'FileArray',
            OBJECT : 'Object',
            OBJECT_ARRAY : 'ObjectArray',
            ARRAY : 'Array',
            DATA_LINK : 'DataLink',
            DATA_LINK_ARRAY : 'DataLinkArray',
            DATE : 'Date',
            PHONE : 'Phone',
            EMAIL : 'EMail',
            GROUP: 'Group',
            COMMENT: 'Comment',
    }; 
    
    SSS.TermAttrNames = {
    	VERSION: '2021-10-15',
        TOOLTIP: 'tooltip_',
        COUNTRY_CODE: 'countryCode_',
        DEFINITION: 'definition_',
        DEPENDENT_TERMS: 'dependentTerms_',
        DEFAULT_LOCALE: 'defaultLocale_',
        DISPLAY_NAME: 'displayName_',
        REF_DATATYPES: 'refDataTypes_',
        REF_DATABASES: 'refDatabases_',
        DATATYPE_NAME: 'dataTypeName_',
        DATATYPE_VERSION: 'dataTypeVersion_',
        DIMENSION_X: 'dimensionX_',
        DIMENSION_Y: 'dimensionY_',
        DISPLAY_STYLE: 'displayStyle_',
        ELEMENT_TYPE: 'elementType_',
        FORMAT: 'format_',
        ID: 'id_',
        MANDATORY: 'mandatory_',
        NAME: 'name_',
        ORDER: 'order_',
        SYNONYMS: 'synonyms_',
        PATH: 'path_',
        PATH_TYPE: 'pathType_',
        URI: 'uri_',
        URI_TYPE: 'uriType_',
        URL: 'url_',
        FILE_ID: 'fileId_',
        VERSION: 'version_',
        ACTIVE: 'active_',
        AVAILABLE_LANGUAGE_IDS: 'availableLanguageIds_',
        DEFAULT_LANGUAGE_ID: 'defaultLanguageId_',
        DISABLED: 'disabled_',
        LIST_ITEM: 'listItem_',
        LIST_ITEM_VALUE: 'listItemValue_',
        LIST_ITEMS: 'listItems_',
        LOWER_BOUNDARY: 'lowerBoundary_',
        LOWER_OPERAND: 'lowerOperand_',
        NAME_TEXT: 'nameText_',
        OPERAND: 'operand_',
        TERM_NAME: 'termName_',
        TEXT: 'text_',
        TTYPE: 'type_',
        RANGE: 'range_',
        MIN_LENGTH:"minLength_",
        MAX_LENGTH:"maxLength_",
        MIN_VALUE:"minValue_",
        MAX_VALUE:"maxValue_",
        NEW_LINE:"newLine_",
        UPPER_BOUNDARY: 'upperBoundary_',
        UPPER_OPERAND: 'upperOperand_',
        UNCERTAINTY: 'uncertainty_',
        VALUE: 'value_',
        UNCERTAINTY_VALUE: 'uncertaintyValue_',
        VALIDATION_RULE : 'validationRule_',
        UNIT: 'unit_',
        VALUE_DELIMITER: 'valueDelimiter_',
        SWEEPABLE: 'sweepable_'
    }; // End of SSS.TermAttrNames

    SSS.Util = {
    	VERSION: '2021-10-15',
        getTermTypes: function() {
            let types = [];
            types.push(SSS.TermTypes.NUMERIC);
            types.push(SSS.TermTypes.STRING);
            types.push(SSS.TermTypes.BOOLEAN);
            types.push(SSS.TermTypes.ARRAY);
            types.push(SSS.TermTypes.LIST);
            //types.push(SSS.TermTypes.LIST_ARRAY);
            types.push(SSS.TermTypes.DATE);
            types.push(SSS.TermTypes.PHONE);
            types.push(SSS.TermTypes.EMAIL);
            types.push(SSS.TermTypes.MATRIX);
            types.push(SSS.TermTypes.DATA_LINK);
            types.push(SSS.TermTypes.DATA_LINK_ARRAY);
            types.push(SSS.TermTypes.OBJECT);
            types.push(SSS.TermTypes.OBJECT_ARRAY);
            types.push(SSS.TermTypes.FILE);
            types.push(SSS.TermTypes.FILE_ARRAY);
            types.push(SSS.TermTypes.GROUP);
            types.push(SSS.TermTypes.COMMENT);
            return types;
        },
        guid: function() {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(char) {
                let random = Math.random() * 16 | 0,
                    value = char === 'x' ? random : (random & 0x3 | 0x8);
                return value.toString(16);
            })
        },
        toLocalizedXml: function(jsonObject, availableLanguageIds, defaultLanguageId) {
            if (!availableLanguageIds) availableLanguageIds = '';
            if (!defaultLanguageId) defaultLanguageId = '';

            let xml = '<?xml version=\'1.0\' encoding=\'UTF-8\'?>';
            xml += '<root available-locales=\'';
            xml += availableLanguageIds + '\' ';
            xml += 'default-locale=\'' + defaultLanguageId + '\'>';

            for (let languageId in jsonObject) {
                let value = jsonObject[languageId];
                xml += '<display language-id=\'' + languageId + '\'>' + value +
                    '</display>';
            }
            xml += '</root>';

            return xml;
        },
        toJSON: function(obj) {
            return JSON.parse(JSON.stringify(obj));
        },
        isEmpty: function(obj) {
            if (obj == null) return true;
            if (obj.length == 0)
                return true;

            if (typeof obj !== 'object') return false;

            for (let key in obj) {
                if (SSS.Util.isEmpty(obj[key]) == false) return false;
            }

            return true;
        },
        removeArrayElement: function(array, index) {
            array.splice(index, 1);
            return array;
        },
        isBrowserEdge: function() {
            let ua = navigator.userAgent,
                tem, M = ua.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i) || [];
            if (/trident/i.test(M[1])) {
                tem = /\brv[ :]+(\d+)/g.exec(ua) || [];
                //return {name:'IE',version:(tem[1]||'')};
                return false;
            }

            return true;
        },
        addFirstArgument: function(argument, args) {
            let newArgs = [];
            for (let i = 0; i < args.length; i++) {
                newArgs.push(args[i]);
            }
            newArgs.unshift(argument);
            return newArgs;
        },
        evalHttpParamSeparator: function(baseURL) {
            let sep = (baseURL.indexOf('?') > -1) ? '&' : '?';
            return sep;
        },
        getLocalFile: function( anchor ){
            return $(anchor)[0].files[0];
        },
        getLocalFileName: function( anchor ){
            let fileName = $(anchor).val();
			
			let slashIndex = fileName.lastIndexOf('\\');
			if( slashIndex < 0 )
                slashIndex = fileName.lastIndexOf('/');
                 
			return fileName.slice(slashIndex+1);
        },
        randomString: function( length, code ){
            let mask = '';
            if (code.indexOf('a') > -1) mask += 'abcdefghijklmnopqrstuvwxyz';
            if (code.indexOf('A') > -1) mask += 'ABCDEFGHIJKLMNOPQRSTUVWXYZ';
            if (code.indexOf('1') > -1) mask += '0123456789';
            if (code.indexOf('!') > -1) mask += '~`!@#$%^&*()_+-={}[]:";\'<>?,./|\\';
            let result = '';
            for (let i = length; i > 0; --i){
                result += mask[Math.floor(Math.random() * mask.length)];
            } 
            return result;
        },
        getLocalizedInputValue(inputId, availableLanguageIds ){
        	//console.log('availableLanguageIds:', availableLanguageIds);
        	var listItem = {};
        	listItem.displayName = {};
        	availableLanguageIds.forEach( function(id){
        		var localizedId = inputId+'_'+id;
        		listItem.displayName[id] = $('#'+localizedId).val();
        	});
        	
        	//console.log('Localized List Item: ', listItem);
        	return listItem;
        },
        setLocalizedInputValue(inputId, $parent, availableLanguageIds, displayName ){
        	//console.log('availableLanguageIds:', availableLanguageIds);
        	availableLanguageIds.forEach( function(id){
        		var localizedId = inputId+'_'+id;
        		if( displayName[id] ){
        			$('#'+localizedId).val( displayName[id]);
        			$('#'+inputId).val(displayName[id]);
        		}
        	});
			$parent.find('.form-text').empty();
        },
        clearLocalizedInputValue(inputId, $parent, availableLanguageIds){
        	//console.log('availableLanguageIds:', availableLanguageIds);
        	availableLanguageIds.forEach( function(id){
        		var localizedId = inputId+'_'+id;
        			$('#'+localizedId).val('');
        			$('#'+inputId).val('');
        	});
			$parent.find('.form-text').empty();
        }
    }; // End of SSS.Util

    SSS.Debug = {
        eventTrace: function(message, event, eventData) {
            console.log('/+++++++++' + message + '++++++++/');
            console.log(event);
            console.log(eventData);
            console.log('/++++++++++++++++++++++++++/');
        }
    }; // End of SSS.Debug

})(window);