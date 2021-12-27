package osp.icecap.sss.web.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchContainerResults;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.util.TrashWebKeys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSDisplayStyles;
import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.security.permission.resource.TermModelPermissionHelper;
import osp.icecap.sss.service.TermLocalServiceUtil;

public class TermDisplayContext {
	
	private static final Log _log = LogFactoryUtil.getLog(TermDisplayContext.class);

	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _httpServletRequest;
	private TrashHelper _trashHelper;
	private Integer _status;
	private String _displayStyle;
	private String _keywords;
	private Boolean _multipleSelection;
	private Boolean _showAddButton;
	private Long _groupId;
	private String _orderByCol;
	private String _orderByType;
	private String _eventName;
	private Boolean _showScheduled;

	public TermDisplayContext(
			RenderRequest renderRequest,
			RenderResponse renderResponse,
			TrashHelper trashHelper) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_trashHelper = trashHelper;
		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public SearchContainer<Term> getSearchContainer(){

		PortletURL portletURL = this.getPortletURL();

		portletURL.setParameter(IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, MVCCommandNames.RENDER_TERM_LIST);

		String termsNavigation = ParamUtil.getString(	_httpServletRequest, IcecapSSSWebKeys.NAVIGATION);
		System.out.println("TermDisplayContext:getSearchContainer:termsNavigation - "+termsNavigation);

		portletURL.setParameter(IcecapSSSWebKeys.NAVIGATION, termsNavigation);

		SearchContainer<Term> termsSearchContainer =
					new SearchContainer<Term>(
										_renderRequest,
										portletURL,
										null,
										"no-terms-were-found");

		termsSearchContainer.setOrderByCol(_getOrderByCol());
		termsSearchContainer.setOrderByType(_getOrderByType());
		
		termsSearchContainer.setOrderByComparator(
					TermLocalServiceUtil.getOrderByNameComparator(
							termsSearchContainer.getOrderByCol(),
							termsSearchContainer.getOrderByType()));
		termsSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));
		
		try {
			_populateResults(termsSearchContainer);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return termsSearchContainer;
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
			_httpServletRequest, IcecapSSSWebKeys.DISPLAY_STYLE, IcecapSSSDisplayStyles.LIST);

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

	private int[] _getStatuses() {
		int[] statuses = {WorkflowConstants.STATUS_APPROVED};

		if (_isShowScheduled()) {
			statuses = new int[] {
				WorkflowConstants.STATUS_APPROVED,
				WorkflowConstants.STATUS_SCHEDULED
			};
		}

		return statuses;
	}
	
	public TrashHelper getTrashHelper() {
		return _trashHelper;
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
					entriesResults.add(TermLocalServiceUtil.getTerm(assetEntry.getClassPK()));
				}
			}
			// Keywords are not presented
			else if (Validator.isNull(keywords)) {
				String termsNavigation = ParamUtil.getString( _httpServletRequest, IcecapSSSWebKeys.NAVIGATION);

				if (termsNavigation.equals(IcecapSSSConstants.NAVIGATION_MINE)) {
					searchContainer.setTotal(
						TermLocalServiceUtil.countTermsByG_U_S(
							themeDisplay.getScopeGroupId(),
							themeDisplay.getUserId(),
							WorkflowConstants.STATUS_ANY));

					entriesResults = TermLocalServiceUtil.getTermsByG_U_S(
							themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
							WorkflowConstants.STATUS_ANY, searchContainer.getStart(),
							searchContainer.getEnd()); //,
					//searchContainer.getOrderByComparator());
				}
				else if( termsNavigation.equals(IcecapSSSConstants.NAVIGATION_GROUP)){
					System.out.println("Group Total: "+TermLocalServiceUtil.countTermsByG_S(
							themeDisplay.getScopeGroupId(),
							WorkflowConstants.STATUS_ANY));
					searchContainer.setTotal(
						TermLocalServiceUtil.countTermsByG_S(
							themeDisplay.getScopeGroupId(),
							WorkflowConstants.STATUS_ANY));
					
					entriesResults = TermLocalServiceUtil.getTermsByG_S(
							themeDisplay.getScopeGroupId(),
							WorkflowConstants.STATUS_ANY, 
							searchContainer.getStart(),
							searchContainer.getEnd());// ,
					//searchContainer.getOrderByComparator());
				}
				else {
					System.out.println("All Total: "+TermLocalServiceUtil.countAllTerms() );
					searchContainer.setTotal(TermLocalServiceUtil.countAllTerms());
					
					entriesResults = TermLocalServiceUtil.getAllTerms(
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
				searchContext.setEnd(searchContainer.getEnd());
				searchContext.setIncludeDiscussions(true);
				searchContext.setKeywords(keywords);
				searchContext.setStart(searchContainer.getStart());

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
				TermLocalServiceUtil.getTerm(searchResult.getClassPK()));
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
