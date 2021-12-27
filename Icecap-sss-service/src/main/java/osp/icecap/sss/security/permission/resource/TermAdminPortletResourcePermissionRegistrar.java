package osp.icecap.sss.security.permission.resource;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;

@Component (
		immediate=true,
		service = {}
)
public class TermAdminPortletResourcePermissionRegistrar {
	
	private ServiceRegistration<PortletResourcePermission> _serviceRegistration;
	
	@Reference
	private StagingPermission _stagingPermission;
	
	@Activate
	public void activate(BundleContext bundleContext) {
		
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("resource.name", IcecapSSSConstants.RESOURCE_NAME);

		_serviceRegistration = bundleContext.registerService(
				PortletResourcePermission.class,
				PortletResourcePermissionFactory.create(
						IcecapSSSConstants.RESOURCE_NAME,
						new StagedPortletPermissionLogic(
								_stagingPermission, 
								IcecapSSSWebPortletKeys.TERM_ADMIN)),
				properties);
		
	};
		
	@Deactivate
	public void deactivate() {
		_serviceRegistration.unregister();
	}
}
