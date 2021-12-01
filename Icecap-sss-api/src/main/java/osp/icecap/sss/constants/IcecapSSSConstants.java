package osp.icecap.sss.constants;

public class IcecapSSSConstants {
	public static final String RESOURCE_NAME = "osp.icecap.sss";
	public static final String TERM_ELEMENTS_DEFAULT_EVENT_HANDLER =
			"TERM_ELEMENTS_DEFAULT_EVENT_HANDLER";

	public static final String TERM_ASSET_ENTRY = "TERM_ASSET_ENTRY";
	public static final String TERM_MANAGEMENT_TOOLBAR_COMPONENT_ID = "termManagementToolbar";

	public static final String TERM_PORTLET_INSTANCE_CONFIGURATION =
		"TERM_PORTLET_INSTANCE_CONFIGURATION";
	
	public static final String SEARCH_CONTAINER_ID="termSearchContainer";
	
	public static final String API_BUNDLE_NAME="osp.icecap.sss.api";
	public static final String SERVICE_BUNDLE_NAME="osp.icecap.sss.service";
	public static final String WEB_BUNDLE_NAME="osp.icecap.sss.web";
	
	public static final String ASC = "asc";
	public static final String DSC = "dsc";
	
	public static final String NAVIGATION_ALL = "all";
	public static final String NAVIGATION_MINE = "mine";
	public static final String NAVIGATION_GROUP = "group";
	public static final String NAVIGATION_GROUP_MINE = "groupMine";
	
	public static final String DISPLAY_VIEW_LIST = "list";
	public static final String DISPLAY_VIEW_ICON = "icon";
	public static final String DISPLAY_VIEW_DESCRIPTIVE = "descriptive";

	public static final String[] DISPLAY_VIEWS() {
		return new String[] { DISPLAY_VIEW_LIST, DISPLAY_VIEW_DESCRIPTIVE, DISPLAY_VIEW_ICON };
	}
	
	public static final String[] NAVIGATION_KEYS() {
		return new String[] { NAVIGATION_ALL, NAVIGATION_GROUP, NAVIGATION_MINE };
	}
}
