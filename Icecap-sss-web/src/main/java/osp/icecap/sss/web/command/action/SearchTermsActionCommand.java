package osp.icecap.sss.web.command.action;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.persistence.PortletUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.trash.TrashHelper;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse.Copy;
import javax.portlet.RenderURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.service.TermLocalService;
import osp.icecap.sss.web.display.context.term.admin.TermAdminDisplayContext;

@Component(
		property = {
				"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN,
				"mvc.command.name=" + MVCCommandNames.ACTION_ADMIN_SEARCH_TERMS
		},
		service = MVCActionCommand.class
)
public class SearchTermsActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		System.out.println("SearchTermsActionCommand.doProcessAction");
		
		TermAdminDisplayContext termAdminDisplayContext = 
				new TermAdminDisplayContext(
				PortalUtil.getLiferayPortletRequest(actionRequest),
				PortalUtil.getLiferayPortletResponse(actionResponse),
				PortalUtil.getHttpServletRequest(actionRequest),
				_termLocalService,
				_trashHelper );

		actionRequest.getPortletSession().setAttribute(TermAdminDisplayContext.class.getName(), termAdminDisplayContext);
		
		RenderURL renderURL = actionResponse.createRedirectURL(Copy.ALL);
		renderURL.setParameter(
				IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, 
				MVCCommandNames.RENDER_ADMIN_TERM_LIST);
		
		actionResponse.sendRedirect(renderURL.toString());
		
	}

	@Reference
	private TermLocalService _termLocalService;
	
	@Reference(unbind = "-")
	protected void setTrashHelper(TrashHelper trashHelper) {
	  _trashHelper = trashHelper;
	}
	protected TrashHelper _trashHelper;
}
