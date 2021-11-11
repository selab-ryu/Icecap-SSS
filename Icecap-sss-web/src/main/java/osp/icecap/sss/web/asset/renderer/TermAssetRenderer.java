package osp.icecap.sss.web.asset.renderer;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import osp.icecap.sss.constants.IcecapSSSAssetRendererPaths;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.web.security.permission.resource.TermModelResourcePermission;

public class TermAssetRenderer extends BaseJSPAssetRenderer<Term> {

	private final Term _term;
	private final ResourceBundleLoader _resourceBundleLoader;
	
	public TermAssetRenderer(Term term, ResourceBundleLoader resourceBundleLoader) {
		_term = term;
		_resourceBundleLoader = resourceBundleLoader;
	}
	
	@Override
	public Term getAssetObject() {
		return _term;
	}

	@Override
	public long getGroupId() {
		return _term.getGroupId();
	}

	@Override
	public long getUserId() {
		return _term.getUserId();
	}

	@Override
	public String getUserName() {
		return _term.getUserName();
	}

	@Override
	public String getUuid() {
		return _term.getUuid();
	}

	@Override
	public String getClassName() {
		return Term.class.getName();
	}

	@Override
	public long getClassPK() {
		return _term.getTermId();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {
		Locale locale = portletRequest.getLocale();
		
		int abstractLength = AssetUtil.ASSET_ENTRY_ABSTRACT_LENGTH;

		if (portletRequest != null) {
			abstractLength = GetterUtil.getInteger(
					portletRequest.getAttribute(WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH),
					AssetUtil.ASSET_ENTRY_ABSTRACT_LENGTH);
		}

		String summary = _term.getDefinition(locale);

		if (Validator.isNull(summary)) {
			summary = HtmlUtil.stripHtml( StringUtil.shorten(summary, abstractLength));
		}

		return summary;
	}

	@Override
	public String getTitle(Locale locale) {
		return _term.getDisplayTitle(locale);
	}

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest, String template) {
		if( template.equals(TEMPLATE_ABSTRACT) ||
			  template.equals(TEMPLATE_FULL_CONTENT) ) {
			return IcecapSSSAssetRendererPaths.TERM_ASSET_RENDERER_PATH+template+".jsp";
		}
		return null;
	}
	
	@Override
	public boolean isPrintable() {
		return true;
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) throws PortalException {
		return TermModelResourcePermission.contains(permissionChecker, _term.getTermId(), ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) throws PortalException {
		return TermModelResourcePermission.contains(permissionChecker, _term.getTermId(), ActionKeys.VIEW);
	}

	public boolean hasDeletePermission(PermissionChecker permissionChecker) throws PortalException {
		return TermModelResourcePermission.contains(permissionChecker, _term.getTermId(), ActionKeys.DELETE);
	}

	@Override
	public boolean include(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			String template) throws Exception {
		httpServletRequest.setAttribute(IcecapSSSWebKeys.TERM, _term);
		
		return super.include(httpServletRequest, httpServletResponse, template);
	}

	@Override
	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {
		Group group = GroupLocalServiceUtil.fetchGroup(_term.getGroupId());

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
						liferayPortletRequest, 
						group, 
						IcecapSSSWebPortletKeys.TERM_MANAGER, 
						0, //referer group id 
						0, //referer Plid
						PortletRequest.RENDER_PHASE);

		    portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.RENDER_TERM_EDIT);
		    portletURL.setParameter(IcecapSSSWebKeys.TERM, String.valueOf(_term.getTermId()));

		    return portletURL;
	}

	@Override
	public String getURLView(LiferayPortletResponse liferayPortletResponse, WindowState windowState) throws Exception {
		AssetRendererFactory<Term> assetRendererFactory = getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(liferayPortletResponse, windowState);

		portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.RENDER_TERM_VIEW);
		    portletURL.setParameter(IcecapSSSWebKeys.TERM_ID, String.valueOf(_term.getTermId()));
		    portletURL.setWindowState(windowState);

		    return portletURL.toString();
	}

	@Override
	public String getURLViewInContext(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, String noSuchEntryRedirect) throws Exception {
		return getURLViewInContext(
		        liferayPortletRequest, noSuchEntryRedirect, MVCCommandNames.RENDER_TERM_VIEW,
		        IcecapSSSWebKeys.TERM_ID, _term.getTermId());
	}
	

}
