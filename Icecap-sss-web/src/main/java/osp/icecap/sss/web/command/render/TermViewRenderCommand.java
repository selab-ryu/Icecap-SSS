package osp.icecap.sss.web.command.render;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import osp.icecap.sss.constants.IcecapSSSJsps;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;

@Component(
	    immediate = true,
	    property = {
	        "javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN,
	        "mvc.command.name="+MVCCommandNames.RENDER_ADMIN_TERM_VIEW
	    },
	    service = MVCRenderCommand.class
	)
public class TermViewRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException {
		
		return IcecapSSSJsps.ADMIN_VIEW_TERM_JSP;
	}
}
