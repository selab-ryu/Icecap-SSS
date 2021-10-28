package osp.icecap.sss.web.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSClassNames;
import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.model.Term;

@Component(
		immediate = true
)
public class TermModelPermission {
	public static boolean contains(
			PermissionChecker permissionChecker, 
			Term term,
			String actionId)
		throws PortalException {

		return _termModelResourcePermission.contains(
			permissionChecker, term, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long termId, String actionId)
		throws PortalException {

		return _termModelResourcePermission.contains(
			permissionChecker, termId, actionId);
	}

	@Reference(
		target = "(model.class.name="+ IcecapSSSClassNames.TERM+")",
		unbind = "-"
	)
	protected void setTermModelPermission(
		ModelResourcePermission<Term> modelResourcePermission) {

		_termModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<Term> _termModelResourcePermission;
}
