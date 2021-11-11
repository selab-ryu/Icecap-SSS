package osp.icecap.sss.web.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.web.security.permission.resource.TermManagerPortletResourcePermission;
import osp.icecap.sss.web.security.permission.resource.TermModelResourcePermission;

public class TermManagementToolbarDisplayContext extends SearchContainerManagementToolbarDisplayContext{

	public TermManagementToolbarDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			HttpServletRequest httpServletRequest, 
			SearchContainer<Term> searchContainer,
			TrashHelper trashHelper, 
			String displayStyle) {
		super(
				liferayPortletRequest, 
				liferayPortletResponse, 
				httpServletRequest,
				searchContainer);

			_trashHelper = trashHelper;
			_displayStyle = displayStyle;
			
			_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteTerms");

						boolean trashEnabled = _trashHelper.isTrashEnabled(
							_themeDisplay.getScopeGroupId());

						dropdownItem.setIcon(
							trashEnabled ? "trash" : "times-circle");

						String label = "delete";

						if (trashEnabled) {
							label = "move-to-recycle-bin";
						}

						dropdownItem.setLabel(LanguageUtil.get(request, label));

						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	@Override
	public String getClearResultsURL() {
		return getSearchActionURL();
	}

	public Map<String, Object> getComponentContext() throws PortalException {
		Map<String, Object> context = new HashMap<>();

		String cmd = Constants.DELETE;

		if (_trashHelper.isTrashEnabled(_themeDisplay.getScopeGroup())) {
			cmd = Constants.MOVE_TO_TRASH;
		}

		context.put(IcecapSSSWebKeys.CMD_DELETE_TERMS, cmd);

		PortletURL deleteTermsURL = liferayPortletResponse.createActionURL();

		deleteTermsURL.getRenderParameters().setValue(ActionRequest.ACTION_NAME, MVCCommandNames.RENDER_TERM_EDIT);
		
		System.out.println("ActionRequest.ACTION_NAME: "+ActionRequest.ACTION_NAME);

		context.put(IcecapSSSWebKeys.URL_DELETE_TERMS, deleteTermsURL.toString());

		context.put(
			IcecapSSSWebKeys.TRASH_ENABLED,
			_trashHelper.isTrashEnabled(_themeDisplay.getScopeGroupId()));

		return context;
	}

	@Override
	public CreationMenu getCreationMenu() {

		if( TermManagerPortletResourcePermission.contains(
				_themeDisplay.getPermissionChecker(), 
				_themeDisplay.getScopeGroupId(), 
				IcecapSSSActionKeys.ADD_TERM)) {
			return new CreationMenu() {
				{
					addDropdownItem(
							dropdownItem -> {
								dropdownItem.setHref(
										liferayPortletResponse.createRenderURL(),
										"mvcRenderCommandName", MVCCommandNames.RENDER_TERM_EDIT,
										"redirect", currentURLObj.toString(),
										Constants.CMD, Constants.ADD);
								dropdownItem.setLabel(
										LanguageUtil.get(request, "add-term"));
							});
				}
			};
		}

		System.out.println("Group "+_themeDisplay.getScopeGroupId()+ " is not permitted for ADD_TERM");
		return null; 
	}

	@Override
	public String getDefaultEventHandler() {
		return "TERM_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		if (!Objects.equals(getNavigation(), "mine")) {
			return null;
		}

		return new LabelItemList() {
			{
				add(
					labelItem -> {
						PortletURL removeLabelURL = getPortletURL();

						removeLabelURL.setParameter(
							"termsNavigation", (String)null);

						labelItem.putData(
							"removeLabelURL", removeLabelURL.toString());

						labelItem.setCloseable(true);

						User user = _themeDisplay.getUser();

						String label = String.format(
							"%s: %s", LanguageUtil.get(request, "owner"),
							user.getFullName());

						labelItem.setLabel(label);
					});
			}
		};
	}

	@Override
	public String getSearchActionURL() {
		PortletURL searchURL = liferayPortletResponse.createRenderURL();

		searchURL.setParameter("mvcRenderCommandName", MVCCommandNames.RENDER_TERM_LIST);

		String navigation = ParamUtil.getString(
			super.request, "navigation", "terms");

		searchURL.setParameter("navigation", navigation);

		searchURL.setParameter("orderByCol", getOrderByCol());
		searchURL.setParameter("orderByType", getOrderByType());

		return searchURL.toString();
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.RENDER_TERM_LIST);

		if (searchContainer.getDelta() > 0) {
			portletURL.setParameter(
				"delta", String.valueOf(searchContainer.getDelta()));
		}

		portletURL.setParameter("orderBycol", searchContainer.getOrderByCol());
		portletURL.setParameter(
			"orderByType", searchContainer.getOrderByType());

		portletURL.setParameter("termsNavigation", getNavigation());

		if (searchContainer.getCur() > 0) {
			portletURL.setParameter(
				"cur", String.valueOf(searchContainer.getCur()));
		}

		return new ViewTypeItemList(portletURL, _displayStyle) {
			{
				addCardViewTypeItem();

				addListViewTypeItem();

				addTableViewTypeItem();
			}
		};
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all", "mine"};
	}

	@Override
	protected String getNavigationParam() {
		return "termsNavigation";
	}

	@Override
	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "name"));
						dropdownItem.setHref(
							_getCurrentSortingURL(), "orderByCol", "name");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "name"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "version"));
						dropdownItem.setHref(
							_getCurrentSortingURL(), "orderByCol",
							"version");
						dropdownItem.setLabel(
							LanguageUtil.get(request, "version"));
					});
			}
		};
	}

	private PortletURL _getCurrentSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter("mvcRenderCommandName", MVCCommandNames.RENDER_TERM_LIST);

		sortingURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");

		String keywords = ParamUtil.getString(super.request, "keywords");

		if (Validator.isNotNull(keywords)) {
			sortingURL.setParameter("keywords", keywords);
		}

		return sortingURL;
	}
	
	private final String _displayStyle;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;
}
