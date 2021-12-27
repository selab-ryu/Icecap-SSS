package osp.icecap.sss.web.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.JSPViewTypeItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.debug.Debug;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.security.permission.resource.TermResourcePermissionHelper;
import osp.icecap.sss.web.util.TermActionDropdownItemsProvider;

public class TermManagementToolbarDisplayContext extends SearchContainerManagementToolbarDisplayContext{

	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;
	private final String _displayStyle;
	private final String _navigation;
	private final TermDisplayContext _termDisplayContext;
	
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	
	private final SearchContainer<Term> _searchContainer;
	
	private final HttpServletRequest _httpServletRequest;

	public TermManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest, 
			TermDisplayContext termDisplayContext) {
		super(
				liferayPortletRequest, 
				liferayPortletResponse, 
				httpServletRequest,
				termDisplayContext.getSearchContainer());

			_liferayPortletRequest = liferayPortletRequest;
			_liferayPortletResponse = liferayPortletResponse;
			_httpServletRequest = httpServletRequest;
			_termDisplayContext = termDisplayContext;
			 _trashHelper = termDisplayContext.getTrashHelper();
			 _displayStyle = termDisplayContext.getDisplayStyle();
			 _navigation = super.getNavigation();
			 System.out.println("***** TermManagementToolbarDisplayContext(). _navigation: "+_navigation);
			 _searchContainer = termDisplayContext.getSearchContainer();
			 System.out.println("_navigation: "+_navigation);
			_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getClearResultsURL() {
		Debug.printHeader("TermManagementToolbarDisplayContext.getClearResultsURL()");
		PortletURL clearResultsURL = super.getPortletURL();
		clearResultsURL.setParameter(IcecapSSSWebKeys.KEYWORDS, StringPool.BLANK);
		System.out.println("clearResultsURL: "+clearResultsURL.toString());
		Debug.printFooter("TermManagementToolbarDisplayContext.getClearResultsURL()");
		return clearResultsURL.toString();
	}
	
	@Override
	public String getComponentId() {
		Debug.printHeader("TermManagementToolbarDisplayContext.getComponentId()");
		System.out.println("TermManagementToolbarDisplayContext Component ID: " + IcecapSSSConstants.TERM_MANAGEMENT_TOOLBAR_COMPONENT_ID);
		Debug.printFooter("TermManagementToolbarDisplayContext.getClearResultsURL()");
		return IcecapSSSConstants.TERM_MANAGEMENT_TOOLBAR_COMPONENT_ID;
	}
	
	@Override
	public String getInfoPanelId() {
		/*
		Debug.printHeader("TermManagementToolbarDisplayContext.getInfoPanelId()");
		String inforPanelId = super.getNamespace() + "termInfoPanelId"; 
		System.out.println("inforPanelId: "+inforPanelId);
		Debug.printFooter("TermManagementToolbarDisplayContext.getInfoPanelId()");
		return inforPanelId;
		*/
		return null;
	}
	
	@Override
	public String getSearchContainerId() {
		Debug.printHeader("TermManagementToolbarDisplayContext.getSearchContainerId()");
		String searchContainerId = super.getNamespace()+"termSearchContainer";
		System.out.println("Search Container ID: "+searchContainerId);
		Debug.printFooter("TermManagementToolbarDisplayContext.getSearchContainerId()");
		return searchContainerId;
	}
	
	@Override
	public String getSearchActionURL() {
		Debug.printHeader("TermManagementToolbarDisplayContext.getSearchActionURL()");
		PortletURL searchURL = getPortletURL();
		System.out.println("searchURL: "+searchURL);
		Debug.printFooter("TermManagementToolbarDisplayContext.getSearchActionURL()");

		return searchURL.toString();
	}
	
	@Override
	public Boolean isSelectable() {
		Debug.printHeader("TermManagementToolbarDisplayContext.isSelectable()");
		System.out.println("Default selectable value: true");
		Debug.printFooter("TermManagementToolbarDisplayContext.isSelectable()");
		return true;
	}

	@Override
	protected String[] getDisplayViews() {
		Debug.printHeader("TermManagementToolbarDisplayContext.getDisplayViews()");
		System.out.println("Default Display View types are define in IcecapSSSConstants");
		Debug.printFooter("TermManagementToolbarDisplayContext.getDisplayViews()");
		return IcecapSSSConstants.VIEW_TYPES();
	}
	
	@Override
	protected String[] getNavigationKeys() {
		Debug.printHeader("TermManagementToolbarDisplayContext.getNavigationKeys()");
		System.out.println("Default NavigationKeys are define in IcecapSSSConstants");
		Debug.printFooter("TermManagementToolbarDisplayContext.getNavigationKeys()");
		return IcecapSSSConstants.NAVIGATION_KEYS();
	}
	
	// Dropdown Items for management toolbar. multi selection.
	// These items appear on the management toolbar.
	@Override
	public List<DropdownItem> getActionDropdownItems() {
		Debug.printHeader("TermManagementToolbarDisplayContext.getActionDropdownItems()");
		List<DropdownItem> itemList = 
					new DropdownItemList() {
						{
							boolean stagedActions = false;
							
							add(
								dropdownItem -> {
									dropdownItem.putData(ActionRequest.ACTION_NAME, MVCCommandNames.ACTION_TERM_DELETE);
									dropdownItem.setIcon("times-circle");
									dropdownItem.setLabel(LanguageUtil.get(request, "delete"));
									dropdownItem.setQuickAction(true);
								});
						}
					};
					
		Debug.printFooter("TermManagementToolbarDisplayContext.getActionDropdownItems()");
		return itemList;
	}
	

	public Map<String, Object> getComponentContext() throws PortalException {
		Debug.printHeader("TermManagementToolbarDisplayContext.getComponentContext()");
		Map<String, Object> context = new HashMap<>();

		String cmd = Constants.DELETE;

		if (_trashHelper.isTrashEnabled(_themeDisplay.getScopeGroup())) {
			cmd = Constants.MOVE_TO_TRASH;
		}

		context.put(IcecapSSSWebKeys.CMD_DELETE_TERMS, cmd);

		PortletURL deleteTermsURL = liferayPortletResponse.createActionURL();

		deleteTermsURL.setParameter(ActionRequest.ACTION_NAME, MVCCommandNames.RENDER_TERM_EDIT);
		
		System.out.println("ActionRequest.ACTION_NAME: "+ActionRequest.ACTION_NAME);

		context.put(IcecapSSSWebKeys.URL_DELETE_TERMS, deleteTermsURL.toString());

		context.put(
			IcecapSSSWebKeys.TRASH_ENABLED,
			_trashHelper.isTrashEnabled(_themeDisplay.getScopeGroupId()));

		Debug.printFooter("TermManagementToolbarDisplayContext.getComponentContext()");
		return context;
	}

	@Override
	public CreationMenu getCreationMenu() {
		if( !TermResourcePermissionHelper.contains(
				_themeDisplay.getPermissionChecker(), 
				_themeDisplay.getScopeGroupId(), 
				IcecapSSSActionKeys.ADD_TERM)) {
			System.out.println("Group "+_themeDisplay.getScopeGroupId()+ " is not permitted for ADD_TERM");
			return null;
		}
		
		CreationMenu menu = 
				new CreationMenu() {
					{
						addDropdownItem(
								dropdownItem -> {
									dropdownItem.setHref(
											liferayPortletResponse.createRenderURL(),
											IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_TERM_EDIT,
											IcecapSSSWebKeys.REDIRECT, currentURLObj.toString(),
											Constants.CMD, Constants.ADD);
									dropdownItem.setLabel(
											LanguageUtil.get(request, "add-term"));
								});
					}
				};
		
		return menu;
	}

	@Override
	public String getDefaultEventHandler() {
		System.out.println("getDefaultEventHandler() called.....");
		return "TERM_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		Debug.printHeader("TermManagementToolbarDisplayContext.getFilterLabelItems()");

//		final String keywords = _termDisplayContext.getKeywords();
		final String keywords = "test keywords";
		if (Validator.isNull(keywords) || Validator.isBlank(keywords)) {
			return null;
		}

		LabelItemList itemList =  new LabelItemList() {
			{
				PortletURL removeLabelURL = _liferayPortletResponse.createRenderURL();
				removeLabelURL.setParameter(IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_TERM_LIST);
				System.out.println("removeLabelURL: "+removeLabelURL.toString());
				
				List<String> words = StringUtil.split(keywords, ' ');
				for( String word : words ) {
					add(
						labelItem -> {
							removeLabelURL.setParameter(IcecapSSSWebKeys.NAVIGATION, (String)null);
							List<String> survivedWords = new ArrayList<>(words);
							removeLabelURL.setParameter(IcecapSSSWebKeys.KEYWORDS, StringUtil.merge(survivedWords, " "));
							labelItem.putData("removeLabelURL", removeLabelURL.toString());
	
							labelItem.setCloseable(true);
							System.out.println("closeable: true");
	
							String label = String.format("%s",  word);
	
							labelItem.setLabel(label);
						});
				}
			}
		};
		Debug.printFooter("TermManagementToolbarDisplayContext.getFilterLabelItems()");
		
		return itemList;
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems( ) {
		System.out.println("===== Begin TermManagementToolbarDisplayContext: getViewTypeItems() =====");
		PortletURL displayStyleURL = _liferayPortletResponse.createRenderURL(); 
		String keywords = ParamUtil.getString( _httpServletRequest, "keywords");
		String mvcCommandName = null;

		if (Validator.isNull(keywords)) {
			displayStyleURL.setParameter(	IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_TERM_LIST);
		}
		else {
			displayStyleURL.setParameter(	IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_SEARCH_TERMS);
		}
		
		ViewTypeItemList viewTypeItemList =
				new ViewTypeItemList( displayStyleURL, _displayStyle ) {
					{
						addCardViewTypeItem();
						addListViewTypeItem();
						addTableViewTypeItem();
					}
				};
		
		System.out.println("===== End of TermManagementToolbarDisplayContext: getViewTypeItems() =====");

		return viewTypeItemList;
	}

	@Override
	protected List<DropdownItem> getOrderByDropdownItems() {
		System.out.println("getOrderByDropdownItems() called.....");
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), IcecapSSSTermAttributes.TERM_NAME) );
						dropdownItem.setHref(
							_getCurrentSortingURL(), 
							IcecapSSSWebKeys.ORDER_BY_COL, IcecapSSSTermAttributes.TERM_NAME );
						dropdownItem.setLabel( LanguageUtil.get(request, "term-name") );
					});
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), Field.MODIFIED_DATE));
						dropdownItem.setHref(
							_getCurrentSortingURL(), 
							IcecapSSSWebKeys.ORDER_BY_COL, Field.MODIFIED_DATE);
						dropdownItem.setLabel(LanguageUtil.get(request, "version"));
					});
			}
		};
	}

	private PortletURL _getCurrentSortingURL() {
		System.out.println("getOrderByDropdownItems() called.....");
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter("mvcRenderCommandName", MVCCommandNames.RENDER_TERM_LIST);

		sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");

		String keywords = ParamUtil.getString(super.request, "keywords");

		if (Validator.isNotNull(keywords)) {
			sortingURL.setParameter("keywords", keywords);
		}

		return sortingURL;
	}

	@Override
	public List<DropdownItem> getFilterNavigationDropdownItems() {
		Debug.printHeader("TermManagementToolbarDisplayContext.getFilterNavigationDropdownItems()");
		long groupId = _termDisplayContext.getGroupId();

		System.out.format("%s: %s\n", "groupId", String.valueOf(groupId) );

		DropdownItemList itemList = new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						if( groupId == 0 ) {
							dropdownItem.setActive(true);
						}
						dropdownItem.setHref(getPortletURL(), IcecapSSSWebKeys.GROUP_ID, 0);
						dropdownItem.setLabel(LanguageUtil.get(request, IcecapSSSConstants.NAVIGATION_ALL));
					});
		
				long scopedGroupId = _themeDisplay.getScopeGroupId();
				Group scopedGroup = GroupLocalServiceUtil.fetchGroup(scopedGroupId);
				add(
					dropdownItem -> {
						dropdownItem.setActive(scopedGroupId == groupId );
						dropdownItem.setHref(
								getPortletURL(), 
								IcecapSSSWebKeys.GROUP_ID, scopedGroupId);
						dropdownItem.setLabel(
							HtmlUtil.escape(
									scopedGroup.getDescriptiveName(
									_themeDisplay.getLocale())));
					});
				
				add(
					dropdownItem -> {
						dropdownItem.setActive(scopedGroupId == groupId );
						dropdownItem.setHref(
								getPortletURL(), 
								IcecapSSSWebKeys.GROUP_ID, scopedGroupId);
						dropdownItem.setLabel(LanguageUtil.get(request, IcecapSSSConstants.NAVIGATION_MINE));
					});
			}
		};
		
		Debug.printHeader("TermManagementToolbarDisplayContext.getFilterNavigationDropdownItems()");
		return itemList;
	}

	@Override
	public int getItemsTotal() {
		return _searchContainer.getTotal();
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return IcecapSSSConstants.VIEW_TYPE_LIST;
	}

	@Override
	protected String getDisplayStyle() {
		return _displayStyle;
	}

	@Override
	protected String getNavigation() {
		return _navigation;
	}

	@Override
	protected Map<String, String> getNavigationEntriesMap() {
		String[] navigationKeys = getNavigationKeys();
		return super.getDefaultEntriesMap(navigationKeys);
	}

	@Override
	protected Map<String, String> getOrderByEntriesMap() {
		return super.getDefaultEntriesMap(getOrderByKeys());
	}

	@Override
	protected String[] getOrderByKeys() {
		String[] orderByKeys = {IcecapSSSTermAttributes.TERM_NAME, Field.MODIFIED_DATE };
		return orderByKeys;
	}
}
