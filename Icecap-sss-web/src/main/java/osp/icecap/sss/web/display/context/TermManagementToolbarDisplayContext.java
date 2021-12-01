package osp.icecap.sss.web.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
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
import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.web.security.permission.resource.TermManagerPortletResourcePermission;

public class TermManagementToolbarDisplayContext extends SearchContainerManagementToolbarDisplayContext{

	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;
	private final String _displayStyle;
	private final TermDisplayContext _termDisplayContext;

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

			_termDisplayContext = termDisplayContext;
			 _trashHelper = termDisplayContext.getTrashHelper();
			 _displayStyle = termDisplayContext.getDisplayStyle();
			
			_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	@Override
	public String getClearResultsURL() {
		System.out.println("getClearResultsURL() called.....");
		PortletURL clearResultsURL = super.getPortletURL();
		clearResultsURL.setParameter("keywords", StringPool.BLANK);
		return clearResultsURL.toString();
	}
	
	@Override
	public String getComponentId() {
		return "termManagementToolbar";
	}
	
	@Override
	public String getInfoPanelId() {
		return "termInfoPanelId";
	}
	
	@Override
	public String getSearchContainerId() {
		return "termSearchContainer";
	}
	
	@Override
	public String getSearchActionURL() {
		System.out.println("getSearchActionURL() called.....");
		PortletURL searchURL = getPortletURL();

		return searchURL.toString();
	}
	
	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getDisplayViews() {
		return IcecapSSSConstants.DISPLAY_VIEWS();
	}
	
	@Override
	protected String[] getNavigationKeys() {
		System.out.println("getNavigationKeys() called.....");
		return IcecapSSSConstants.NAVIGATION_KEYS();
	}
	
	@Override
	public List<DropdownItem> getActionDropdownItems() {
		System.out.println("getActionDropdownItems() called.....");
		return new DropdownItemList() {
			{
				System.out.println("***** Add dropdown lists.....");
				boolean stagedActions = false;
				
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteTerms");

						boolean trashEnabled = _trashHelper.isTrashEnabled(
							_themeDisplay.getScopeGroupId());

						dropdownItem.setIcon( trashEnabled ? "trash" : "times-circle" );

						String label = "delete";
						if (trashEnabled) {
							label = "move-to-recycle-bin";
						}
						dropdownItem.setLabel(LanguageUtil.get(request, label));

						dropdownItem.setQuickAction(true);
					});
				
				add(
			            dropdownItem -> {
			              dropdownItem.setHref("#edit");
			              dropdownItem.setLabel("Edit");
			            });
			}
		};
	}

	public Map<String, Object> getComponentContext() throws PortalException {
		System.out.println("getComponentContext() called.....");
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
		System.out.println("getCreationMenu() called.....");
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
										Constants.CMD, IcecapSSSActionKeys.ADD_TERM);
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
		System.out.println("getDefaultEventHandler() called.....");
		return "TERM_MANAGEMENT_TOOLBAR_DEFAULT_EVENT_HANDLER";
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {

		System.out.println("getFilterLabelItems: termsNavigation -"+super.getNavigation());
		if (!Objects.equals(super.getNavigation(), "mine")) {
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
							"%s: %s", 
							LanguageUtil.get(request, "owner"),
							user.getFullName());

						labelItem.setLabel(label);
					});
			}
		};
	}


	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		System.out.println("............getViewTypeItems..............");
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.RENDER_TERM_LIST);

		if (searchContainer.getDelta() > 0) {
			portletURL.setParameter(
				"delta", String.valueOf(searchContainer.getDelta()));
		}

		portletURL.setParameter("orderBycol", searchContainer.getOrderByCol());
		portletURL.setParameter("orderByType", searchContainer.getOrderByType());

		portletURL.setParameter("termsNavigation", getNavigation());

		if (searchContainer.getCur() > 0) {
			portletURL.setParameter("cur", String.valueOf(searchContainer.getCur()));
		}

		return new ViewTypeItemList(portletURL, _displayStyle) {
			{
				super.addCardViewTypeItem();

				super.addListViewTypeItem();

				super.addTableViewTypeItem();
			}
		};
	}


	@Override
	protected String getNavigationParam() {
		System.out.println("getNavigationParam() called.....");
		return "termsNavigation";
	}

	@Override
	protected List<DropdownItem> getOrderByDropdownItems() {
		System.out.println("getOrderByDropdownItems() called.....");
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
		long[] groupIds = _termDisplayContext.getSelectedGroupIds();

		if (groupIds.length <= 1) {
			return null;
		}

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							_termDisplayContext.getGroupId() == 0);
						dropdownItem.setHref(getPortletURL(), "groupId", 0);
						dropdownItem.setLabel(LanguageUtil.get(request, "all"));
					});

				for (long groupId : groupIds) {
					Group group = GroupLocalServiceUtil.fetchGroup(groupId);

					if (group == null) {
						continue;
					}

					add(
						dropdownItem -> {
							dropdownItem.setActive(
								_termDisplayContext.getGroupId() == groupId );
							dropdownItem.setHref(
								getPortletURL(), "groupId", groupId);
							dropdownItem.setLabel(
								HtmlUtil.escape(
									group.getDescriptiveName(
										_themeDisplay.getLocale())));
						});
				}
			}
		};
	}
}
