package osp.icecap.sss.constants;

public class IcecapSSSConstants {
	public static final String RESOURCE_NAME = "osp.icecap.sss";
	public static final String TERM_ELEMENTS_DEFAULT_EVENT_HANDLER =
			"TERM_ELEMENTS_DEFAULT_EVENT_HANDLER";

	public static final String TERM_ASSET_ENTRY = "TERM_ASSET_ENTRY";
	public static final String TERM_MANAGEMENT_TOOLBAR_COMPONENT_ID = "termManagementToolbar";

	public static final String TERM_PORTLET_INSTANCE_CONFIGURATION =
		"TERM_PORTLET_INSTANCE_CONFIGURATION";
	
	public static final String SEARCH_CONTAINER_ID="searchedTermsContainer";
	
	public static final String API_BUNDLE_NAME="osp.icecap.sss.api";
	public static final String SERVICE_BUNDLE_NAME="osp.icecap.sss.service";
	public static final String WEB_BUNDLE_NAME="osp.icecap.sss.web";
	
	public static final String ASC = "asc";
	public static final String DSC = "dsc";
	
	public static final String NAVIGATION_ALL = "all";
	public static final String NAVIGATION_MINE = "mine";
	public static final String NAVIGATION_GROUP = "group";
	public static final String NAVIGATION_GROUP_MINE = "groupMine";
	
	public static final String VIEW_TYPE_LIST = "list";
	public static final String VIEW_TYPE_CARD = "card";
	public static final String VIEW_TYPE_TABLE = "table";
	
	public static final String[] VIEW_TYPES() {
		return new String[] { VIEW_TYPE_LIST, VIEW_TYPE_TABLE, VIEW_TYPE_CARD };
	}
	
	public static final String[] NAVIGATION_KEYS() {
		return new String[] { NAVIGATION_ALL, NAVIGATION_GROUP, NAVIGATION_MINE };
	}
}
