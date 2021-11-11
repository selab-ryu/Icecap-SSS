package osp.icecap.sss.web.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSConstants;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.service.TermLocalService;
import osp.icecap.sss.web.asset.renderer.TermAssetRenderer;
import osp.icecap.sss.web.security.permission.resource.TermModelResourcePermission;

@Component(
		immediate = true,
		property = {
				"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_MANAGER
		},
		service = AssetRendererFactory.class
)
public class TermAssetRendererFactory extends BaseAssetRendererFactory<Term> {
	public TermAssetRendererFactory() {
		super.setClassName(Term.class.getName());
		super.setLinkable(true);
		super.setPortletId(IcecapSSSWebPortletKeys.TERM_MANAGER);
		super.setSearchable(true);
	}

	@Override
	public AssetRenderer<Term> getAssetRenderer(long termId, int type) throws PortalException {
		Term term = _termLocalService.getTerm(termId);

		TermAssetRenderer termAssetRenderer =
				new TermAssetRenderer(term, _resourceBundleLoader);

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
			LiferayPortletResponse liferayPortletResponse, long classTypeId) {
		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
				liferayPortletRequest, getGroup(liferayPortletRequest),
				IcecapSSSWebPortletKeys.TERM_MANAGER, 
				0, 
				0, 
				PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.RENDER_TERM_EDIT);

		return portletURL;
	}

	@Override
	public PortletURL getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState) {

		LiferayPortletURL liferayPortletURL =
				liferayPortletResponse.createLiferayPortletURL(
						IcecapSSSWebPortletKeys.TERM_MANAGER, 
						PortletRequest.RENDER_PHASE);

		try {
			liferayPortletURL.setWindowState(windowState);
		}
		catch (WindowStateException wse) {
		}

		return liferayPortletURL;
	}
	
	@Override
	public String getIconCssClass() {
	    return "term";
	}
	
	@Override
	public boolean hasAddPermission(
			PermissionChecker permissionChecker, 
			long groupId, 
			long classTypeId) throws Exception {
		return TermModelResourcePermission.contains(
				permissionChecker, groupId, ActionKeys.ADD_ENTRY);
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, 
			long classPK, 
			String actionId) throws Exception {

		return TermModelResourcePermission.contains(
				permissionChecker, classPK, actionId);
	}
	
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
	public void setResourceBundleLoader( ResourceBundleLoader resourceBundleLoader) {

		_resourceBundleLoader = resourceBundleLoader;
	}
	private ResourceBundleLoader _resourceBundleLoader;
	
	@Reference(
			target = "(osgi.web.symbolicname="+IcecapSSSConstants.WEB_BUNDLE_NAME+")", 
			unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}
	private ServletContext _servletContext;
	
	public static final String TYPE = "term";

}
