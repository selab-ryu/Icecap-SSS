package osp.icecap.sss.service.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

import osp.icecap.sss.constants.IcecapSSSTermAttributes;

@Component(
        immediate = true,
        property = "indexer.class.name=osp.icecap.sss.model.Term",
        service = ModelSummaryContributor.class
)
public class TermModelSummaryContributor implements ModelSummaryContributor {

	@Override
	public Summary getSummary(Document document, Locale locale, String snippet) {
		Summary summary = createSummary(document, locale);
		summary.setMaxContentLength(128);

		return summary;
	}

	private Summary createSummary(Document document, Locale locale) {
		String title = document.get(IcecapSSSTermAttributes.DISPLAY_NAME, IcecapSSSTermAttributes.VERSION);
		String content = document.get(
				IcecapSSSTermAttributes.NAME +
				IcecapSSSTermAttributes.DEFINITION
		);

		return new Summary(locale, title, content);
	}
}
