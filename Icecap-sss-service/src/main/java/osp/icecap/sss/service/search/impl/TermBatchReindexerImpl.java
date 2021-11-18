package osp.icecap.sss.service.search.impl;

import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.batch.BatchIndexingActionable;
import com.liferay.portal.search.indexer.IndexerDocumentBuilder;
import com.liferay.portal.search.indexer.IndexerWriter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.model.Term;
import osp.icecap.sss.search.TermBatchReindexer;

@Component(
		immediate = true, 
		service = TermBatchReindexer.class
)
public class TermBatchReindexerImpl implements TermBatchReindexer {

	@Override
	public void reindex(long termId, long companyId) {
		BatchIndexingActionable batchIndexingActionable = indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(dynamicQuery -> {
			Property termIdProperty = PropertyFactoryUtil.forName("termId");
			dynamicQuery.add(termIdProperty.eq(termId));
		});

		batchIndexingActionable.setCompanyId(companyId);

		batchIndexingActionable.setPerformActionMethod((Term term) -> {
			Document document = indexerDocumentBuilder.getDocument(term);
			batchIndexingActionable.addDocuments(document);
		});

		batchIndexingActionable.performActions();
	}

	@Reference(
			target = "(indexer.class.name=osp.icecap.sss.model.Term)"
	)
	protected IndexerDocumentBuilder indexerDocumentBuilder;

	@Reference(
			target = "(indexer.class.name=osp.icecap.sss.model.Term)"
	)
	protected IndexerWriter<Term> indexerWriter;
}
