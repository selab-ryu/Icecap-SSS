package osp.icecap.sss.web.command.render;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.trash.TrashHelper;

import java.util.Enumeration;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSJsps;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.debug.Debug;
import osp.icecap.sss.service.TermLocalService;
import osp.icecap.sss.web.display.context.term.admin.TermAdminDisplayContext;
import osp.icecap.sss.web.display.context.term.admin.TermAdminManagementToolbarDisplayContext;

@Component(
	    immediate = true,
	    property = {
	        "javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN,
	        "mvc.command.name="+MVCCommandNames.RENDER_ADMIN_SEARCH_TERMS
	    },
	    service = MVCRenderCommand.class
	)
public class SearchTermsRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		
		Debug.printHeader("SearchTermsRenderCommand");
		
		Enumeration<String> keys = renderRequest.getParameterNames();
		while( keys.hasMoreElements() ) {
			String key = keys.nextElement();
			System.out.println(key + ": "+ParamUtil.getString(renderRequest, key));
		}
		
		TermAdminDisplayContext termAdminDisplayContext = 
					new TermAdminDisplayContext(
												PortalUtil.getLiferayPortletRequest(renderRequest),
												PortalUtil.getLiferayPortletResponse(renderResponse),
												PortalUtil.getHttpServletRequest(renderRequest),
												_termLocalService,
												_trashHelper);
		
		TermAdminManagementToolbarDisplayContext termAdminManagementToolbarDisplayContext =
				new TermAdminManagementToolbarDisplayContext(
							renderRequest, 
							renderResponse, 
							termAdminDisplayContext
				);
		renderRequest.setAttribute(
				TermAdminManagementToolbarDisplayContext.class.getName(), 
				termAdminManagementToolbarDisplayContext );
		
		Debug.printFooter("SearchTermsRenderCommand");
		return IcecapSSSJsps.ADMIN_TERM_LIST_JSP;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		  this._portal = portal;
	}
	protected Portal _portal;
	
	@Reference(unbind = "-")
	protected void setTrashHelper(TrashHelper trashHelper) {
	  _trashHelper = trashHelper;
	}
	protected TrashHelper _trashHelper;
	
	@Reference
	private TermLocalService _termLocalService;
}
