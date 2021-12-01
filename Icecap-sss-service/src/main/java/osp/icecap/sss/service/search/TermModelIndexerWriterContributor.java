package osp.icecap.sss.service.search;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.batch.DynamicQueryBatchIndexingActionableFactory;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.index.contributor.helper.ModelIndexerWriterDocumentHelper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.model.Term;
import osp.icecap.sss.search.TermBatchReindexer;
import osp.icecap.sss.service.TermLocalService;

@Component(
        immediate = true,
        property = "indexer.class.name=osp.icecap.sss.model.Term",
        service = ModelIndexerWriterContributor.class
)
public class TermModelIndexerWriterContributor implements ModelIndexerWriterContributor<Term> {

	@Override
	public void customize(BatchIndexingActionable batchIndexingActionable,
			ModelIndexerWriterDocumentHelper modelIndexerWriterDocumentHelper) {
		System.out.println("TermModelIndexerWriterContributor......");
		batchIndexingActionable.setPerformActionMethod((Term term) -> {
			System.out.println("setPerformActionMethod....");
			Document document = modelIndexerWriterDocumentHelper.getDocument(term);

			batchIndexingActionable.addDocuments(document);
		});
	}

	@Override
	public BatchIndexingActionable getBatchIndexingActionable() {
		return dynamicQueryBatchIndexingActionableFactory.getBatchIndexingActionable(
				termLocalService.getIndexableActionableDynamicQuery());
	}

	@Override
	public long getCompanyId(Term term) {
		return term.getCompanyId();
	}

	@Override
	public void modelIndexed(Term term) {
		termBatchReindexer.reindex(
			    term.getTermId(), term.getCompanyId());
	}
 
	@Reference
    protected DynamicQueryBatchIndexingActionableFactory	dynamicQueryBatchIndexingActionableFactory;

    @Reference
    protected TermBatchReindexer termBatchReindexer;

    @Reference
    protected TermLocalService termLocalService;
}
