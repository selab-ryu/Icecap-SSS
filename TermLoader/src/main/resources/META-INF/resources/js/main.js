$.structureTreeIcon = function(type, iconClass){
	var iconName = type || 'etc';
	
	return {
		html : '<img src="' + contextPath + '/images/structureType/' + iconName + '.png" alt="' + iconName + '" class="' + iconClass + '" />'
	};
}

$.treeExtensionIcon = function(extension, iconClass){
	var iconName = extension || 'file';
	
	return {
		html : '<img src="' + contextPath + '/images/extension/' + iconName + '.png" alt="' + iconName + '" class="' + iconClass + '" />'
	};
}