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
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.TrashHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.debug.Debug;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.service.TermLocalService;
import osp.icecap.sss.web.security.permission.resource.TermModelPermissionHelper;

public class TermAdminDisplayContext implements Serializable{
	
	private static final Log _log = LogFactoryUtil.getLog(TermAdminDisplayContext.class);

	private final LiferayPortletRequest _renderRequest;
	private final LiferayPortletResponse _renderResponse;
	private final HttpServletRequest _httpServletRequest;
	private final TermLocalService _termLocalService;
	private final TrashHelper _trashHelper;
	private final Integer _status;
	private final String _navigation;
	private final String _displayStyle;
	private final String _keywords;
	private final Boolean _multipleSelection;
	private final Boolean _showAddButton;
	private final Long _groupId;
	private final String _orderByCol;
	private final String _orderByType;
	private final String _eventName;
	private final Boolean _showScheduled;
	private final ThemeDisplay _themeDisplay;
	
	private SearchContainer< Term> _searchContainer;

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
		
		_navigation = ParamUtil.getString(_httpServletRequest, IcecapSSSWebKeys.NAVIGATION, IcecapSSSConstants.NAVIGATION_MINE);
		_displayStyle = ParamUtil.getString(
				_httpServletRequest, IcecapSSSWebKeys.DISPLAY_STYLE, IcecapSSSConstants.VIEW_TYPE_TABLE);
		_status = ParamUtil.getInteger(_httpServletRequest, IcecapSSSTermAttributes.STATUS, WorkflowConstants.STATUS_ANY);
		_orderByCol = ParamUtil.getString(
				_httpServletRequest, IcecapSSSWebKeys.ORDER_BY_COL, IcecapSSSTermAttributes.TERM_NAME);
		_orderByType = ParamUtil.getString(
				_httpServletRequest, IcecapSSSWebKeys.ORDER_BY_TYPE, IcecapSSSConstants.ASC);
		_keywords = ParamUtil.getString(_httpServletRequest, IcecapSSSWebKeys.KEYWORDS, null);
		_multipleSelection = ParamUtil.getBoolean( _httpServletRequest, IcecapSSSWebKeys.MULTIPLE_SELECTION, false);
		_groupId = _themeDisplay.getScopeGroupId();
		_showAddButton = ParamUtil.getBoolean(_httpServletRequest, IcecapSSSWebKeys.SHOW_ADD_BUTTON, true);
		_showScheduled = ParamUtil.getBoolean(_httpServletRequest, IcecapSSSWebKeys.SHOW_SCHEDULED, false);
		_eventName = ParamUtil.getString(
									_httpServletRequest, IcecapSSSWebKeys.EVENT_NAME,
									_renderResponse.getNamespace() + IcecapSSSWebKeys.SELECT_TERM);
		
		Debug.printHeader("TermAdminDisplayContext");
		System.out.println("+++ _navigation: "+_navigation);
		System.out.println("+++ _displayStyle: "+_displayStyle);
		System.out.println("+++ _status: "+_status);
		System.out.println("+++ _orderByCol: "+_orderByCol);
		System.out.println("+++ _orderByType: "+_orderByType);
		System.out.println("+++ _keywords: "+_keywords);
		System.out.println("+++ _multipleSelection: "+_multipleSelection);
		System.out.println("+++ _groupId: "+_groupId);
		System.out.println("+++ _showAddButton: "+_showAddButton);
		System.out.println("+++ _showScheduled: "+_showScheduled);
		System.out.println("+++ _eventName: "+_eventName);
		Debug.printFooter("TermAdminDisplayContext");
		
		
		_searchContainer = _createSearchContainer(
				IcecapSSSConstants.SEARCH_CONTAINER_ID,
				getSearchURL( Validator.isNotNull(_keywords) && !_keywords.isEmpty() ) );
	}

	public TrashHelper getTrashHelper() {
		return _trashHelper;
	}

	public Integer getStatus() {
		return _status;
	}

	public String getNavigation() {
		return _navigation;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getKeywords() {
		return _keywords;
	}

	public Boolean getMultipleSelection() {
		return _multipleSelection;
	}

	public Boolean getShowAddButton() {
		return _showAddButton;
	}

	public Long getGroupId() {
		return _groupId;
	}

	public String getOrderByCol() {
		return _orderByCol;
	}

	public String getOrderByType() {
		return _orderByType;
	}

	public String getEventName() {
		return _eventName;
	}

	public Boolean getShowScheduled() {
		return _showScheduled;
	}

	public ThemeDisplay getThemeDisplay() {
		return _themeDisplay;
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
	
	public PortletURL getSearchURL( boolean hasKeywords ) {
		PortletURL portletURL = this.getPortletURL();

		portletURL.setParameter(IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_ADMIN_TERM_LIST);
		
		return portletURL;
	}
	
	public SearchContainer<Term> getSearchContainer(){
		return _searchContainer;
	}

	private SearchContainer<Term> _createSearchContainer( String searchContainerId, PortletURL searchURL ){

		_searchContainer =
					new SearchContainer<Term>(
										_renderRequest,
										searchURL,
										null,
										"no-terms-were-found");
		
		_searchContainer.setId(searchContainerId);
		_searchContainer.setOrderByCol(_orderByCol);
		_searchContainer.setOrderByType(_orderByType);
		
		System.out.println("getSearchContainer.orderByCol: "+ _orderByCol );
		System.out.println("getSearchContainer.orderByType: "+ _orderByType );
		
		OrderByComparator<Term> orderByComparator = _termLocalService.getOrderByNameComparator( _orderByCol, _orderByType );
		
		_searchContainer.setOrderByComparator( orderByComparator );
		
		_searchContainer.setRowChecker( new EmptyOnClickRowChecker(_renderResponse) );
		
		try {
			_performRetrieval(_searchContainer);
		} catch (PortalException e) {
			e.printStackTrace();
		}
		
		return _searchContainer;
	}
	
	public String getSearchContainerId() {
		return IcecapSSSConstants.SEARCH_CONTAINER_ID;
	}

	public List<String> getAvailableActions(Term term)
		throws PortalException {

		List<String> availableActionDropdownItems = new ArrayList<>();

		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

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

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();
		
		portletURL.setParameter(IcecapSSSWebKeys.GROUP_ID, String.valueOf(_groupId));

		if (_getListable() != null) {
			portletURL.setParameter(IcecapSSSWebKeys.LISTABLE, String.valueOf(_getListable()));
		}

		portletURL.setParameter( 
				IcecapSSSWebKeys.MULTIPLE_SELECTION, 
				Boolean.TRUE.toString());

		portletURL.setParameter(IcecapSSSWebKeys.SHOW_ADD_BUTTON, String.valueOf(_showAddButton));

		portletURL.setParameter(
				IcecapSSSWebKeys.SHOW_SCHEDULED, String.valueOf(_showScheduled));

		portletURL.setParameter(IcecapSSSWebKeys.EVENT_NAME, _eventName);
		
		portletURL.setParameter(IcecapSSSWebKeys.DISPLAY_STYLE, _displayStyle);
		portletURL.setParameter(IcecapSSSWebKeys.ORDER_BY_COL, _orderByCol);
		portletURL.setParameter(IcecapSSSWebKeys.ORDER_BY_TYPE, _orderByType);
		portletURL.setParameter(IcecapSSSWebKeys.NAVIGATION, _navigation);
		portletURL.setParameter(IcecapSSSWebKeys.KEYWORDS, _keywords);
		
		return portletURL;
	}

	public boolean hasKeywords() {
		if( Validator.isNull(_keywords) || _keywords.isEmpty() ) {
			return false;
		}
		
		return true;
	}

	private int[] _getStatuses() {
		int[] statuses = {_status};

		if (_showScheduled) {
			statuses = new int[] {
				WorkflowConstants.STATUS_APPROVED,
				WorkflowConstants.STATUS_SCHEDULED
			};
		}

		return statuses;
	}
	
	private void _performRetrieval(SearchContainer<Term> searchContainer)
			throws PortalException {

			List<Term> entriesResults = null;

			long assetCategoryId = ParamUtil.getLong(_httpServletRequest, IcecapSSSWebKeys.CATEGORY_ID);
			String assetTagName = ParamUtil.getString(_httpServletRequest, IcecapSSSWebKeys.TAG);
			
			// Browse through category system. Use Asset service
			if ((assetCategoryId != 0) || Validator.isNotNull(assetTagName)) {
				AssetEntryQuery assetEntryQuery = new AssetEntryQuery(Term.class.getName(), searchContainer);

				assetEntryQuery.setExcludeZeroViewCount(false);
				assetEntryQuery.setOrderByCol1(_orderByCol);
				assetEntryQuery.setOrderByType1(_orderByType);
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
			else if (Validator.isNull(_keywords)) {
				String termsNavigation = ParamUtil.getString( _httpServletRequest, IcecapSSSWebKeys.NAVIGATION);

				if (termsNavigation.equals(IcecapSSSConstants.NAVIGATION_MINE)) {
					searchContainer.setTotal(
						_termLocalService.countTermsByU_S(
							_themeDisplay.getUserId(),
							_status));

					entriesResults = _termLocalService.getTermsByU_S(
							_themeDisplay.getUserId(),
							_status, 
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator());
				}
				else if( termsNavigation.equals(IcecapSSSConstants.NAVIGATION_GROUP)){
					searchContainer.setTotal(
						_termLocalService.countTermsByG_S(
							_themeDisplay.getScopeGroupId(),
							_status));
					
					entriesResults = _termLocalService.getTermsByG_S(
							_themeDisplay.getScopeGroupId(),
							_status, 
							searchContainer.getStart(),
							searchContainer.getEnd(),
							searchContainer.getOrderByComparator());
				}
				else {
					searchContainer.setTotal(_termLocalService.countTermsByStatus(_status));
					
					entriesResults = _termLocalService.getTermsByStatus(
							_status,
							searchContainer.getStart(),
							searchContainer.getEnd() ,
					        searchContainer.getOrderByComparator());
				}
			}
			// If keywords are presented, it should use search engine!!!
			else {
				Indexer<Term> indexer = IndexerRegistryUtil.getIndexer(Term.class);

				SearchContext searchContext = SearchContextFactory.getInstance(
					_httpServletRequest);

				searchContext.setAttribute(Field.STATUS, _getStatuses());
				searchContext.setAttribute("pagenationType", "more");
				searchContext.setStart(searchContainer.getStart());
				searchContext.setEnd(searchContainer.getEnd());
				searchContext.setIncludeDiscussions(true);
				searchContext.setKeywords(_keywords);
				searchContext.setLocale(_themeDisplay.getLocale());

				if (!_navigation.equals(IcecapSSSConstants.NAVIGATION_MINE)) {
					searchContext.setOwnerUserId(_themeDisplay.getUserId());
				}

				boolean reverseSort = false;

				if (_orderByType.equals(IcecapSSSConstants.DSC)) {
					reverseSort = true;
				}

				Sort sort = SortFactoryUtil.create(_orderByCol, Sort.STRING_TYPE, reverseSort);

				searchContext.setSorts(sort);

				Hits hits = indexer.search(searchContext);

				searchContainer.setTotal(hits.getLength());

				entriesResults = new ArrayList<Term>();
				Document[] docs = hits.getDocs();
				for( Document doc : docs ) {
					long termId = GetterUtil.getLong( doc.get(Field.ENTRY_CLASS_PK) );
					Term term = _termLocalService.getTerm(termId);
					
					entriesResults.add(term);
					System.out.println( "==== Begin Document Fields - "+doc.get(Field.ENTRY_CLASS_PK) );
					
					Map<String, Field> fields = doc.getFields();
					fields.forEach((key, field) ->{
						System.out.println(key + ": (" + field.getName() + "-"+ field.getValue());
					});
					System.out.println( "==== End Document Fields" );
				}
				
				/*
				List<SearchResult> searchResults =
					SearchResultUtil.getSearchResults(
						hits, _themeDisplay.getLocale() );

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
				*/
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
