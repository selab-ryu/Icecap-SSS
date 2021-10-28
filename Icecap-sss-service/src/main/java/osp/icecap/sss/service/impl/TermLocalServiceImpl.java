/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package osp.icecap.sss.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchContainerResults;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.exception.InvalidTermException;
import osp.icecap.sss.exception.NoSuchTermException;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.service.base.TermLocalServiceBaseImpl;
import osp.icecap.sss.util.comparator.TermModifiedDateComparator;
import osp.icecap.sss.util.comparator.TermNameComparator;
import osp.icecap.sss.util.comparator.TermVersionComparator;

/**
 * The implementation of the term local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>osp.icecap.sss.service.TermLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Jerry H. Seo, Won Cheol Ryu
 * @see TermLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=osp.icecap.sss.model.Term",
	service = AopService.class
)
public class TermLocalServiceImpl extends TermLocalServiceBaseImpl {
	
	@Indexable(type = IndexableType.REINDEX)
	public Term addTerm(
			String name,
			String version,
			String type,
			Map<Locale, String> displayNameMap,
			Map<Locale, String> definitionMap,
			Map<Locale, String> tooltipMap,
			String[] synonyms,
			String attributes, // attributes for each type
			ServiceContext sc) throws PortalException {
		
		if( !verifyTerm( name, version ) ) {
			throw new InvalidTermException(name+" "+version+" Invalid"); 
		}
		
		long termId = super.counterLocalService.increment();
		Term term = super.termLocalService.createTerm(termId);
		
		term.setName(name);
		term.setVersion(version);
		term.setType(type);
		term.setDisplayNameMap(displayNameMap);
		term.setDefinitionMap(definitionMap);
		term.setTooltipMap(tooltipMap);
		if( attributes != null ) {
			term.setAttributesJSON(attributes.toString());
		}
		
		Date now = new Date();
		term.setCompanyId(sc.getCompanyId());
		term.setGroupId(sc.getScopeGroupId());
		term.setUserId(sc.getUserId());
		term.setCreateDate(now);
		term.setModifiedDate(now);
		term.setStandardized(false);
		
		term.setStatus(WorkflowConstants.STATUS_DRAFT);
		
		super.termPersistence.update(term);
		
		super.resourceLocalService.addResources(
				term.getCompanyId(), 
				term.getGroupId(), 
				term.getUserId(), 
				Term.class.getName(), 
				term.getPrimaryKey(), 
				false, 
				true, 
				true);
		
		super.assetEntryLocalService.updateEntry(
			term.getUserId(), 
			term.getGroupId(), 
			term.getCreateDate(), 
			term.getModifiedDate(),
			Term.class.getName(), 
			term.getPrimaryKey(), 
			term.getUuid(), 
			0,
			sc.getAssetCategoryIds(), 
			sc.getAssetTagNames(), 
			true,
			true, 
			null, 
			null, 
			term.getCreateDate(),
			null, 
			ContentTypes.TEXT_HTML_UTF8, 
			term.getName(),
			null, 
			null, 
			null, 
			null,
			0, 0, null);
		Indexer<Term> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Term.class);
		indexer.reindex(term);
		return term;
	}
	
	@Indexable(type = IndexableType.REINDEX)
	public Term updateTerm(
			long termId, 
			String name, 
			String version,
			String type, 
			Map<Locale, String> displayNameMap,
			Map<Locale, String> definitionMap,
			Map<Locale, String> tooltipMap,
			String[] synonyms,
			String attributes, // attributes for each type
			ServiceContext sc) throws PortalException {
		Term term = super.termPersistence.findByPrimaryKey(termId);
		
		term.setName(name);
		term.setVersion(version);
		term.setDisplayNameMap(displayNameMap);
		term.setType(type);
		if( attributes != null ) {
			term.setAttributesJSON(attributes.toString());
		}
		
		term.setUserId(sc.getUserId());
		term.setModifiedDate(new Date() );
		
		super.resourceLocalService.updateResources(
				term.getCompanyId(),
				term.getGroupId(), 
				Term.class.getName(), 
				term.getPrimaryKey(),
				sc.getModelPermissions());
		
		super.assetEntryLocalService.updateEntry(
				term.getUserId(), 
				term.getGroupId(), 
				term.getCreateDate(), 
				term.getModifiedDate(),
				Term.class.getName(), 
				term.getPrimaryKey(), 
				term.getUuid(), 
				0,
				sc.getAssetCategoryIds(), 
				sc.getAssetTagNames(), 
				true,
				true, 
				null, 
				null, 
				term.getCreateDate(),
				null, 
				ContentTypes.TEXT_HTML_UTF8, 
				term.getName(),
				null, 
				null, 
				null, 
				null,
				0, 0, null);
		Indexer<Term> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Term.class);
		indexer.reindex(term);
		return term;
	}
	
	@Indexable(type = IndexableType.DELETE)
	public Term removeTerm( long termId ) throws PortalException {
		Term term = super.termPersistence.remove(termId);
		
		super.assetEntryLocalService.deleteEntry(Term.class.getName(), term.getPrimaryKey());

		super.resourceLocalService.deleteResource(
				term.getCompanyId(), 
				Term.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL, 
				term.getPrimaryKey());
		
		Indexer<Term> indexer = IndexerRegistryUtil.nullSafeGetIndexer(Term.class);
		indexer.delete(term);
		return term;
	}
	
	public List<Term> getAllTerms(){
		return super.termPersistence.findAll();
	}
	public List<Term> getAllTerms( int start, int end ){
		return super.termPersistence.findAll(start, end);
	}
	public int countAllTerms() {
		return super.termPersistence.countAll();
	}
	
	public List<Term> getTermsByGroupId( long groupId ){
		return super.termPersistence.findByGroupId(groupId);
	}
	public List<Term>  getTermsByGroupId( long groupId, int start, int end ){
		return super.termPersistence.findByGroupId(groupId, start, end);
	}
	public int countTermsByGroupId(long groupId) {
		return super.termPersistence.countByGroupId(groupId);
	}

	public List<Term> getTermsByUserId( long userId ){
		return super.termPersistence.findByUserId(userId);
	}
	public List<Term>  getTermsByUserId( long userId, int start, int end ){
		return super.termPersistence.findByUserId(userId, start, end);
	}
	public int countTermsByUserId(long userId) {
		return super.termPersistence.countByUserId(userId);
	}

	public List<Term> getTermsByStatus( int status ){
		return super.termPersistence.findByStatus(status);
	}
	public List<Term>  getTermsByStatus( int status, int start, int end ){
		return super.termPersistence.findByStatus(status, start, end);
	}
	public int countTermsByStatus(int status) {
		return super.termPersistence.countByStatus(status);
	}

	public List<Term> getTermsByName( String termName ){
		return super.termPersistence.findByName(termName);
	}
	
	public List<Term> getTermsByG_S( long groupId, int status ){
		return super.termPersistence.findByG_S(groupId, status);
	}
	public List<Term> getTermsByG_S( long groupId, int status, int start, int end ){
		return super.termPersistence.findByG_S(groupId, status, start, end);
	}
	public int countTermsByG_S( long groupId, int status ){
		return super.termPersistence.countByG_S(groupId, status);
	}
	
	public List<Term> getTermsByG_U_S( long groupId, long userId, int status ){
		return super.termPersistence.findByG_U_S(groupId, userId, status);
	}
	public List<Term> getTermsByG_U_S( long groupId, long userId, int status, int start, int end ){
		return super.termPersistence.findByG_U_S(groupId, userId, status, start, end);
	}
	public int countTermsByG_U_S( long groupId, long userId, int status ){
		return super.termPersistence.countByG_U_S(groupId, userId, status);
	}
	
	public String getName( long termId, Locale locale ) throws NoSuchTermException {
		Term term = super.termPersistence.findByPrimaryKey(termId);
		
		return "";
	}
	
	public OrderByComparator<Term> getOrderByNameComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = true;

		if (orderByType.equals("desc")) {
			orderByAsc = false;
		}

		OrderByComparator<Term> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = new TermNameComparator(orderByAsc);
			System.out.println("Create TermNameComparator");
		}
		else {
			orderByComparator = new TermVersionComparator(orderByAsc);
			System.out.println("Create TermVersionComparator");
		}

		return orderByComparator;
	}
	
	public SearchContainerResults<AssetEntry> getSearchContainerResults(
			SearchContainer<Term> searchContainer)
		throws PortalException {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery(
			Term.class.getName(), searchContainer);

		assetEntryQuery.setExcludeZeroViewCount(false);
		assetEntryQuery.setOrderByCol1("publishDate");
		assetEntryQuery.setVisible(Boolean.TRUE);

		int total = AssetEntryServiceUtil.getEntriesCount(assetEntryQuery);

		assetEntryQuery.setEnd(searchContainer.getEnd());
		assetEntryQuery.setStart(searchContainer.getStart());

		List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(
			assetEntryQuery);

		return new SearchContainerResults<>(assetEntries, total);
	}
	
	/**
	 * 
	 * @param termName
	 * @param termVersion
	 * @return true if the term is verified.
	 */
	private boolean verifyTerm( String termName, String termVersion ) {
		return true;
	}
}