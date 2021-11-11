package osp.icecap.sss.web.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSConstants;

@Component(
		immediate = true,
		service = {}
)
public class TermManagerPortletResourcePermission {
	
	public static void check(
			PermissionChecker permissionChecker, long classPK, String actionId) throws PrincipalException {
		_portletResourcePermission.check(permissionChecker, classPK, actionId);
	}
	
	public static void check(
			PermissionChecker permissionChecker, Group group, String actionId)
		throws PortalException {
		_portletResourcePermission.check(permissionChecker, group, actionId);
	}
	
	public static boolean contains(
			PermissionChecker permissionChecker, Group group, String actionId) {

			return _portletResourcePermission.contains(
				permissionChecker, group, actionId);
	}
	
	public static boolean contains(
			PermissionChecker permissionChecker, 
			long groupId, 
			String actionId){
		
		System.out.println("groupId: "+groupId );
		System.out.println("Action ID: "+actionId);
		System.out.println("Resource Name: "+_portletResourcePermission.getResourceName());
		
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
