package osp.icecap.sss.web.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSConstants;

@Component(
		immediate = true
)
public class TermManagerPermission {
	public static boolean contains(
			PermissionChecker permissionChecker, 
			long groupId, 
			String actionId) throws PortalException {
		
		return _portletResourcePermission.contains(permissionChecker, groupId, actionId);
	}

	@Reference(
			target = "(resource.name="+IcecapSSSConstants.RESOURCE_NAME+")", 
			unbind = "-"
	)
	protected void setPortletResourcePermission(PortletResourcePermission portletResourcePermission) {
		_portletResourcePermission = portletResourcePermission;
	}

	private static PortletResourcePermission _portletResourcePermission;
}
