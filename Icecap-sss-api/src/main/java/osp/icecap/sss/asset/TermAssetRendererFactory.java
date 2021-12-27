package osp.icecap.sss.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.asset.TermAssetRenderer;
import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.security.permission.resource.TermModelPermissionHelper;
import osp.icecap.sss.service.TermLocalService;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN
		},
		service = AssetRendererFactory.class
)
public class TermAssetRendererFactory extends BaseAssetRendererFactory<Term> {
	
	@Reference(unbind = "-")
	protected void setTermLocalService(
			TermLocalService termLocalService) {

		_termLocalService = termLocalService;
	}
	private TermLocalService _termLocalService;
	
	@Reference(
			target = "(osgi.web.symbolicname="+IcecapSSSConstants.WEB_BUNDLE_NAME+")", 
			unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}
	private ServletContext _servletContext;
	
	public static final String TYPE = "term";
    private static final boolean _LINKABLE = true;

    private ModelResourcePermission<Term> _termModelResourcePermission;

	public TermAssetRendererFactory() {
		super.setClassName(Term.class.getName());
		super.setLinkable(true);
		super.setPortletId(IcecapSSSWebPortletKeys.TERM_ADMIN);
		super.setSearchable(true);
		super.setSelectable(true);
	}

	@Override
	public AssetRenderer<Term> getAssetRenderer(long termId, int type) throws PortalException {
		Term term = _termLocalService.getTerm(termId);

		TermAssetRenderer termAssetRenderer = new TermAssetRenderer(term, _termModelResourcePermission);

		termAssetRenderer.setAssetRendererType(type);
		termAssetRenderer.setServletContext(_servletContext);

		return termAssetRenderer;
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public String getClassName() {
		return Term.class.getName();
	}
	
	@Override
	public PortletURL getURLAdd(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, 
			long classTypeId) {
		
		PortletURL portletURL = null;
		
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);
			
			portletURL = liferayPortletResponse.createLiferayPortletURL(
					getControlPanelPlid(themeDisplay), 
					IcecapSSSWebPortletKeys.TERM_ADMIN, 
					PortletRequest.RENDER_PHASE);
			portletURL.setParameter(
					IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, 
					MVCCommandNames.RENDER_ADMIN_TERM_EDIT);
			portletURL.setParameter(IcecapSSSWebKeys.SHOW_BACK, Boolean.FALSE.toString());
		}
		catch( PortalException e) {
			
		}

		return portletURL;
	}

	@Override
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
				liferayPortletResponse.createLiferayPortletURL(
						IcecapSSSWebPortletKeys.TERM_ADMIN, 
						PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}
	
	@Override
    public boolean isLinkable() {
        return _LINKABLE;
    }
	
	@Override
	public String getIconCssClass() {
	    return "term";
	}
	
	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, 
			long classPK, 
			String actionId) throws Exception {

		return TermModelPermissionHelper.contains(
				permissionChecker, classPK, actionId);
	}
	
	
}
