package osp.icecap.sss.web.command.render;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.trash.TrashHelper;

import java.util.Enumeration;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSJsps;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.web.display.context.TermDisplayContext;

@Component(
	    immediate = true,
	    property = {
	        "javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_MANAGER,
	        "mvc.command.name="+MVCCommandNames.RENDER_ROOT,
	        "mvc.command.name="+MVCCommandNames.RENDER_TERM_LIST
	    },
	    service = MVCRenderCommand.class
	)
public class TermListViewRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		
		Enumeration<String> keys = renderRequest.getParameterNames();
		while( keys.hasMoreElements() ) {
			String key = keys.nextElement();
			System.out.println( key + " : " + ParamUtil.getString(renderRequest, key));
		}
		
		renderRequest.setAttribute(
				TermDisplayContext.class.getName(), 
				new TermDisplayContext(
						renderRequest,
						renderResponse,
						_trashHelper));
		
		return IcecapSSSJsps.TERM_LIST_JSP;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		  this._portal = portal;
	}
	protected Portal _portal;
	
	@Reference(unbind = "-")
	protected void setTrashHelper(TrashHelper trashHelper) {
	  this._trashHelper = trashHelper;
	}
	protected TrashHelper _trashHelper;
}
