package osp.icecap.sss.web.display.context;

import com.liferay.asset.kernel.model.AssetEntry;
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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.TrashHelper;

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
import javax.servlet.http.HttpServletRequest;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.service.TermLocalServiceUtil;
import osp.icecap.sss.web.security.permission.resource.TermModelResourcePermission;

public class TermDisplayContext {
	public TermDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			TrashHelper trashHelper) {

			_liferayPortletRequest = liferayPortletRequest;
			_liferayPortletResponse = liferayPortletResponse;
			_trashHelper = trashHelper;

			_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(liferayPortletRequest);

			_httpServletRequest = _liferayPortletRequest.getHttpServletRequest();
		}

		public List<String> getAvailableActions(Term term)
			throws PortalException {

			List<String> availableActionDropdownItems = new ArrayList<>();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			if (TermModelResourcePermission.contains(
					permissionChecker, term, IcecapSSSActionKeys.DELETE_TERM)) {

				availableActionDropdownItems.add(IcecapSSSActionKeys.DELETE_TERM);
			}
			
			if (TermModelResourcePermission.contains(
					permissionChecker, term, IcecapSSSActionKeys.UPDATE_TERM)) {

				availableActionDropdownItems.add(IcecapSSSActionKeys.UPDATE_TERM);
			}
			
			if (TermModelResourcePermission.contains(
					permissionChecker, term, IcecapSSSActionKeys.ADD_TERM)) {

				availableActionDropdownItems.add(IcecapSSSActionKeys.ADD_TERM);
			}

			if (TermModelResourcePermission.contains(
					permissionChecker, term, IcecapSSSActionKeys.REVIEW_TERM)) {

				availableActionDropdownItems.add(IcecapSSSActionKeys.REVIEW_TERM);
			}

			if (TermModelResourcePermission.contains(
					permissionChecker, term, IcecapSSSActionKeys.APPROVE_TERM)) {

				availableActionDropdownItems.add(IcecapSSSActionKeys.APPROVE_TERM);
			}

			return availableActionDropdownItems;
		}

		public Map<String, Object> getComponentContext() throws PortalException {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Map<String, Object> context = new HashMap<>();

			context.put(
				"trashEnabled",
				_trashHelper.isTrashEnabled(themeDisplay.getScopeGroupId()));

			return context;
		}

		public String getDisplayStyle() {
			String displayStyle = ParamUtil.getString(
				_httpServletRequest, "displayStyle");

			if (Validator.isNull(displayStyle)) {
				displayStyle = _portalPreferences.getValue(
					IcecapSSSWebPortletKeys.TERM_MANAGER, "terms-display-style", "icon");
			}
			else {
				_portalPreferences.setValue(
						IcecapSSSWebPortletKeys.TERM_MANAGER, "terms-display-style",
					displayStyle);

				_httpServletRequest.setAttribute(
					WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
			}

			return displayStyle;
		}

		public SearchContainer<Term> getSearchContainer()
			throws PortalException, PortletException {

			PortletURL portletURL = _liferayPortletResponse.createRenderURL();

			portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.RENDER_TERM_LIST);

			String termsNavigation = ParamUtil.getString(
				_httpServletRequest, "termsNavigation");

			portletURL.setParameter("termsNavigation", termsNavigation);

			SearchContainer<Term> termsSearchContainer =
				new SearchContainer<>(
					_liferayPortletRequest,
					PortletURLUtil.clone(portletURL, _liferayPortletResponse), null,
					"no-entries-were-found");

			String orderByCol = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "name");

			termsSearchContainer.setOrderByCol(orderByCol);

			String orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");

			termsSearchContainer.setOrderByType(orderByType);

			termsSearchContainer.setOrderByComparator(
				TermLocalServiceUtil.getOrderByNameComparator(
						termsSearchContainer.getOrderByCol(),
						termsSearchContainer.getOrderByType()));
			 
			termsSearchContainer.setRowChecker(
				new EmptyOnClickRowChecker(_liferayPortletResponse));

			_populateResults(termsSearchContainer);

			return termsSearchContainer;
		}
		
		public TrashHelper getTrashHelper() {
			return _trashHelper;
		}

		private int _getStatus() {
			if (_status != null) {
				return _status;
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			PermissionChecker permissionChecker =
				themeDisplay.getPermissionChecker();

			if (permissionChecker.isContentReviewer(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId())) {

				_status = WorkflowConstants.STATUS_ANY;
			}
			else {
				_status = WorkflowConstants.STATUS_APPROVED;
			}

			return _status;
		}

		private void _populateResults(SearchContainer<Term> searchContainer)
			throws PortalException {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			List<Term> entriesResults = null;

			long assetCategoryId = ParamUtil.getLong(
				_httpServletRequest, "categoryId");
			String assetTagName = ParamUtil.getString(_httpServletRequest, "tag");

			String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

			if ((assetCategoryId != 0) || Validator.isNotNull(assetTagName)) {
				SearchContainerResults<AssetEntry> searchContainerResults =
					TermLocalServiceUtil.getSearchContainerResults(searchContainer);

				searchContainer.setTotal(searchContainerResults.getTotal());

				List<AssetEntry> assetEntries = searchContainerResults.getResults();

				entriesResults = new ArrayList<>(assetEntries.size());

				for (AssetEntry assetEntry : assetEntries) {
					entriesResults.add(
						TermLocalServiceUtil.getTerm(assetEntry.getClassPK()));
				}
			}
			else if (Validator.isNull(keywords)) {
				String termsNavigation = ParamUtil.getString( _httpServletRequest, "termsNavigation");

				if (termsNavigation.equals("mine")) {
					searchContainer.setTotal(
						TermLocalServiceUtil.countTermsByG_U_S(
							themeDisplay.getScopeGroupId(),
							themeDisplay.getUserId(),
							WorkflowConstants.STATUS_ANY));
				}
				else {
					searchContainer.setTotal(
						TermLocalServiceUtil.countTermsByG_S(
							themeDisplay.getScopeGroupId(),
							WorkflowConstants.STATUS_ANY));
				}

				if (termsNavigation.equals("mine")) {
					entriesResults = TermLocalServiceUtil.getTermsByG_U_S(
						themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
						WorkflowConstants.STATUS_ANY, searchContainer.getStart(),
						searchContainer.getEnd()); //,
						//searchContainer.getOrderByComparator());
				}
				else {
					entriesResults = TermLocalServiceUtil.getTermsByG_S(
						themeDisplay.getScopeGroupId(),
						WorkflowConstants.STATUS_ANY, 
						searchContainer.getStart(),
						searchContainer.getEnd());// ,
						//searchContainer.getOrderByComparator());
				}
			}
			else {
				Indexer<Term> indexer = IndexerRegistryUtil.getIndexer(Term.class);

				SearchContext searchContext = SearchContextFactory.getInstance(
					_httpServletRequest);

				searchContext.setAttribute(Field.STATUS, _getStatus());
				searchContext.setEnd(searchContainer.getEnd());
				searchContext.setIncludeDiscussions(true);
				searchContext.setKeywords(keywords);
				searchContext.setStart(searchContainer.getStart());

				String termsNavigation = ParamUtil.getString(
					_httpServletRequest, "termsNavigation");

				if (termsNavigation.equals("mine")) {
					searchContext.setOwnerUserId(themeDisplay.getUserId());
				}

				String orderByCol = ParamUtil.getString(
					_httpServletRequest, "orderByCol", "name");
				String orderByType = ParamUtil.getString(
					_httpServletRequest, "orderByType", "asc");

				Sort sort = null;

				boolean orderByAsc = true;

				if (Objects.equals(orderByType, "asc")) {
					orderByAsc = false;
				}

				if (Objects.equals(orderByCol, "name")) {
					sort = new Sort(Field.NAME, Sort.STRING_TYPE, orderByAsc);
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

		private Optional<Term> _toTermOptional(
			SearchResult searchResult) {

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

		private static final Log _log = LogFactoryUtil.getLog(
			TermDisplayContext.class);

		private final HttpServletRequest _httpServletRequest;
		private final LiferayPortletRequest _liferayPortletRequest;
		private final LiferayPortletResponse _liferayPortletResponse;
		private final PortalPreferences _portalPreferences;
		private Integer _status;
		private final TrashHelper _trashHelper;

}
