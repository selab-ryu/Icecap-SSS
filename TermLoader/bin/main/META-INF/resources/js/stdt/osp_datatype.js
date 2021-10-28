(function(OSP){
	'use strict';

	if( OSP.DataType )	return;
	
	OSP.Path = function( jsonObject ){
		var Path = this;
		OSP._MapObject.apply(Path);

//		Path.VERSION='2019-3-15';
		Path[OSP.Constants.VERSION]=OSP.Constants.OSP_VERSION;
		Path.url = function( url ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.CONTENT, arguments) );
		};
		
		Path.setPath = function( id, parent, name, type, relative ){
			if( arguments.length < 3 )
				return false;
			
			Path.id( id );
			Path.parent(parent);
			Path.type(type);
			if( type === OSP.Constants.FOLDER )
				Path.folderName(name);
			else if( type === OSP.Constants.EXT )
				Path.extension(name);
			else
				Path.fileName(name);

			Path.relative(relative);
			return true;
		};
						   
		Path.id = function( id ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.CONTENT, arguments) );
		};

		Path.type = function( type ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.TYPE, arguments) );
		};

		Path.parent = function( parent ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.PARENT, arguments) );
		};

		Path.name = function( name ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.NAME, arguments) );
		};

		Path.repositoryType = function( repositoryType ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.REPOSITORY_TYPE, arguments) );
		};

		Path.user = function( user ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.USER, arguments) );
		};
		
		Path.relative = function( relative ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.RELATIVE, arguments) );
		};
		
		Path.content = function( content ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.CONTENT, arguments) );
		};

		Path.dlEntryId = function( entryId ){
			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.CONTENT, arguments) );
		};
		
		Path.fileName = function( fileName ){
			if( Path.type() !== OSP.Constants.FILE )
				return false;

			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.NAME, arguments) );
		};
		
		Path.extension = function( ext ){
			if( Path.type() !== OSP.Constants.EXT )
				return false;

			switch( arguments.length ){
			case 0:
				return Path.property(OSP.Constants.NAME).replace('*', '').replace('.', '').replace('/','');
			case 1:
				return Path.property(OSP.Constants.NAME, ext.replace('*', '').replace('.', '').replace('/',''));
			default:
				return false;
			}
		};

		Path.folderName = function( folderName ){
			if( Path.type() !== OSP.Constants.FOLDER )
				return false;

			return Path.property.apply( Path, OSP.Util.addFirstArgument(OSP.Constants.NAME, arguments) );
		};

		Path.fullPath = function(){
			if( Path.type() === OSP.Enumeration.PathType.URL )
				return Path.url();
			if( !Path.parent() && !Path.name())
				return '';
			if( !Path.parent() )
				return Path.name();
			if( !Path.name() )
				return Path.parent();

			return Path.parent()+'/'+ Path.name();
		};
		
		Path.getFullFolderPath = function(){
			var folder = '';
			if( Path.type() === OSP.Enumeration.PathType.FOLDER  ){
				folder = OSP.Util.mergePath( Path.parent(), Path.name() );
			}
			else if ( Path.type() === OSP.Enumeration.PathType.FILE ||
						Path.type() === OSP.Enumeration.PathType.EXT ||
						Path.type() === OSP.Enumeration.PathType.FILE_CONTENT ){
				folder = Path.parent();
			}

			return folder;
		};
		
		Path.clone = function(){
			return new OSP.Path( OSP.Util.toJSON( Path ) );
		};

		Path.deserialize = function( jsonObject ){
			for( var key in jsonObject )
				Path._deserialize( key, jsonObject[key] );
		};

		if( arguments.length === 1 ){
			Path.deserialize(jsonObject);
		}
	}; // End of Path

	OSP.InputData = function( jsonInputData ){
		var InputData = this;
		OSP.Path.apply(InputData);

		InputData.VERSION='20190228-GA01';

		InputData.order = function( order ){
			return InputData.property.apply( InputData, OSP.Util.addFirstArgument(OSP.Constants.ORDER, arguments) );
		};
		
		InputData.dataType = function( dataType ){
			return InputData.property.apply( InputData, OSP.Util.addFirstArgument(OSP.Constants.DATA_TYPE, arguments) );
		};

		InputData.setDataType = function( typeName, version ){
			var dataType = InputData.dataType();
			if( !dataType ){
				dataType = {};
				InputData.dataType(dataType);
			}
			
			dataType.name_ = typeName;
			dataType.version_ = version;
		};

		InputData.clone = function(){
			return new OSP.InputData( OSP.Util.toJSON(InputData) );
		};
		
		InputData.dirty = function( dirty ){
			return InputData.property.apply( InputData, OSP.Util.addFirstArgument(OSP.Constants.DIRTY, arguments) );
		};
		
		InputData.portName = function( portName ){
			return InputData.property.apply( InputData, OSP.Util.addFirstArgument(OSP.Constants.PORT_NAME, arguments) );
		};

		InputData.fileType = function( fileType ){
			return InputData.property.apply( InputData, OSP.Util.addFirstArgument(OSP.Constants.FILE_TYPE, arguments) );
		};
		InputData.classPK = function( classPK ){
			return InputData.property.apply( InputData, OSP.Util.addFirstArgument(OSP.Constants.CLASS_PK, arguments) );
		};
		
		InputData.deserialize = function( jsonInputData ){
			//console.log( 'jsonInputData: ', jsonInputData );
			for( var key in jsonInputData ){
				//console.log('key: '+key);
				//console.log('value: ', jsonInputData[key]);
				switch( key ){
					case OSP.Constants.TYPE:
						InputData.property( OSP.Constants.TYPE, jsonInputData[key].replace(/_/g,''));
						break;
					case OSP.Constants.REPOSITORY_TYPE:
					case OSP.Constants.USER:
					case OSP.Constants.PARENT:
					case OSP.Constants.NAME:
					case OSP.Constants.DIRTY:
					case OSP.Constants.ORDER:
					case OSP.Constants.DATA_TYPE:
					case OSP.Constants.CONTENT:
					case OSP.Constants.ID:
					case OSP.Constants.URI:
					case OSP.Constants.RELATIVE:
					case OSP.Constants.PORT_NAME:
					case OSP.Constants.FILE_TYPE:
					case OSP.Constants.CLASS_PK:
					case OSP.Constants.version:
					case "fullPath_":
					case 'VERSION':
						InputData.property( key, jsonInputData[key] );
						break;
					case 'context_':
					case OSP.Constants.CONTENT:
						InputData.property( OSP.Constants.CONTENT, jsonInputData[key] );
						break;
					default:
						console.log('Un-recognizable InputData key: ' + key);
				}
			}
		};

		if( arguments.length === 1 )
			InputData.deserialize(jsonInputData);
	}; // End of InputData

	OSP.DataType = function(){
		var DataType = this;
		OSP._OpenObject.apply(DataType);

		DataType.VERSION='2019315';
		
		var DataStructure = function( jsonObject ){
			var DS = this;
			OSP._MapObject.apply(DS);

			var Range = function( jsonObject ) {
				var R = this;
				OSP._MapObject.apply( R );

				R.lowerBoundary = function( boundary ){
					return R.property.apply( R, OSP.Util.addFirstArgument(OSP.Constants.LOWER_BOUNDARY, arguments) );
				};

				R.upperBoundary = function( boundary ){
					return R.property.apply( R, OSP.Util.addFirstArgument(OSP.Constants.UPPER_BOUNDARY, arguments) );
				};

				R.operand = function( operand ){
					return R.property.apply( R, OSP.Util.addFirstArgument(OSP.Constants.OPERAND, arguments) );
				};

				R.includeBoundary = function( whichEnd, flag ){
					var operand = R.operand();
					if( !operand )	return false;

					if( whichEnd === OSP.Constants.LOWER_BOUNDARY && flag == true )
						operand = '=' + operand[1];
					else if( whichEnd === OSP.Constants.LOWER_BOUNDARY && flag == false )
						operand = '<' + operand[1];
					else if( whichEnd === OSP.Constants.UPPER_BOUNDARY && flag == true )
						operand = operand[0] + '=';
					else if( whichEnd === OSP.Constants.UPPER_BOUNDARY && flag == false )
						operand = operand[0] + '>';
					else
						return false;

					R.operand( operand );
					return true;
				};

				R.checkLowerBoundary = function(){
					var operand = R.operand();
					if( !operand )	return false;
					else{
						if( operand[0] === '=' ) return true;
						else	return false;
					}
				};

				R.checkUpperBoundary = function(){
					var operand = R.operand();
					if( !operand )	return false;
					else{
						if( operand[1] === '=' ) return true;
						else	return false;
					}
				};

				R.setRange = function( lowerBoundary, upperBoundary, operand ) {
					var result = R.property( OSP.Constants.LOWER_BOUNDARY, lowerBoundary );
					if( result === false )	return result;
					result = R.property( OSP.Constants.UPPER_BOUNDARY, upperBoundary );
					if( result === false )	return result;
					result = R.property( OSP.Constants.OPERAND, operand);

					return result;
				};

				R.verify = function( value ){
					var lowerBoundaryContained = R.checkLowerBoundary();
					var upperBoundaryContained = R.checkUpperBoundary();
					var lowerBoundary = R.lowerBoundary();
					var upperBoundary = R.upperBoundary();

					if( !lowerBoundary && !upperBoundary )	return true;

					if( lowerBoundaryContained && !upperBoundary){
						if( Number(value) >= Number(lowerBoundary) )	return true;
					}
					else if ( !lowerBoundaryContained && !upperBoundary ){
						if( Number(value) > Number(lowerBoundary) )	return true;
					}
					else if ( !lowerBoundary && upperBoundaryContained ){
						if( Number(value) <= Number(upperBoundary) )	return true;
					}
					else if( !lowerBoundary && !upperBoundaryContained){
						if( Number(value) < Number(upperBoundary) )	return true;
					}
					else if(  lowerBoundaryContained && upperBoundaryContained ){
						if( Number(value) >= Number(lowerBoundary) && Number(value) <= Number(upperBoundary) )
							return true;
					}
					else if( !lowerBoundaryContained && upperBoundaryContained ){
						if( Number(value) > Number(lowerBoundary) && Number(value) <= Number(upperBoundary) )
							return true;
					}
					else if( lowerBoundaryContained && !upperBoundaryContained ){
						if( Number(value) >= Number(lowerBoundary) && Number(value) < Number(upperBoundary) )
							return true;
					}
					else if( !lowerBoundaryContained && !upperBoundaryContained ){
						if( Number(value) > Number(lowerBoundary) && Number(value) < Number(upperBoundary) )
							return true;
					}

					return false;
				};

				R.clone = function(){
					return new Range( OSP.Util.toJSON(R) );
				};

				R.deserialize = function( jsonRange ){
					for( var key in jsonRange ){
						R.property( key, jsonRange[key] );
					}
				};

				R.upgrade = function( oldRange ){
					for( var key in oldRange ){
						if( OSP.Util.isEmpty(oldRange[key]) )	continue;
						switch( key ){
						case 'lower-limit':
							R.lowerBoundary(oldRange[key]);
							break;
						case 'upper-limit':
							R.upperBoundary(oldRange[key]);
							break;
						case 'operand':
							R.operand(oldRange[key]);
							break;
						default:
							alert( 'Undefined Range property: '+key);
						}
					}
				};

				if( arguments.length === 1 )
					R.deserialize(jsonObject);
			}; // End of Range
			DS.newRange = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new Range();
					case 1:
						return new Range(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newRange');
						return null;
				}
			};
			
			// get parameter names by type.
			DS.parametersByType = function(type){
				var parameterNames = DS.parameterNames();
				
				var parameters = parameterNames.filter(name => type == DS.parameter(name).type() );

				return parameters;
			}

			var _ActivateCondition = function( jsonObject ){
				var AC = this;
				OSP._MapObject.apply(AC);

				AC.parameterName = function( name ) {
					return AC.property.apply( AC, OSP.Util.addFirstArgument(OSP.Constants.PARAMETER_NAME, arguments) );
				};

				AC.type = function( type ){
					return AC.property.apply( AC, OSP.Util.addFirstArgument(OSP.Constants.TYPE, arguments) );
				};

				AC._upgrade = function( key, value, newCondition ){
					if( OSP.Util.isEmpty(value) ) return;

					switch( key ){
					case 'variable-name':
						newCondition.parameterName( value );
						break;
					case 'type':
						newCondition.type( value );
						break;
					}
				};
			}; // End of _ActivateCondition
			
			
			var NumericActivateCondition = function( jsonObject ){
				var NAC = this;
				_ActivateCondition.apply(NAC);
				NAC.type(OSP.Constants.NUMERIC);

				NAC.range = function( range ){
					return NAC.property.apply( NAC, OSP.Util.addFirstArgument(OSP.Constants.RANGE, arguments) );
				};

				NAC.rangeLowerBoundary = function( boundary ){
					var range = NAC.range();
					if( !range )	return false;

					return range.lowerBoundary.apply( range, arguments );
				};

				NAC.rangeUpperBoundary = function( boundary ){
					var range = NAC.range();
					if( !range )	return false;

					return range.upperBoundary.apply( range, boundary );
				};

				NAC.rangeOperand = function( operand ){
					var range = NAC.range();
					if( !range )	return false;

					return range.operand.apply( range, operand );
				};

				NAC.rangeCheckLowerBoundary = function(){
					var range = NAC.range();
					if( !range )	return false;
					return range.checkLowerBoundary();
				};

				NAC.rangeCheckUpperBoundary = function(){
					var range = NAC.range();
					if( !range )	return false;
					return range.checkUpperBoundary();
				};

				NAC.rangeIncludeBoundary = function( whichEnd, flag ){
					var range = NAC.range();
					if( !range )	return false;
					return range.includeBoundary( whichEnd, flag );
				};

				NAC.verifyRange = function( value ){
					var range = NAC.range();
					if( !range )	return true;
					return range.verify( value );
				};

				NAC.setRange = function( lowerBoundary, upperBoundary, operand ){
					var range = NAC.range();
					if( !range ){
						range = new Range();
						NAC.range( range );
					}

					return range.setRange(lowerBoundary, upperBoundary, operand);
				};

				NAC.setCondition = function(parameterName, lower, upper, operand, assignValue) {
					var range = NAC.range();
					if( !range ){
						range = new Range();
						NAC.range( range );
					}

					NAC.parameterName(parameterName);
					switch( arguments.length ){
					case 5:
						NAC.property(OSP.Constants.ASSIGN_VALUE, assignValue);
					case 4:
						return range.setRange(lower, upper, operand);
					default:
						return false;
					}
				};

				NAC.checkActivate = function( value ){
					if( NAC.verifyRange(value) == true ){
						if( NAC.assignValue() == false )
							return true;
						else
							return NAC.assignValue();
					}
					else
						return false;
				};

				NAC.assignValue = function( assignValue ){
					return NAC.property.apply( NAC, OSP.Util.addFirstArgument(OSP.Constants.ASSIGN_VALUE, arguments) );
				};

				NAC.clone = function(){
					return new NumericActivateCondition( OSP.Util.toJSON(NAC) );
				};

				NAC.deserialize = function(jsonObject){
					for( var key in jsonObject){
						switch( key ){
						case OSP.Constants.RANGE:
							NAC.range( new Range( jsonObject[key] ) );
							break;
						default:
							NAC.property(key, jsonObject[key]);
						}
					}
				};

				NAC.upgrade = function( oldCondition ){
					for( var key in oldCondition ){
						if( OSP.Util.isEmpty(oldCondition[key]) )	continue;

						switch( key ){
						case 'domain':
							var range = new Range();
							range.upgrade( oldCondition[key] );
							NAC.range( range );
							break;
						default:
							NAC._upgrade( key, oldCondition[key], NAC );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					NAC.deserialize(jsonObject);
			}; // End of NumericActivateCondition
			DS.newNumericActivateCondition = function( jsonObject ){
				switch( arguments.length ){
				case 0:
					return new NumericActivateCondition();
				case 1:
					return new NumericActivateCondition(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newNumericActivateCondition');
					return null;
				}
			};

			var ListItemActivateCondition = function( jsonObject ){
				var LAC = this;
				_ActivateCondition.apply(LAC);
				
				LAC.type(OSP.Constants.LIST);

				LAC.value = function( value ) {
					return LAC.property.apply( LAC, OSP.Util.addFirstArgument(OSP.Constants.LIST_ITEM_VALUE, arguments) );
				};

				LAC.setCondition = function(parameterName, listItemValue, assignValue) {
					switch( arguments.length ){
					case 3:
						LAC.assignValue(assignValue);
					case 2:
						LAC.parameterName(parameterName);
						LAC.value(listItemValue);
						return LAC;
					}

					return false;
				};

				LAC.assignValue = function( value ){
					return LAC.property.apply( LAC, OSP.Util.addFirstArgument(OSP.Constants.ASSIGN_VALUE, arguments) );
				};

				LAC.checkActivate = function(value){
					if(LAC.value() === value){
						if( !LAC.assignValue() || LAC.assignValue() === '')
							return true;
						else
							return LAC.assignValue();
					}
					else	return false;
				};

				LAC.clone = function(){
					return new ListItemActivateCondition( OSP.Util.toJSON(LAC) );
				};

				LAC.upgrade = function( oldCondition ){
					for( var key in oldCondition ){
						if( OSP.Util.isEmpty(oldCondition[key]) )	continue;

						switch( key ){
						case 'list-item-value':
							LAC.value(oldCondition[key]);
							break;
						case 'assign-value':
							LAC.assignValue(oldCondition[key]);
							break;
						default:
							LAC._upgrade( key, oldCondition[key], LAC );
							break;
						}
					}
				};
				
				LAC.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						LAC._deserialize( key, jsonObject[key] );
				};

				if( arguments.length === 1 )
					LAC.deserialize(jsonObject);
			}; // End of ListItemActivateCondition
			
			DS.newListItemActivateCondition = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new ListItemActivateCondition();
					case 1:
						return new ListItemActivateCondition(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newListItemActivateCondition');
						return null;
				}
			};
			var ListItem = function( jsonObject ) {
				var LI = this;
				OSP._MapObject.apply(LI);
				
				LI.text = function( textObject ) {
					return LI.property.apply( LI, OSP.Util.addFirstArgument(OSP.Constants.TEXT, arguments) );
				};

				LI.value = function( value ) {
					return LI.property.apply( LI, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments) );
				};

				LI.localizedText = function(languageId, text) {
					var itemText = LI.text();

					switch( arguments.length ){
					case 1:
						if( !itemText )	return '';
						else	return itemText[languageId];
					case 2:
						if( !itemText ){
							itemText = {};
							LI.text( itemText );
						}
						return itemText[languageId] = text;
					default:
						return false;
					}
				};
				LI.visibleParameters = function(paramNameArray){
					return LI.property.apply(LI, OSP.Util.addFirstArgument(OSP.Constants.VISIBLE_PARAMETER, arguments));
				}
				LI.removeVisibleParameters = function(){
					var isSuccess = false;

					delete LI.visibleParameters_;
					
					isSuccess = true;
					
					return isSuccess;
				}
				LI.removeVisibleParameter = function(name){
					var isSuccess = false;
					
					var visibleParameters = LI.visibleParameters();
					if(visibleParameters){
						visibleParameters = visibleParameters.filter(eachParameter => eachParameter.name != name);
						LI.visibleParameters(visibleParameters);
					}
					
					isSuccess = true;
					
					return isSuccess;
				}
				LI.visibleParameterNames = function(){
					var listItem = this;
					var names = new Array();
					if(listItem.visibleParameters()){
						names = listItem.visibleParameters().map(visibleParameter => visibleParameter.name);
					}
					return names;
				}
				LI.requiredParameterNames = function(){
					var listItem = this;
					var names = new Array();
					if(listItem.visibleParameters()){
						listItem.visibleParameters().forEach(function(visibleParameter){
							if(typeof(visibleParameter.isRequried) === 'boolean'){
								if(visibleParameter.isRequried){
									names.push(visibleParameter.name);
								}
							}else{
								if(visibleParameter.isRequried == 'true'){
									names.push(visibleParameter.name);
								}
							}
							
						});
					}
					return names;
				}
				
				LI.removeItemText = function(languageId) {
					var itemText = LI.text();
					if( !itemText )	return true;

					return delete itemText[languageId];
				};

				LI.textXml = function(availableLanguageIds, defalutLanguageId) {
					var itemText = LI.text();
					if( !itemText )	itemText = {};
					return OSP.Util.toLocalizedXml( itemText, availableLanguageIds, defalutLanguageId );
				};

				LI.activateConditions = function( conditions ) {
					return LI.property.apply(LI, OSP.Util.addFirstArgument(OSP.Constants.ACTIVATE_CONDITIONS, arguments));
				};

				LI.addActivateCondition = function( conditionOrPrameterName, minOrListItemValue, maxOrAssignValue, operand, assignValue ) {
					var conditions = LI.activateConditions();
					if( !conditions ){
						conditions = [];
						LI.activateConditions(conditions);
					}

					var condition;
					switch( arguments.length ){
					case 1:
						condition = conditionOrPrameterName;
						break;
					case 2:
						condition = new ListItemActivateCondition();
						condition.setCondition( conditionOrPrameterName, minOrListItemValue );
						break;
					case 3:
						condition = new ListItemActivateCondition();
						condition.setCondition(conditionOrPrameterName, minOrListItemValue, maxOrAssignValue );
						break;
					case 4:
						condition = new NumericActivateCondition();
						condition.setCondition( conditionOrPrameterName, minOrListItemValue, maxOrAssignValue, operand );
						break;
					case 5:
						condition = new NumericActivateCondition();
						condition.setCondition( conditionOrPrameterName, minOrListItemValue, maxOrAssignValue, operand, assignValue );
						break;
					default:
						return conditions;
					}

					conditions.push( condition );
					return conditions;
				};

				LI.removeActivateCondition = function( parameterName, minOrListItemValue, max ) {
					var conditions = LI.activateConditions();
					if( !conditions )	return true;

					switch( arguments.length ){
					case 1:
						for( var index in conditions ){
							var condition = conditions[index];
							if( condition.parameterName() === parameterName ){
								conditions.splice( index, 1);
								LI.removeActivateCondition(parameterName);
								break;
							}
						}
						return conditions;
					case 2:
						for(var index in conditions ){
							var condition = conditions[index];
							if( parameterName === condition.parameterName() &&
								minOrListItemValue === condition.value() ){
								conditions.splice( index, 1 );
								break;
							}
						}
						return conditions;
					case 3:
						for(var index in conditions ){
							var condition = conditions[index];
							if( parameterName === condition.parameterName() &&
								minOrListItemValue === condition.rangeLowerBoundary() &&
								max === condition.rangeUpperBoundary() ){
								conditions.splice( index, 1 );
								break;
							}
						}
						return conditions;
					default:
						return conditions;
					}
				};

				LI.checkActivate = function(parameterName, value){
					var conditions = LI.activateConditions();
					if( !conditions )	return true;

					var activate = false;
					for( var index in conditions ){
						var condition = conditions[index];
						if( condition.parameterName() === parameterName ){
							activate = condition.checkActivate( value );
							if( activate )	return activate;
						}
					}

					return activate;
				};

				LI.clone = function(){
					return new ListItem( OSP.Util.toJSON(LI) );
				};

				LI.deserialize = function( jsonObject ){
					for( var key in jsonObject ){
						if( key === OSP.Constants.ACTIVATE_CONDITIONS ){
							var jsonConditions = jsonObject[key];
							for( var index in jsonConditions ){
								var jsonCondition = jsonConditions[index];
								var condition;
								if( jsonCondition[OSP.Constants.TYPE] === OSP.Constants.LIST)
									condition = new ListItemActivateCondition( jsonCondition );
								else
									condition = new NumericActivateCondition( jsonCondition );

								LI.addActivateCondition(condition);
							}
						}
						else
							LI.property( key, jsonObject[key] );
					}
				};

				LI.upgrade = function( oldListItem ){
					for( var key in oldListItem ){
						if( OSP.Util.isEmpty(oldListItem[key]) )	continue;

						switch( key ){
						case 'value':
							LI.value(oldListItem[key]);
							break;
						case 'localized-text-map':
							LI.text(oldListItem[key]['map']);
							break;
						case 'activate-condition-container':
							var oldConditions = oldListItem[key]['container'];
							var newConditions = [];
							for( var index in oldConditions ){
								var oldCondition = oldConditions[index];
								if( OSP.Util.isEmpty(oldCondition) )	continue;
								var newCondition;
								switch( oldCondition['type'] ){
								case 'numeric':
									newCondition = new NumericActivateCondition();
									break;
								case 'list':
									newCondition = new ListItemActivateCondition();
									break;
								default:
									alert( 'Unknown condition: '+oldCondition['type']);
									return;
								}
								newCondition.upgrade( oldCondition['type'] );
								newConditions.push(newCondition);
							}

							LI.activateConditions(newConditions);
							break;
						}
					}
				};

				if( arguments.length === 1 )
					LI.deserialize(jsonObject);
			}; // End of ListItem
			DS.newListItem = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new ListItem();
					case 1:
						return new ListItem(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newListItem');
						return null;
				}
			};

			var Sweep = function( jsonObject ){
				var S = this;
				OSP._MapObject.apply(S);

				S.sliceCount = function( count ){
					return S.property.apply( S, OSP.Util.addFirstArgument(OSP.Constants.SLICE_COUNT, arguments) );
				};

				S.sliceValue = function( value ){
					return S.property.apply( S, OSP.Util.addFirstArgument(OSP.Constants.SLICE_VALUE, arguments) );
				};

				S.maxSlice = function( maxSlice ){
					return S.property.apply( S, OSP.Util.addFirstArgument(OSP.Constants.SLICE_MAX, arguments) );
				};

				S.range = function( range ){
					return S.property.apply( S, OSP.Util.addFirstArgument(OSP.Constants.RANGE, arguments) );
				};

				S.rangeLowerBoundary = function( boundary){
					var range = S.range();
					if( !range )	return false;

					return range.lowerBoundary.apply( range, arguments );
				};

				S.rangeUpperBoundary = function( boundary ){
					var range = S.range();
					if( !range )	return false;
					else	return range.upperBoundary.apply(range, arguments);
				};

				S.rangeOperand = function( operand ){
					var range = S.range();
					if( !range )	return false;

					return range.operand.apply( range, operand );
				};

				S.rangeCheckLowerBoundary = function(){
					var range = S.range();
					if( !range )	return false;

					return range.checkLowerBoundary();
				};

				S.rangeCheckUpperBoundary = function(){
					var range = S.range();
					if( !range )	return false;

					return range.checkUpperBoundary();
				};

				S.rangeIncludeBoundary = function( whichEnd, flag ){
					var range = S.range();
					if( !range )	return false;

					return range.includeBoundary( whichEnd, flag );
				};

				S.verifyRange = function( value ){
					var range = S.range();
					if( !range )	return true;
					return range.verify( value );
				};

				S.setRange = function( lowerBoundary, upperBoundary, operand ){
					var range = S.range();
					if( !range ){
						range = new Range();
						S.range( range );
					}

					return range.setRange.apply(range, arguments);
				};

				S.getSlicedValues = function(){
					var strLower = S.rangeLowerBoundary();
					var strUpper = S.rangeUpperBoundary();
					if( strLower === false || strUpper === false ){
						return false;
					}

					var lower = Number( strLower );
					var upper = Number( strUpper );

					var values = [];
					var isExponential = OSP.Util.isExponetioal(''+strLower) || OSP.Util.isExponetioal(''+strUpper);
					var includeLower = S.rangeCheckLowerBoundary();
					var includeUpper = S.rangeCheckUpperBoundary();
					var sliceValue;
					var method = S.sweepMethod();

					var index =0;
					var sliceCount;

					if( method === OSP.Enumeration.SweepMethod.BY_SLICE ){
							sliceCount = Number(S.sliceCount());
							//console.log('sliceCount: '+sliceCount);
					}
					else {
							sliceValue = Number(S.sliceValue());
							sliceCount =  Math.floor(OSP.Util.safeFloatSubtract(upper, lower)/sliceValue);
							//console.log('sliceCount: '+sliceCount);
					}

					//console.log("included: "+includeLower+','+includeUpper);
					if(includeLower == true  && includeUpper == true){
						values[index] = OSP.Util.toFloatString(lower, isExponential);
						//console.log('sliced value['+index+']:'+lower);
						++index;
						--sliceCount;
						//console.log('sliced value['+sliceCount+']:'+upper);
					}
					else if(includeLower == true){
						values[index] = OSP.Util.toFloatString(lower, isExponential);
						++index;
					}
					else{
						++sliceCount;
					}

					if( method == OSP.Enumeration.SweepMethod.BY_SLICE )
						sliceValue = OSP.Util.safeFloatSubtract(upper, lower) / sliceCount;

					var value;
					(index == 0) ? ( value = OSP.Util.safeFloatSum(lower, sliceValue)) :
											  (value = OSP.Util.safeFloatSum(values[index-1], sliceValue));
					while(value < upper){
						values[index] = OSP.Util.toFloatString(value, isExponential);
//							alert('sliced value['+index+']:'+values[index]);
						//console.log('sliced value['+index+']:'+values[index]);
						++index;
						value = OSP.Util.safeFloatSum(value, sliceValue);
					}
					if(includeUpper == true){
						values[index] = OSP.Util.toFloatString(upper, isExponential);
						//console.log('sliced value['+(sliceCount-1)+']:'+upper);
					}

					return values;
				};

				S.sweepMethod = function( method ){
					return S.property.apply( S, OSP.Util.addFirstArgument(OSP.Constants.SWEEP_METHOD, arguments) );
				};
				
				S.clone = function(){
					return new Sweep( OSP.Util.toJSON(S) );
				};

				S.deserialize = function( jsonObject ){
					for( var key in jsonObject){
						switch(key){
						case OSP.Constants.RANGE:
							S.range( new Range( jsonObject[key] ) );
							break;
						default:
							S.property( key, jsonObject[key] );
						}
					}
				};

				S.upgrade = function( oldSweep ){
					for( var key in oldSweep ){
						if( OSP.Util.isEmpty(oldSweep[key]) )	continue;

						switch( key ){
						case 'slice':
							S.sliceCount( oldSweep[key] );
							break;
						case 'slice-value':
							S.sliceValue( oldSweep[key] );
							break;
						case 'slice-max':
							S.maxSlice( oldSweep[key] );
							break;
						case 'sweep-by-slice':
							if( oldSweep[key] == true )
								S.sweepMethod( OSP.Enumeration.SweepMethod.BY_SLICE );
							else
								S.sweepMethod( OSP.Enumeration.SweepMethod.BY_VALUE );
							break;
						case 'domain':
							var range = new Range();
							range.upgrade( oldSweep[key] );
							S.range( range );
							break;
						default:
							alert( 'Unknown sweep attribute: '+ key);
							return;
						}
					}
				};

				if( arguments.length === 1 )
					S.deserialize(jsonObject);
			}; // End of Sweep
			DS.newSweep = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new Sweep();
					case 1:
						return new Sweep(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newSweep');
						return null;
				}
			};

			var _Parameter = function( jsonObject ) {
				var _Param = this;
				OSP._MapObject.apply(_Param);

				_Param.name = function( name ) {
					return _Param.property.apply( _Param, OSP.Util.addFirstArgument(OSP.Constants.NAME, arguments) );
				};

				_Param.type = function( type ){
					return _Param.property.apply( _Param, OSP.Util.addFirstArgument(OSP.Constants.TYPE, arguments) );
				};
				_Param.required = function( isRequired ){
					return _Param.property.apply( _Param, OSP.Util.addFirstArgument(OSP.Constants.REQUIRED, arguments) );
				}
				_Param.nameText = function( nameText ) {
					return _Param.property.apply( _Param, OSP.Util.addFirstArgument(OSP.Constants.NAME_TEXT, arguments) );
				};

				_Param.localizedNameText = function( languageId, text ) {
					var nameText = _Param.nameText();

					switch( arguments.length ){
					case 1:
						if( !nameText )	return '';
						return nameText[languageId];
					case 2:
						if( !nameText ){
							nameText = {};
							_Param.nameText( nameText );
						}
						return nameText[languageId] = text;
					default:
						return '';
					}
				};

				_Param.removeNameText = function( languageId ) {
					return _Param.removeProperty( OSP.Constants.NAME_TEXT, languageId );
				};

				_Param.localizedNameTextXML = function(availableLanguageIds, defaultLanguageId) {
					var nameText = _Param.nameText();
					if( !nameText ){
						nameText = {};
					}

					return OSP.Util.toLocalizedXml(nameText, availableLanguageIds, defaultLanguageId);
				};

				_Param.description = function( description ) {
					return _Param.property.apply(_Param, OSP.Util.addFirstArgument(OSP.Constants.DESCRIPTION, arguments));
				};

				_Param.localizedDescription = function( languageId, text ) {
					var description = _Param.description();
					
					switch( arguments.length ){
					case 1:
						if( !description )	return '';
						else	return description[languageId];
					case 2:
						if( !description ){
							description = {};
							_Param.description( description );
						}
						return description[languageId] = text;
					default:
						return '';
					}
				};

				_Param.removeLocalizedDescription = function( languageId ) {
					return _Param.removeProperty(OSP.Constants.NAME_TEXT, languageId);
				};

				_Param.localizedDescriptionXML = function(availableLanguageIds, defaultLanguageId) {
					var description = _Param.description();
					if( !description )	description = {};

					return OSP.Util.toLocalizedXml(description, availableLanguageIds, defaultLanguageId);
				};

				_Param.activateConditions = function( conditions ) {
					return _Param.property.apply(_Param, OSP.Util.addFirstArgument(OSP.Constants.ACTIVATE_CONDITIONS, arguments));
				};

				_Param.addActivateCondition = function( conditionOrPrameterName, minOrListItemValue, maxOrAssignValue, operand, assignValue ) {
					var conditions = _Param.activateConditions();
					if( !conditions ){
						conditions = [];
						_Param.activateConditions(conditions);
					}

					var condition;
					switch( arguments.length ){
						case 1:
							condition = conditionOrPrameterName;
							break;
						case 2:
							condition = new ListItemActivateCondition();
							condition.setCondition( conditionOrPrameterName, minOrListItemValue );
							break;
						case 3:
							condition = new ListItemActivateCondition();
							condition.setCondition(conditionOrPrameterName, minOrListItemValue, maxOrAssignValue );
							break;
						case 4:
							condition = new NumericActivateCondition();
							condition.setCondition( conditionOrPrameterName, minOrListItemValue, maxOrAssignValue, operand );
							break;
						case 5:
							condition = new NumericActivateCondition();
							condition.setCondition( conditionOrPrameterName, minOrListItemValue, maxOrAssignValue, operand, assignValue );
							break;
						default:
							return conditions;
					}

					conditions.push( condition );
					return conditions;
				};

				_Param.cleanActivateConditions = function(){
					return _Param.removeProperty(OSP.Constants.ACTIVATE_CONDITIONS);
				};

				_Param.removeActivateCondition = function( parameterName, minOrListItemValue, max ) {
					var conditions = _Param.activateConditions();
					if( !conditions )	return true;

					switch( arguments.length ){
					case 1:
						for( var index in conditions ){
							var condition = conditions[index];
							if( condition.parameterName() === parameterName ){
								conditions.splice( index, 1);
								_Param.removeActivateCondition(parameterName);
								break;
							}
						}
						return conditions;
					case 2:
						for(var index in conditions ){
							var condition = conditions[index];
							if( parameterName === condition.parameterName() &&
								minOrListItemValue === condition.value() ){
								conditions.splice( index, 1 );
								break;
							}
						}
						return conditions;
					case 3:
						for(var index in conditions ){
							var condition = conditions[index];
							if( parameterName === condition.parameterName() &&
								minOrListItemValue === condition.rangeLowerBoundary() &&
								max === condition.rangeUpperBoundary() ){
								conditions.splice( index, 1 );
								break;
							}
						}
						return conditions;
					default:
						return conditions;
					}
				};

				_Param.checkActivate = function(parameterName, value){
					var conditions = _Param.activateConditions();
					if( !conditions )	return true;

					var activate = false;
					for( var index in conditions ){
						var condition = conditions[index];
						if( condition.parameterName() === parameterName ){
							activate = condition.checkActivate( value );
							if( activate )	return activate;
						}
					}

					return activate;
				};

				_Param.order = function( order ) {
					return _Param.property.apply(_Param, OSP.Util.addFirstArgument(OSP.Constants.ORDER, arguments));
				};

				_Param.active = function( active ){
					return _Param.property.apply(_Param, OSP.Util.addFirstArgument(OSP.Constants.ACTIVE, arguments));
				};

				_Param.disabled = function( disabled ){
					return _Param.property.apply(_Param, OSP.Util.addFirstArgument(OSP.Constants.DISABLED, arguments));
				};

				_Param.verifyName = function(name){
					if(/[a-zA-Z\\_]/.test(name.charAt(0)) == false) return false;
					if(/[^a-zA-Z0-9\\_]/.test(name))	return false;
					return true;
				};

				_Param._deserialize = function( key, jsonObject ){
					switch(key){
					case OSP.Constants.ACTIVATE_CONDITIONS:
						var jsonConditions = jsonObject;

						for( var index in jsonConditions ){
							var jsonCondition = jsonConditions[index];
							var condition;
							if( jsonCondition[OSP.Constants.TYPE] === OSP.Constants.LIST )
//								condition = _Param.newListItemActivateCondition( jsonCondition );
								condition = new ListItemActivateCondition(jsonCondition)
							else
//								condition = _Param.newNumericActivateCondition( jsonCondition );
								condition = new NumericActivateCondition(jsonCondition)
							
							_Param.addActivateCondition(condition);
						}
						break;
					default:
						_Param.property( key, jsonObject );
					}
				};

				_Param._upgrade = function( key, value, newParameter ){
					if( OSP.Util.isEmpty(value) )	return;

					switch( key ){
					case 'name':
						newParameter.name( value );
						break;
					case 'type':
						if( value === 'control_active' || value === 'control_associate')
							value = 'list';
						newParameter.type( value );
						break;
					case 'name-text-map':
						newParameter.nameText( value['map'] );
						break;
					case 'description-map':
						newParameter.description( value['map'] );
						break;
					case 'order':
						newParameter.order( value );
						break;
					case 'active':
						newParameter.active( value );
						break;
					case 'disable':
						newParameter.disabled( value );
						break;
					case 'activate-condition-container':
						if( OSP.Util.isEmpty(value) )	break;

						var oldConditions = value['container'];
						if( oldConditions.length === 0 )	break;
						var newConditions = [];

						for( var index in oldConditions ){
							var oldCondition = oldConditions[index];
							if( OSP.Util.isEmpty(oldCondition) )	continue;

							var newCondition;
							switch( oldCondition['type'] ){
							case 'numeric':
								newCondition = new NumericActivateCondition();
								break;
							case 'list':
								newCondition = new ListItemActivateCondition();
								break;
							default:
								alert( 'Unknown condition type: '+oldCondition['type'] );
								return;
							}
							newCondition.upgrade( oldCondition );
							newConditions.push( newCondition );
						}

						newParameter.activateConditions(newConditions);
						break;
					}
				}
			}; // End of _Parameter

			var NumericParameter = function( jsonObject ){
				var NP = this;
				_Parameter.apply(NP);
				NP.type( OSP.Constants.NUMERIC);
				NP.active(true);

				NP.unit = function( unit ){
					return NP.property.apply(NP, OSP.Util.addFirstArgument(OSP.Constants.UNIT, arguments));
				};

				NP.value = function( value ) {
					if( arguments.length === 1 ){
						if( NP.verifyRange( value ) === false )
							return false;
					}
					return NP.property.apply(NP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				NP.range = function( range ) {
					return NP.property.apply(NP, OSP.Util.addFirstArgument(OSP.Constants.RANGE, arguments));
				};

				NP.rangeLowerBoundary = function( boundary ){
					var range = NP.range();
					if( !range )	return false;

					return range.lowerBoundary.apply( range, arguments );
				};

				NP.rangeUpperBoundary = function( boundary ){
					var range = NP.range();
					if( !range )	return false;

					return range.upperBoundary.apply( range, arguments );
				};

				NP.rangeOperand = function( operand ){
					var range = NP.range();
					if( !range )	return false;

					return range.operand.apply( range, operand );
				};

				NP.rangeCheckLowerBoundary = function(){
					var range = NP.range();
					if( !range )	return false;

					return range.checkLowerBoundary();
				};

				NP.rangeCheckUpperBoundary = function(){
					var range = NP.range();
					if( !range )	return false;

					return range.checkUpperBoundary();
				};

				NP.rangeIncludeBoundary = function( whichEnd, flag ){
					var range = NP.range();
					if( !range )	return false;

					return range.includeBoundary( whichEnd, flag );
				};

				NP.verifyRange = function( value ){
					var range = NP.range();
					if( !range )	return true;
					return range.verify( value );
				};

				NP.setRange = function( lowerBoundary, upperBoundary, operand ){
					var range = NP.range();
					if( !range ){
						range = new Range();
						NP.range( range );
					}

					return range.setRange.apply( range, arguments );
				};

				NP.sweep = function( sweep ){
					return NP.property.apply(NP, OSP.Util.addFirstArgument(OSP.Constants.SWEEP, arguments));
				};

				NP.sweepable = function( flag ){
					return NP.property.apply(NP, OSP.Util.addFirstArgument(OSP.Constants.SWEEPABLE, arguments));
				};

				NP.maxSweepSlice = function( max ){
					var sweep = NP.sweep();
					if( !sweep ){
						sweep = new Sweep();
						NP.sweep(sweep);
					}

					return sweep.maxSlice.apply( sweep, arguments );
				};

				NP.sweepMethod = function( method ){
					var sweep = NP.sweep();
					if( !sweep ){
						sweep = new Sweep();
						NP.sweep(sweep);
					}

					return sweep.sweepMethod.apply( sweep, arguments );
				};

				NP.sweepCount = function( count ){
					return NP.property.apply(NP, OSP.Util.addFirstArgument(OSP.Constants.SWEEP_COUNT, arguments));
				};

				NP.sweepSliceCount = function( count ){
					var sweep = NP.sweep();
					if( !sweep ){
						sweep = new Sweep();
						NP.sweep(sweep);
					}

					return sweep.sliceCount.apply( sweep, arguments );
				};

				NP.sweepSliceValue = function( value ){
					var sweep = NP.sweep();
					if( !sweep ){
						sweep = new Sweep();
						NP.sweep(sweep);
					}

					return sweep.sliceValue.apply( sweep, arguments );
				};

				NP.getSweepSlicedValues = function(){
					var sweep = NP.sweep();
					if( !sweep ){
						console.log('Sweep is not defined: '+NP.name());
						return false;
					}

					return sweep.getSlicedValues();
				};

				NP.sweepRange = function( range ){
					var sweep = NP.sweep();
					if( !sweep ){
						sweep = new Sweep();
						NP.sweep(sweep);
					}

					return sweep.range.apply( sweep, arguments );
				};

				NP.sweepRangeLowerBoundary = function( boundary ) {
					var sweep = NP.sweep();
					if( !sweep ){
						sweep = new Sweep();
						NP.sweep(sweep);
					}

					return sweep.rangeLowerBoundary.apply(sweep, arguments);
				};

				NP.sweepRangeUpperBoundary = function( boundary ) {
					var sweep = NP.sweep();
					if( !sweep ){
						sweep = new Sweep();
						NP.sweep(sweep);
					}

					return sweep.rangeUpperBoundary.apply(sweep, arguments);
				};

				NP.sweepRangeOperand = function( operand ) {
					var sweep = NP.sweep();
					if( !sweep ){
						sweep = new Sweep();
						NP.sweep(sweep);
					}

					return sweep.rangeOperand.apply(sweep, arguments);
				};

				NP.sweepRangeCheckLowerBoundary = function(){
					var sweep = NP.sweep();
					if( !sweep )	return false;

					return sweep.rangeCheckLowerBoundary();
				};

				NP.sweepRangeCheckUpperBoundary = function(){
					var sweep = NP.sweep();
					if( !sweep )	return false;

					return sweep.rangeCheckUpperBoundary();
				};

				NP.sweepRangeIncludeBoundary = function( whichEnd, flag ){
					var sweep = NP.sweep();
					if( !sweep )	return false;

					return sweep.rangeIncludeBoundary( whichEnd, flag );
				};

				NP.setSweepRange = function(lowerBoundary, upperBoundary, operand) {
					if( Number(lowerBoundary) > Number(upperBoundary) )
						return false;
					if( !NP.verifyRange(lowerBoundary) ||
						 !NP.verifyRange(upperBoundary))		return false;


					var sweep = NP.sweep();
					if( !sweep ){
						sweep = new Sweep();
						NP.sweep( sweep );
					}

					return sweep.setRange.apply( sweep, arguments );
				};

				NP.verifySweepRange = function( value ){
					var sweep = NP.getSweep();
					if( !sweep )	return false;

					return sweep.verifyRange(value);
				};

				NP.removeSweep = function(){
					NP.removeProperty(OSP.Constants.SWEEPED);
					NP.removeProperty(OSP.Constants.SWEEP);
				};

				NP.sweeped = function( flag ){
					return NP.property.apply(NP, OSP.Util.addFirstArgument(OSP.Constants.SWEEPED, arguments));
				};
				
				NP.clone = function(){
					return new NumericParameter( OSP.Util.toJSON(NP) );
				};

				NP.deserialize = function (jsonObject){
					for( var key in jsonObject){
						switch(key){
						case OSP.Constants.RANGE:
							NP.range(new Range(jsonObject[key]) );
							break;
						case OSP.Constants.SWEEP:
							NP.sweep( new Sweep( jsonObject[key] ) );
							break;
						default:
							NP._deserialize(key, jsonObject[key]);
						}
					}
				};

				NP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )continue;

						switch( key ){
							case 'unit':
								NP.unit(variable[key]);
								break;
							case 'value':
								NP.value(variable[key]);
								break;
							case 'sweeped':
								NP.sweeped(variable[key]);
								break;
							case 'sweepable':
								NP.sweepable(variable[key]);
								break;
							case 'value-domain':
								var range = new Range();
								range.upgrade( variable[key] );
								NP.range( range );
								break;
							case 'sweep':
								var sweep = new Sweep();
								sweep.upgrade( variable[key] );
								NP.sweep(sweep);
								break;
							default:
								NP._upgrade( key, variable[key], NP );
								break;
						}
					}
				};

				if( arguments.length === 1 )
					NP.deserialize(jsonObject);
			}; // End of NumericParameter
			DS.newNumericParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new NumericParameter();
					case 1:
						return new NumericParameter(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newNumericParameter');
						return null;
				}
			};

			var ListParameter = function( jsonObject ){
				var LP = this;
				_Parameter.apply(LP);
				LP.type( OSP.Constants.LIST);
				LP.active(true);

				LP.value = function( value ) {
					return LP.property.apply(LP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};
				LP.listItems = function( list ) {
					return LP.property.apply(LP, OSP.Util.addFirstArgument(OSP.Constants.LIST_ITEMS, arguments));
				};

				LP.listItem = function(itemValue, listItem) {
					var listItems = LP.listItems();

					switch( arguments.length ){
					case 1:
						if( !listItems )	return false;
						return listItems[itemValue];
					case 2:
						if( !listItems ){
							listItems = {};
							LP.listItems( listItems );
						}
						return listItems[itemValue] = listItem;
					default:
						return false;
					}
				};
				
				LP.addListItem = function( itemValue ){
					var listItems = LP.listItems();
					if( !listItems ){
						listItems = {};
						LP.listItems( listItems );
					}

					var listItem = new ListItem();
					listItem.value( itemValue );
					//console.log( 'list: '+JSON.stringify(listItems,null,4));
					//console.log( 'itemValue: '+itemValue);
					listItems[itemValue] = listItem;
					return listItem;
				};

				LP.localizedListItemText = function(itemValue, languageId, text){
					var list = LP.listItems();
					if( !list )	return false;

					var listItem = list[itemValue];
					switch( arguments.length ){
					case 1:
						return listItem;
					case 2:
						return listItem.localizedText( languageId );
					case 3:
						return listItem.localizedText( languageId, text );
					default:
						return false;
					}
				};

				LP.localizedListItems = function( languageId ){
					var list = LP.listItems();
					if( !list )	return false;
					//console.log('List: '+ JSON.stringify(list,null,4));
					var localizedListItems = {};
					for( var key in list ){
						var listItem = list[key];
						localizedListItems[key] = listItem.localizedText(languageId);
					}

					return localizedListItems;
				};

				LP.listXml = function( availablLanguageIds, defaultLanguageId ){
					var list = LP.listItems();
					if( !list )	return false;

					var listXml = [];
					for( var key in list ){
						var listItem = list[key];
						listXml.push( listItem.textXml(availablLanguageIds, defaultLanguageId) );
					}

					return listXml;
				};
				LP.removeAllItems = function(){
					return delete LP.listItems_;
				}
				LP.removeListItem = function(itemValue) {
					var list = LP.listItem(itemValue);
					if( !list )	return true;
					return delete LP.listItems_[itemValue];
				};

				LP.clone = function(){
					return new ListParameter( OSP.Util.toJSON(LP) );
				};

				LP.deserialize = function ( jsonObject ){
					for( var key in jsonObject ){
						switch( key ){
						case OSP.Constants.LIST_ITEMS:
							var jsonList = jsonObject[key];
							for( var index in jsonList ){
								var listItem = new ListItem( jsonList[index] );
								LP.listItem( listItem.value(), listItem );
							}
							break;
						default:
							LP._deserialize( key, jsonObject[key] );
						}
					}
				};
				LP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							LP.value(variable[key]);
							break;
						case 'list-map':
							var oldListItems = variable[key]['map'];
							if( oldListItems.length === 0 )	break;

							for( var listItemKey in oldListItems ){
								var oldListItem = oldListItems[listItemKey];
								var newListItem = LP.addListItem(listItemKey);
								newListItem.upgrade( oldListItem );
							}
							break;
						default:
							LP._upgrade( key, variable[key], LP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					LP.deserialize(jsonObject);
			}; // End of ListParameter
			DS.newListParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new ListParameter();
					case 1:
						return new ListParameter(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newListParameter');
						return null;
				}
			};

			var StringParameter = function( jsonObject ){
				var SP = this;
				_Parameter.apply(SP);
				SP.type( OSP.Constants.STRING);
				SP.active(true);

				SP.value = function( value ) {
					return SP.property.apply(SP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				SP.clone = function(){
					return new StringParameter( OSP.Util.toJSON(SP) );
				};

				SP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						SP._deserialize( key, jsonObject[key] );
				};
				
				SP.validationRule = function(value){
					return SP.property.apply(SP, OSP.Util.addFirstArgument(OSP.Constants.VALIDATION_RULE, arguments));
				}
				
				SP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							SP.value(variable[key]);
							break;
						default:
							SP._upgrade( key, variable[key], SP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					SP.deserialize(jsonObject);
			}; // End of StringParameter
			DS.newStringParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new StringParameter();
					case 1:
						return new StringParameter(jsonObject);
					default:
						console.log( 'Arguments mismatch: newStringParameter');
						return null;
				}
			};
			
			var BooleanParameter = function( jsonObject ){
				var BP = this;
				_Parameter.apply(BP);
				BP.type( OSP.Constants.BOOLEAN);
				BP.active(true);

				BP.value = function( value ) {
					return BP.property.apply(BP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				BP.clone = function(){
					return new BooleanParameter( OSP.Util.toJSON(BP) );
				};

				BP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						BP._deserialize( key, jsonObject[key] );
				};

				BP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							BP.value(variable[key]);
							break;
						default:
							BP._upgrade( key, variable[key], BP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					BP.deserialize(jsonObject);
			}; // End of StringParameter
			DS.newBooleanParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new BooleanParameter();
					case 1:
						return new BooleanParameter(jsonObject);
					default:
						console.log( 'Arguments mismatch: newBooleanParameter');
						return null;
				}
			};
			
			var FileParameter = function( jsonObject ){
				var FP = this;
				
				_Parameter.apply(FP);
				FP.type( OSP.Constants.FILE_PARAMETER);
				FP.active(true);
				
				FP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						FP._deserialize( key, jsonObject[key] );
				};
				
				FP.fileId = function( value ) {
					return FP.property.apply(FP, OSP.Util.addFirstArgument("fileId_", arguments));
				};
				FP.dataTypeName = function( value ) {
					return FP.property.apply(FP, OSP.Util.addFirstArgument(OSP.Constants.DATATYPE_NAME, arguments));
				};
				FP.dataTypeVersion = function( value ) {
					return FP.property.apply(FP, OSP.Util.addFirstArgument(OSP.Constants.DATATYPE_VERSION, arguments));
				};
				if( arguments.length === 1 )
					FP.deserialize(jsonObject);
			}
			DS.newFileParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new FileParameter();
					case 1:
						return new FileParameter(jsonObject);
					default:
						console.log( 'Arguments mismatch: newFileParameter');
						return null;
				}
			};
			
			// newMatrixParameter, MatrixParameter
			var MatrixParameter = function( jsonObject ){
				var MP = this;
				_Parameter.apply(MP);
				MP.type( OSP.Constants.MATRIX);
				MP.active(true);

				MP.value = function( value ) {
					return MP.property.apply(MP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				MP.dimension = function( dimension ) {
					return MP.property.apply(MP, OSP.Util.addFirstArgument(OSP.Constants.DIMENSION, arguments));
				};

				MP.clone = function(){
					return new MatrixParameter( OSP.Util.toJSON(MP) );
				};

				MP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						MP._deserialize( key, jsonObject[key] );
				};
				
				MP.matrixValue = function( value ) {
					return MP.property.apply(MP, OSP.Util.addFirstArgument('matrixValue_', arguments));
				};

				MP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							MP.value(variable[key]);
							break;
						case 'dimension':
							MP.dimension(variable[key]);
							break;
						default:
							MP._upgrade( key, variable[key], MP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					MP.deserialize(jsonObject);
			}; // End of VectorParameter
			DS.newMatrixParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new MatrixParameter();
					case 1:
						return new MatrixParameter(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newMatrixParameter');
						return null;
				}
			};
			
			
			var VectorParameter = function( jsonObject ){
				var VP = this;
				_Parameter.apply(VP);
				VP.type( OSP.Constants.VECTOR);
				VP.active(true);

				VP.value = function( value ) {
					return VP.property.apply(VP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				VP.getValueString = function(leftBrace, rightBrace, delimiter, space){
					if(delimiter !== ' ')	delimiter += space;
					var string = JSON.stringify(VP.value());
					string = string.replace('[', leftBrace).replace(']', rightBrace).replace(/,/g, delimiter).replace(/\"/gi,"");
					return string;
				};

				VP.dimension = function( dimension ) {
					return VP.property.apply(VP, OSP.Util.addFirstArgument(OSP.Constants.DIMENSION, arguments));
				};

				VP.clone = function(){
					return new VectorParameter( OSP.Util.toJSON(VP) );
				};

				VP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						VP._deserialize( key, jsonObject[key] );
				};
				
                VP.stringDeserialize = function( strVector ){
                    var form = strVector.replace(/\[|\]|,/g, '');
                    var vector = [];
                    for( var index in form ){
                        var element = form[index];
                        if( element ){
                            form.push(element);
                        }
                    }
                    
                    VF.value( form );
                };

				VP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							VP.value(variable[key]);
							break;
						case 'dimension':
							VP.dimension(variable[key]);
							break;
						default:
							VP._upgrade( key, variable[key], VP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					VP.deserialize(jsonObject);
			}; // End of VectorParameter
			DS.newVectorParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new VectorParameter();
					case 1:
						return new VectorParameter(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newVectorParameter');
						return null;
				}
			};

			var CommentParameter = function( jsonObject ){
				var CP = this;
				_Parameter.apply(CP);
				CP.type( OSP.Constants.COMMENT);
				CP.active(true);

				CP.value = function( value ) {
					return CP.property.apply(CP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				CP.clone = function(){
					return new CP( OSP.Util.toJSON(CP) );
				};

				CP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						CP._deserialize( key, jsonObject[key] );
				};

				CP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							CP.value(variable[key]);
							break;
						default:
							CP._upgrade( key, variable[key], CP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					CP.deserialize(jsonObject);
			}; // End of CommentParameter
			DS.newCommentParameter = function(jsonObject){
				switch( arguments.length ){
					case 0:
						return new CommentParameter();
					case 1:
						return new CommentParameter(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newCommentParameter');
						return null;
				}
			};
			
			var MultiStringParameter = function( jsonObject ){
				var MSP = this;
				_Parameter.apply(MSP);
				MSP.type( OSP.Constants.MULTI_STRING);
				MSP.active(true);

				MSP.value = function( value ) {
					return MSP.property.apply(MSP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				MSP.clone = function(){
					return new MultiStringParameter( OSP.Util.toJSON(MSP) );
				};

				MSP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						MSP._deserialize( key, jsonObject[key] );
				};

				MSP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							MSP.value(variable[key]);
							break;
						default:
							MSP._upgrade( key, variable[key], MSP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					MSP.deserialize(jsonObject);
			}; // End of MultiStringParameter
			DS.newMultiStringParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new MultiStringParameter();
					case 1:
						return new MultiStringParameter(jsonObject);
					default:
						console.log( 'Arguments mismatch: newMultiStringParameter');
						return null;
				}
			};
			// date parameter creation function is start. 
			var DateParameter = function(jsonObject){
				var DP = this;
				
				_Parameter.apply(DP);
				
				DP.type( OSP.Constants.DATE);
				DP.active(true);

				DP.value = function( value ) {
					return DP.property.apply(DP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};
				
				DP.format = function(value){
					return DP.property.apply(DP, OSP.Util.addFirstArgument(OSP.Constants.FORMAT, arguments));
				}
				
				DP.clone = function(){
					return new DateParameter( OSP.Util.toJSON(DP) );
				};

				DP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						DP._deserialize( key, jsonObject[key] );
				};

				DP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) ) continue;

						switch( key ){
						case 'value':
							DP.value(variable[key]);
							break;
						default:
							DP._upgrade( key, variable[key], DP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					DP.deserialize(jsonObject);
			}
			DS.newDateParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new DateParameter();
					case 1:
						return new DateParameter(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newDateParameter');
						return null;
				}
			};
			//end of date parameter creation function.
			
			// PhoneNumber parameter creation function is start.
			var PhoneNumberParameter = function(jsonObject){
				var PNP = this;

				_Parameter.apply(PNP);

				PNP.type( OSP.Constants.PHONE_NUMBER);
				PNP.active(true);

				PNP.value = function( value ) {
					return PNP.property.apply(PNP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				PNP.clone = function(){
					return new PhoneNumberParameter( OSP.Util.toJSON(PNP) );
				};

				PNP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						PNP._deserialize( key, jsonObject[key] );
				};

				PNP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) ) continue;

						switch( key ){
						case 'value':
							PNP.value(variable[key]);
							break;
						default:
							PNP._upgrade( key, variable[key], PNP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					PNP.deserialize(jsonObject);
			}
			DS.newPhoneNumberParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new PhoneNumberParameter();
					case 1:
						return new PhoneNumberParameter(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newPhoneNumberParameter');
						return null;
				}
			};
			//end of date parameter creation function.
			
			
			// email parameter creation function is start.
			var EMailParameter = function(jsonObject){
				var EMP = this;
				
				_Parameter.apply(EMP);
				
				EMP.type( OSP.Constants.EMAIL);
				EMP.active(true);

				EMP.clone = function(){
					return new EMailParameter( OSP.Util.toJSON(EMP) );
				};

				EMP.deserialize = function( jsonObject ){
					for( var key in jsonObject ){
						EMP._deserialize( key, jsonObject[key] );
					}
				};
				
				EMP.value = function( value ) {
					return EMP.property.apply(EMP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				EMP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) ) continue;

						switch( key ){
						case 'value':
							EMP.value(variable[key]);
							break;
						default:
							EMP._upgrade( key, variable[key], EMP );
							break;
						}
					}
				};
				if( arguments.length === 1 )
					EMP.deserialize( jsonObject );
			}
			DS.newEMailParameter = function( jsonObject ){
				switch( arguments.length ){
				case 0:
					return new EMailParameter();
				case 1:
					return new EMailParameter(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newEMailParameter');
					return null;
				}
			}
			//end of email parameter creation function.

			// object parameter creation function start.
			var ObjectParameter = function( jsonObject ){
				var OP = this;
				_Parameter.apply(OP);
				OP.type( OSP.Constants.OBJECT);
				OP.active(true);

				OP.value = function( value ) {
					return OP.property.apply(OP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				OP.clone = function(){
					return new newObjectParameter( OSP.Util.toJSON(OP) );
				};

				OP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						OP._deserialize( key, jsonObject[key] );
				};
				
				OP.dataTypeName = function( value ) {
					return OP.property.apply(OP, OSP.Util.addFirstArgument('dataTypeName_', arguments));
				}; 

				OP.dataTypeVersion = function( value ) {
					return OP.property.apply(OP, OSP.Util.addFirstArgument('dataTypeVersion_', arguments));
				}; 
				
				OP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							OP.value(variable[key]);
							break;
						default:
							OP._upgrade( key, variable[key], OAP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					OP.deserialize(jsonObject);
			}; // End of Object Parameter
			DS.newObjectParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new ObjectParameter();
					case 1:
						return new ObjectParameter(jsonObject);
					default:
						console.log( 'Arguments mismatch: newObjectParameter');
						return null;
				}
			};// end of object parameter creation function.
			
			// object array parameter creation function start.
			var ObjectArrayParameter = function( jsonObject ){
				var OAP = this;
				_Parameter.apply(OAP);
				OAP.type( OSP.Constants.OBJECT_ARRAY);
				OAP.active(true);

				OAP.value = function( value ) {
					return OAP.property.apply(OAP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				OAP.clone = function(){
					return new newObjetArrayParameter( OSP.Util.toJSON(OAP) );
				};

				OAP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						OAP._deserialize( key, jsonObject[key] );
				};
				
				OAP.dataTypes = function( value ) {
					return OAP.property.apply(OAP, OSP.Util.addFirstArgument('dataTypes_', arguments));
				}; 
				
				OAP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							OAP.value(variable[key]);
							break;
						default:
							OAP._upgrade( key, variable[key], OAP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					OAP.deserialize(jsonObject);
			}; // End of MultiStringParameter
			DS.newObjetArrayParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new ObjectArrayParameter();
					case 1:
						return new ObjectArrayParameter(jsonObject);
					default:
						console.log( 'Arguments mismatch: newObjetArrayParameter');
						return null;
				}
			};// end of object array parameter creation function.
			
			// datalink parameter creation function start.
			var DataLinkParameter = function( jsonObject ){
				var DLP = this;
				_Parameter.apply(DLP);
				DLP.type( OSP.Constants.DATA_LINK);
				DLP.active(true);

				DLP.value = function( value ) {
					return DLP.property.apply(DLP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				DLP.clone = function(){
					return new newDataLinkParameter( OSP.Util.toJSON(DLP) );
				};

				DLP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						DLP._deserialize( key, jsonObject[key] );
				};
				
				DLP.databaseName = function( value ) {
					return OP.property.apply(DLP, OSP.Util.addFirstArgument('databaseName_', arguments));
				}; 

				DLP.databaseVersion = function( value ) {
					return OP.property.apply(DLP, OSP.Util.addFirstArgument('databaseVersion_', arguments));
				}; 
				
				DLP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							DLP.value(variable[key]);
							break;
						default:
							DLP._upgrade( key, variable[key], DLP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					DLP.deserialize(jsonObject);
			}; // End of Object Parameter
			DS.newDataLinkParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new DataLinkParameter();
					case 1:
						return new DataLinkParameter(jsonObject);
					default:
						console.log( 'Arguments mismatch: newDataLinkParameter');
						return null;
				}
			};// end of datalink parameter creation function.
			
			// object array parameter creation function start.
			var DataBaseLinkArrayParameter = function( jsonObject ){
				var DBLAP = this;
				_Parameter.apply(DBLAP);
				DBLAP.type( OSP.Constants.DATABASE_LINK_ARRAY);
				DBLAP.active(true);

				DBLAP.value = function( value ) {
					return DBLAP.property.apply(DBLAP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};

				DBLAP.clone = function(){
					return new newDataBaseLinkArrayParameter( OSP.Util.toJSON(DBLAP) );
				};

				DBLAP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						DBLAP._deserialize( key, jsonObject[key] );
				};
				
				DBLAP.databases = function( value ) {
					return DBLAP.property.apply(DBLAP, OSP.Util.addFirstArgument('databases_', arguments));
				}; 
				
				DBLAP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							DBLAP.value(variable[key]);
							break;
						default:
							DBLAP._upgrade( key, variable[key], DBLAP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					DBLAP.deserialize(jsonObject);
			}; // End of DataBaseLinkArrayParameter
			DS.newDataBaseLinkArrayParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new DataBaseLinkArrayParameter();
					case 1:
						return new DataBaseLinkArrayParameter(jsonObject);
					default:
						console.log( 'Arguments mismatch: newObjetArrayParameter');
						return null;
				}
			};// end of DataBaseLinkArrayParameter creation function.
			
			
			// array parameter creation function start.
			var ArrayParameter = function( jsonObject ){
				var AP = this;
				_Parameter.apply(AP);
				AP.type( OSP.Constants.ARRAY);
				AP.active(true);

				AP.value = function( value ) {
					return OAP.property.apply(AP, OSP.Util.addFirstArgument(OSP.Constants.VALUE, arguments));
				};
				
				AP.clone = function(){
					return new newArrayParameter( OSP.Util.toJSON(AP) );
				};

				AP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						AP._deserialize( key, jsonObject[key] );
				};
				
				AP.childrenType = function( value ) {
					return AP.property.apply(AP, OSP.Util.addFirstArgument('childrenType_', arguments));
				}; 
				
				AP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'value':
							AP.value(variable[key]);
							break;
						default:
							AP._upgrade( key, variable[key], OAP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					AP.deserialize(jsonObject);
			}; // End of MultiStringParameter
			DS.newArrayParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new ArrayParameter();
					case 1:
						return new ArrayParameter(jsonObject);
					default:
						console.log( 'Arguments mismatch: newArrayParameter');
						return null;
				}
			};// end of object array parameter creation function.
			
			
			// group parameter creation function is start.
			var GroupParameter = function( jsonObject ){
				var GP = this;
				_Parameter.apply(GP);
				GP.type( OSP.Constants.GROUP);
				GP.active(true);

				GP.parameters = function( parameters ){
					return GP.property.apply(GP, OSP.Util.addFirstArgument(OSP.Constants.PARAMETERS, arguments));
				};

				GP.attachParameter = function( parameterName ){
					var parameters = GP.parameters();
					if( !parameters ){
						parameters = [];
						GP.parameters( parameters );
					}

					parameters.push( parameterName );
					return parameters;
				};

				GP.detachParameter = function( parameterName ){
					var parameters = GP.parameters();
					if( !parameters )	return true;

					for( var index in parameters ){
						var parameter = conditions[index];
						if( parameter === parameterName ){
							parameters.splice( index, 1 );
							return parameters;
						}
					}

					return parameters;
				};

				GP.hasParameter = function( parameterName ){
					var parameters = GP.parameters();
					for(var index in parameters ){
						if(parameters[index] === parameterName )	return true;
					}
					return false;
				};

				GP.clone = function(){
					return new GroupParameter( OSP.Util.toJSON(GP) );
				};

				GP.deserialize = function( jsonObject ){
					for( var key in jsonObject )
						GP._deserialize( key, jsonObject[key] );
				};

				GP.upgrade = function( variable ){
					for( var key in variable ){
						if( OSP.Util.isEmpty(variable[key]) )	continue;

						switch( key ){
						case 'activate-condition-container':
							var parameters = [];
							var oldConditions = variable[key]['container'];
							for( var index in oldConditions ){
								parameters.push( oldConditions[index]['variable-name'] );
							}
							GP.parameters( parameters );
							break;
						default:
							GP._upgrade( key, variable[key], GP );
							break;
						}
					}
				};

				if( arguments.length === 1 )
					GP.deserialize(jsonObject);
			};
			DS.newGroupParameter = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new GroupParameter();
					case 1:
						return new GroupParameter(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newGroupParameter');
						return null;
				}
			}; // End of GroupParameter

			var VectorForm = function( jsonObject ) {
				var VF = this;
				OSP._MapObject.apply( VF );

				VF.setForm = function(braceChar, delimiter, sample) {
					VF.braceChar( braceChar );
					VF.delimiter( delimiter );
					VF.sample( sample );
				};
				VF.braceChar = function( char ) {
					return VF.property.apply(VF, OSP.Util.addFirstArgument(OSP.Constants.BRACE_CHAR, arguments));
				};

				VF.delimiter = function( delimiter ) {
					return VF.property.apply(VF, OSP.Util.addFirstArgument(OSP.Constants.DELIMITER, arguments));
				};

				VF.space = function( space ) {
					return VF.property.apply(VF, OSP.Util.addFirstArgument(OSP.Constants.SPACE, arguments));
				};

				VF.sample = function( sample ) {
					return VF.property.apply(VF, OSP.Util.addFirstArgument(OSP.Constants.SAMPLE, arguments));
				};

				VF.clone = function(){
					return new VectorForm( OSP.Util.toJSON(VF) );
				};

				VF.deserialize = function( jsonForm ){
					for( var key in jsonForm ){
						VF._deserialize( key, jsonForm[key] );
					}
				};
				
				VF.upgrade = function( oldForm ){
					for( var key in oldForm ){
						if( OSP.Util.isEmpty(oldForm[key]) )	continue;
						//console.log( key + ": " + oldForm[key]);
						switch( key ){
						case 'brace-char':
							VF.braceChar(oldForm[key]);
							break;
						case 'delimiter':
							VF.delimiter(oldForm[key]);
							break;
						case 'space':
							VF.space( oldForm[key] );
							break;
						case 'sample':
							VF.sample( oldForm[key] );
							break;
						default:
							alert( 'Unknown vector form key: '+key);
							break;
						}
					}
				};

				if( arguments.length === 1 )
					VF.deserialize(jsonObject);
			}; // End of VectorForm
			DS.newVectorForm = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new VectorForm();
					case 1:
						return new VectorForm(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newVectorForm');
						return null;
				}
			};

			var ParameterForm = function( jsonObject ) {
				var PF = this;
				OSP._MapObject.apply( PF );

				PF.setForm = function(valueDelimiter, parameterDelimiter, commentChar, controlChar) {
					PF.valueDelimiter( valueDelimiter );
					PF.parameterDelimiter( parameterDelimiter );
					PF.commentChar( commentChar );
					PF.controlChar( controlChar );
				};

				PF.valueDelimiter = function( delimiter ) {
					return PF.property.apply(PF, OSP.Util.addFirstArgument(OSP.Constants.VALUE_DELIMITER, arguments));
				};

				PF.parameterDelimiter = function( delimiter ) {
					return PF.property.apply(PF, OSP.Util.addFirstArgument(OSP.Constants.PARAMETER_DELIMITER, arguments));
				};

				PF.commentChar = function( char ) {
					return PF.property.apply(PF, OSP.Util.addFirstArgument(OSP.Constants.COMMENT_CHAR, arguments));
				};

				PF.space = function( space ) {
					return PF.property.apply(PF, OSP.Util.addFirstArgument(OSP.Constants.SPACE, arguments));
				};

				PF.controlChar = function( char ){
					return PF.property.apply(PF, OSP.Util.addFirstArgument(OSP.Constants.CONTROL_CHAR, arguments));
				};

				PF.clone = function(){
					return new ParameterForm( OSP.Util.toJSON(PF) );
				};

				PF.deserialize = function( jsonForm ){
					for( var key in jsonForm ){
						PF._deserialize( key, jsonForm[key] );
					}
				};

				PF.upgrade = function( oldForm ){
					for( var key in oldForm ){
						if( OSP.Util.isEmpty(oldForm[key]) )	continue;

						switch( key ){
						case 'value-delimiter':
							PF.valueDelimiter(oldForm[key]);
							break;
						case 'variable-delimiter':
							PF.parameterDelimiter(oldForm[key]);
							break;
						case 'space':
							PF.space( oldForm[key] );
							break;
						case 'control-char':
							PF.controlChar( oldForm[key] );
							break;
						case 'comment-char':
							PF.commentChar( oldForm[key] );
							break;
						default:
							alert( 'Unknown parameter form key: '+key);
							break;
						}
					}
				};

				if( arguments.length === 1 )
					PF.deserialize( jsonObject );
			}; // End of ParameterForm
			DS.newParameterForm = function( jsonObject ){
				switch( arguments.length ){
					case 0:
						return new ParameterForm();
					case 1:
						return new ParameterForm(jsonObject);
					default:
						colsole.log( 'Arguments mismatch: newParameterForm');
						return null;
				}
			};

			DS.vectorForm = function( form ) {
				return DS.property.apply(DS, OSP.Util.addFirstArgument(OSP.Constants.VECTOR_FORM, arguments));
			};

			DS.setVectorForm = function(braceChar, delimiter, sample) {
				var vectorForm = DS.vectorForm();
				if( !vectorForm ){
					vectorForm = DS.newVectorForm();
					DS.vectorForm( vectorForm );
				}

				return vectorForm.setForm(braceChar, delimiter, sample);
			};

			DS.vectorFormBraceChar = function( char ) {
				var vectorForm = DS.vectorForm();
				if( !vectorForm )	return false;
				return vectorForm.braceChar.apply(vectorForm, arguments);
			};

			DS.vectorFormDelimiter = function( delimiter ) {
				var vectorForm = DS.vectorForm();
				if( !vectorForm )	return false;
				return vectorForm.delimiter.apply(vectorForm, arguments);
			};

			DS.vectorFormSpace = function( space ) {
				var vectorForm = DS.vectorForm();
				if( !vectorForm )	return false;
				return vectorForm.space.apply(vectorForm, arguments);
			};

			DS.vectorFormSample = function( sample ) {
				var vectorForm = DS.vectorForm();
				if( !vectorForm )	return false;
				return vectorForm.sample.apply(vectorForm, arguments);
			};

			DS.parameterForm = function( form ) {
				return DS.property.apply(DS, OSP.Util.addFirstArgument(OSP.Constants.PARAMETER_FORM, arguments));
			};

			DS.setParameterForm = function(valueDelimiter, parameterDelimiter, commentChar, controlChar) {
				var parameterForm = DS.parameterForm();
				if( !parameterForm ){
					parameterForm = new ParameterForm();
					DS.parameterForm( parameterForm );
				}

				return parameterForm.setForm(valueDelimiter, parameterDelimiter, commentChar, controlChar);
			};

			DS.parameterFormValueDelimiter = function( delimiter ) {
				var form = DS.parameterForm();
				if( !form )	return false;
				return form.valueDelimiter.apply(form, arguments);
			};

			DS.parameterFormParameterDelimiter = function( delimiter ) {
				var form = DS.parameterForm();
				if( !form )	return false;
				return form.parameterDelimiter.apply(form, arguments);
			};

			DS.parameterFormCommentChar = function( char ) {
				var form = DS.parameterForm();
				if( !form )	return false;
				return form.commentChar.apply(form, arguments);
			};

			DS.parameterFormControlChar = function( char ) {
				var form = DS.parameterForm();
				if( !form )	return false;
				return form.controlChar.apply(form, arguments);
			};

			DS.parameterFormSpace = function( space ) {
				var form = DS.parameterForm();
				if( !form )	return false;
				return form.space.apply(form, arguments);
			};

			DS.parameters = function( parameters ) {
				return DS.property.apply(DS, OSP.Util.addFirstArgument(OSP.Constants.PARAMETERS, arguments));
			};

			DS.parameter = function( parameterName ){
				var parameters = DS.parameters();
				if( !parameters )	return false;

				if( parameters.hasOwnProperty(parameterName) )
					return parameters[parameterName];
				else
					return false;
			};

			DS.parameterByOrder= function( order, groupOrder ){
				var parameters = DS.parameters();
				if( !parameters )	return false;

				for( var key in parameters ){
					var parameter = parameters[key];

					if( !groupOrder ){
						if( !DS.isInGroup( parameter.name() ) ){
							if( parameter.order() == order)	return parameter;
						}
					}
					else{
						var group = DS.parameterByOrder(groupOrder);
						var names = group.getAttachedParameterNames();
						for(var i in names ){
							parameter = DS.parameter(names[i]);
							if(parameter.order() == order)	return parameter;
						}
					}
				}
			};

			DS.topLevelParameters = function(){
				var parameters = DS.parameters();
				if( !parameters )	return false;

				var topLevels = [];
				for( var key in parameters ){
					var parameter = parameters[key];
					if( !DS.isInGroup(parameter.name()) )
						topLevels.push( parameter );
				}
				return topLevels;
			};

			DS.activeParameters = function(){
				var parameters = DS.parameters();
				if( !parameters )	return false;

				var actives = [];
				for( var key in parameters ){
					var parameter = parameters[key];
					if( parameter.active() )
						actives.push( parameter );
				}
				return actives;
			};

			DS.addParameter = function( parameter ) {
				var parameters = DS.parameters();
				if( !parameters ){
					parameters = {};
					DS.parameters( parameters );
				};
				parameters[parameter.name()] = parameter;
				return parameters;
			};

			DS.removeParameter = function( parameterName ) {
				var parameters = DS.parameters();
				if( !parameters )	return true;
				
				delete parameters[parameterName];
				
				var parameterNames = Object.keys(parameters);
				parameterNames.forEach(function(eachName){
					if(parameters[eachName].type() === OSP.Constants.GROUP){
						if(parameters[eachName].parameters()){
							var filteredArray = parameters[eachName].parameters().filter(nameInArray => parameterName != nameInArray);
							parameters[eachName].parameters(filteredArray);
						}
					}else if(parameters[eachName].type() === OSP.Constants.LIST){
						var listItems = parameters[eachName].listItems();
						if(listItems){
							for(var listItem in listItems){
								listItems[listItem].removeVisibleParameter(parameterName);
							}
						}
					}
				});
				
				return parameters;
			};

			DS.newParameter = function( parameterName, type ){
				if( DS.verifyParameterName(parameterName) == false )
					return false;

				var parameters = DS.parameters();
				var parameter;
				switch( type ){
					case OSP.Constants.NUMERIC:
						parameter = DS.newNumericParameter();
						break;
					case OSP.Constants.LIST:
						parameter = DS.newListParameter();
						break;
					case OSP.Constants.STRING:
						parameter = DS.newStringParameter();
						break;
					case OSP.Constants.VECTOR:
						parameter = DS.newVectorParameter();
						break;
					case OSP.Constants.MATRIX:
						parameter = DS.newMatrixParameter();
						break;
					case OSP.Constants.COMMENT:
						parameter = DS.newCommentParameter();
						break;
					case OSP.Constants.GROUP:
						parameter = DS.newGroupParameter();
						break;
					case OSP.Constants.MULTI_STRING:
						parameter = DS.newMultiStringParameter();
						break;
					case OSP.Constants.FILE_PARAMETER:
						parameter = DS.newFileParameter();
						break;
					case OSP.Constants.DATE:
						parameter = DS.newDateParameter();
						break;
					case OSP.Constants.PHONE_NUMBER:
						parameter = DS.newPhoneNumberParameter();
						break;
					case OSP.Constants.EMAIL:
						parameter = DS.newEMailParameter();
						break;
					case OSP.Constants.OBJECT:
						parameter = DS.newObjectParameter();
						break;
					case OSP.Constants.OBJECT_ARRAY:
						parameter = DS.newObjetArrayParameter();
						break;
					case OSP.Constants.ARRAY:
						parameter = DS.newArrayParameter();
						break;
					case OSP.Constants.DATABASE_LINK_ARRAY:
						parameter = DS.newDataBaseLinkArrayParameter();
						break;
					default:
						parameter = false;
				}
				
				parameter.name( parameterName );
				DS.addParameter(parameter);
				return parameter;
			};
			
			DS.sweepMax = function( max ){
				return DS.property.apply(DS, OSP.Util.addFirstArgument(OSP.Constants.SWEEP_MAX, arguments));
			};

			DS.sweepCount = function( count ){
				return DS.property.apply(DS, OSP.Util.addFirstArgument(OSP.Constants.SWEEP_COUNT, arguments));
			};

			DS.increaseSweepCount = function(){
				var count = DS.sweepCount();
				DS.sweepCount(++count);
				return DS.sweepCount();
			};

			DS.decreaseSweepCount = function(){
				var count = DS.sweepCount();
				DS.sweepCount(count>0 ? --count : 0);
				return DS.sweepCount();
			};

			DS.sweepedParameters = function(){
				var parameters = DS.parameters();

				var sweepedParameters = [];
				for(var i in parameters ){
					var parameter = parameters[i];
					if( parameter.sweeped() )
						sweepedParameters.push( parameter );
				}

				return sweepedParameters;
			};

			DS.cleanSweepedParameters = function(){
				var parameters = DS.sweepedParameters();
				if( OSP.Util.isEmpty(parameters) )	return false;

				for( var index in parameters ){
					var parameter = parameters[index];
					parameter.removeSweep();
				}

				return true;
			};

			DS.sweeped = function(){
				if( DS.sweepCount() === 0)	return false;
				else		return true;
			};

			DS.defaultLanguageId = function( languageId ){
				return DS.property.apply(DS, OSP.Util.addFirstArgument(OSP.Constants.DEFAULT_LANGUAGE_ID, arguments));
			};

			DS.availableLanguageIds = function( languageIds ){
				return DS.property.apply(DS, OSP.Util.addFirstArgument(OSP.Constants.AVAILABLE_LANGUAGE_IDS, arguments));
			};

			DS.addLanuageId = function( languageId ){
				var languageIds = DS.availableLanguageIds();
				if( !languageIds ){
					languageIds = [];
					DS.availableLanguageIds(languageIds);
				}

				for( var index in languageIds){
					if( languageIds[index] === languageId )	return false;
				}

				languageIds.push(languageId);
				return true;
			};

			DS.removeLanguageId = function( languageId ){
				var languageIds = DS.availableLanuageIds();
				if( !languageIds )	return true;

				for( var index in languageIds){
					if( languageIds[index] === languageId ){
						languageIds.splice( index, 1);
						return true;
					}
				}

				return false;
			};

			DS.verifyParameterName = function( parameterName ){
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

			DS.cloneParameter = function( parameter, count, currentParamNum){
				var jsonData = JSON.stringify( parameter );
				//console.log(JSON.stringify(parameter));
				var prefix = parameter.name();
				var index;
				var clones = [];
				for(index=0; index<count; index++){
					var name = prefix + "_" + (index+1);
					var emptyIndex = index+1;

					while(!DS.verifyParameterName(name)){
						name = prefix + "_" + emptyIndex;
						emptyIndex++;
					}

					var clone = DS.newParameter( name, parameter.type());
					var data = JSON.parse(jsonData);
					clone.deserialize(data);
					if( clone.verifyName(name) ){
						clone.orderNum = currentParamNum++;
						clone.name(name);
						clone.removeProperty(OSP.Constants.ACTIVATE_CONDITIONS);
						clones.push(clone);
						//console.log(JSON.stringify(clone));
					}
				}

				//console.log("index: "+index);
				if( index == count ){
					for( index = 0; index < count; index++ ){
						DS.addParameter(clones[index]);
					}
				}
				else
					return null;

				return clones;
			};

			DS.cloneParameterGroup = function( groupParameter, count, currentParamNum){
				if( groupParameter.type() !== OSP.Constants.GROUP )
					return [];

				var newGroups = DS.cloneParameter( groupParameter, count, currentParamNum);
				if( newGroups == null )	return null;
				for( var i in newGroups ){
					var group = newGroups[i];
					group.removeProperty(OSP.Constants.ACTIVATE_CONDITIONS);

					var orgSet = groupParameter.activateConditions();
					for( var j in orgSet ){
						var orgParamName = orgSet[j].parameterName();
						var orgParameter = DS.getParameter( orgParameterName );
						var paramClones = DS.cloneParameter( orgParameter, 1 );

						var clone = paramClones[0];
						DS.addParameter(clone);
						group.attachParameter( clone.name() );
					}
				}

				return newGroups;
			};

			DS.parameterNames = function(){
				var parameters = DS.parameters();
				if( !parameters )	return false;

				var names = [];
				switch( arguments.length ){
				case 0:
					for( var index in parameters ){
						names.push( parameters[index].name() );
					}
					break;
				default:
					for(var index = 0; index < arguments.length; index++) {
						for( var param in parameters ){
							if( parameters[param].type() === arguments[index] ){
								names.push( parameters[param].name() );
							}
						}
					}
				}
				return names;
			};

			DS.isInGroup = function(parameterName){
				var names = DS.parameterNames(OSP.Constants.GROUP);
				//console.log(JSON.stringify(names));
				for(var index in names ){
					var group = DS.parameter(names[index]);
					if( group.hasParameter(parameterName))	return true;
				}
				return false;
			};
			DS.hasFileParameter = function(){
				var hasFile = false;
				
				var parameters = DS.parameters();
				for(var parameterName in parameters){
					var parameter = DS.parameter(parameterName);
					if(parameter.type() == OSP.Constants.FILE_PARAMETER){
						hasFile = true;
						break;
					}
				}
				
				return hasFile;
			}
			DS.getFileParaName = function(){
				var fileNames = new Array();
				
				var parameters = DS.parameters();
				for(var parameterName in parameters){
					var parameter = DS.parameter(parameterName);
					if(parameter.type() == OSP.Constants.FILE_PARAMETER){
						fileNames.push(parameter.name());
					}else{
						continue;
					}
				}
				
				return fileNames;
			}
			DS.getRequiredNames = function(){
				var parameters = DS.parameters();
				
				var mandatoryParameters = new Array();

				for(var parameterName in parameters){
					if(parameters[parameterName].type() != OSP.Constants.FILE_PARAMETER
							&& parameters[parameterName].required()){
						mandatoryParameters.push(parameterName);
					}
				}
				
				return mandatoryParameters;
			}
			DS.deserialize = function( jsonObject ){
				//console.log( JSON.stringify(jsonObject,null,4));
				for( var key in jsonObject ){
					switch ( key ){
					case OSP.Constants.VECTOR_FORM:
						DS.vectorForm( DS.newVectorForm( jsonObject[key]) );
						break;
					case OSP.Constants.PARAMETER_FORM:
						DS.parameterForm( DS.newParameterForm( jsonObject[key]) );
						break;
					case OSP.Constants.PARAMETERS:
						var jsonParameters = jsonObject[key];
						for( var i in jsonParameters ){
							var jsonParameter = jsonParameters[i];
							var parameter;
							switch( jsonParameter[OSP.Constants.TYPE] ){
							case OSP.Constants.COMMENT:
								parameter = DS.newCommentParameter( jsonParameter );
								break;
							case OSP.Constants.GROUP:
								parameter = DS.newGroupParameter( jsonParameter );
								break;
							case OSP.Constants.LIST:
								parameter = DS.newListParameter( jsonParameter );
								break;
							case OSP.Constants.NUMERIC:
								parameter = DS.newNumericParameter( jsonParameter );
								break;
							case OSP.Constants.STRING:
								parameter = DS.newStringParameter( jsonParameter );
								break;
							case OSP.Constants.VECTOR:
								parameter = DS.newVectorParameter( jsonParameter );
								break;
							case OSP.Constants.MATRIX:
								parameter = DS.newMatrixParameter( jsonParameter );
								break;
							case OSP.Constants.MULTI_STRING:
								parameter = DS.newMultiStringParameter( jsonParameter );
								break;
							case OSP.Constants.FILE_PARAMETER:
								parameter = DS.newFileParameter( jsonParameter );
								break;
							case OSP.Constants.DATE:
								parameter = DS.newDateParameter( jsonParameter );
								break;
							case OSP.Constants.PHONE_NUMBER:
								parameter = DS.newPhoneNumberParameter( jsonParameter );
								break;
							case OSP.Constants.EMAIL:
								parameter = DS.newEMailParameter( jsonParameter );
								break;
							case OSP.Constants.OBJECT:
								parameter = DS.newObjectParameter( jsonParameter );
								break;
							case OSP.Constants.OBJECT_ARRAY:
								parameter = DS.newObjetArrayParameter( jsonParameter );
								break;
							case OSP.Constants.ARRAY:
								parameter = DS.newArrayParameter( jsonParameter );
								break;
							case OSP.Constants.BOOLEAN:
								parameter = DS.newBooleanParameter( jsonParameter );
								break;
							case OSP.Constants.DATA_LINK:
								parameter = DS.newDataLinkParameter( jsonParameter );
								break;
							case OSP.Constants.DATABASE_LINK_ARRAY:
								parameter = DS.newDataBaseLinkArrayParameter( jsonParameter );
								break;
							case OSP.Constants.FILE_PATH:
								parameter = DS.newFileParameter( jsonParameter );
								break;
							default:
								console.log('Un-recognizable parameter type: '+jsonParameter[OSP.Constants.TYPE]);
								return false;
							}
							DS.addParameter(parameter);
						}
						break;
					default:
						//console.log( key+':'+jsonObject[key]);
						DS._deserialize( key, jsonObject[key] );
					}
				}
			};

			DS.clone = function(){
				return new DataStructure( OSP.Util.toJSON(DS) );
			};

			DS.activeParameterFormattedInputs = function( page ){
				//console.log( DS);
				var parameterDelimiter = DS.parameterFormParameterDelimiter();
				if( !parameterDelimiter ){
					parameterDelimiter = '\n';
				}else{
					parameterDelimiter+='\n';
				}
				var valueDelimiter = DS.parameterFormValueDelimiter();
				var parameterSpaceChar = DS.parameterFormSpace() ? parameterSpaceChar = ' ': parameterSpaceChar = '';
				var parameterCommentChar = DS.parameterFormCommentChar();
				var vectorBraceChar = DS.vectorFormBraceChar();
				var vectorDelimiter = DS.vectorFormDelimiter();
				
				var getFormattedLines = function (parameter){
					var formattedString = [];
					switch(parameter.type()){
					case OSP.Constants.NUMERIC:
						if( parameter.sweeped() ){
							//console.log('getFormattedLines: ', parameter.name() );
							var sweepValues = parameter.getSweepSlicedValues();

							for(var i=0; i<sweepValues.length; i++){
								formattedString[i] =
									parameter.name() + parameterSpaceChar + valueDelimiter +
									parameterSpaceChar + sweepValues[i] + parameterSpaceChar + parameterDelimiter;
							}
						}
						else
							formattedString[0] =
								parameter.name() + parameterSpaceChar + valueDelimiter +
								parameterSpaceChar + parameter.value() + parameterSpaceChar + parameterDelimiter;
						break;
					case OSP.Constants.LIST:
					case OSP.Constants.DATE:
					case OSP.Constants.STRING:
					case OSP.Constants.EMAIL:
					case OSP.Constants.PHONE_NUMBER:
						formattedString[0] =
							parameter.name() + parameterSpaceChar + valueDelimiter +
							parameterSpaceChar + parameter.value() + parameterSpaceChar + parameterDelimiter;
						break;
					case OSP.Constants.COMMENT:
						formattedString[0] = parameterCommentChar +
							parameter.value() + parameterSpaceChar + parameterDelimiter;
						break;
					case OSP.Constants.VECTOR:
						var startBraceChar, endBraceChar;
						var vectorSpace;
						switch( vectorBraceChar ){
						case OSP.Constants.ROUND:
							startBraceChar = '(';
							endBraceChar = ')';
							vectorSpace = '';
							break;
						case OSP.Constants.ROUND_SPACE:
							startBraceChar = '( ';
							endBraceChar = ' )';
							vectorSpace = ' ';
							break;
						case OSP.Constants.SQUARE:
							startBraceChar = '[';
							endBraceChar = ']';
							vectorSpace = '';
							break;
						case OSP.Constants.SQUARE_SPACE:
							startBraceChar = '[ ';
							endBraceChar = ' ]';
							vectorSpace = ' ';
							break;
						}

						formattedString[0] =
							parameter.name() + parameterSpaceChar + valueDelimiter + parameterSpaceChar +
							parameter.getValueString(
									startBraceChar,
									endBraceChar,
									vectorDelimiter,
									vectorSpace) + parameterSpaceChar + parameterDelimiter;
						break;
					case OSP.Constants.MULTI_STRING:
						if(typeof(parameter.value()) != 'undefined'){
							if(parameter.value().trim().length > 0){
								formattedString[0] = parameter.name() + valueDelimiter + " " + parameterSpaceChar + parameter.value().replace(/\n/g, "\n") + parameterSpaceChar + parameterDelimiter;
							}
						}
						break;
					case OSP.Constants.FILE_PARAMETER:
						if(typeof(parameter.fileId()) != 'undefined'){
							var fileId = parameter.fileId() ? parameter.fileId() : '';
							formattedString[0] =
								parameter.name() + parameterSpaceChar + valueDelimiter +
								parameterSpaceChar + fileId + parameterSpaceChar + parameterDelimiter;
						}
						break;
					default:
						break;
					}
					//console.log(formattedString);
					return formattedString;
				};

				var getFormattedInputs = function (parameters){
					var pages = [[]];
					for(var index in parameters){
						var parameter = parameters[index];
						var lines = getFormattedLines(parameter);
						//console.log( 'Parameter: ', parameter );
						//console.log( 'Formatted Lines: ', lines);
						switch(lines.length){
						case 0:
							break;
						case 1:
							for( var pageIndex in pages ){
								var page = pages[pageIndex];
								page.push(lines[0]);
							}
							break;
						default:
							var lineCount = lines.length;
							var proliferatedPages = [];
							for( var lineIndex = 0; lineIndex<lineCount; lineIndex++){
								var clones = JSON.parse( JSON.stringify(pages) );
								for( var cloneIndex in clones ){
									var clone = clones[cloneIndex];
									clone.push(lines[lineIndex]);
								}
								
								proliferatedPages = proliferatedPages.concat( clones );
							}
							pages = proliferatedPages;
						}
					}
					//DS.setFileContent(input.join("\n")+"\n");
					//inputs.push(DS.clone());
					// inputs.push(input);
					return pages;
				};

				var activeParameters = DS.activeParameters();
				//console.log( 'Active Parameters: ', activeParameters );

				var formattedInputs = getFormattedInputs(activeParameters);
				//console.log(JSON.stringify(formattedInputs, null, 4));
				if( arguments.length === 1)
					return formattedInputs[page];
				else
					return formattedInputs;
			};

			DS.setAllParametersInactive = function(){
				var parameters = DS.activeParameters();
				for(var i=0; i<parameters.length; i++){
					var parameter = parameters[i];
					switch(parameter.type()){
					case OSP.Constants.COMMENT:
					case OSP.Constants.GROUP:
						break;
					default:
						parameter.active(false);
					}
				}
			};

			DS.loadInput = function(input){
				var valueDelimiter = DS.parameterFormValueDelimiter();
				if(valueDelimiter.indexOf('=') > -1) valueDelimiter = valueDelimiter.trim();
				var parameterDelimiter = DS.parameterFormParameterDelimiter();
				if( !parameterDelimiter )	parameterDelimiter = '\n';
//				parameterDelimiter += "\n";
				
				var commentChar = DS.parameterFormCommentChar();
				var lines =input.split(parameterDelimiter);
//				DS.setAllParametersInactive();// TODO : 메서드 사용 여부 확인.
				//console.log('**************************************');
				for(var i in lines){
				    var line = lines[i].trim();
                    if ( line.charAt(0) === commentChar || line.length <= 0 ){
                        continue;
                    }
                    //console.log('Line: '+ line);
                    
                    /* Bug fix when invoking sample data from workbench */
                    var delimiterIndex;
                    if(valueDelimiter.trim()){
                    	delimiterIndex = line.indexOf( valueDelimiter );
                    } else {
                    	delimiterIndex = line.indexOf(' ') > 0 ? line.indexOf(' ') : line.indexOf('\t');
                    }

                    var parameterName = "";
                    var parameterValue = "";
                    
                    if(delimiterIndex != -1){
                    	parameterName = line.slice(0, delimiterIndex ).trim();
                    	parameterValue = line.slice(delimiterIndex+valueDelimiter.length, line.length ).trim();
                    }else{
                    	parameterName = line;
                    }
                    
					if(parameterName.indexOf('=') == 0) parameterName = parameterName.substring(1); 
					
					if( parameterName.length === 0) continue;
					
					var parameter = DS.parameter(parameterName);
					//console.log('parameter: ', parameter);
					if(parameter){
						switch(parameter.type()){
						case OSP.Constants.VECTOR:
							var vectorForm = DS.vectorForm();
							parameterValue = parameterValue.replace(/\{|\[|\(|\}|\)|\]|,/g, ' ');
							var form = [];
							var elements = parameterValue.split( ' ' );
							for( var index in elements ){
								if( elements[index] && elements[index].trim() )
									form.push( elements[index].trim() );
							}
							parameter.value(form);
							break;
						case OSP.Constants.NUMERIC:
							if(parameter.sweeped() === true){
								parameter.sweeped(false);
								DS.decreaseSweepCount();
							}
							parameter.value(parameterValue);
							break;
							//alert(parameterName + ': '+parameter.sweeped());
						case OSP.Constants.FILE_PARAMETER:
							parameter.fileId( parameterValue );
							break;
						default:
							parameter.value(parameterValue);
						//console.log('value: '+parameterValue);
						}
						parameter.active(true);
					}else{
						console.log("No matched parameter with parameters in dataType ( parameter name : "+parameterName + " )");
					}
				}
			};

			DS.upgrade = function ( oldStructure ){
				for( var key in oldStructure ){
					//console.log( 'key: '+key);
					if( OSP.Util.isEmpty( oldStructure[key] ) )
						continue;

					switch( key ){
					case 'sweep-max':
						DS.sweepMax(oldStructure[key]);
						break;
					case 'variable-map':
						var variables = oldStructure[key]['map'];
						for( var variableName in variables ){
							var variable = variables[variableName];
							if( OSP.Util.isEmpty(variable) )	continue;

							var parameter;
							switch( variable['type']){
							case 'numeric':
								parameter = DS.newNumericParameter();
								break;
							case 'control_active':
							case 'control_associate':
							case 'list':
								parameter = DS.newListParameter();
								break;
							case 'string':
								parameter = DS.newStringParameter();
								break;
							case 'vector':
								parameter = DS.newVectorParameter();
								break;
							case 'matrix':
								parameter = DS.newMatrixParameter();
								break;
							case 'group':
								parameter = DS.newGroupParameter();
								break;
							case 'comment':
								parameter = DS.newCommentParameter();
								break;
							case 'multiString':
								parameter = DS.newMultiStringParameter();
								break;
							case 'file':
								parameter = DS.newFileParameter();
								break;
							case 'date':
								parameter = DS.newDateParameter();
								break;
							case 'phoneNumber':
								parameter = DS.newPhoneNumberParameter();
								break;
							case 'email':
								parameter = DS.newEMailParameter();
								break;
							case 'object':
								parameter = DS.newObjectParameter();
								break;
							case 'objectArray':
								parameter = DS.newObjetArrayParameter();
								break;
							case 'array':
								parameter = DS.newArrayParameter();
								break;
							case 'dataLinkArray':
								parameter = DS.newDataBaseLinkArrayParameter();
								break;
							default:
								alert('Unknown variable type: '+variable['type']);
								return;
							}

							parameter.upgrade(variable);
							DS.addParameter( parameter );
						}
						break;
					case 'vector-form':
						var vectorForm = new VectorForm();
						vectorForm.upgrade( oldStructure[key] );
						DS.vectorForm(vectorForm);
						break;
					case 'variable-form':
						var parameterForm = new ParameterForm();
						parameterForm.upgrade( oldStructure[key] );
						DS.parameterForm(parameterForm);
						break;
					case 'default-language-id':
						DS.defaultLanguageId(oldStructure[key]);
						break;
					case 'available-language-ids':
						var value = oldStructure[key].replace('{', '').replace('}', '');
						var idStrs =value.split(',');
						var languageIds = [];
						for( var index in idStrs ){
							var id = idStrs[index].trim();
							if( id.length > 0 )
								languageIds.push( id );
						}
						DS.availableLanguageIds(languageIds);
						break;
					default:
						alert( 'Unknown data structure key: '+key);
						break;
					}
				}

				//newCanvas.append( JSON.stringify(DS,null,4));
			};

			if( arguments.length === 1 )
				DS.deserialize(jsonObject);
		};// End of DataStructure
		DataType.newDataStructure = function( jsonObject ){
			switch( arguments.length ){
				case 0:
					return new DataStructure();
				case 1:
					return new DataStructure(jsonObject);
				default:
					colsole.log( 'Arguments mismatch: newDataStructure');
					return null;
			}
		};

		DataType.structure = function( structure ){
			return DataType.property.apply(DataType, OSP.Util.addFirstArgument(OSP.Constants.STRUCTURE, arguments));
		};

		DataType.defaultEditor = function( uuid ){
			return DataType.property.apply(DataType, OSP.Util.addFirstArgument(OSP.Constants.DEFAULT_EDITOR, arguments));
		};

		DataType.defaultAnalyzer = function( uuid ){
			return DataType.property.apply(DataType, OSP.Util.addFirstArgument(OSP.Constants.DEFAULT_ANALYZER, arguments));
		};

		DataType.availableEditors = function( editors ){
			return DataType.property.apply(DataType, OSP.Util.addFirstArgument(OSP.Constants.AVAILABLE_EDITORS, arguments));
		};

		DataType.availableAnalyzers = function( analyzers ){
			return DataType.property.apply(DataType, OSP.Util.addFirstArgument(OSP.Constants.AVAILABLE_ANALYZERS, arguments));
		};

		DataType.addEditor = function( editor ){
			var editors = DataType.availableEditors();
			if( !editors ){
				editors = [];
				DataType.availableEditors(editors);
			}
			editors.push(editor);

			return editors;
		};

		DataType.removeEditor = function( appId ){
			var editors = DataType.availableEditors();
			if( !editors )	return false;

			for( var key in editors ){
				if( editors[key] === appId ){
					editors.splice( key, 1);
					if( appId === DataType.defaultEditor() )
						DataType.removeProperty(OSP.Constants.DEFAULT_EDITOR);
					return true;
				}
			}

			return false;
		};

		DataType.addAnalyzer = function( editor ){
			var analyzers = DataType.availableAnalyzers();
			if( !analyzers ){
				analyzers = [];
				DataType.availableAnalyzers(analyzers);
			}
			analyzers.push(editor);

			return analyzers;
		};

		DataType.removeAnalyzer = function( uuid ){
			var analyzers = DataType.availableAnalyzers();

			if( !analyzers )	return false;

			for( var key in analyzers ){
				if( analyzers[key] === uuid ){
					analyzers.splice( key, 1);
					if( uuid === DataType.defaultAnalyzer() )
						DataType.removeProperty(OSP.Constants.DEFAULT_ANALYZER);
					return true;
				}
			}

			return false;
		};

		DataType.clone = function(){
			return new OSP.DataType( OSP.Util.toJSON(DataType) );
		};

		var displayUI =  function( dataStructure, namespace, canvas, contextPath, languageId, jsonDisplay, jsonInputDisplay, isFileHide){
			canvas.empty();
			var Layout;
			var Style;
			var Util = {
				refreshJSON : function(){
					if( !jsonDisplay )	return;
					jsonDisplay.empty();
					jsonDisplay.html(JSON.stringify(dataStructure, null, 4));
					if( !jsonInputDisplay )	return;
					jsonInputDisplay.empty();
					jsonInputDisplay.html(JSON.stringify(dataStructure.getActiveParameterFormattedInputs(), null, 4));
				},
				convertUndefinedToEmptyString : function(value){
					if( !value )
						return '';
					else
						return value;
				},
				isFormView: function(flag){
					if( !flag || flag == false )
						return false;
					else return true;
				}
			}; // End of Util

			var TagAttr = {
				valueRangeInputId : function(parameter){
					return namespace+'_'+parameter.name()+'_range';
				},
				valueRangeInputName : function(parameter){
					return namespace+'_'+parameter.name()+'_range';
				},
				sweepCheckBoxId : function(parameter){
					return namespace+'_'+parameter.name()+'_sweepCheckBox';
				},
				sweepMethodRadioBySliceId: function(parameter){
					return namespace+'_'+parameter.name()+'_sweepMethodBySlice';
				},
				sweepMethodRadioName: function(parameter){
					return namespace+'_'+parameter.name()+'_sweepMethod';
				},
				sweepMethodRadioByValueId: function(parameter){
					return namespace+'_'+parameter.name()+'_sweepMethodByValue';
				},
				sweepRangeLowerBoundaryId : function(parameter){
					return namespace+'_'+parameter.name()+'_sweep_lowerBoundary';
				},
				sweepRangeUpperBoundaryId : function(parameter){
					return namespace+'_'+parameter.name()+'_sweep_upperBoundary';
				},
				sweepRangeLowerOperandId : function(parameter){
					return namespace+'_'+parameter.name()+'_sweep_lowerOperand';
				},
				sweepRangeUpperOperandId : function(parameter){
					return namespace+'_'+parameter.name()+'_sweep_upperOperand';
				},
				sweepSliceValueId : function(parameter){
					return namespace+'_'+parameter.name()+'_sweep_sliceValue';
				},
				sweepSliceValueName : function(parameter){
					return namespace+'_'+parameter.name()+'_sweep_sliceValue';
				},
				sweepSliceValueUnitId : function(parameter){
					return namespace+'_'+parameter.name()+'_sweep_sliceValueUnit';
				},
				vectorInputDivId : function(parameter){
					return namespace+'_'+parameter.name()+'_vectorInputDiv';
				},
				parameterRowId : function(parameter){
					return namespace+'_'+parameter.name();
				},
				inputPageId: function(page){
					return namespace+'_page_'+page;
				}
			}; // End of TagAttr

			var valueRangeBoundaryDiv = function(parameter, place){
				var boundary;
				var div = $('<div>').addClass(Layout.InputAddon);
				if( place === OSP.Constants.LOWER_BOUNDARY){
					boundary = parameter.rangeLowerBoundary();
//					div.attr('style', Style.ValueRangeLowerBoundaryDiv);
				}
				else{
					boundary = parameter.rangeUpperBoundary();
//					div.attr('style', Style.ValueRangeUpperBoundaryDiv);
				}
				div.append( boundary );
				div.append( rangeUnit(parameter) );

				return div;
			};
			var rangeUnitDiv = function(parameter){
				var unit = parameter.unit();
				if( !unit )
					unit = '';
				
				var div = $('<div>').addClass(Layout.InputAddon);
//					div.attr('style', Style.RangeUnitDiv);
				div.append(unit);
				return div;
			};
			var rangeUnit = function(parameter){
				var unit = parameter.unit();
				if( !unit )
					unit = '';

				return unit;
			};
			var valueRangeOperandDiv = function(parameter, place){
				var div = $('<div>').addClass(Layout.InputAddon);
//				div.attr('style', Style.ValueRangeOperandDiv);
				var operand;
				if( place === OSP.Constants.LOWER_OPERAND ){
					if( parameter.rangeCheckLowerBoundary() )
						operand = '&le;';
					else
						operand = '<';
				}
				else {
					if( parameter.rangeCheckUpperBoundary())
						operand = '&le;';
					else
						operand = '<';
				}
				div.append(operand);

				return div;
			};
			var valueRangeInputDiv = function(parameter, eventFlag, div){
				var input = $('<input>');
				input.attr({
					'type':'text',
					'id' : TagAttr.valueRangeInputId(parameter),
					'name': TagAttr.valueRangeInputName(parameter),
					'value': parameter.value() || '',// TODO
					'class': Layout.AuiFieldSelect,
					'data-key' : parameter.name()
				});
				
				if( eventFlag === true ){
					//console.log('onChange Handler binded...');
					var onInputChange = function (event){
						var newValue = event.data.sourceTag.val();
						//console.log('parameter value changed: ' + newValue);
						var parameter = 	event.data.sourceParameter;
						if( parameter.value(newValue) == false ){
							alert(newValue+' is out of the range.');
							event.data.sourceTag.val(parameter.value());
						}
						checkActivateParameters(parameter);
						Util.refreshJSON();
					}

					input.bind('change',
							{
								'sourceTag': input,
								'sourceParameter':parameter
							},
							onInputChange
					);

					div.setParameterValue = function(value){
						input.attr('value', value);
						input.trigger( "change" );
					};
				}

				div.append(input);

				return div;
			};
			var sweepCheckBoxDiv = function(parameter){
				var div = $('<div>');
				div.attr('style', Style.SweepCheckBoxDiv);
				div.addClass(Layout.SweepCheckBoxDiv);
				
				var label = $('<label/>');
				label.attr('style', Style.SweepRadio);
				var checkBox = $('<input type=\"checkbox\" >Sweep</input>');
				if( parameter.sweeped() )	checkBox.attr('checked', 'checked');
				var id = TagAttr.sweepCheckBoxId(parameter);
				checkBox.attr('id', id);

				label.append(checkBox);
				div.append(label);

				return div;
			};
			var sweepMethodDiv = function(parameter){
				var div = $('<div>');
				div.addClass(Layout.SweepMethodDiv);
				div.attr('style', Style.SweepMethodDiv);

				var divRadio = $('<div>');
				divRadio.addClass(Layout.SweepRadioBySlice);

				var label = $("<label/>");
				var radioBySlice = $('<input type=\"radio\" value=\"slice\">By Slice</input>');
				//radioBySlice.addClass(Layout.SweepRadioBySlice);
				radioBySlice.attr({
					'id': TagAttr.sweepMethodRadioBySliceId(parameter),
					'name': TagAttr.sweepMethodRadioName(parameter),
					'style': Style.SweepRadioBySlice
				});
				label.append(radioBySlice)
				divRadio.append(label);
				div.append(divRadio);

				divRadio = $('<div>');
				divRadio.addClass(Layout.SweepRadioByValue);
				label = $("<label/>");

				var radioByValue = $('<input type=\"radio\" value=\"value\">By Value</input>');
				//radioByValue.addClass(Layout.SweepRadioByValue);
				radioByValue.attr({
					'id': TagAttr.sweepMethodRadioByValueId(parameter),
					'name': TagAttr.sweepMethodRadioName(parameter),
					'style': Style.SweepRadioByValue
				});

				if( parameter.sweepMethod() == OSP.Enumeration.SweepMethod.BY_SLICE )
					radioBySlice.attr('checked', 'checked');
				else
					radioByValue.attr('checked', 'checked');

				label.append(radioByValue)
				divRadio.append(label);
				div.append(divRadio);

				return div;
			};

			var onSweepChange = function (event){
				var newValue = Number( event.data.sourceTag.val() );
				var section = event.data.section;
				var parameter = 	event.data.sourceParameter;
				switch(section){
				case OSP.Constants.LOWER_BOUNDARY:
					if(parameter.sweepRangeLowerBoundary(newValue) == false){
						alert(newValue+' is out of the range.');
						event.data.sourceTag.val(parameter.sweepRangeLowerBoundary());
					}
					//console.log(JSON.stringify(parameter.getSweep(), null, 4));
					break;
				case OSP.Constants.UPPER_BOUNDARY:
					//console.log('sweepRangeUpperBoundary: '+parameter.sweepRangeUpperBoundary());
					if(parameter.sweepRangeUpperBoundary(newValue) == false){
						alert(newValue+' is out of the range.');
						event.data.sourceTag.val(parameter.sweepRangeUpperBoundary());
					}
					//console.log(JSON.stringify(parameter.sweep(), null, 4));
					break;
				case OSP.Constants.LOWER_OPERAND:
				//console.log('lower operand changed: '+newValue);
				if( newValue === '=')
						parameter.sweepRangeIncludeBoundary(OSP.Constants.LOWER_BOUNDARY, true);
					else
						parameter.sweepRangeIncludeBoundary(OSP.Constants.LOWER_BOUNDARY, false);
					break;
				case OSP.Constants.UPPER_OPERAND:
					//console.log('upper operand changed: '+newValue);
					if( newValue === '=')
						parameter.sweepRangeIncludeBoundary(OSP.Constants.UPPER_BOUNDARY, true);
					else
						parameter.sweepRangeIncludeBoundary(OSP.Constants.UPPER_BOUNDARY, false);
					break;
				case OSP.Enumeration.DivSection.SWEEP_SLICE_VALUE:
					var maxSlice = Number(parameter.maxSweepSlice());
					var sweepMethod = parameter.sweepMethod();
					
					if( !maxSlice )	maxSlice = 20;
					//console.log('Max Slice: '+maxSlice );
					//console.log('sweepMethod: '+sweepMethod );
					//console.log('newValue: '+newValue );
					if( sweepMethod === OSP.Enumeration.SweepMethod.BY_SLICE ){
						if(newValue > maxSlice || newValue < 2){
							alert('Slice should be between 2 and ' + maxSlice + '.');
							event.data.sourceTag.val( newValue < 2 ? 2 : parameter.sweepSliceCount() );
						}
						else
							parameter.sweepSliceCount( newValue );
					}
					else{
						var slices = OSP.Util.safeFloatSubtract(parameter.sweepRangeUpperBoundary(),
																									parameter.sweepRangeLowerBoundary()) / newValue;
						if( slices > maxSlice || slices < 2){
							alert('Slice should be between 2 and ' + maxSlice + ' by slice value.');
							event.data.sourceTag.val(parameter.sweepSliceValue());
						}
						else
							parameter.sweepSliceValue(newValue);
					}
					break;
				default:
				}
				Util.refreshJSON();
			};

			var sweepRangeBoundaryDiv = function(parameter, place){
				var limit;
				var input = $('<input>');
				var inputId;
				if( place === OSP.Constants.LOWER_BOUNDARY){
					limit = parameter.sweepRangeLowerBoundary();
					inputId = TagAttr.sweepRangeLowerBoundaryId(parameter);
				}
				else{
					limit = parameter.sweepRangeUpperBoundary();
					inputId = TagAttr.sweepRangeUpperBoundaryId(parameter);
				}
				var props = {
					'type' : 'text',
					'id' : inputId,
					'value' : limit,
					'class': Layout.AuiFieldSelect
				};
				
				input.attr(props);
				input.bind('change',
						{
							'sourceTag': input,
							'sourceParameter':parameter,
							'section' : place
						},
						onSweepChange
				);

				return input;
			};
			
			var sweepRangeOperandDiv = function(parameter, place){
				var select = $('<select>');
				select.addClass(Layout.AuiFieldSelect);
				select.css("min-width","60px");
				var selectId;
				var optionE, optionT;

				if( place === OSP.Constants.LOWER_OPERAND ){
					selectId = TagAttr.sweepRangeLowerOperandId(parameter);
					optionE = $('<option value=\"=\">&le;</option>');
					optionT = $('<option value=\"&lt;\">&lt;</option>');
					//console.log('isSweepRangeLowerBoundaryContained :' + parameter.isSweepRangeLowerBoundaryContained());
					if(parameter.sweepRangeCheckLowerBoundary() == true)
						optionE.attr('selected', 'selected');
					else
						optionT.attr('selected', 'selected');
				}
				else{
					//console.log('isSweepRangeUpperBoundaryContained :' + parameter.isSweepRangeUpperBoundaryContained());
					selectId = TagAttr.sweepRangeUpperOperandId(parameter);
					optionE = $('<option value=\"=\">&le;</option>');
					optionT = $('<option value=\"&gt;\">&lt;</option>');
					if(parameter.sweepRangeCheckUpperBoundary() == true)
						optionE.attr('selected', 'selected');
					else
						optionT.attr('selected', 'selected');
				}

				select.append(optionE);
				select.append(optionT);
				select.bind('change',
						{
							'sourceTag': select,
							'sourceParameter':parameter,
							'section' : place
						},
						onSweepChange
				);

				return select;
			};
			var sweepSliceValueDiv = function(parameter){
				var input = $('<input>');
				input.attr({
					'type':'text',
					'id' : TagAttr.sweepSliceValueId(parameter),
					'name': TagAttr.sweepSliceValueName(parameter),
					'class': Layout.AuiFieldSelect
				});
				var value;
				if(parameter.sweepMethod() == OSP.Enumeration.SweepMethod.BY_SLICE  ){
					value = Util.convertUndefinedToEmptyString(parameter.sweepSliceCount());
				}else{
					value = Util.convertUndefinedToEmptyString(parameter.sweepSliceValue());
				}

				if( value )
					input.val(value);

				input.bind('change',
						{
							'sourceTag': input,
							'sourceParameter':parameter,
							'section': OSP.Enumeration.DivSection.SWEEP_SLICE_VALUE
						},
						onSweepChange
				);

				return input;
			};
			var sweepSliceValueUnitDiv = function (parameter){
				var div = $('<div>');
				div.attr({
					'id': TagAttr.sweepSliceValueUnitId(parameter),
					'style': Style.SweepSliceValueUnitDiv
				});
				if( parameter.sweepMethod() == true)
					div.append('Slices');
				else{
					if ( parameter.unit() )
						div.append(parameter.unit());
				}
				return div;
			};
			var sweepRangeDiv = function(parameter){
				var div = $('<div>');
				div.addClass(Layout.SweepMethodDiv);
				div.attr('style', Style.SweepRangeDiv);
				
				var sweepRangeCol = $("<div>").addClass("col-md-12").css("padding","0px").appendTo(div);
				var formGroup = $("<div>").addClass("form-group").appendTo(sweepRangeCol);
				var inputGroup = $("<div>").addClass("input-group").appendTo(formGroup);
				
				
				
				inputGroup.append(sweepRangeBoundaryDiv(parameter, OSP.Constants.LOWER_BOUNDARY));
//				inputGroup.append($("<span class=\"input-group-addon\" style=\"width:0px; padding-left:0px; padding-right:0px; border:none;\"></span>"));
				
				inputGroup.append(sweepRangeOperandDiv(parameter, OSP.Constants.LOWER_OPERAND));
				inputGroup.append($('<div class=\"input-group-addon\">x</div>'));
				inputGroup.append(sweepRangeOperandDiv(parameter, OSP.Constants.UPPER_OPERAND));
//				inputGroup.append($("<span class=\"input-group-addon\" style=\"width:0px; padding-left:0px; padding-right:0px; border:none;\"></span>"));
				inputGroup.append(sweepRangeBoundaryDiv(parameter, OSP.Constants.UPPER_BOUNDARY));
//				inputGroup.append($("<span class=\"input-group-addon\" style=\"width:0px; padding-left:0px; padding-right:0px; border:none;\"></span>"));

				var divSweepSliceValue = sweepSliceValueDiv(parameter);
				inputGroup.append(divSweepSliceValue);
				div.setSweepSliceValue = function(value){
					var input = divSweepSliceValue.find('input');
					input.val(value);
				};
				
				div.setSweepSliceValueUnit = function(unit){
					var unitDiv = $('#'+TagAttr.sweepSliceValueUnitId(parameter));
					unitDiv.html(unit);
				};

				return div;
			};
			/* 요구사항반영 주석처리 2020-11-09. 필요시 주석 해제
			var sweepDiv = function(parameter, eventFlag){
				var div = $('<div>');
				div.addClass("row-fluid");
				var divCol = $("<div>").addClass("col-md-12").appendTo(div);
				
				if(parameter.sweeped()){
					div.attr('style', Style.SweepDivVisible);
				}
				else{
					if( eventFlag === true ){
						div.attr('style', Style.SweepDivInvisible);
					}else{
					    div.attr('style', Style.SweepDivVisible);
					}
				}

				var divSweepMethod = sweepMethodDiv(parameter);
				var divSweepRange = sweepRangeDiv(parameter);

				var onSweepMethodChange = function (event){
					var sweepMethodDiv = event.data.sweepMethodDiv;
					var sweepRangeDiv = event.data.sweepRangeDiv;
					var parameter = event.data.parameter;
					var method = sweepMethodDiv.find('input:checked').val();
					if( method === OSP.Enumeration.SweepMethod.BY_SLICE ){
						parameter.sweepMethod( OSP.Enumeration.SweepMethod.BY_SLICE );
						sweepRangeDiv.setSweepSliceValue(
								Util.convertUndefinedToEmptyString(parameter.sweepSliceCount()));
						sweepRangeDiv.setSweepSliceValueUnit('Slices');
					}
					else{
						parameter.sweepMethod( OSP.Enumeration.SweepMethod.BY_VALUE);
						sweepRangeDiv.setSweepSliceValue(
								Util.convertUndefinedToEmptyString(parameter.sweepSliceValue()));
						sweepRangeDiv.setSweepSliceValueUnit(
								Util.convertUndefinedToEmptyString(parameter.unit()));
					}
					Util.refreshJSON();
				}

				var data = {
						'sweepMethodDiv': divSweepMethod,
						'sweepRangeDiv': divSweepRange,
						'parameter': parameter
				};
				divSweepMethod.bind('change',
						data,
						onSweepMethodChange);

				divCol.append(divSweepMethod);
				divCol.append(divSweepRange);

				return div;
			};
			*/
			var parameterNameDiv = function(parameter){
				var div = $('<div>')
								.addClass(Layout.ParameterNameDiv)
								.attr({
									style : Style.ParameterNameDiv,
									title : parameter.name()
								})
								.text(parameter.name()+' : ');
				return div;
			};
			var numericParameterValueDiv = function(parameter, eventFlag){
				var div = $('<div>');
				div.addClass(Layout.ParameterRangeInlineDiv);
//				div.attr('style', Style.NumericParameterValueDiv);
				
				var formGroupDiv = $('<div>').addClass("form-group").appendTo(div);
				var inputGroupDiv = $('<div>').addClass(Layout.InputGroup).appendTo(formGroupDiv);
				
				var range = parameter.range();
				if( !range || JSON.stringify(range) === '{}'){
					var divInput = valueRangeInputDiv(parameter, eventFlag, inputGroupDiv);
					inputGroupDiv.append(divInput);
					inputGroupDiv.append(rangeUnitDiv(parameter));
				}
				else {
					var lowerBoundary = parameter.rangeLowerBoundary();
					if( lowerBoundary ){
						inputGroupDiv.append(valueRangeBoundaryDiv(parameter, OSP.Constants.LOWER_BOUNDARY));
//						div.append(rangeUnitDiv(parameter));
						inputGroupDiv.append(valueRangeOperandDiv(parameter, OSP.Constants.LOWER_OPERAND));
					}
					
					divInput = valueRangeInputDiv(parameter, eventFlag, inputGroupDiv);
					inputGroupDiv.append(divInput);
					inputGroupDiv.append(rangeUnitDiv(parameter));

					var upperBoundary = parameter.rangeUpperBoundary();
					if( upperBoundary ){
						inputGroupDiv.append(valueRangeOperandDiv(parameter, OSP.Constants.UPPER_OPERAND));
						inputGroupDiv.append(valueRangeBoundaryDiv(parameter, OSP.Constants.UPPER_BOUNDARY));
//						div.append(rangeUnitDiv(parameter));
					}
				}
				/* TODO
				if( parameter.sweepable() ){
					var divSweep = sweepDiv(parameter, eventFlag);
					var divCheckBox = sweepCheckBoxDiv(parameter);
					var checkBox = divCheckBox.find('input');

					var onSweepDivToggle = function (event){
						var origin = event.data.origin;
						var target = event.data.target;
						var parameter = event.data.sourceParameter;
						if( parameter.sweeped() ){
							parameter.sweeped(false);
							dataStructure.sweepCount() > 0 ? dataStructure.decreaseSweepCount() : 0;
							target.toggle();
						}
						else{
							if( dataStructure.sweepMax() == dataStructure.sweepCount() ){
								origin.attr('checked', false);
							}
							else{
								dataStructure.increaseSweepCount();
								parameter.sweeped(true);
								target.toggle();
							}
						}
						Util.refreshJSON();
					}

					var data = {
							'origin': checkBox,
							'target': divSweep,
							'sourceParameter': parameter
					};
					checkBox.bind('change', data, onSweepDivToggle);

					div.append(divCheckBox);
					div.append(divSweep);
				}
				*/

				if( eventFlag === true){
					div.setParameterValue = function(value){
						divInput.setParameterValue(value);
					};
				}

				return div;
			};
			var listParameterValueDiv = function(parameter, eventFlag){
				var div = $('<div>');
				div.addClass(Layout.ParameterRangeInlineDiv);
//				div.attr('style', Style.ListParameterValueDiv);
				var select = $('<select>');
				var id = TagAttr.valueRangeInputId(parameter);
				var name = id;
				select.attr({
					'id' : id,
					'name' : name,
					'class': Layout.AuiFieldSelect,
					'data-key' : parameter.name()
				});
				var listItems = parameter.localizedListItems(languageId);
				if( OSP.Util.isEmpty(listItems) ){
					listItems = parameter.localizedListItems(dataStructure.defaultLanguageId());
				}
				//console.log(JSON.stringify(listItems,null,4));

				var savedValue = parameter.value();
				$('<option/>').appendTo(select);
				for(var key in listItems){
					var listItem = listItems[key];
					/* TODO &amp; escape. */
					if(listItem.includes("&amp;")) listItem = listItem.replace('&amp;', '&');
					var option = $('<option/>');
					option.val(key);
					
					var listItemObj = parameter.listItem(key);
					if(listItemObj.visibleParameters()){
						var visibleArray = new Object();
						$(listItemObj.visibleParameters()).each(function(i, itemInfo){
							if(dataStructure.parameter(itemInfo.name).type() == OSP.Constants.FILE_PARAMETER){
								return true;
							}else{
								visibleArray[itemInfo.name] = itemInfo.isRequried;
							}
						});
						option.data('visible', visibleArray);
					}
					
					if( savedValue == key )
						option.attr('selected', 'selected');
					option.text(listItem);
					select.append(option);
				}

				if( eventFlag === true ){
					var onSelectChange = function (event){
						var newValue = event.data.sourceTag.val();
						//console.log('parameter value changed: ' + newValue);
						var parameter = 	event.data.sourceParameter;
						if( parameter.value(newValue) == false ){
							alert(newValue+' is out of the range.');
							event.data.sourceTag.val(parameter.value());
						}
						checkActivateParameters(parameter);
						Util.refreshJSON();
					}

					var data = {
							'sourceTag': select,
							'sourceParameter': parameter
					};
					select.bind('change', data, onSelectChange);
					div.setParameterValue = function(value){
						select.val(value);
						select.trigger('change');
					};
					div.setDisplayNone = function(itemValue){
						//console.log('Display None: ' + 'listParameterValueDiv');
						var option = select.find('option[value=\"'+itemValue+'\"]');
						option.attr('style', 'display:none');
					};
					div.listItemToggle = function(itemValue, flag){
						//console.log('ListItem Toggle: ' + 'listParameterValueDiv');
						var option = select.find('option[value=\"'+itemValue+'\"]');
						option.toggle(flag);
					}
				}

				div.append(select);
				return div;
			};
			var vectorParameterValueDiv = function(parameter, eventFlag){
				var div = $('<div>');
				div.addClass(Layout.ParameterRangeInlineDiv);
				
				var formGroupDiv = $('<div>')
//											.addClass("form-group")
											.appendTo(div);
				var inputGroupDiv = $('<div>').addClass(Layout.InputGroup).appendTo(formGroupDiv);
				
//				div.attr('style', Style.VectorParameterValueDiv);
				var leftBrace, rightBrace;
				switch(dataStructure.vectorFormBraceChar()){
				case OSP.Constants.SQUARE_SPACE:
				case OSP.Constants.SQUARE:
					leftBrace = '[ ';
					rightBrace = ' ]';
					break;
				case OSP.Constants.ROUND_SPACE:
				case OSP.Constants.ROUND:
					leftBrace = '( ';
					rightBrace = ' )';
					break;
				}
				var vector = parameter.value();
				var leftBraceDiv = $('<div>').addClass(Layout.InputAddon);
				leftBraceDiv.html(leftBrace);
				inputGroupDiv.append(leftBraceDiv);
				
				var inputDiv = $('<div>');
				inputDiv.attr('id', TagAttr.vectorInputDivId(parameter))
						.css({
							"display" : "flex",
							"justify-content" : "space-between",
							"width" : "70%"
						});
				var dimension = Number( parameter.dimension());
				var inputWidth = Math.floor(100/dimension);
				
				for(var i=0; i<dimension; i++){
					if( i !== 0){
						inputDiv.append(dataStructure.vectorFormDelimiter());
					}
					var input = $('<input>');
					input.attr({
						'type' : 'text',
						'style': 'width:'+inputWidth+'%',
						'class': Layout.AuiFieldSelect,
						'value': vector[i],
						'data-key' : parameter.name()
					});
					if( eventFlag === true ){
						var onElementChange = function (event){
							var newValue = event.data.sourceTag.val();
							var parameter = 	event.data.sourceParameter;
							var vector = parameter.value();
							var index = event.data.index;
							vector[index]=newValue;
							parameter.value(vector);
						}

						var data = {
								'sourceTag': input,
								'sourceParameter': parameter,
								'index': i
						};
						input.bind('change', data, onElementChange);
					}

					inputDiv.append(input);
				}
				inputGroupDiv.append(inputDiv);
				var rightBraceDiv = $('<div>').addClass(Layout.InputAddon);
				rightBraceDiv.html(rightBrace);
				inputGroupDiv.append(rightBraceDiv);

				if( eventFlag === true ){
					inputGroupDiv.setParameterValue = function(value){
						var inputs = inputDiv.find('input');
						for(var i=0; i<inputs.length; i++){
							inputs[i].val(value[i]);
							inputs[i].trigger('change');
						}
					};
				}

				return div;
			};
			
			var stringParameterValueDiv = function(parameter, eventFlag){
				var div = $('<div>');
				div.addClass(Layout.ParameterRangeDiv);

				var input = $('<input>');
				input.attr({
					'type' : 'text',
					'class': Layout.AuiFieldSelect,
					'value': parameter.value() || '', // TODO
					'data-key' : parameter.name()
				});
				$(input).val(parameter.value())
				if( eventFlag === true ){
					var onInputChange = function ( event ){
						var newValue = event.data.sourceTag.val();
						//console.log('parameter value changed: ' + newValue);
						var parameter = 	event.data.sourceParameter;
						parameter.value(newValue);
						Util.refreshJSON();
					}

					var data = {
							'sourceTag': input,
							'sourceParameter': parameter
					};
					input.bind('change', data, onInputChange);
					div.setParameterValue = function(value){
						input.val(value);
						input.trigger('change');
					};
				}

				div.append(input);

				return div;
			};
			var fileParameterValueDiv = function(parameter, eventFlag, isFileHide){
				var div = $('<div>');
				div.addClass(Layout.FileParameterInfoDiv)
					.attr('style', Style.FileInfoDiv);
				if(isFileHide){
					var fileId = parameter.fileId() || '';
					$(div).addClass('parameter-file-download')
							.attr("id",namespace + 'fileInfo_' + parameter.name())
							.attr('data-key', parameter.name());
					if(fileId.length){
						$(div).attr('data-value', fileId);
					}
					/*
					if(fileId.length > 0){
						$(div).attr('data-key', parameter.name())
							.attr('data-value', fileId);
					}
					else{
						var input = $('<input>');
						input.attr({
							'type' : 'file',
							'class': Layout.AuiFieldSelect,
							'data-key' : parameter.name(),
							'disabled' : true
						});
						$(div).append(input);
					}
					*/
				}else{
					var input = $('<input>');
					input.attr({
						'type' : 'file',
						'class': Layout.AuiFieldSelect,
						'data-key' : parameter.name(),
						'disabled' : true
					});
					
					div.append(input);
				}
				return div;
			};
			var multiStringParameterValueDiv = function(parameter, eventFlag){
				var div =$('<div/>');
				div.addClass(Layout.ParameterRangeDiv);
				
				var textArea = $('<textarea/>');
				textArea.attr({
					'class' : Layout.AuiFieldSelect,
					'data-key' : parameter.name()
				});
				var escapedVal = parameter.value().replace(/\/n/g,'\n');
				$(textArea).val(escapedVal);
				if( eventFlag === true ){
					var onInputChange = function ( event ){
						var newValue = event.data.sourceTag.val();
						//console.log('parameter value changed: ' + newValue);
						var parameter = 	event.data.sourceParameter;
						parameter.value(newValue);
						Util.refreshJSON();
					}

					var data = {
							'sourceTag': textArea,
							'sourceParameter': parameter
					};
					textArea.on('change', data, onInputChange);
					div.setParameterValue = function(value){
						textArea.val(value);
						textArea.trigger('change');
					};
				}
				div.append(textArea);
				
				return div;
			};
			var dateParameterValueDiv = function(parameter, eventFlag){
				var div = $('<div/>')
								.addClass(Layout.ParameterRangeDiv);

				var input = $('<input/>'),
					dateType = 'date',
					initDate = parameter.value() ? new Date(parameter.value()) : new Date(),
					viewMode = 'YMD';
				
				//  start setting perfect dateTimePicker config.
				if(parameter.format() == 'YYYY-MM-DD HH:mm:ss'){
					viewMode = 'YMDHMS';
				}
				
				var dateConfig = {
						'format' : parameter.format() || 'YYYY-MM-DD',
						'viewMode' : viewMode,
						'date' : initDate
				};
				
				$(input)
					.attr({
						'type' : 'text',
						'class': 'form-control',
						'data-key' : parameter.name(),
						'data-editor' : 'datetimepicker'
					})
					.val(parameter.value())
					.data('datetimepickerConfig',dateConfig)// end of setting dateTimePicker config.
					.on('keydown', function(e){//only mouse event can enter date.
						e.preventDefault();
						e.stopPropagation();
						return false;
					})

				div.append(input);

				return div;
			};
			
			var phoneNumberParameterValueDiv = function(parameter, eventFlag){
				var div = $('<div>')
								.addClass(Layout.ParameterRangeDiv + ' input-group')
								.data('key', parameter.name());
				var values = [];
				if(parameter.value()){
					values = parameter.value().split('-');

					if(values.length != 3) values.length = 3
				}
				
				for(let i = 0 ; i < 3 ; i++){
					var eachValue = "";
					
					if(values[i]){
						eachValue = values[i];
					}
					
					// make input box.
					$('<input/>')
							.attr({
								'type' : 'text',
								'maxlength' : '4'
							})
							.val(eachValue)
							.addClass('form-control')
							.on('change', function(e){// It allow only number key enter.
								if($(this).val() == '' || !isNaN($(this).val())){
									$(this)
										.removeClass('border-danger')
									
									var phoneNumberValue = '';
									$(div).find('input').each(function(j, input){
										phoneNumberValue += $(input).val();
										if(j < 2) phoneNumberValue += '-';
									});
									if(phoneNumberValue.trim() == '--') phoneNumberValue = '';
									parameter.value(phoneNumberValue);
								}else{
									$(this)
										.val('')
										.addClass('border-danger')
								}
							})
							.popover({
								content : 'Only Number',
								placement : 'top',
								trigger : 'focus'
							})
							.appendTo(div);
					// make hyphen box.
					if(i < 2){
						$('<div/>')
							.addClass('input-group-append')
							.append(
									$('<span/>')
										.addClass('input-group-text bg-transparent border-0')
										.text('-')
							)
							.appendTo(div);
					}
				}
				
				return div;
			};
			
			
			var emailParameterValueDiv = function(parameter, eventFlag){
				var div = $('<div/>')
								.addClass(Layout.ParameterRangeDiv + ' input-group')
								.data('key', parameter.name());
				
				var values = [];
				if(parameter.value()){
					values = parameter.value().split('@');
					
					if(values.length < 2) values.length = 2;
				}
				
				for(let i = 0 ; i < 2 ; i++){
					var eachValue = "";
					
					if(values[i]){
						eachValue = values[i];
					}
					
					// make input box.
					$('<input/>')
							.attr({
								'type' : 'text'
							})
							.val(eachValue)
							.addClass('form-control')
							.on('change', function(e){
								// change value event
								var emailValue = '';
								$(div).find('input').each(function(j, input){
									emailValue += $(input).val();
									if(j == 0) emailValue += '@';
								});
								
								if(emailValue == '@') emailValue = '';
								
								parameter.value(emailValue);
							})
							.appendTo(div);
					// make hyphen box.
					if(i == 0){
						$('<div/>')
							.addClass('input-group-append')
							.append(
									$('<span/>')
										.addClass('input-group-text bg-transparent border-0')
										.text('@')
							)
							.appendTo(div);
					}
				}
				return div;
			};
			
			var parameterDescriptionDiv = function(parameter){
				var div = $('<div>')// outer description div.
								.addClass(Layout.ParameterDescriptionDiv)
								.attr('style',Style.ParameterDescriptionDiv);
				
				var content = parameter.localizedDescription(languageId),
					hasDescription = false;// content for parameter description.
				if(content.length == 0){// localized content setting.
					var languageIds = DataType.structure().availableLanguageIds();
					languageIds.forEach(function(languageId){
						content = parameter.localizedDescription(languageId);
						if(content.length > 0){
							hasDescription = true;
							return false;// break after setting content.
						}
					});
					if(content.length == 0){
						content = $('<span/>') //set non-description message for this parameter.
										.addClass('text-danger')
										.text('No Description for this Parameter.');
					}
				}

				$('<i/>')
					.addClass("icon-question-sign")
					.popover({// popover config setting for printing description using bootstrap.
						content : content,
						trigger : 'hover',
						placement : 'top',
						html : !hasDescription// non-description message is setting as html.
					})
					.attr("title", content)
					.appendTo(div);
				
				return div;
			};

			//This checks if a parameter is active or not from its activation conditions.
			var checkInitialActivate = function(parameter, tag){
				var checkListItem = function(){
					if(parameter.type() === OSP.Constants.LIST){
						//console.log('Check List Item Activate.... ');
						var listItems = parameter.listItems();
						//console.log(JSON.stringify(listItems));
						for( var key in listItems ){
							//console.log('Check List Item Activate: '+listItems[j].getItemValue());
							var listItem = listItems[key];
							var itemConditions = listItem.activateConditions();
							for(var k=0; k<itemConditions.length; k++){
								var itemConditionParameter = dataStructure.parameter(itemConditions[k].parameterName());
								if(itemConditions[k].checkActivate(itemConditionParameter.value()) == false){
									//console.log('ListItem Toggle: ' + 'checkInitialActivate');
									tag.setDisplayNone(listItem.value());
								}
							}
						}
					}
				};

				var conditions = parameter.activateConditions();
				if(conditions == false ){
					checkListItem();
					parameter.active(true);
					return;
				}
				//console.log(JSON.stringify(conditions));
				for(var i=0; i<conditions.length; i++){
					var conditionParameter = dataStructure.parameter(conditions[i].parameterName());
					var assignValue = conditions[i].checkActivate(conditionParameter.value());
					switch( assignValue ){
					case true:
						//console.log(parameter.getName()+': '+true);
						break;
					case false:
						//console.log(parameter.getName()+': '+false);
						break;
					default:
						//console.log(parameter.getName()+': '+assignValue);
						tag.setParameterValue(assignValue);
						tag.find('input').attr('disabled', 'disabled');
						tag.find('select').attr('disabled', 'disabled');
						break;
					}
					if( assignValue !== false ){
						//console.log('Activate: '+parameter.getName());
						//checkListItem();
						parameter.active(true);
						return;
					}
				}
				//console.log('Inactivate: '+parameter.name());
				tag.attr('style', 'display:none;');
				parameter.active(false);
			};
			var checkActivateParameters = function(parameter){
				//console.log('========== checkActivateParameters ==================');
				//console.log('Source Parameter: '+parameter.getName());
				//console.log('Source Value: '+parameter.getValue());
				var parameters = dataStructure.parameters();

				for(var paramName in parameters){
					//console.log('-------------');
					var targetParameter = parameters[paramName];
					//console.log('Target Parameter: '+targetParameter.getName());
					//console.log('Target Type: '+targetParameter.getType());
					//Check ItemLists for LIST type parameters
					if(targetParameter.type() === OSP.Constants.LIST){
						//console.log('Processing ListParameter...........');
						var listItems = targetParameter.listItems();
						//console.log(JSON.stringify(listItems));
						//console.log('List Item Count: '+listItems.length);
						for( var itemValue in listItems ){
							var targetItem = listItems[itemValue];
							//console.log('++Target Item: '+targetItem.getItemValue());
							var activate = targetItem.checkActivate(parameter.name(), parameter.value());
							//console.log('++Activate: '+activate);
							var select = $('#'+TagAttr.valueRangeInputId(targetParameter));
							//console.log('++SelectId: '+select.attr('id'));
							var option = select.find('option[value=\"'+targetItem.value()+'\"]');
							//console.log('++Option Value: ' + option.val());
							option.toggle(activate);
						}
					}

					if( targetParameter === parameter){
						//console.log('Source and Target are same: omitted');
						continue;
					}
					if(targetParameter.type() === OSP.Constants.GROUP){
						//console.log('Target parameter type is a GROUP: omitted');
						targetParameter.active(true);
						continue;
					}
					var activate = false;
					var disable = false;
					var conditions = targetParameter.activateConditions();
					if( conditions == false )	activate = true;
					else{
						for(var j=0; j<conditions.length; j++){
							var condition = conditions[j];
							var conditionParameter = dataStructure.parameter(condition.parameterName());
							var assignValue = condition.checkActivate(conditionParameter.value());
							switch( assignValue ){
							case true:
								activate= true;
								disable = false;
								break;
							case false:
								activate = false;
								break;
							default:
								activate= true;
								disable = true;
								break;
							}
							if( activate == true ){
								parameter.active(true);
								break;
							}
						}
					}
					//console.log('Activate: '+activate);
					//console.log('Disabled: '+disable);

					targetParameter.active(activate);
					if( activate == true ){
						targetParameter.disabled(disable);
						$('#'+TagAttr.parameterRowId(targetParameter)).toggle(true);
						if( disable == true ){
							$('#'+TagAttr.parameterRowId(targetParameter)).find('input').attr('disabled', 'disabled');
							$('#'+TagAttr.parameterRowId(targetParameter)).find('select').attr('disabled', 'disabled');
						}
						else{
							$('#'+TagAttr.parameterRowId(targetParameter)).find('input').removeAttr('disabled');
							$('#'+TagAttr.parameterRowId(targetParameter)).find('select').removeAttr('disabled');
						}
					}
					else{
						$('#'+TagAttr.parameterRowId(targetParameter)).toggle(false);
					}

					//console.log('-------------');
				}
			};

			var numericParameterRow = function(parameter, eventFlag){
				var div = $('<div>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow
				});
				div.addClass(Layout.ParameterRow);
				div.append(parameterNameDiv(parameter));
				var valueDiv = numericParameterValueDiv(parameter, eventFlag);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));

				if( eventFlag === true ){
					div.setParameterValue = function(value){
						valueDiv.setParameterValue(value);
					};
					checkInitialActivate(parameter, div);
				}

				return div;
			};
			
			var groupParameterRow = function( parameter, eventFlag ){
				var outerDiv = $('<div/>');/*class=\"span12\" 삭제*/
				outerDiv.attr('id', TagAttr.parameterRowId(parameter))
						.addClass('row')
						.css({
							margin : 'auto'
						});
				
				
				var groupNameRow = $('<div/>')
										.addClass("custom-col-13")
										.css({
											lineHeight : "40px",
											borderBottom : "2px solid #b4b4b4",
											fontWeight : 600,
										})
										.appendTo(outerDiv);
				
				var groupNameDiv = $("<div/>").addClass("col-md-10").appendTo(groupNameRow);
				
				var groupName = parameter.localizedNameText(languageId);
				if( !groupName || groupName == false || groupName == '' )
					groupName = parameter.localizedNameText(dataStructure.defaultLanguageId());

				if( OSP.Util.isEmpty(groupName) )
					groupName = parameter.name();
				
				groupNameDiv.html("&nbsp;"+groupName);
				
				
				var groupDescriptionDiv = $("<div/>").addClass("col-md-1")
													.appendTo(groupNameRow);
				groupDescriptionDiv.append(parameter.localizedDescription(languageId));
				
				

				var attachedNames = parameter.parameters();
				if(attachedNames.length > 0){
					for(var index in attachedNames){
						var subParameter = dataStructure.parameter(attachedNames[index]);
						var subParameterDiv = parameterRow( subParameter, eventFlag, isFileHide);
						
						$(subParameterDiv).addClass('w-100 d-flex');
						if(!isFileHide){
							$(subParameterDiv).children('div:eq(0)')
													.removeClass('custom-col-4')
													.addClass('custom-col-6');
							$(subParameterDiv).children('div:eq(1)')
													.addClass('custom-col-7');
							
						}else{
							$(subParameterDiv).children('div:eq(1)')
													.addClass('custom-col-9');
							
						}

//						$(subParameterDiv).children('div:eq(1)').addClass('col-md-6');
						outerDiv.append(subParameterDiv);
//						outerDiv.append( parameterRow( subParameter, eventFlag) );
					}
				}else{
//					div.css("padding", "5px 0px");
				}

//				outerDiv.append(div)
				return outerDiv;
			};
			
			var listParameterRow = function(parameter, eventFlag){
				var div = $('<div/>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow
				});
				div.addClass(Layout.ParameterRow);
				div.append(parameterNameDiv(parameter));
				var valueDiv = listParameterValueDiv(parameter, eventFlag);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));

				if( eventFlag === true ){
					div.setParameterValue = function(value){
						valueDiv.setParameterValue(value);
					};
					div.setDisplayNone = function(itemValue){
						valueDiv.setDisplayNone(itemValue);
					};
					div.listItemToggle = function(key, flag){
						valueDiv.listItemToggle(key, flag);
					};
					checkInitialActivate(parameter, div);
				}

				return div;
			};

			var vectorParameterRow = function( parameter, eventFlag ){
				var div = $('<div/>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow
				});
				div.addClass(Layout.ParameterRow);
				div.append(parameterNameDiv(parameter));
				var valueDiv = vectorParameterValueDiv(parameter, eventFlag);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));
				if( eventFlag === true ){
					div.setParameterValue = function(value){
						valueDiv.setParameterValue(value);
					};
					checkInitialActivate(parameter, div);
				}

				return div;
			};
			var stringParameterRow = function(parameter, eventFlag){
				var div = $('<div/>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow
				});
				div.addClass(Layout.ParameterRow);
				div.append(parameterNameDiv(parameter));
				var valueDiv = stringParameterValueDiv(parameter, eventFlag);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));
				if( eventFlag === true ){
					div.setParameterValue = function(value){
						valueDiv.setParameterValue(value);
					};
					checkInitialActivate(parameter, div);
				}

				return div;
			};
			var fileParameterRow = function(parameter, eventFlag, isFileHide){
				var div = $('<div/>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow,
				});
				div.addClass(Layout.ParameterRow);
				div.append(parameterNameDiv(parameter));
				var valueDiv = fileParameterValueDiv(parameter, eventFlag, isFileHide);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));
/*				if( eventFlag === true ){
					div.setParameterValue = function(value){
						valueDiv.setParameterValue(value);
					};
					checkInitialActivate(parameter, div);
				}
*/
				return div;
			};
			var commentRow =  function(parameter, eventFlag){
				var div = $('<div/>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow
				});
				div.addClass(Layout.ParameterRow);
				var nameDiv = $('<div/>');
				nameDiv.addClass(Layout.ParameterNameDiv);
				nameDiv.attr('style', Style.ParameterNameDiv);
				nameDiv.append('Comment : ');
				div.append(nameDiv);
				var valueDiv = stringParameterValueDiv(parameter, eventFlag);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));

				if( eventFlag === true ){
					div.setParameterValue = function(value){
						valueDiv.setParameterValue(value);
					};
					checkInitialActivate(parameter, div);
				}

				return div;
			};
			var multiStringParameterRow = function(parameter, eventFlag){
				var div = $('<div/>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow
				});
				div.addClass(Layout.ParameterRow);
				div.append(parameterNameDiv(parameter));
				var valueDiv = multiStringParameterValueDiv(parameter, eventFlag);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));
				if( eventFlag === true ){
					div.setParameterValue = function(value){
						valueDiv.setParameterValue(value);
					};
					checkInitialActivate(parameter, div);
				}

				return div;
			};
			
			// start make div for date parameter.
			var dateParameterRow = function(parameter, eventFlag){
				var div = $('<div/>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow
				});
				div.addClass(Layout.ParameterRow);
				div.append(parameterNameDiv(parameter));
				var valueDiv = dateParameterValueDiv(parameter, eventFlag);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));
				if( eventFlag === true ){
					div.setParameterValue = function(value){
						valueDiv.setParameterValue(value);
					};
					checkInitialActivate(parameter, div);
				}

				return div;
			};// end of making div for date parameter.
			
			// start make div for phoneNumber parameter.
			var phoneNumberParameterRow = function(parameter, eventFlag){
				var div = $('<div/>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow
				});
				div.addClass(Layout.ParameterRow);
				div.append(parameterNameDiv(parameter));
				var valueDiv = phoneNumberParameterValueDiv(parameter, eventFlag);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));

				return div;
			};// end of making div for phoneNumber parameter.
			
			// start make div for mail parameter.
			var emailParameterRow = function(parameter, eventFlag){
				var div = $('<div/>');
				div.attr({
					'id': TagAttr.parameterRowId(parameter),
					'style': Style.ParameterRow
				});
				div.addClass(Layout.ParameterRow);
				div.append(parameterNameDiv(parameter));
				var valueDiv = emailParameterValueDiv(parameter, eventFlag);
				div.append(valueDiv);
				div.append(parameterDescriptionDiv(parameter));

				return div;
			};// end of making div for mail parameter.
			
			var parameterRow = function( parameter, eventFlag, isFileHide){
//				console.log(JSON.stringify(parameter, null, 4));
				var div;
				switch(parameter.type()){
				case OSP.Constants.GROUP:
					div = groupParameterRow(parameter, eventFlag);
					break;
				case OSP.Constants.NUMERIC:
					div = numericParameterRow(parameter, eventFlag);
					break;
				case OSP.Constants.LIST:
					div = listParameterRow(parameter, eventFlag);
					break;
				case OSP.Constants.VECTOR:
					div = vectorParameterRow(parameter, eventFlag);
					break;
				case OSP.Constants.STRING:
					div = stringParameterRow(parameter, eventFlag);
					break;
				case OSP.Constants.COMMENT:
					div = commentRow(parameter, eventFlag);
					break;
				case OSP.Constants.MULTI_STRING:
					div = multiStringParameterRow(parameter, eventFlag);
					break;
				case OSP.Constants.FILE_PARAMETER :
					div = fileParameterRow(parameter, eventFlag, isFileHide);
					break;
				case OSP.Constants.DATE :
					div = dateParameterRow(parameter, eventFlag, isFileHide);
					break;
				case OSP.Constants.PHONE_NUMBER :
					div = phoneNumberParameterRow(parameter, eventFlag, isFileHide);
					break;
				case OSP.Constants.EMAIL :
					div = emailParameterRow(parameter, eventFlag, isFileHide);
					break;
				}

				var parameterClicked = function( event ){
					event.stopPropagation();
					// for target html information for fucusing value part
//					event.data.targetHtml = event.target;
					Liferay.fire( 'parameterSelected', event.data);
				};

				//console.log('Numeric callback bined');
				div
				.css("cursor","pointer")
				.on('click',
					{
						'targetPortlet': namespace,
						'sourceTag': div,
						'sourceParameter': parameter
					},
					parameterClicked
				);

				return div ;
			};

			return {
				editor : function(){

					Layout = {
							SweepCheckBoxDiv: 'form-group',
							SweepMethodDiv: 'row-fluid',
//							SweepRadioBySlice: 'col-md-6 col-xs-6 col-lg-6 align-self-center',
//							SweepRadioByValue: 'col-md-6 col-xs-6 col-lg-6 align-self-center',
//							ParameterNameDiv: 'col-md-3 col-xs-3 col-lg-3 align-self-center text-truncate',
//							ParameterRangeDiv: 'col-md-6 col-xs-6 col-lg-6 align-self-center',
//							FileParameterInfoDiv: 'col-md-6 col-xs-6 col-lg-6 text-right align-self-center',
//							ParameterRangeInlineDiv: 'col-md-6 col-xs-6 col-lg-6 align-self-center',
//							ParameterDescriptionDiv: 'col-md-1 col-xs-1 col-lg-1 align-self-center',
							SweepMethodValueDiv: 'span11',
							SweepMethodValue: 'span12',
							ParameterNameDiv: 'align-self-center text-truncate custom-col-4',
							ParameterDescriptionDiv: 'align-self-center custom-col-2',
							SweepRadioBySlice: 'align-self-center custom-col-7',
							SweepRadioByValue: 'align-self-center custom-col-7',
							ParameterRangeDiv: 'align-self-center custom-col-7',
							FileParameterInfoDiv: 'text-right align-self-center custom-col-7',
							ParameterRangeInlineDiv: 'align-self-center',
							ParameterRow: 'row row-fluid parameter-row',
							AuiFieldSelect: 'form-control',
							InputGroup:'input-group',
							InputAddon:'input-group-addon'
					};

					Style = {
							SweepCheckBoxDiv: 'padding-left:10px;',
							SweepMethodDiv: ' padding-top:7px; border-bottom:2px solid grey; display:flex;',
							SweepRadio: 'display:inline;',
							SweepRadioBySlice: 'display:inline;',
							SweepRadioByValue: 'display:inline;',
							SweepRangeLowerBoundaryDiv: 'display:inline;',
							SweepRangeLowerBoundary: 'width:40px; text-align:right;',
							SweepRangeOperandDiv: 'padding: 0px 10px; display:inline;',
							SweepRangeOperandSelect: 'width:50px; height:27px; display:inline;',
							SweepRangeDiv: 'padding:5px;',
							SweepSliceValueDiv: 'padding:5px;',
							SweepSliceValue: 'width:40px; text-align:right; display:inline;',
							SweepSliceValueUnitDiv: 'width:20px; display:inline;',
							SweepDivVisible: 'border:3px solid #d0d0d0;display:block;margin-top:5px;',
							SweepDivInvisible: 'border:3px solid #d0d0d0;display:none;margin-top:5px;',
							ParameterNameDiv:'text-align:right; word-wrap: break-word;vertical-align:middle;line-height:30px;',
							ParameterDescriptionDiv:'text-align:center;display:inline;margin-left: 0px;line-height: 30px;vertical-align: middle;',
							ValueRangeLowerBoundaryDiv: 'width:60px; text-align:right; display:inline;',
							ValueRangeUpperBoundaryDiv: 'width:60px; display:inline;',
							RangeUnitDiv:'width:20px; display:inline;',
							ValueRangeOperandDiv: 'padding: 1px 10px; display:inline;',
							ValueRangeInputDiv: 'display:inline;',
							ValueRangeInput: 'width: 60px; text-align:right; display:inline;',
							NumericParameterValueDiv:'text-align:center; padding: 2px 10px; display:inline;margin:0px;',
							ListParameterValueDiv:'text-align:center; padding: 2px 10px; display:inline;margin: 0 auto;',
							GroupParameterRow:'border:3px inset #cce7ff; padding:0px 0px 0px 30px; margin:5px 0px 10px 0px;',
							ParameterRow:'padding:5px 0px;margin:auto;',
							VectorParameterValueDiv:'text-align:center; padding: 2px 10px; display:inline;margin: 0 auto;',
							VectorInput: 'width: 40px; text-align:right;',
							StringParameterValueDiv:'text-align:center; padding: 2px 10px; display:inline;margin: 0 auto;',
							StringInput: 'width: 70%;',
							MultiStringDiv : 'padding:0px 10px;background:white;border:1px solid #ccc;border-radius:4px;',
							FileInfoDiv : 'display:flex;justify-content:center;cursor:auto;align-items:center;'
					};

					var parameters = dataStructure.topLevelParameters();
					//console.log( JSON.stringify(parameters,null,4));
					if(Object.keys(parameters).length > 1){
						parameters.sort( (a, b) => { return a.orderNum == b.orderNum ?  0 : (a.orderNum > b.orderNum ?  1 : -1) } );
					}
					var dragedTargetDivId = "";
					for(var index in parameters){
						/*
						if((index % 2)== 0)
							Style.ParameterRow += ' background-color:#f0f0f0;';
						else
							Style.ParameterRow += ' background-color:#f8f8f8;';
						*/
						var thisParameterRow = parameterRow( parameters[index], true, isFileHide);
						var iconDiv = $('<div/>')
											.addClass('align-self-center custom-col-2')
//											.addClass('col-md-1 col-sm-1 align-self-center')
											.css({
												cursor: 'move',
												lineHeight : '40px'
											});
						
						
						if(parameters[index].type() === OSP.Constants.GROUP){
							iconDiv.css('border-bottom','2px solid rgb(180, 180, 180)');
							
							$(thisParameterRow).addClass('my-4');
							if(!isFileHide){
								$(thisParameterRow).addClass('border');
							}
						}else{
							$(thisParameterRow).addClass('my-2');
							if(!isFileHide){
								$(thisParameterRow).addClass('border');
							}
						}
						
						var moveIcon = $('<i/>').addClass(namespace+'moveIcon icon-reorder')
												.data('parentId', $(thisParameterRow).attr('id'))
												.prop('draggable', true);
						
						$(moveIcon).on('dragstart', function(event){
							dragedTargetDivId = event.target.closest('div').parentNode.id;

							event.originalEvent.dataTransfer.setDragImage(document.querySelector('#'+dragedTargetDivId), 0, 0);
							
							$(event.target).trigger('click');
						});
						$(moveIcon).on('drag', function(event){
							event.preventDefault();
						});
						$(moveIcon).on('dragend', function(event){
							event.preventDefault();
							event.stopPropagation();
							
							var dropDivId = dragedTargetDivId;
							var dropParaName = dropDivId.substring(namespace.length+1);
							
							var paraDivs = canvas.children('div');

							var yEvent = event.pageY;
							var pushFlg = false;
							var sortedParameters = new Array();
							$(paraDivs).each(function(i, paraDiv){
								var yDiv = $(paraDiv).children('div').eq(0).offset().top;
								
								var divId = $(paraDiv).attr('id');
								var eachParaName = divId.substring(namespace.length+1);

								if(dropDivId == divId) return true;
								
								if(pushFlg){
									sortedParameters.push(eachParaName);
									if(DataType.structure().parameter(eachParaName).type() == OSP.Constants.GROUP){
										if(DataType.structure().parameter(eachParaName).parameters()){
											var innerParameters = DataType.structure().parameter(eachParaName).parameters();
											sortedParameters = [...sortedParameters, ...innerParameters];
										}
									}
									return true;
								}else{
									if(yEvent < yDiv){
										sortedParameters.push(dropParaName);
										
										if(DataType.structure().parameter(dropParaName).type() == OSP.Constants.GROUP){
											if(DataType.structure().parameter(dropParaName).parameters()){
												var innerParameters = DataType.structure().parameter(dropParaName).parameters();
												sortedParameters = [...sortedParameters, ...innerParameters];
											}
										}
										
										sortedParameters.push(eachParaName);
										if(DataType.structure().parameter(eachParaName).type() == OSP.Constants.GROUP){
											if(DataType.structure().parameter(eachParaName).parameters()){
												var innerParameters = DataType.structure().parameter(eachParaName).parameters();
												sortedParameters = [...sortedParameters, ...innerParameters];
											}
										}
											
										pushFlg = true;
									}else{
										sortedParameters.push(eachParaName);
										
										if(DataType.structure().parameter(eachParaName).type() == OSP.Constants.GROUP){
											if(DataType.structure().parameter(eachParaName).parameters()){
												var innerParameters = DataType.structure().parameter(eachParaName).parameters();
												sortedParameters = [...sortedParameters, ...innerParameters];
											}
										}
									}
								}
								
								if(i == paraDiv.length-1 && !pushFlg){
									sortedParameters.push(dropParaName);
									
									if(DataType.structure().parameter(dropParaName).type() == OSP.Constants.GROUP){
										if(DataType.structure().parameter(dropParaName).parameters()){
											var innerParameters = DataType.structure().parameter(dropParaName).parameters();
											sortedParameters = [...sortedParameters, ...innerParameters];
										}
									}
								}
							});
							var orderCnt = 0;
							$(sortedParameters).each(function(i, paraName){
								DataType.structure().parameter(paraName).orderNum = 1+orderCnt++;
							});
							DataType.showStructuredDataEditor( namespace, canvas, contextPath, languageId);
						});
						$(canvas).parent().on('dragover', function(event){
							event.preventDefault();
						});
						moveIcon.appendTo(iconDiv);
						$(thisParameterRow).prepend(iconDiv);
						
						canvas.append(thisParameterRow);
					}
				},
				form : function(){
					Layout = {
							SweepCheckBoxDiv: 'form-group',
							SweepMethodDiv: 'row-fluid',
//							SweepRadioBySlice: 'col-md-6 align-self-center',
//							SweepRadioByValue: 'col-md-6 align-self-center',
//							ParameterNameDiv: 'col-md-3  align-self-center text-truncate',
//							ParameterRangeDiv: 'col-md-6 align-self-center',
//							ParameterRangeInlineDiv: 'col-md-6 align-self-center',
//							ParameterDescriptionDiv: 'col-md-1 align-self-center',
							SweepMethodValueDiv: 'span11',
							SweepMethodValue: 'span12',
							

							ParameterNameDiv: 'align-self-center text-truncate custom-col-4',
							ParameterDescriptionDiv: 'align-self-center custom-col-2',
							SweepRadioBySlice: 'align-self-center custom-col-7',
							SweepRadioByValue: 'align-self-center custom-col-7',
							ParameterRangeDiv: 'align-self-center custom-col-7',
							ParameterRangeInlineDiv: 'align-self-center custom-col-7',
							FileParameterInfoDiv: 'text-right align-self-center custom-col-7',
							
							ParameterRow: 'row-fluid parameter-row',
							AuiFieldSelect: 'form-control',
							InputGroup:'input-group',
							InputAddon:'input-group-addon'
					};
					Style = {
							SweepCheckBoxDiv: 'padding-left:10px;',
							SweepMethodDiv: ' padding-top:7px; border-bottom:2px solid grey; display : flex;',
							SweepRadio: 'display:inline;',
							SweepRadioBySlice: 'display:inline;',
							SweepRadioByValue: 'display:inline;',
							SweepRangeLowerBoundaryDiv: 'display:inline;',
							SweepRangeLowerBoundary: 'width:40px; text-align:right;',
							SweepRangeOperandDiv: 'padding: 0px 10px; display:inline;',
							SweepRangeOperandSelect: 'width:50px; height:27px; display:inline;',
							SweepRangeDiv: 'padding:5px;',
							SweepSliceValueDiv: 'padding:5px;',
							SweepSliceValue: 'width:40px; text-align:right; display:inline;',
							SweepSliceValueUnitDiv: 'width:20px; display:inline;',
							SweepDivVisible: 'border:3px solid #d0d0d0;display:block;margin-top:5px;',
							SweepDivInvisible: 'border:3px solid #d0d0d0;display:none;margin-top:5px;',
							ParameterNameDiv:'text-align:right; word-wrap: break-word;vertical-align:middle;line-height:30px;',
							ParameterDescriptionDiv:'text-align:center;display:inline;margin-left: 0px;line-height: 30px;vertical-align: middle;',
							ValueRangeLowerBoundaryDiv: 'width:60px; text-align:right; display:inline;',
							ValueRangeUpperBoundaryDiv: 'width:60px; display:inline;',
							RangeUnitDiv:'width:20px; display:inline;',
							ValueRangeOperandDiv: 'padding: 1px 10px; display:inline;',
							ValueRangeInputDiv: 'display:inline;',
							ValueRangeInput: 'width: 60px; text-align:right; display:inline;',
							NumericParameterValueDiv:'text-align:center; padding: 2px 10px; display:inline;margin:0px;',
							ListParameterValueDiv:'text-align:center; padding: 2px 10px; display:inline;margin:0px;',
							GroupParameterRow:'border:3px inset #cce7ff; padding:0px 0px 0px 30px; margin:5px 0px 10px 0px;',
							ParameterRow:'padding:5px 0px;',
							VectorParameterValueDiv:'text-align:center; padding: 2px 10px; display:inline;margin:0px;',
							VectorInput: 'width: 40px; text-align:right;',
							StringParameterValueDiv:'text-align:center; padding: 2px 10px; display:inline;margin: 0 auto;',
							StringInput: 'width: 70%;',
							MultiStringDiv : 'padding:0px 10px;background:white;border:1px solid #ccc;border-radius:4px;'
					};

					var parameters = dataStructure.topLevelParameters();
					for(var index in parameters){
						/*
						if((index % 2)== 0)
							Style.ParameterRow += ' background-color:#f0f0f0;';
						else
							Style.ParameterRow += ' background-color:#f8f8f8;';
						*/
						canvas.append(parameterRow( parameters[index] ));
					}
				},

				viewer : function(){
					var formattedInputs = dataStructure.activeParameterFormattedInputs();
					//console.log("formattedInputs--->"+JSON.stringify(formattedInputs,null,4));
					var inputCount = formattedInputs.length;
					var currentViewPage = 1;
					var viewerDiv = $('<div>');
					var viewSection = $('<div class="row-fluid">');

					if(inputCount>1){
						var getPageId = function (page){
							return namespace+'_page_'+page;
						};
						var getPrevPageId = function (page){
							return namespace+'_page_'+(page-1);
						};
						var getNextPageId = function (page){
							return namespace+'_page_'+(page+1);
						};

						var previousPage = function (event){
							if( currentViewPage == 1)	return;
							var pageTag = event.data.pageTag;
							$('#'+getPageId(currentViewPage)).toggle();
							$('#'+getPrevPageId(currentViewPage)).toggle();
							currentViewPage -= 1;
							pageTag.text(currentViewPage);
						};
						var nextPage = function(event){
							if( currentViewPage ==  (inputCount)) return;
							var pageTag = event.data.pageTag;
							$('#'+getPageId(currentViewPage)).toggle();
							$('#'+getNextPageId(currentViewPage)).toggle();
							currentViewPage += 1;
							pageTag.text(currentViewPage);
						};

						var navSection = $('<div class="row-fluid">');
						var  buttonDiv = $('<div class="span4">');
						var pagerUl = $('<ul class="pager">');
						var prevLi = $('<li class="previous" style="cursor: pointer;"><span>Prev</span></li>');
						var pageLi = $('<li></li>');
						pageLi.text(currentViewPage);
						var nextLi = $('<li class="next" style="cursor: pointer;"><span>Next</span></li>');
						prevLi.find('span').bind('click', {'pageTag':pageLi}, previousPage);
						nextLi.find('span').bind('click', {'pageTag':pageLi}, nextPage);

						pagerUl.append(prevLi);
						pagerUl.append(pageLi);
						pagerUl.append(nextLi);
						buttonDiv.append(pagerUl);
						navSection.append(buttonDiv);

						viewerDiv.append(navSection);
					}


					for( var i=0; i<formattedInputs.length; i++){
						var inputDiv = $('<div>');
						inputDiv.attr('id', namespace+'_page_'+(i+1));
						if((i+1) !== currentViewPage )	inputDiv.attr('style', 'display:none');
						var input = formattedInputs[i].toString().replace(/,/g, '');
						inputDiv.html(input);
						viewSection.append(inputDiv);
					}

					viewerDiv.append(viewSection);
					canvas.append(viewerDiv);
				}
			};
		}; // End of displayUI

		DataType.showStructuredDataEditor = function( namespace, canvas, contextPath, languageId, fileHide, jsonDisplay, jsonInputDisplay ){
			var ui = displayUI( DataType.structure(), namespace, canvas, contextPath, languageId, jsonDisplay, jsonInputDisplay, fileHide );
			ui.editor();
		};

		DataType.showDataStructureForm = function(namespace, canvas, contextPath, languageId, jsonDisplay, jsonInputDisplay){
			var ui = displayUI( DataType.structure(), namespace, canvas, contextPath, languageId, jsonDisplay, jsonInputDisplay );
			ui.form();
		};

		DataType.showStructuredDataViewer = function(namespace, canvas, contextPath, languageId, jsonDisplay, jsonInputDisplay){
			//console.log(JSON.stringify(DataType.structure(),null,4));
			var ui = displayUI( DataType.structure(), namespace, canvas, contextPath, languageId, jsonDisplay, jsonInputDisplay );
			ui.viewer();
		};

		DataType.deserializeStructure = function( jsonObject ){
			return DataType.structure(DataType.newDataStructure( jsonObject));
		};
		
		DataType.loadStructure = function( content ){
			var structure = DataType.structure();
			console.log( 'OSP Structure: ', structure );
			if( !structure ){
				console.log('[ERROR] no data structure: '+DataType.name() );
				console.log( content );
				return false;
			}
			structure.loadInput( content );

			return structure;
		};
		
	}; // End of DataType

})(OSP);