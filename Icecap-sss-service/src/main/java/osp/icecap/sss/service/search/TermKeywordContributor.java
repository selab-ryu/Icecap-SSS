package osp.icecap.sss.service.search;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.query.QueryHelper;
import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.KeywordQueryContributorHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSTermAttributes;

@Component(
        immediate = true,
        property = "indexer.class.name=osp.icecap.sss.model.Term",
        service = KeywordQueryContributor.class
)
public class TermKeywordContributor implements KeywordQueryContributor {
	
	@Reference
	protected QueryHelper queryHelper;

	@Override
	public void contribute(String keywords, BooleanQuery booleanQuery,
			KeywordQueryContributorHelper keywordQueryContributorHelper) {
		
		SearchContext searchContext = keywordQueryContributorHelper.getSearchContext();

		queryHelper.addSearchTerm(booleanQuery, searchContext, IcecapSSSTermAttributes.TERM_NAME, true);
		queryHelper.addSearchLocalizedTerm( booleanQuery, searchContext, IcecapSSSTermAttributes.DISPLAY_NAME, false);
		queryHelper.addSearchLocalizedTerm( booleanQuery, searchContext, IcecapSSSTermAttributes.DEFINITION, false);
		queryHelper.addSearchTerm( booleanQuery, searchContext, IcecapSSSTermAttributes.SYNONYMS, false);

	}

}
