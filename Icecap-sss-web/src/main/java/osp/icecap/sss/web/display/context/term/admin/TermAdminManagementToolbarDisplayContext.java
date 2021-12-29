package osp.icecap.sss.web.display.context.term.admin;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
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
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.MimeResponse.Copy;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;
import javax.servlet.http.HttpServletRequest;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.debug.Debug;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.security.permission.resource.TermModelPermissionHelper;
import osp.icecap.sss.security.permission.resource.TermResourcePermissionHelper;

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
		 System.out.println("***** TermAdminManagementToolbarDisplayContext(). _navigation: "+_navigation);
		 
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		_locale = _themeDisplay.getLocale();
		_permissionChecker = _themeDisplay.getPermissionChecker();
		
		System.out.println(	"Search Container ID: "+super.getSearchContainerId());
	}

	@Override
	public String getClearResultsURL() {
		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getClearResultsURL()");
		PortletURL clearResultsURL = super.getPortletURL();
		clearResultsURL.setParameter(IcecapSSSWebKeys.KEYWORDS, StringPool.BLANK);
		System.out.println("clearResultsURL: "+clearResultsURL.toString());
		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getClearResultsURL()");
		return clearResultsURL.toString();
	}
	
	@Override
	public String getSearchContainerId() {
		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getSearchContainerId()");
		String searchContainerId = super.getNamespace()+IcecapSSSConstants.SEARCH_CONTAINER_ID;
		
		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getSearchContainerId()");
		return searchContainerId;
	}
	
	public SearchContainer<Term> getSearchContainer(){
		return _termAdminDisplayContext.getSearchContainer();
	}
	
	@Override
	public String getSearchActionURL() {
		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getSearchActionURL()");
		RenderURL searchURL =  _liferayPortletResponse.createRenderURL();
		searchURL.setParameter(
				IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME,
				MVCCommandNames.RENDER_ADMIN_SEARCH_TERMS);
		System.out.println("searchURL: "+searchURL);
		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getSearchActionURL()");

		return searchURL.toString();
	}
	
	@Override
	protected String[] getDisplayViews() {
		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getDisplayViews()");
		System.out.println("Default Display View types are define in IcecapSSSConstants");
		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getDisplayViews()");
		return IcecapSSSConstants.VIEW_TYPES();
	}
	
	@Override
	protected String[] getNavigationKeys() {
		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getNavigationKeys()");
		System.out.println("Default NavigationKeys are define in IcecapSSSConstants");
		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getNavigationKeys()");
		return IcecapSSSConstants.NAVIGATION_KEYS();
	}
	
	// Dropdown Items for management toolbar. multi selection.
	// These items appear on the management toolbar.
	@Override
	public List<DropdownItem> getActionDropdownItems() {
		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getActionDropdownItems()");
		List<DropdownItem> itemList = 
					new DropdownItemList() {
						{
							boolean stagedActions = false;
							
							add(
								dropdownItem -> {
									dropdownItem.putData(ActionRequest.ACTION_NAME, MVCCommandNames.ACTION_ADMIN_TERM_DELETE);
									dropdownItem.setIcon("times-circle");
									dropdownItem.setLabel(LanguageUtil.get(request, "delete"));
									dropdownItem.setQuickAction(true);
								});
						}
					};
					
		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getActionDropdownItems()");
		return itemList;
	}
	
	public List<DropdownItem> getTermActionDropdownItems( long termId){
		Debug.printHeader("TermAdminManagementToolbarDisplayContext.getTermActionDropdownItems()");
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
				
		Debug.printFooter("TermAdminManagementToolbarDisplayContext.getTermActionDropdownItems()");
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

		return new ViewTypeItemList(getPortletURL(), getDisplayStyle()) {
			{
				if (ArrayUtil.contains(getDisplayViews(), "card")) {
					ViewTypeItem viewType = addCardViewTypeItem();
				}

				if (ArrayUtil.contains(getDisplayViews(), "list")) {
					ViewTypeItem viewType = addListViewTypeItem();
				}

				if (ArrayUtil.contains(getDisplayViews(), "table")) {
					ViewTypeItem viewType = addTableViewTypeItem();
				}
			}
		};
	}

	@Override
	protected String getDefaultDisplayStyle() {
		return IcecapSSSConstants.VIEW_TYPE_TABLE;
	}

	@Override
	protected String getDisplayStyle() {
		return _termAdminDisplayContext.getDisplayStyle();
	}
}
