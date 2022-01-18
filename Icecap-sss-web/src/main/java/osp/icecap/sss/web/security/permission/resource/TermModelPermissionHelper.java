package osp.icecap.sss.web.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSClassNames;
import osp.icecap.sss.model.Term;

@Component(
		immediate = true
)
public class TermModelPermissionHelper{
	
	@Reference(
			target = "(model.class.name="+ IcecapSSSClassNames.TERM+")",
			unbind = "-"
			)
	protected void setTermModelResourcePermission(
			ModelResourcePermission<Term> modelResourcePermission) {
		System.out.println("Model Resource Permission Injected: "+modelResourcePermission.getModelName());
		_termModelResourcePermission = modelResourcePermission;
	}
	private static ModelResourcePermission<Term> _termModelResourcePermission;
	
	public static boolean contains(
			PermissionChecker permissionChecker, 
			Term term,
			String actionId) throws PortalException{

		return _termModelResourcePermission.contains(
				permissionChecker, 
				term, 
				actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, 
			long termId, 
			String actionId) throws PortalException{
		
		return _termModelResourcePermission.contains(
				permissionChecker, 
				termId, 
				actionId);
	}

}
