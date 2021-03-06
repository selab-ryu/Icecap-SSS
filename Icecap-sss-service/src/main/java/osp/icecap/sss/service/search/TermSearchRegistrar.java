package osp.icecap.sss.service.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.model.Term;

@Component(immediate = true)
public class TermSearchRegistrar {
	@Activate
	protected void activate(BundleContext bundleContext) {
			_serviceRegistration = modelSearchRegistrarHelper.register(
					Term.class, 
					bundleContext, 
					modelSearchDefinition -> {
								modelSearchDefinition.setDefaultSelectedFieldNames(
										Field.ASSET_TAG_NAMES, Field.COMPANY_ID,
										Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
										Field.GROUP_ID, Field.MODIFIED_DATE, Field.UID,
										IcecapSSSTermAttributes.NAME, IcecapSSSTermAttributes.DEFINITION,
										IcecapSSSTermAttributes.TOOLTIP, IcecapSSSTermAttributes.SYNONYMS,
										IcecapSSSTermAttributes.DISPLAY_NAME, IcecapSSSTermAttributes.TYPE);

								modelSearchDefinition.setModelIndexWriteContributor(
										modelIndexWriterContributor);
								modelSearchDefinition.setModelSummaryContributor(
										modelSummaryContributor);
					});
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}
	
	@Reference(target = "(indexer.class.name=osp.icecap.sss.model.Term)")
	protected ModelIndexerWriterContributor<Term> modelIndexWriterContributor;

	@Reference
	protected ModelSearchRegistrarHelper modelSearchRegistrarHelper;

	@Reference(target = "(indexer.class.name=osp.icecap.sss.model.Term)")
	protected ModelSummaryContributor modelSummaryContributor;

	private ServiceRegistration<?> _serviceRegistration;
}
