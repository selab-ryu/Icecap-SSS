package osp.icecap.sss.search.impl;

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
		BatchIndexingActionable batchIndexingActionable = _indexerWriter.getBatchIndexingActionable();

		batchIndexingActionable.setAddCriteriaMethod(dynamicQuery -> {
			Property termIdProperty = PropertyFactoryUtil.forName("termId");
			dynamicQuery.add(termIdProperty.eq(termId));
		});

		batchIndexingActionable.setCompanyId(companyId);

		batchIndexingActionable.setPerformActionMethod((Term term) -> {
			Document document = _indexerDocumentBuilder.getDocument(term);
			batchIndexingActionable.addDocuments(document);
		});

		batchIndexingActionable.performActions();
	}

	@Reference(
			target = "(indexer.class.name=osp.icecap.sss.model.Term)"
	)
	protected IndexerDocumentBuilder _indexerDocumentBuilder;

	@Reference(
			target = "(indexer.class.name=osp.icecap.sss.model.Term)"
	)
	protected IndexerWriter<Term> _indexerWriter;
}
