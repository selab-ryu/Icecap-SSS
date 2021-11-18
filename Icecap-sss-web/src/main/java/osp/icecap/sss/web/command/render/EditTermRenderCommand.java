package osp.icecap.sss.web.command.render;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSJsps;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.service.TermLocalService;

@Component(
	    immediate = true,
	    property = {
	        "javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_MANAGER,
	        "mvc.command.name="+MVCCommandNames.RENDER_TERM_EDIT
	    },
	    service = MVCRenderCommand.class
	)
public class EditTermRenderCommand implements MVCRenderCommand {

	public String render( 
			RenderRequest renderRequest, 
			RenderResponse renderResponse) throws PortletException {
		
		long termId = ParamUtil.getLong(renderRequest, IcecapSSSWebKeys.TERM_ID, 0);
		
		if( termId > 0 ) {
			try {
				Term term = _termLocalService.getTerm(termId);
				renderRequest.setAttribute(IcecapSSSWebKeys.TERM, term);
			}
			catch( PortalException e) {
				e.printStackTrace();
			}
		}
		else {
			String termType = ParamUtil.getString(renderRequest, IcecapSSSWebKeys.TERM_TYPE);
			System.out.println("In Edit Render: termType - " + termType );
		}
		
		return IcecapSSSJsps.EDIT_TERM_JSP;
	}
	
	@Reference
	private volatile TermLocalService _termLocalService;
}
