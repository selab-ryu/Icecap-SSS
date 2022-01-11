package osp.icecap.sss.web.display.context.term.admin;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.TrashHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.security.permission.resource.TermModelPermissionHelper;
import osp.icecap.sss.service.TermLocalService;

public class TermAdminDisplayContext implements Serializable{
	
	private static final Log _log = LogFactoryUtil.getLog(TermAdminDisplayContext.class);

	private final LiferayPortletRequest _renderRequest;
	private final LiferayPortletResponse _renderResponse;
	private final HttpServletRequest _httpServletRequest;
	private TermLocalService _termLocalService;
	private TrashHelper _trashHelper;
	private Integer _status;
	private String _navigation;
	private String _displayStyle;
	private String _keywords;
	private Boolean _multipleSelection;
	private Boolean _showAddButton;
	private Long _groupId;
	private String _orderByCol;
	private String _orderByType;
	private String _eventName;
	private Boolean _showScheduled;
	private String _namespace;
	private ThemeDisplay _themeDisplay;

	public TermAdminDisplayContext (
			LiferayPortletRequest renderRequest,
			LiferayPortletResponse renderResponse,
			HttpServletRequest httpServletRequest,
			TermLocalService termLocalService,
			TrashHelper trashHelper) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_httpServletRequest = httpServletRequest;
		_termLocalService = termLocalService;
		_trashHelper = trashHelper;
		
		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		_namespace = _themeDisplay.getPortletDisplay().getNamespace();
	}

	public LiferayPortletRequest getLiferayPortletRequest() {
		return _renderRequest;
	}
	
	public LiferayPortletResponse getLiferayPortletResponse() {
		return _renderResponse;
	}

	public HttpServletRequest getHttpServletRequest() {
		return _httpServletRequest;
	}

	public SearchContainer<Term> getSearchContainer(){

		PortletURL portletURL = this.getPortletURL();

		portletURL.setParameter(IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_TERM_LIST);

		String navigation = ParamUtil.getString(	_httpServletRequest, IcecapSSSWebKeys.NAVIGATION, IcecapSSSConstants.NAVIGATION_MINE);
		System.out.println("TermDisplayContext:getSearchContainer:termsNavigation - "+navigation);

		portletURL.setParameter(IcecapSSSWebKeys.NAVIGATION, navigation);

		_displayStyle = getDisplayStyle();
		
		SearchContainer<Term>searchContainer =
					new SearchContainer<Term>(
										_renderRequest,
										portletURL,
										null,
										"no-terms-were-found");
		
		searchContainer.setId(getSearchContainerId());
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByType(_getOrderByType());
		
		OrderByComparator<Term> orderByComparator = 
				_termLocalService.getOrderByNameComparator(
							searchContainer.getOrderByCol(), 
							searchContainer.getOrderByType() );
		
		searchContainer.setOrderByComparator( orderByComparator );
		
		searchContainer.setRowChecker( new EmptyOnClickRowChecker(_renderResponse) );
		
		try {
			_populateResults(searchContainer);
		} catch (PortalException e) {
			e.printStackTrace();
		}
		
		return searchContainer;
	}
	
	public String getSearchContainerId() {
		return IcecapSSSConstants.SEARCH_CONTAINER_ID;
	}

	public List<String> getAvailableActions(Term term)
		throws PortalException {

		List<String> availableActionDropdownItems = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (TermModelPermissionHelper.contains(
				permissionChecker, term, IcecapSSSActionKeys.DELETE_TERM)) {

			availableActionDropdownItems.add(IcecapSSSActionKeys.DELETE_TERM);
		}
		
		if (TermModelPermissionHelper.contains(
				permissionChecker, term, IcecapSSSActionKeys.UPDATE_TERM)) {

			availableActionDropdownItems.add(IcecapSSSActionKeys.UPDATE_TERM);
		}
		
		if (TermModelPermissionHelper.contains(
				permissionChecker, term, IcecapSSSActionKeys.ADD_TERM)) {

			availableActionDropdownItems.add(IcecapSSSActionKeys.ADD_TERM);
		}

		if (TermModelPermissionHelper.contains(
				permissionChecker, term, IcecapSSSActionKeys.REVIEW_TERM)) {

			availableActionDropdownItems.add(IcecapSSSActionKeys.REVIEW_TERM);
		}

		if (TermModelPermissionHelper.contains(
				permissionChecker, term, IcecapSSSActionKeys.APPROVE_TERM)) {

			availableActionDropdownItems.add(IcecapSSSActionKeys.APPROVE_TERM);
		}

		return availableActionDropdownItems;
	}

	public String getDisplayStyle() {
		System.out.println("TermDisplayContext.getDisplayStyle() called.......");
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, IcecapSSSWebKeys.DISPLAY_STYLE, IcecapSSSConstants.VIEW_TYPE_TABLE);

		return _displayStyle;
	}
	
	public String getEventName() {
		if (_eventName != null) {
			return _eventName;
		}

		_eventName = ParamUtil.getString(
			_httpServletRequest, IcecapSSSWebKeys.EVENT_NAME,
			_renderResponse.getNamespace() + IcecapSSSWebKeys.SELECT_TERM);

		return _eventName;
	}

	public boolean isShowAddButton() {
		if (_showAddButton != null) {
			return _showAddButton;
		}

		_showAddButton = ParamUtil.getBoolean(
			_httpServletRequest, IcecapSSSWebKeys.SHOW_ADD_BUTTON);

		return _showAddButton;
	}
	
	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_httpServletRequest, IcecapSSSWebKeys.GROUP_ID);

		return _groupId;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(IcecapSSSWebKeys.GROUP_ID, String.valueOf(getGroupId()));

		if (_getListable() != null) {
			portletURL.setParameter(IcecapSSSWebKeys.LISTABLE, String.valueOf(_getListable()));
		}

		if (isMultipleSelection()) {
			portletURL.setParameter(
				IcecapSSSWebKeys.MULTIPLE_SELECTION, Boolean.TRUE.toString());
		}

		if (isShowAddButton()) {
			portletURL.setParameter(IcecapSSSWebKeys.SHOW_ADD_BUTTON, Boolean.TRUE.toString());
		}

		portletURL.setParameter(
				IcecapSSSWebKeys.SHOW_SCHEDULED, String.valueOf(_isShowScheduled()));

		portletURL.setParameter(IcecapSSSWebKeys.EVENT_NAME, getEventName());
		
		return portletURL;
	}

	public TrashHelper getTrashHelper() {
		return _trashHelper;
	}

	public boolean isMultipleSelection() {
		if (_multipleSelection != null) {
			return _multipleSelection;
		}

		_multipleSelection = ParamUtil.getBoolean(
			_httpServletRequest, IcecapSSSWebKeys.MULTIPLE_SELECTION);

		return _multipleSelection;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, IcecapSSSWebKeys.KEYWORDS);

		return _keywords;
	}
	
	public boolean hasKeywords() {
		if( Validator.isNull(_keywords) || _keywords.isEmpty() ) {
			return false;
		}
		
		return true;
	}

	private int[] _getStatuses() {
		int[] statuses = {getStatus()};

		if (_isShowScheduled()) {
			statuses = new int[] {
				WorkflowConstants.STATUS_APPROVED,
				WorkflowConstants.STATUS_SCHEDULED
			};
		}

		return statuses;
	}
	
	private int getStatus() {
		_status = ParamUtil.getInteger(_httpServletRequest, IcecapSSSTermAttributes.STATUS, WorkflowConstants.STATUS_ANY);

		return _status;
	}

	private void _populateResults(SearchContainer<Term> searchContainer)
			throws PortalException {

			System.out.println("_populateResults");
			ThemeDisplay themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(	WebKeys.THEME_DISPLAY);

			List<Term> entriesResults = null;

			long assetCategoryId = ParamUtil.getLong(_httpServletRequest, IcecapSSSWebKeys.CATEGORY_ID);
			String assetTagName = ParamUtil.getString(_httpServletRequest, IcecapSSSWebKeys.TAG);

			String keywords = ParamUtil.getString(_httpServletRequest, IcecapSSSWebKeys.KEYWORDS, null);
			System.out.println("_populateResults: keywords - "+keywords);
			
			getStatus();
			// Browse through category system. Use Asset service
			if ((assetCategoryId != 0) || Validator.isNotNull(assetTagName)) {
				AssetEntryQuery assetEntryQuery = new AssetEntryQuery(Term.class.getName(), searchContainer);

				assetEntryQuery.setExcludeZeroViewCount(false);
				assetEntryQuery.setOrderByCol1(IcecapSSSTermAttributes.TERM_NAME);
				assetEntryQuery.setVisible(Boolean.TRUE);

				int total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

				assetEntryQuery.setEnd(searchContainer.getEnd());
				assetEntryQuery.setStart(searchContainer.getStart());

				List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(assetEntryQuery);

				searchContainer.setTotal(total);

				entriesResults = new ArrayList<>(assetEntries.size());

				for (AssetEntry assetEntry : assetEntries) {
					entriesResults.add(_termLocalService.getTerm(assetEntry.getClassPK()));
				}
			}
			// Keywords are not presented
			else if (Validator.isNull(keywords)) {
				String termsNavigation = ParamUtil.getString( _httpServletRequest, IcecapSSSWebKeys.NAVIGATION);

				if (termsNavigation.equals(IcecapSSSConstants.NAVIGATION_MINE)) {
					System.out.println("");
					searchContainer.setTotal(
						_termLocalService.countTermsByG_U_S(
							themeDisplay.getScopeGroupId(),
							themeDisplay.getUserId(),
							_status));

					entriesResults = _termLocalService.getTermsByG_U_S(
							themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
							_status, searchContainer.getStart(),
							searchContainer.getEnd()); //,
					//searchContainer.getOrderByComparator());
				}
				else if( termsNavigation.equals(IcecapSSSConstants.NAVIGATION_GROUP)){
					System.out.println("Group Total: "+_termLocalService.countTermsByG_S(
							themeDisplay.getScopeGroupId(),
							_status));
					searchContainer.setTotal(
						_termLocalService.countTermsByG_S(
							themeDisplay.getScopeGroupId(),
							_status));
					
					entriesResults = _termLocalService.getTermsByG_S(
							themeDisplay.getScopeGroupId(),
							_status, 
							searchContainer.getStart(),
							searchContainer.getEnd());// ,
					//searchContainer.getOrderByComparator());
				}
				else {
					System.out.println("All Total: "+_termLocalService.countTermsByStatus(_status) );
					searchContainer.setTotal(_termLocalService.countTermsByStatus(_status));
					
					entriesResults = _termLocalService.getTermsByStatus(
							_status,
							searchContainer.getStart(),
							searchContainer.getEnd());// ,
					//searchContainer.getOrderByComparator());
				}
			}
			// If keywords are presented, it should use search engine!!!
			else {
				Indexer<Term> indexer = IndexerRegistryUtil.getIndexer(Term.class);

				if( Validator.isNull(indexer) ) {
					System.out.println("Indexer for term does not exist!!!");
				} else {
					System.out.println("Indexer for term exists: "+indexer.getClassName());
				}
				
				SearchContext searchContext = SearchContextFactory.getInstance(
					_httpServletRequest);

				searchContext.setAttribute(Field.STATUS, _getStatuses());
				searchContext.setStart(searchContainer.getStart());
				searchContext.setEnd(searchContainer.getEnd());
				searchContext.setIncludeDiscussions(true);
				searchContext.setKeywords(keywords);

				String termsNavigation = ParamUtil.getString(
					_httpServletRequest, IcecapSSSWebKeys.NAVIGATION);
				System.out.println("TermDisplayContext: _populateResults: termsNavigation - "+termsNavigation);

				if (termsNavigation.equals(IcecapSSSConstants.NAVIGATION_MINE)) {
					searchContext.setOwnerUserId(themeDisplay.getUserId());
				}

				String orderByCol = ParamUtil.getString(
					_httpServletRequest, IcecapSSSWebKeys.ORDER_BY_COL, IcecapSSSTermAttributes.TERM_NAME);
				String orderByType = ParamUtil.getString(
					_httpServletRequest, IcecapSSSWebKeys.ORDER_BY_TYPE, IcecapSSSConstants.ASC);
				System.out.println("TermDisplayContext: _populateResults: orderByCol - "+orderByCol);
				System.out.println("TermDisplayContext: _populateResults: orderByType - "+orderByType);

				Sort sort = null;

				boolean orderByAsc = true;

				if (Objects.equals(orderByType, IcecapSSSConstants.DSC)) {
					orderByAsc = false;
				}

				if (Objects.equals(orderByCol, IcecapSSSTermAttributes.TERM_NAME)) {
					sort = new Sort(IcecapSSSTermAttributes.TERM_NAME, Sort.STRING_TYPE, orderByAsc);
				}
				else {
					sort = new Sort(orderByCol, orderByAsc);
				}

				searchContext.setSorts(sort);

				Hits hits = indexer.search(searchContext);

				searchContainer.setTotal(hits.getLength());

				List<SearchResult> searchResults =
					SearchResultUtil.getSearchResults(
						hits, LocaleUtil.getDefault());

				Stream<SearchResult> stream = searchResults.stream();

				entriesResults = stream.map(
					this::_toTermOptional
				).filter(
					Optional::isPresent
				).map(
					Optional::get
				).collect(
					Collectors.toList()
				);
			}

			searchContainer.setResults(entriesResults);
		}

		private Boolean _getListable() {
			Boolean listable = null;

			String listableValue = ParamUtil.getString(
				_httpServletRequest, IcecapSSSWebKeys.LISTABLE, null);

			if (Validator.isNotNull(listableValue)) {
				listable = ParamUtil.getBoolean(
					_httpServletRequest, IcecapSSSWebKeys.LISTABLE, true);
			}

			return listable;
		}
		
	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, IcecapSSSWebKeys.ORDER_BY_COL, IcecapSSSTermAttributes.TERM_NAME);

		return _orderByCol;
	}
	
	private String _getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, IcecapSSSWebKeys.ORDER_BY_TYPE, IcecapSSSConstants.ASC);

		return _orderByType;
	}
	
	private boolean _isShowScheduled() {
		if (_showScheduled != null) {
			return _showScheduled;
		}

		_showScheduled = ParamUtil.getBoolean(
			_httpServletRequest, IcecapSSSWebKeys.SHOW_SCHEDULED);

		return _showScheduled;
	}

	private Optional<Term> _toTermOptional(SearchResult searchResult) {

		try {
			return Optional.of(
				_termLocalService.getTerm(searchResult.getClassPK()));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Term search index is stale and contains entry " +
						searchResult.getClassPK());
			}

			return Optional.empty();
		}
	}
}
