package osp.icecap.sss.web.command.render;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSJsps;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.service.TermLocalService;

@Component(
	    immediate = true,
	    property = {
	        "javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN,
	        "mvc.command.name="+MVCCommandNames.RENDER_ADMIN_TERM_DELETE
	    },
	    service = MVCRenderCommand.class
	)
public class DeleteTermRenderCommand implements MVCRenderCommand {

	public String render( 
			RenderRequest renderRequest, 
			RenderResponse renderResponse) throws PortletException {
		
		System.out.println("DELETE RENDER COMMAND");
		
		return IcecapSSSJsps.ADMIN_TERM_LIST_JSP;
	}
	
	@Reference
	private volatile TermLocalService _termLocalService;
}
