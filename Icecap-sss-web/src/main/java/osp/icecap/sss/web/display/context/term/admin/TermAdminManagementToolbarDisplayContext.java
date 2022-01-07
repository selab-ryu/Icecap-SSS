package osp.icecap.sss.web.display.context.term.admin;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;
import javax.servlet.http.HttpServletRequest;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.debug.Debug;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.security.permission.resource.TermModelPermissionHelper;
import osp.icecap.sss.security.permission.resource.TermResourcePermissionHelper;
import osp.icecap.sss.web.taglib.clay.TermVerticalCard;

public class TermAdminManagementToolbarDisplayContext 
						extends SearchContainerManagementToolbarDisplayContext{

	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;
	private final String _displayStyle;
	private final String _navigation;
	private final TermAdminDisplayContext _termAdminDisplayContext;
	
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	
	private final HttpServletRequest _httpServletRequest;
	private final PermissionChecker _permissionChecker;
	
	private final Locale _locale;

	public TermAdminManagementToolbarDisplayContext(
			RenderRequest renderRequest,
			RenderResponse renderResponse,
			TermAdminDisplayContext termAdminDisplayContext) {

		super(
				termAdminDisplayContext.getLiferayPortletRequest(), 
				termAdminDisplayContext.getLiferayPortletResponse(), 
				termAdminDisplayContext.getHttpServletRequest(),
				termAdminDisplayContext.getSearchContainer());
		
		_termAdminDisplayContext = termAdminDisplayContext;
		_liferayPortletRequest = _termAdminDisplayContext.getLiferayPortletRequest();
		_liferayPortletResponse = _termAdminDisplayContext.getLiferayPortletResponse();
		_httpServletRequest = _termAdminDisplayContext.getHttpServletRequest();

		 _trashHelper = _termAdminDisplayContext.getTrashHelper();
		 _displayStyle = _termAdminDisplayContext.getDisplayStyle();
		 _navigation = getNavigation();
		 
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		_locale = _themeDisplay.getLocale();
		_permissionChecker = _themeDisplay.getPermissionChecker();
		
	}

	@Override
	public String getClearResultsURL() {
//		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getClearResultsURL()");
		PortletURL clearResultsURL = super.getPortletURL();
		clearResultsURL.setParameter(IcecapSSSWebKeys.KEYWORDS, StringPool.BLANK);
//		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getClearResultsURL()");
		return clearResultsURL.toString();
	}
	
	@Override
	public String getSearchContainerId() {
//		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getSearchContainerId()");
		String searchContainerId = _termAdminDisplayContext.getSearchContainerId();
		
//		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getSearchContainerId()");
		return searchContainerId;
	}
	
	
	public Long[] getSelectedTerms(){
		List<ResultRow> rows = searchContainer.getResultRows();
		List<Long> selected = new ArrayList<Long>(); 
		System.out.println("++++ ");
		for( ResultRow row : rows ) {
			System.out.println(row.getState());
		}
		
		return (Long[])selected.toArray();
	}
	
	public SearchContainer<Term> getSearchContainer(){
		return searchContainer;
	}
	
	@Override
	public String getSearchActionURL() {
//		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getSearchActionURL()");
		RenderURL searchURL =  _liferayPortletResponse.createRenderURL();
		searchURL.setParameter(
				IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME,
				MVCCommandNames.RENDER_ADMIN_SEARCH_TERMS);
//		System.out.println("searchURL: "+searchURL);
//		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getSearchActionURL()");

		return searchURL.toString();
	}
	
	@Override
	protected String[] getDisplayViews() {
//		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getDisplayViews()");
		String[] viewTypes = new String[] { 
				IcecapSSSConstants.VIEW_TYPE_CARDS, 
				IcecapSSSConstants.VIEW_TYPE_LIST,
				IcecapSSSConstants.VIEW_TYPE_TABLE};
//		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getDisplayViews()");
		return viewTypes;
	}
	
	@Override
	protected String[] getNavigationKeys() {
//		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getNavigationKeys()");
		System.out.println("Default NavigationKeys are define in IcecapSSSConstants");
//		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getNavigationKeys()");
		return IcecapSSSConstants.NAVIGATION_KEYS();
	}
	
	// Dropdown Items for management toolbar. multi selection.
	// These items appear on the management toolbar.
	@Override
	public List<DropdownItem> getActionDropdownItems() {
//		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getActionDropdownItems()");
		List<DropdownItem> itemList = 
					new DropdownItemList() {
						{
							boolean stagedActions = false;
							
							PortletURL actionURL = _liferayPortletResponse.createActionURL();
							
							add(
								dropdownItem -> {
									dropdownItem.setIcon("trash");
									dropdownItem.setLabel(LanguageUtil.get(request, "delete"));
									dropdownItem.setQuickAction(true);
									dropdownItem.putData("cmd", IcecapSSSActionKeys.DELETE_TERMS);
								});
						}
					};
					
//		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getActionDropdownItems()");
		return itemList;
	}
	
	public List<String> getAvailableActions( Term term ){
		if( Validator.isNull(_termAdminDisplayContext)) {
			return null;
		}
		
		try {
			return _termAdminDisplayContext.getAvailableActions(term);
		} catch (PortalException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getBulkActionURL() {
		ActionURL actionURL = _liferayPortletResponse.createActionURL();
		actionURL.setParameter("actionName", MVCCommandNames.ACTION_ADMIN_BULK_ACTIONS);
		
		return actionURL.toString();
	}
	
	public List<DropdownItem> getTermActionDropdownItems( long termId){
//		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getTermActionDropdownItems()");
		List<DropdownItem> itemList = 
				new DropdownItemList() {
					{
						if (_hasUpdatePermission( termId )) {
							add(dropdownItem -> {
								dropdownItem.setHref(
										_liferayPortletResponse.createRenderURL(), 
										IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_ADMIN_TERM_EDIT, 
										Constants.CMD, Constants.UPDATE,
										IcecapSSSWebKeys.REDIRECT, _getRedirectURL(), 
										IcecapSSSWebKeys.TERM_ID, termId);

									dropdownItem.setIcon("edit");
									dropdownItem.setLabel(LanguageUtil.get(_locale, "edit"));
							});
						}

						if (_hasDeletePermission( termId )) {
							PortletURL deleteURL =  _liferayPortletResponse.createActionURL();
							
							long[] termIds = { termId};
							deleteURL.setParameter(ActionRequest.ACTION_NAME, MVCCommandNames.ACTION_ADMIN_TERM_DELETE);
							deleteURL.setParameter(Constants.CMD, Constants.DELETE);
							deleteURL.setParameter(IcecapSSSWebKeys.REDIRECT, _getRedirectURL());
							deleteURL.setParameter(IcecapSSSWebKeys.TERM_IDS, Arrays.toString(termIds) );
							
							System.out.println("deleteURL: "+deleteURL.toString());

							add( dropdownItem -> {
								dropdownItem.setHref(deleteURL);
								dropdownItem.setIcon("delete");
								dropdownItem.setLabel(
									LanguageUtil.get(_httpServletRequest, "delete"));
							});		
						}
					}
				};
				
//		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getTermActionDropdownItems()");
		return itemList;
	}

	@Override
	public CreationMenu getCreationMenu() {
		if( !TermResourcePermissionHelper.contains(
				_permissionChecker, 
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
											IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_ADMIN_TERM_EDIT,
											IcecapSSSWebKeys.REDIRECT, currentURLObj.toString(),
											Constants.CMD, Constants.ADD);
									dropdownItem.setLabel(
											LanguageUtil.get(request, "add-term"));
								});
					}
				};
		
		return menu;
	}
	
	public TermVerticalCard getVerticalCard( 
			Term term, 
			RenderRequest renderRequest,
			RenderResponse renderResponse,
			RowChecker rowChecker,
			String termViewURL) {
		
		return new TermVerticalCard(
				term, 
				renderRequest, 
				renderResponse, 
				rowChecker, 
				termViewURL, 
				getTermActionDropdownItems(term.getTermId()));
	}

	private boolean _hasDeletePermission( long termId ) {
		boolean hasPermission = false;
		try {
			hasPermission = TermModelPermissionHelper.contains(
						_permissionChecker, termId, ActionKeys.DELETE);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Delete Permission: "+hasPermission);
		return hasPermission;
	}
	
	private boolean _hasUpdatePermission( long termId ) {
		boolean hasPermission = false;
		try {
			hasPermission = TermModelPermissionHelper.contains(
				_permissionChecker, termId, ActionKeys.UPDATE);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Update Permission: "+hasPermission);
		return hasPermission;
	}
	
	private String _getRedirectURL() {
		PortletURL redirectURL = _liferayPortletResponse.createRenderURL();

		redirectURL.setParameter(
				IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_ADMIN_TERM_LIST);
		redirectURL.setParameter(	IcecapSSSWebKeys.DISPLAY_STYLE, _displayStyle);

		return redirectURL.toString();
	}

	@Override
	public int getItemsTotal() {
		return searchContainer.getTotal();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		if (ArrayUtil.isEmpty(getDisplayViews())) {
			return null;
		}

		List<ViewTypeItem> viewTypeItemList = new ArrayList<ViewTypeItem>();

		String[] displayViews = getDisplayViews();
		
		RenderURL renderURL = _liferayPortletResponse.createRenderURL();
		
		String keywords = _termAdminDisplayContext.getKeywords();
		String renderCommand = MVCCommandNames.RENDER_ADMIN_TERM_LIST;
		if( Validator.isNotNull(keywords) && !keywords.isEmpty() ) {
			renderCommand = MVCCommandNames.RENDER_ADMIN_SEARCH_TERMS;
		}
		
		if (ArrayUtil.contains(displayViews, IcecapSSSConstants.VIEW_TYPE_CARDS)) {
			ViewTypeItem viewType = new ViewTypeItem();
			
			viewType.setActive( _displayStyle.equals(IcecapSSSConstants.VIEW_TYPE_CARDS) );
			viewType.setHref(renderURL,
						IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, renderCommand,
						IcecapSSSWebKeys.DISPLAY_STYLE, IcecapSSSConstants.VIEW_TYPE_CARDS,
						IcecapSSSWebKeys.KEYWORDS, keywords);
			viewType.setIcon("cards2");
			viewType.setLabel(LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "cards"));
			viewTypeItemList.add(viewType);
		}

		if (ArrayUtil.contains(displayViews, IcecapSSSConstants.VIEW_TYPE_LIST)) {
			ViewTypeItem viewType = new ViewTypeItem();
			
			viewType.setActive( _displayStyle.equals(IcecapSSSConstants.VIEW_TYPE_LIST) );
			viewType.setHref(renderURL,
						IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, renderCommand,
						IcecapSSSWebKeys.DISPLAY_STYLE, IcecapSSSConstants.VIEW_TYPE_LIST,
						IcecapSSSWebKeys.KEYWORDS, keywords);
			viewType.setIcon("list");
			viewType.setLabel(LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "list"));
			viewTypeItemList.add(viewType);
		}

		if (ArrayUtil.contains(displayViews, IcecapSSSConstants.VIEW_TYPE_TABLE)) {
			ViewTypeItem viewType = new ViewTypeItem();
			
			viewType.setActive( _displayStyle.equals(IcecapSSSConstants.VIEW_TYPE_TABLE) );
			viewType.setHref(renderURL,
						IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, renderCommand,
						IcecapSSSWebKeys.DISPLAY_STYLE, IcecapSSSConstants.VIEW_TYPE_TABLE,
						IcecapSSSWebKeys.KEYWORDS, keywords);
			viewType.setIcon("table");
			viewType.setLabel(LanguageUtil.get(LocaleUtil.getMostRelevantLocale(), "table"));
			viewTypeItemList.add(viewType);
		}
		
		return viewTypeItemList;
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return IcecapSSSConstants.VIEW_TYPE_TABLE;
	}

	@Override
	protected String getDisplayStyle() {
		return _termAdminDisplayContext.getDisplayStyle();
	}

	@Override
	public String getSortingURL() {
		Debug.printHeader("++++ getSortingURL");
		_termAdminDisplayContext.getKeywords();
		return super.getSortingURL();
	}

	@Override
	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		// TODO Auto-generated method stub
		return super.getFilterNavigationDropdownItems();
	}

	@Override
	protected List<DropdownItem> getOrderByDropdownItems() {
		// TODO Auto-generated method stub
		return super.getOrderByDropdownItems();
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {LanguageUtil.get(_locale, "parameter-name"), LanguageUtil.get(_locale, "modified-date")};
	}

	@Override
	public Boolean isSelectable() {
		return true;
	}

	@Override
	public Boolean getSupportsBulkActions() {
		return true;
	}

	@Override
	public Boolean isShowAdvancedSearch() {
		return false;
	}

	@Override
	public String getComponentId() {
		return "termAdminManagementToolbar";
	}

	@Override
	public String getSearchFormMethod() {
		System.out.println("----- Search Form Method: "+super.getSearchFormMethod());
		return super.getSearchFormMethod();
	}

	@Override
	public String getSearchFormName() {
		System.out.println("----- Search Form Name: "+super.getSearchFormName());
		return "searchForm";
//		return super.getSearchFormName();
	}

	@Override
	public String getSearchInputName() {
		System.out.println("----- Search Input Name: "+super.getSearchInputName());
		return super.getSearchInputName();
	}

	@Override
	public String getSearchValue() {
		System.out.println("----- Search Value: "+super.getSearchValue());
		return super.getSearchValue();
	}

	@Override
	public int getSelectedItems() {
		System.out.println("----- Selected Items: "+super.getSelectedItems());
		return super.getSelectedItems();
	}

	@Override
	public String getDefaultEventHandler() {
		if( Validator.isNull(super.getDefaultEventHandler())) {
			System.out.println("Default Event Handler is null.");
			return null;
		}
		else {
			System.out.println("Default Event Handler: "+super.getDefaultEventHandler());
		}
		return super.getDefaultEventHandler();
	}
}
