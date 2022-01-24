(function(SX, $, Liferay) {
    'use strict';

    if (!SX) {
    	return;
    }
    
    SX = {
    	SSS: {
    		TermTypes: {
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
    		},
    		TermAttrNames: {
    	    		ACTIVE: 'active',
    	    		AVAILABLE_LANGUAGE_IDS: 'availableLanguageIds',
    	            COUNTRY_CODE: 'countryCode',
    	            DATATYPE_NAME: 'dataTypeName',
    	            DATATYPE_VERSION: 'dataTypeVersion',
    	            DEFINITION: 'definition',
    	            DEFAULT_LANGUAGE_ID: 'defaultLanguageId',
    	            DEFAULT_LOCALE: 'defaultLocale',
    	            DEPENDENT_TERMS: 'dependentTerms',
    	            DIMENSION_X: 'dimensionX',
    	            DIMENSION_Y: 'dimensionY',
    	            DISABLED: 'disabled',
    	            DISPLAY_NAME: 'displayName',
    	            DISPLAY_STYLE: 'displayStyle',
    	            ELEMENT_TYPE: 'elementType',
    	            FILE_ID: 'fileId',
    	            FORMAT: 'format',
    	            ID: 'id',
    	            LIST_ITEM: 'listItem',
    	            LIST_ITEM_VALUE: 'listItemValue',
    	            LIST_ITEMS: 'listItems',
    	            LOWER_BOUNDARY: 'lowerBoundary',
    	            LOWER_OPERAND: 'lowerOperand',
    	            MANDATORY: 'mandatory',
    	            NAME: 'name',
    	            MAX_LENGTH:"maxLength_",
    	            MAX_VALUE:"maxValue_",
    	            MIN_LENGTH:"minLength_",
    	            MIN_VALUE:"minValue_",
    	            NAME_TEXT: 'nameText',
    	            NEW_LINE:"newLine_",
    	            OPERAND: 'operand',
    	            ORDER: 'order',
    	            PATH: 'path',
    	            PATH_TYPE: 'pathType',
    	            RANGE: 'range',
    	            REF_DATATYPES: 'refDataTypes',
    	            REF_DATABASES: 'refDatabases',
    	            SWEEPABLE: 'sweepable'
    	            SYNONYMS: 'synonyms',
    	            TERM_NAME: 'termName',
    	            TEXT: 'text',
    	            TOOLTIP: 'tooltip',
    	            TTYPE: 'type',
    	            UNCERTAINTY: 'uncertainty',
    	            UNCERTAINTY_VALUE: 'uncertaintyValue',
    	            UNIT: 'unit',
    	            UPPER_BOUNDARY: 'upperBoundary',
    	            UPPER_OPERAND: 'upperOperand',
    	            URI: 'uri',
    	            URI_TYPE: 'uriType',
    	            URL: 'url',
    	            VALIDATION_RULE : 'validationRule',
    	            VALUE_DELIMITER: 'valueDelimiter',
    	            VALUE: 'value',
    	            VERSION: 'version',
    	        }; // End of SSS.TermAttrNames
    	},
    	Util: {
                getTermTypes: function() {
                    let types = [];
                    types.push(SX.SSS.TermTypes.NUMERIC);
                    types.push(SX.SSS.TermTypes.STRING);
                    types.push(SX.SSS.TermTypes.BOOLEAN);
                    types.push(SX.SSS.TermTypes.ARRAY);
                    types.push(SX.SSS.TermTypes.LIST);
                    types.push(SX.SSS,TermTypes.LIST_ARRAY);
                    types.push(SX.SSS,TermTypes.DATE);
                    types.push(SX.SSS,TermTypes.PHONE);
                    types.push(SX.SSS,TermTypes.EMAIL);
                    types.push(SX.SSS,TermTypes.MATRIX);
                    types.push(SX.SSS,TermTypes.DATA_LINK);
                    types.push(SX.SSS,TermTypes.DATA_LINK_ARRAY);
                    types.push(SX.SSS,TermTypes.OBJECT);
                    types.push(SX.SSS,TermTypes.OBJECT_ARRAY);
                    types.push(SX.SSS,TermTypes.FILE);
                    types.push(SX.SSS,TermTypes.FILE_ARRAY);
                    types.push(SX.SSS,TermTypes.GROUP);
                    types.push(SX.SSS,TermTypes.COMMENT);
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
                		var localizedId = '#'+inputId+'_'+id;
                		console.log( 'document way: ', document.getElementById(inputId+'_'+id));
                		console.log('input: ', $(localizedId));
                		console.log('localizedId: '+localizedId + ', '+$(localizedId).val() );
                		listItem.displayName[id] = $(localizedId).val();
                	});
                	
                	console.log('Localized List Item: ', listItem);
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
    };
    
	SX.SSS.Term = function(){
		var Term = this;
		
		var LocaleObject = function( jsonObject ){
			var LO = this;
			
			LO.getLocaleText = function( locale , defaultLocale){
				var text = LO[locale];
				if( SSS.Util.isEmpty( text ) && defaultLocale ){
					return LO.getLocaleText( LO.defaultLocale() );
				} else {
					return text;
				}
			};
			
			LO.defaultLocale = function( locale ){
				return LO.property.apply(LO, SSS.Util.addFirstArgument(SSS.TermAttrNames.DEFAULT_LOCALE, arguments) );				
			};
			
			LO.setLocaleText = function( locale, text ){
				return LO[locale] = text;
			};
			
			LO.addLocaleText = function( locale, text){
				LO.setLocaleText( locale, text );
			};
			
			LO.removeLocale = function( locale ){
				return delete LO[locale];
			};
			
			LO.getXML = function( defaultLanguageId ){
				if( defaultLanguageId )
					return SSS.Util.toLocalizedXml(SSS.Util.toJSON(LO), '', defaultLanguageId);
				else
					return SSS.Util.toLocalizedXml(SSS.Util.toJSON(LO), '', LO.defaultLocale() );
			}; 
			
			LO.clone = function(){
				return new LocaleObject( SSS.Util.toJSON(LO) );
			};

			LO.deserialize = function (jsonObject){
				for( var key in jsonObject){
					LO[key] = jsonObject[key];
				}
			};

			if( arguments.length === 1 )
				LO.deserialize(jsonObject);
		};
		
		var _Term = function( jsonObject ) {
			var _TM = this;
			
			/**
			 * @memberOf Term._Term
			 */
			_TM.property = function( key, value){
				switch( arguments.length ){
					case 1:
						if( _TM.hasOwnProperty(key) )
							return _TM[key];
						else
							return false;
					case 2:
						_TM[key] = value;
						return true;
					default:
						return false;
				}
			};
			
			_TM.removeProperty = function( firstKey, secondKey ){
				switch( arguments.length ){
				case 1:
					delete _TM[firstKey];
					return true;
				case 2:
					var firstObj = _TM.property(firstKey);
					if( typeof firstObj === 'object' )
						return delete firstObj[secondKey];

					return false;
				default:
					return false;
				}
			};
			
			_TM.toJsonObject = function(){
				return JSON.parse( JSON.stringify(_TM) );
			};
			
			_TM.arrayExist = function(arrayObject,key){
				return arrayObject.some(function(arrVal) {
					return key === arrVal;
				});
			};

			_TM._deserialize = function( key, value ){
				if( typeof value === 'function')	return false;
				else _TM.property(key, value);

				return true;
			};

			_TM.name = function( name ) {
				return _TM.property.apply( _TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.NAME, arguments) );
			};

			_TM.type = function( type ){
				return _TM.property.apply( _TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.TYPE, arguments) );
			};

			_TM.mandatory = function( mandatory ){
				return _TM.property.apply( _TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.MANDATORY, arguments) );
			}
			
			_TM.displayName = function( displayName ) {
				return _TM.property.apply( _TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.DISPLAY_NAME, arguments) );
			};

			_TM.getLocaleDisplayName = function( locale, defaultLocale ) {
				var displayName = _TM.displayName();
				if( !displayName )	return '';
				
				return  displayName.getLocaleText( locale, defaultLocale );
			};
				
			_TM.setLocaleDisplayName = function( locale, text ){
				var displayName = _TM.displayName();
				if( !displayName ){
					displayName = new LocaleObject();
					_TM.displayName( displayName );
				}
				
				displayName.setLocaleText(locale, text);
			};
			
			_TM.addLocaleDisplayName = function( locale, text ){
				_TM.setLocaleDisplayName( locale, text );
			};

			_TM.removeLocaleDisplayName = function( locale ) {
				var displayName = _TM.displayName();
				if( !displayName ){
					return null;
				}
				
				displayName.removeLocale( locale );
				if( displayName.length === 0 ){
					_TM.removeProperty( SSS.TermAttrNames.DISPLAY_NAME );
					return null;
				}
				
				return displayName;
			};

			_TM.getDisplayNameXML = function(availableLanguageIds, defaultLanguageId) {
				var displayName = _TM.displayName();
				if( !displayName ){
					return '';
				}

				return displayName.getXML( availableLanguageIds, defaultLanguageId );
			};

			_TM.synonyms = function( synonyms ) {
				return _TM.property.apply(_TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.SYNONYMS, arguments));
			};
			
			_TM.addSynonym = function( synonym ){
				var synonyms = _TM.synonyms();
				if( !synonyms ){
					synonyms = [];
				}
				
				synonyms.push( synonym );
				
				return synonyms;
			};

			_TM.removeSynonym = function( synonym ){
				var synonyms = _TM.synonyms();
				if( !synonyms ){
					return null;
				}
				
				for( var i = 0; i<synonyms.length; i++){
					if( synonyms[i] === synonym ){
						SSS.Util.removeArrayElement( sysnonyms, i);
						return synonyms;
					}
				}
				
				return null;
			};

			_TM.definition = function( definition ) {
				return _TM.property.apply(_TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.DEFINITION, arguments));
			};
			
			_TM.getLocaleDefinition = function( locale, defaultLocale ){
				var definition = _TM.definition();
				if( !definition)	return '';
				
				return  definition.getLocaleText( locale, defaultLocale );
			};
			
			_TM.setLocaleDefinition = function( locale, text ){
				var definition = _TM.definition();
				if( !definition ){
					definition = new LocaleObject();
					_TM.definition( definition );
				}
				
				definition.setLocaleText(locale, text);
			};
			
			_TM.addLocaleDefinition = function( locale, text ){
				_TM.setLocaleDefinition( locale, text );
			};

			_TM.removeLocaleDefinition = function( locale ) {
				var definition = _TM.definition();
				if( !definition ){
					return null;
				}
				
				definition.removeLocale( locale );
				if( definition.length === 0 ){
					_TM.removeProperty( SSS.TermAttrNames.DEFINITION );
					return null;
				}
				
				return definition;
			};

			_TM.getDefinitionXML = function(availableLanguageIds, defaultLanguageId) {
				var definition = _TM.definition();
				if( !definition ){
					return '';
				}

				return definition.getXML( availableLanguageIds, defaultLanguageId );
			};
			
			_TM.tooltip = function( tooltip ) {
				return _TM.property.apply(_TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.TOOLTIP, arguments));
			};

			_TM.getLocaleTooltip = function( locale, defaultLocale ){
				var tooltip = _TM.tooltip();
				if( !tooltip)	return '';
				
				return  tooltip.getLocaleText( locale, defaultLocale );
			};
			
			_TM.setLocaleTooltip = function( locale, text ){
				var tooltip = _TM.tooltip();
				if( !tooltip ){
					tooltip = new LocaleObject();
					_TM.tooltip( tooltip );
				}
				
				tooltip.setLocaleText(locale, text);
			};
			
			_TM.addLocaleTooltip = function( locale, text ){
				_TM.setLocaleTooltip( locale, text );
			};

			_TM.removeLocaleTooltip = function( locale ) {
				var tooltip = _TM.tooltip();
				if( !tooltip ){
					return null;
				}
				
				tooltip.removeLocale( locale );
				if( tooltip.length === 0 ){
					_TM.removeProperty( SSS.TermAttrNames.TOOLTIP );
					return null;
				}
				
				return tooltip;
			};

			_TM.getTooltipXML = function(availableLanguageIds, defaultLanguageId) {
				var tooltip = _TM.tooltip();
				if( !tooltip ){
					return '';
				}

				return tooltip.getXML( availableLanguageIds, defaultLanguageId );
			};
			
			_TM.order = function( order ) {
				return _TM.property.apply(_TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.ORDER, arguments));
			};

			_TM.active = function( active ){
				return _TM.property.apply(_TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.ACTIVE, arguments));
			};

			_TM.disabled = function( disabled ){
				return _TM.property.apply(_TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.DISABLED, arguments));
			};

			_TM.value = function( value ){
				return _TM.property.apply(_TM, SSS.Util.addFirstArgument(SSS.TermAttrNames.VALUE, arguments));
			};

			_TM.verifyName = function(name){
				if(/[a-zA-Z\\_]/.test(name.charAt(0)) == false) return false;
				if(/[^a-zA-Z0-9\\_]/.test(name))	return false;
				return true;
			};

			_TM._deserialize = function( key, jsonObject ){
				switch( key ){
				case SSS.TermAttrNames.DISPLAY_NAME:
					_TM.displayName( new LocaleObject( jsonObject ) );
					break;
				case SSS.TermAttrNames.DEFINITION:
					_TM.definition( new LocaleObject( jsonObject ) );
					break;
				case SSS.TermAttrNames.TOOLTIP:
					_TM.tooltip( new LocaleObject( jsonObject ) );
					break;
				default: 
					_TM.property( key, jsonObject );
				}
			};

		}; // End of _TM

		// 1. NumericTerm
		var NumericTerm = function( jsonObject ){
			var NT = this;
			_Term.apply(NT);
			NT.type( SX.SSS,TermTypes.NUMERIC);
			NT.unit = function( unit ){
				return NT.property.apply(NT, SSS.Util.addFirstArgument(SSS.TermAttrNames.UNIT, arguments));
			};

			NT.minValue = function( value ) {
				return NT.property.apply(NT, SSS.Util.addFirstArgument(SSS.TermAttrNames.MIN_VALUE, arguments));
			};

			NT.maxValue = function( value ) {
				return NT.property.apply(NT, SSS.Util.addFirstArgument(SSS.TermAttrNames.MAX_VALUE, arguments));
			};
			
			NT.uncertainty = function( uncertainty ) {
				return NT.property.apply(NT, SSS.Util.addFirstArgument(SSS.TermAttrNames.UNCERTAINTY, arguments));
			};
			
			NT.uncertaintyValue = function( uncertaintyValue ) {
				return NT.property.apply(NT, SSS.Util.addFirstArgument(SSS.TermAttrNames.UNCERTAINTY_VALUE, arguments));
			};

			NT.sweepable = function( sweepable ){
				if( !NT.minValue() || !NT.maxValue() ){
					return false;
				}
				return NT.property.apply(NT, SSS.Util.addFirstArgument(SSS.TermAttrNames.SWEEPABLE, arguments));
			};

			NT.clone = function(){
				return new NumericTerm( SSS.Util.toJSON(NT) );
			};

			NT.deserialize = function (jsonObject){
				for( var key in jsonObject){
					NT._deserialize(key, jsonObject[key]);
				}
			};
			
			if( arguments.length === 1 )
				NT.deserialize(jsonObject);
		}; // End of NumericTerm
		
		Term.newNumericTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new NumericTerm();
				case 1:
					return new NumericTerm(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newNumericTerm');
					return null;
			}
		};

		//2. ListTerm
		var ListTerm = function( jsonObject ){
			var LT = this;
			_Term.apply(LT);
			
			LT.type( SX.SSS,TermTypes.LIST);
			
			var ListItem = function( jsonObject ){
				var Item = this;
	
				Item.value = function( value ){
					return Item.property.apply(Item, SSS.Util.addFirstArgument(SSS.TermAttrNames.VALUE, arguments));
				};

				Item.displayName = function( displayName ){
					return Item.property.apply(Item, SSS.Util.addFirstArgument(SSS.TermAttrNames.DISPLAY_NAME, arguments));
				};
				
				Item.getLocaleDisplayName = function( locale, defaultLocale ){
					var displayName = Item.displayName();
					if( !displayName)	return '';
					
					return  displayName.getLocaleText( locale, defaultLocale );
				};
				
				Item.setLocaleDisplayName = function( locale, text ){
					var displayName = Item.displayName();
					if( !displayName ){
						displayName = new LocaleObject();
						_TM.displayName( displayName );
					}
					
					displayName.setLocaleText(locale, text);
				};
				
				Item.addLocaleDisplayName = function( locale, text ){
					Item.setLocaleDisplayName( locale, text );
				};

				Item.removeLocaleDisplayName = function( locale ) {
					var displayName = Item.displayName();
					if( !displayName ){
						return null;
					}
					
					displayName.removeLocale( locale );
					if( displayName.length === 0 ){
						delete Item[SSS.TermAttrNames.DISPLAY_NAME];
						return null;
					}
					
					return displayName;
				};

				Item.getDisplayNameXML = function(availableLanguageIds, defaultLanguageId) {
					var displayName = Item.displayName();
					if( !displayName ){
						return '';
					}

					return displayName.getXML( availableLanguageIds, defaultLanguageId );
				};
				
				Item.dependentTerms = function(terms){
					return Item.property.apply(Item, SSS.Util.addFirstArgument(SSS.TermAttrNames.DEPENDENT_TERMS, arguments));
				};
				
				Item.addDependentTerm = function( termName ){
					var terms = Item.dependentTerms();
					if( !terms ){
						terms = [];
						Item.dependentTerms( terms );
					}
					
					terms.push( termName );
				};
				
				Item.removeDependentTerm = function( termName ){
					var terms = Item.dependentTerms();
					if( !terms ){
						return terms;
					}
					
					for( var i=1; i<terms.length; i++){
						var term = terms[i];
						if( term.value() === termName ){
							return SSS.Util.removeArrayElement( terms, i );
						}
					}
					
					return terms;
				};
				
				Item.clone = function(){
					return new ListItem( SSS.Util.toJSON(Item) );
				};

				Item.deserialize = function (jsonObject){
					for( var key in jsonObject){
						switch( key ){
						case SSS.TermAttrNames.DISPLAY_NAME:
							Item.displayName( new LocaleObject( jsonObject[key] ) );
							break;
						default:
							Item[key] =  jsonObject[key];
						}
					}
				};

				if( arguments.length === 1 )
					Item.deserialize(jsonObject);
			};

			LT.listItems = function( items ) {
				return LT.property.apply(LT, SSS.Util.addFirstArgument(SSS.TermAttrNames.LIST_ITEMS, arguments));
			};

			/**
			 * Returns ListItem object including functions
			 */
			LT.getListItem = function( itemValue ) {
				var listItems = LT.listItems();
				if( !listItems )	return null;

				for( var i=0; i<listItems.length; i++ ){
					var item = listItems[i];
					if( item.value() === itemValue ){
						return item;
					}
				}
				return null;
			};
			
			LT.addListItem = function( itemValue, jsonDisplayName ) {
				var listItems = LT.listItems();
				if( !listItems ){
					listItems = [];
					LT.listItems( listItems );
				}
				
				var item = new ListItem();
				item.value(itemValue);
				
				item.displayName(new LocaleObject( jsonDisplayName ));
				listItems.push(item);
				
				return listItems;
			};
				
			LT.removeListItem = function( itemValue ){
				var listItems = LT.listItems();
				if( !listItems ){
					return null;
				}

				for( var i=0; i<listItems.length; i++){
					var item = listItems[i];
					if( item.value() === itemValue ){
						return SSS.Util.removeArrayList( listItems, i);
					}
				}
				return listItems;
			};

			LT.getLocaleItemDisplayName = function(itemValue, locale, defaultLocale){
				var listItems = LT.listItems();
				if( !listItems ){
					return '';
				}
				
				for( var i=0; i<listItems.length; i++){
					var item = listItems[i];
					if( item.value() === itemValue ){
						return item.getLocaleDisplayName( locale, defaultLocale );
					}
				}
				return null;
			}
			
			LT.addLocaleItemDisplayName = function(itemValue, locale, displayName){
				var listItems = LT.listItems();
				if( !listItems ){
					listItems = [];
					var item = new ListItem();
					item.value(itemValue);
					item.setLocaleDisplayName(locale, displayName);
					listItems.push( item );
					LT.listItems( listItems );
					return listItems;
				}
				else{
					for( var i=0; i<listItems.length; i++ ){
						var item = listItems[i];
						if( item.value() === itemValue ){
							item.addLocaleDisplayName(locale, displayName);
							return listItems;
						}
					}
				}

				var item = new ListItem();
				item.value(itemValue);
				item.setLocaleDisplayName(locale, displayName);
				listItems.push( item );

				return listItems;
			}

			LT.removeLocaleListItemText = function(itemValue, locale){
				var listItems = LT.listItems();
				if( !listItems ){
					return null;
				}

				for( var i=0; i<listItems.length; i++ ){
					var item = listItems[i];
					if( item.value() === itemValue ){
						var displayName = item.displayName();
						displayName.removeLocale( locale );
						return listItems;
					}
				}
				
				return listItems;
			}

			LT.removeAllItems = function(){
				return LT.removeProperty(SSS.TermAttrNames.LIST_ITEMS);
			};
			
			LT.dependentTerms = function( tems ) {
				return LT.property.apply(LT, SSS.Util.addFirstArgument(SSS.TermAttrNames.DEPENDENT_ITEMS, arguments));
			};
			
			LT.addDependentTerm = function( term ){
				var terms = LT.dependentTerms();
				if( !terms ){
					terms = [];
					LT.dependentTerms( terms );
				}
				
				terms.push( term );
				return terms;
			};
			
			LT.removeDependentTerm = function( term ){
				var terms = LT.dependentTerms();
				if( !terms ){
					return null;
				}
				
				for( var i=0; i<terms.length; i++ ){
					if( term === terms[i] ){
						SSS.Util.removeArrayElement( terms, i);
						return terms;
					}
				}
				return null;
			};

			LT.getItemDependentTerms = function( itemValue ){
				var items = LT.listItems();
				if( !items )		return null;
				
				for( var i=0; i<items.length; i++ ){
					var item = items[i];
					return item.dependentTerms();
				}
				
				return null; 
			};
			
			LT.displayStyle = function( style ) {
				return LT.property.apply(LT, SSS.Util.addFirstArgument(SSS.TermAttrNames.DISPLAY_STYLE, arguments));
			};

			LT.clone = function(){
				return new ListTerm( SSS.Util.toJSON(LT) );
			};

			LT.deserialize = function ( jsonObject ){
				for( var key in jsonObject ){
					switch( key ){
					case SSS.TermAttrNames.LIST_ITEMS:
						var jsonItems = jsonObject[key];
						for( var i=0; i<jsonItems.length; i++ ){
							LT.addListItem( new ListItem(jsonItems[i]) );
						}
						break;
					default:
						LT._deserialize( key, jsonObject[key] );
					}
				}
			};
			
			if( arguments.length === 1 )
				LT.deserialize(jsonObject);
		}; 
		Term.newListTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new ListTerm();
				case 1:
					return new ListTerm(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newListTerm');
					return null;
			}
		};//2. End of ListTerm

		// 4. StringTerm
		var StringTerm = function( jsonObject ){
			var ST = this;
			_Term.apply(ST);
			ST.type( SX.SSS,TermTypes.STRING);

			ST.minLength = function( length ){
				return ST.property.apply(ST, SSS.Util.addFirstArgument(SSS.TermAttrNames.MIN_LENGTH, arguments));
			};

			ST.maxLength = function( length ){
				return ST.property.apply(ST, SSS.Util.addFirstArgument(SSS.TermAttrNames.MAX_LENGTH, arguments));
			};
			
			ST.newLine = function( allowd ){
				return ST.property.apply(ST, SSS.Util.addFirstArgument(SSS.TermAttrNames.VALIDATION_RULE, arguments));
			};

			ST.validationRule = function( rule ){
				return ST.property.apply(ST, SSS.Util.addFirstArgument(SSS.TermAttrNames.VALIDATION_RULE, arguments));
			};

			ST.clone = function(){
				return new StringTerm( SSS.Util.toJSON(ST) );
			};

			ST.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					SP._deserialize( key, jsonObject[key] );
			};
				
			if( arguments.length === 1 )
				ST.deserialize(jsonObject);
		};
		Term.newStringTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new StringTerm();
				case 1:
					return new StringTerm(jsonObject);
				default:
					console.log( 'Arguments mismatch: newStringTerm()');
					return null;
			}
		}; //4. End of StringTerm
		
		//5. BooleanTerm
		var BooleanTerm = function( jsonObject ){
			var BT = this;
			_Term.apply(BT);
			BT.type( SX.SSS,TermTypes.BOOLEAN );

			BT.clone = function(){
				return new BooleanTerm( OSP.Util.toJSON(BT) );
			};

			BT.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					BT._deserialize( key, jsonObject[key] );
			};

			if( arguments.length === 1 )
				BT.deserialize(jsonObject);
		};
		Term.newBooleanTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new BooleanTerm();
				case 1:
					return new BooleanTerm(jsonObject);
				default:
					console.log( 'Arguments mismatch: newBooleanTerm()');
					return null;
			}
		}; //5. End of BooleanTerm
		
		// 6. FileTerm
		var FileTerm = function( jsonObject ){
			var FT = this;
				
			_Term.apply(FT);
			FT.type(SX.SSS,TermTypes.FILE);

			FT.uriType = function( type ){
				return FT.property.apply(FT, SSS.Util.addFirstArgument(SSS.TermAttrNames.URI_TYPE, arguments));
			}
			
			FT.dataTypeName = function( name ) {
				return FT.property.apply(FT, SSS.Util.addFirstArgument(SSS.TermAttrNames.DATATYPE_NAME, arguments));
			};
			FT.dataTypeVersion = function( version ) {
				return FT.property.apply(FT, SSS.Util.addFirstArgument(SSS.TermAttrNames.DATATYPE_VERSION, arguments));
			};

			FT.clone = function(){
				return new FileTerm( SSS.Util.toJSON(FT) );
			};
			
			FT.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					FT._deserialize( key, jsonObject[key] );
			};
				
			if( arguments.length === 1 )
				FT.deserialize(jsonObject);
		};
		Term.newFileTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new FileTerm();
				case 1:
					return new FileTerm(jsonObject);
				default:
					console.log( 'Arguments mismatch: newFileTerm()');
					return null;
			}
		};// 6.  End of FileTerm
		
		// 7. FileArrayTerm
		var FileArrayTerm = function( jsonObject ){
			var FA = this;
				
			_Term.apply(FA);
			FA.type(SX.SSS,TermTypes.FILE_ARRAY);

			FA.uriType = function( type ){
				return FA.property.apply(FA, SSS.Util.addFirstArgument(SSS.TermAttrNames.URI_TYPE, arguments));
			}
			
			FA.addFile = function( fileUri, dataTypeName, dataTypeVersion ){
				let ary = FA.value();
				if( !ary ){
					ary = [];
					FA.value( ary );
				}
				
				return ary.push({
					'uri': fileUri,
					'dataTypeName': dataTypeName,
					'dataTypeVersion': dataTypeVersion
				});
			};
			
			FA.removeFile = function( fileUri ){
				let ary = FA.value();
				if( !ary ){
					return ary;
				}
				
				for( let i=0; i<ary.length; i++ ){
					let file = ary[i];
					if( file.uri === fileUri ){
						return SSS.Util.removeArrrayElement( ary, i );
					}
				}
				
				return ary; 
			};
			
			FA.clone = function(){
				return new FileArrayTerm( SSS.Util.toJSON(FA) );
			};
			
			FA.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					FA._deserialize( key, jsonObject[key] );
			};
				
			if( arguments.length === 1 )
				FA.deserialize(jsonObject);
		};
		Term.newFileArrayTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new FileArrayTerm();
				case 1:
					return new FileArrayTerm(jsonObject);
				default:
					console.log( 'Arguments mismatch: newFileArrayTerm()');
					return null;
			}
		}; // 7. End of FileArrayTerm
		
		// 8. MatrixTerm
		var MatrixTerm = function( jsonObject ){
			var MT = this;
			_Term.apply(MT);
			
			MT.type( SX.SSS,TermTypes.MATRIX );

			MT.dimensionX = function( dimension ) {
				return MT.property.apply(MT, SSS.Util.addFirstArgument(SSS.TermAttrNames.DIMENSION_X, arguments));
			};

			MT.dimensionY = function( dimension ) {
				return MT.property.apply(MT, SSS.Util.addFirstArgument(SSS.TermAttrNames.DIMENSION_Y, arguments));
			};

			MT.dimension = function( dimensionJSON ) {
				switch( arguments.length ){
				case 0:
					return {
						'x': MT.dimensionX(),
						'y': MT.dimensionY()
			 		};
				case 1:
					MT.dimensionX( dimensionJSON.x );
					MT.dimensionY( dimensionJSON.y );
					return MT.dimension();
				default:
					console.log( 'Arguments mismatch: MatrixTerm.dimension()');
					return null;
				}
			};

			MT.clone = function(){
				return new MatrixTerm( SSS.Util.toJSON(MT) );
			};

			MT.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					MT._deserialize( key, jsonObject[key] );
			};
			
			if( arguments.length === 1 )
				MT.deserialize(jsonObject);
		}; 
		Term.newMatrixTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new MatrixTerm();
				case 1:
					return new MatrixTerm(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newMatrixTerm()');
					return null;
			}
		};//8. End of MatrixTerm
		
		// 9. CommentTerm
		var CommentTerm = function( jsonObject ){
			var CT = this;
			_Term.apply(CT);
			CT.type( SX.SSS,TermTypes.COMMENT);

			CT.clone = function(){
				return new CT( SSS.Util.toJSON(CT) );
			};

			CT.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					CT._deserialize( key, jsonObject[key] );
			};

			if( arguments.length === 1 )
				CT.deserialize(jsonObject);
		};
		Term.newCommentTerm = function(jsonObject){
			switch( arguments.length ){
				case 0:
					return new CommentTerm();
				case 1:
					return new CommentTerm(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newCommentTerm()');
					return null;
			}
		}; //9. End of CommentTerm
			
		// 10. DateTerm
		var DateTerm = function(jsonObject){
			var DT = this;
			
			_Term.apply(DT);
			
			DT.type( SX.SSS,TermTypes.DATE);
			
			DT.format = function(value){
				return DT.property.apply(DT, SSS.Util.addFirstArgument(SSS.TermAttrNames.FORMAT, arguments));
			};
			
			DT.clone = function(){
				return new DateTerm( SSS.Util.toJSON(DT) );
			};

			DT.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					DT._deserialize( key, jsonObject[key] );
			};

			if( arguments.length === 1 )
				DT.deserialize(jsonObject);
		}; 
		Term.newDateTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new DateTerm();
				case 1:
					return new DateTerm(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newDateTerm()');
					return null;
			}
		}; //10. end of DateTerm.
			
		// 11. PhoneTerm
		var PhoneTerm = function(jsonObject){
			var PT = this;

			_Term.apply(PT);

			PT.type( SX.SSS,TermTypes.PHONE);

			PT.countryCode = function(value){
				return PT.property.apply(PT, SSS.Util.addFirstArgument(SSS.TermAttrNames.COUNTRY_CODE, arguments));
			};

			PT.clone = function(){
				return new PhoneTerm( SSS.Util.toJSON(PT) );
			};

			PT.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					PT._deserialize( key, jsonObject[key] );
			};

			if( arguments.length === 1 )
				PT.deserialize(jsonObject);
		};
		Term.newPhoneTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new PhoneTerm();
				case 1:
					return new PhoneTerm(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newPhoneTerm()');
					return null;
			}
		}; //11. end of PhoneTerm
			
		// 12. EMailTerm
		var EMailTerm = function(jsonObject){
			var ET = this;
			_Term.apply(ET);
			
			ET.type( SX.SSS,TermTypes.EMAIL);

			ET.clone = function(){
				return new EMailTerm( SSS.Util.toJSON(ET) );
			};

			ET.deserialize = function( jsonObject ){
				for( var key in jsonObject ){
					ET._deserialize( key, jsonObject[key] );
				}
			};
			
			if( arguments.length === 1 )
				ET.deserialize( jsonObject );
		};
		Term.newEMailTerm = function( jsonObject ){
			switch( arguments.length ){
			case 0:
				return new EMailTerm();
			case 1:
				return new EMailTerm(jsonObject);
			default:
				colsole.log( 'Arguments mismatch: newEMailTerm()');
				return null;
			}
		};	//12. end of EMailTerm

		// 13. ObjectTerm
		var ObjectTerm = function( jsonObject ){
			var OT = this;
			_Term.apply(OT);
			OT.type( SX.SSS,TermTypes.OBJECT);
			
			OT.refDataTypes = function( dataTypes ){
				return OT.property.apply(OT, SSS.Util.addFirstArgument(SSS.TermAttrNames.REF_DATATYPES, arguments));
			};
			
			OT.addRefDataType = function( name, version ){
				let dataTypes = OT.refDataTypes();
				if( !dataTypes ){
					dataTypes = [];
					OT.refDataTypes( dataTypes );
				}
				
				dataTypes.push( {
					'name': name,
					'version': version
				});
				
				return dataTypes;
			};
			
			OT.removeRefDataType = function( name, version ){
				let dataTypes = OT.refDataTypes();
				if( !dataTypes ){
					return null;
				}
				
				for( let i=0; i<dataTypes.length; i++ ){
					let dataType = dataTypes[i];
					if( dataType.name === name && dataType.version ){
						return SSS.Util.removeArrayElement( dataTypes, i ); 
					}
				}
				
				return dataTypes;
			};
			
			OT.clone = function(){
				return new newObjectTerm( SSS.Util.toJSON(OT) );
			};

			OT.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					OT._deserialize( key, jsonObject[key] );
			};
			
			if( arguments.length === 1 )
				OT.deserialize(jsonObject);
		}; 
		Term.newObjectTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new ObjectTerm();
				case 1:
					return new ObjectTerm(jsonObject);
				default:
					console.log( 'Arguments mismatch: newObjectTerm()');
					return null;
			}
		};//13.  end of ObjectTerm
			
		// 14. ObjectArrayTerm
		var ObjectArrayTerm = function( jsonObject ){
			var OA = this;
			_Term.apply(OA);
			OA.type( SX.SSS,TermTypes.OBJECT_ARRAY);

			OA.addObject = function( obj ){
				let ary = OA.value();
				if( !ary ){
					ary = [];
					OA.value( ary );
				}
				
				return ary.push(obj);
			};
			
			OA.removeObject = function( index ){
				let ary = OA.value();
				if( !ary ){
					return ary;
				}
				
				return SSS.Util.removeArrrayElement( ary, index );
			};

			OA.refDataTypes = function( dataTypes ){
				return OA.property.apply(OA, SSS.Util.addFirstArgument(SSS.TermAttrNames.REF_DATATYPES, arguments));
			};
			
			OA.addRefDataType = function( name, version ){
				let dataTypes = OA.refDataTypes();
				if( !dataTypes ){
					dataTypes = [];
					OA.refDataTypes( dataTypes );
				}
				
				dataTypes.push( {
					'name': name,
					'version': version
				});
				
				return dataTypes;
			};
			
			OA.removeRefDataType = function( name, version ){
				let dataTypes = OA.refDataTypes();
				if( !dataTypes ){
					return null;
				}
				
				for( let i=0; i<dataTypes.length; i++ ){
					let dataType = dataTypes[i];
					if( dataType.name === name && dataType.version === version  ){
						return SSS.Util.removeArrayElement( dataTypes, i ); 
					}
				}
				
				return dataTypes;
			};

			OA.clone = function(){
				return new newObjectArrayTerm( SSS.Util.toJSON(OA) );
			};

			OA.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					OA._deserialize( key, jsonObject[key] );
			};
			
			if( arguments.length === 1 )
				OA.deserialize(jsonObject);
		}; 
		Term.newObjetArrayTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new ObjectArrayTerm();
				case 1:
					return new ObjectArrayTerm(jsonObject);
				default:
					console.log( 'Arguments mismatch: newObjetArrayTerm()');
					return null;
			}
		};//14. end of ObjectArrayTerm
			
		// 15. DataLinkTerm
		var DataLinkTerm = function( jsonObject ){
			var DL = this;
			_Term.apply(DL);
			DL.type( SX.SSS,TermTypes.DATA_LINK);

			DL.refDatabases = function( databases ){
				return DL.property.apply(DL, SSS.Util.addFirstArgument(SSS.TermAttrNames.REF_DATABASES, arguments));
			};

			DL.addRefDatabase = function( name, version ){
				let databases = DL.refDatabases();
				if( !databases ){
					databases = [];
					DL.refDatabases( databases );
				}
				
				databases.push( {
					'name': name,
					'version': version
				});
				
				return databases;
			};
			
			DL.removeRefDatabase = function( name, version ){
				let databases = DL.refDatabases();
				if( !databases ){
					return null;
				}
				
				for( let i=0; i<databases.length; i++ ){
					let database = databases[i];
					if( database.name === name && database.version === version ){
						return SSS.Util.removeArrayElement( databases, i ); 
					}
				}
				
				return databases;
			};
			
			DL.clone = function(){
				return new newDataLinkTerm( SSS.Util.toJSON(DL) );
			};

			DL.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					DL._deserialize( key, jsonObject[key] );
			};
			
			if( arguments.length === 1 )
				DL.deserialize(jsonObject);
		};
		Term.newDataLinkTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new DataLinkTerm();
				case 1:
					return new DataLinkTerm(jsonObject);
				default:
					console.log( 'Arguments mismatch: newDataLinkTerm()');
					return null;
			}
		};//15. end of DataLinkTerm
			
		// 16. DataLinkArrayTerm
		var DataLinkArrayTerm = function( jsonObject ){
			var DA = this;
			_Term.apply(DA);
			DA.type( SX.SSS,TermTypes.DATA_LINK_ARRAY);

			DA.addDataLink = function( link ){
				let ary = OA.value();
				if( !ary ){
					ary = [];
					DA.value( ary );
				}
				
				return ary.push(link);
			};
			
			DA.removeObject = function( index ){
				let ary = DA.value();
				if( !ary ){
					return ary;
				}
				
				return SSS.Util.removeArrrayElement( ary, index );
			};

			DA.refDataTypes = function( dataTypes ){
				return DA.property.apply(DA, SSS.Util.addFirstArgument(SSS.TermAttrNames.REF_DATATYPES, arguments));
			};
			DA.refDatabases = function( databases ){
				return DA.property.apply(DA, SSS.Util.addFirstArgument(SSS.TermAttrNames.REF_DATABASES, arguments));
			};

			DA.addRefDatabase = function( name, version ){
				let databases = DA.refDatabases();
				if( !databases ){
					databases = [];
					DA.refDatabases( databases );
				}
				
				databases.push( {
					'name': name,
					'version': version
				});
				
				return databases;
			};
			
			DA.removeRefDatabase = function( name, version ){
				let databases = DA.refDatabases();
				if( !databases ){
					return null;
				}
				
				for( let i=0; i<databases.length; i++ ){
					let database = databases[i];
					if( database.name === name && database.version === version ){
						return SSS.Util.removeArrayElement( databases, i ); 
					}
				}
				
				return databases;
			};
			
			DA.clone = function(){
				return new newObjectArrayTerm( SSS.Util.toJSON(DA) );
			};

			DA.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					DA._deserialize( key, jsonObject[key] );
			};
			
			if( arguments.length === 1 )
				DA.deserialize(jsonObject);
		};
		Term.newDataLinktArrayTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new DataLinkArrayTerm();
				case 1:
					return new DataLinkArrayTerm(jsonObject);
				default:
					console.log( 'Arguments mismatch: newDataLinkArrayTerm()');
					return null;
			}
		};//16.  end of DataLinkArrayTerm
			
		// 17. ArrayTerm
		var ArrayTerm = function( jsonObject ){
			var AT = this;
			_Term.apply(AT);
			AT.type( SX.SSS,TermTypes.ARRAY);
			
			AT.addElement = function( element ){
				let ary = AT.value();
				if( !ary ){
					ary = [];
					AT.value( ary );
				}
				
				return ary.push(element);
			};
			
			AT.removeElement = function( index ){
				let ary = AT.value();
				if( !ary ){
					return ary;
				}
				
				return SSS.Util.removeArrrayElement( ary, index );
			};
		
			AT.clone = function(){
				return new ArrayTerm( SSS.Util.toJSON(AT) );
			};

			AT.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					AT._deserialize( key, jsonObject[key] );
			};
			
			AT.elementType = function( value ) {
				return AT.property.apply(AT, SSS.Util.addFirstArgument(SSS.TermAttrNames.ELEMENT_TYPE, arguments));
			}; 

			if( arguments.length === 1 )
				AT.deserialize(jsonObject);
		};
		Term.newArrayTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new ArrayTerm();
				case 1:
					return new ArrayTerm(jsonObject);
				default:
					console.log( 'Arguments mismatch: newArrayTerm()');
					return null;
			}
		};//17. end of 16. ArrayTerm
		
			
		// 18. GroupTerm
		var GroupTerm = function( jsonObject ){
			var GT = this;
			_Term.apply(GT);
			GT.type( SX.SSS,TermTypes.GROUP);

			GT.addTerm = function( termName ){
				var terms = GT.value();
				if( !terms ){
					terms = [];
					GT.value( terms );
				}

				terms.push( termName );
				return terms;
			};

			GT.removeTerm = function( termName ){
				var terms = GT.value();
				if( !terms )	return true;

				for( var index in terms ){
					var term = terms[index];
					if( term === termName ){
						return SSS.Util.removeArrayElement( terms, index);
					}
				}

				return terms;
			};

			GT.clone = function(){
				return new GroupTerm( SSS.Util.toJSON(GT) );
			};

			GT.deserialize = function( jsonObject ){
				for( var key in jsonObject )
					GT._deserialize( key, jsonObject[key] );
			};

			if( arguments.length === 1 )
				GT.deserialize(jsonObject);
		};
		Term.newGroupTerm = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new GroupTerm();
				case 1:
					return new GroupTerm(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newGroupTerm()');
					return null;
			}
		}; //18. End of GroupTerm

		Term.verifyTermName = function( parameterName ){
			if(/[a-zA-Z0-9\\_]/.test(parameterName.charAt(0)) === false) return false;
			if(/[^a-zA-Z0-9\\_]/.test(parameterName))		return false;

			var parameters = DS.parameters();
			if( !parameters )	return true;

			for( var i in parameters ){
				var parameter = parameters[i];
				if( parameter.name() === parameterName )
					return false;
			}

			return true;
		};
		
		Term.getEditForm = function(actionUrl, formName, formId, method, cssClass ){
			var $form = $('<form class="form" action="'+actionUrl+'" name="'+formName+'" id='+formId+'" method='+method+'>');
			$form.append($('<input>'));
			
			console.log( $form );
			return $form;
		}
	};
})(StationX, AUI.$, Liferay);
