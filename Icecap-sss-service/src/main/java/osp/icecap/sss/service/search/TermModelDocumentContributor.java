package osp.icecap.sss.service.search;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;

import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.model.Term;

/**
 * The contributor is a actual indexer when each Term data is added or updated.
 * Super interface ModelDocumentContributor has only one unimplemented API.
 * 
**/
@Component(
		immediate = true,
		property = "indexer.class.name=osp.icecap.sss.model.Term",
		service = ModelDocumentContributor.class
)
public class TermModelDocumentContributor implements ModelDocumentContributor<Term> {

	@Override
	public void contribute(Document document, Term term) {
			System.out.println("TermModelDocumentContributor.......");
			document.addDate(Field.MODIFIED_DATE, term.getModifiedDate());
			document.addKeyword(IcecapSSSTermAttributes.TERM_NAME, term.getTermName());
			document.addLocalizedKeyword(IcecapSSSTermAttributes.DEFINITION, term.getDefinitionMap(), true);
			document.addLocalizedKeyword(IcecapSSSTermAttributes.DISPLAY_NAME, term.getDisplayNameMap(), true);
			document.addLocalizedKeyword(IcecapSSSTermAttributes.TOOLTIP, term.getTooltipMap(), true);
			document.addKeyword(IcecapSSSTermAttributes.SYNONYMS, term.getSynonyms());
	}
	
}
