package osp.icecap.sss.web.command.action;

import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;


import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;

@Component(
		property = {
				"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN,
				"mvc.command.name=" + MVCCommandNames.ACTION_LOAD_TERM_ATTRIBURES
		},
		service = MVCActionCommand.class
)
public class LoadTermAttributesActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		String termType = ParamUtil.getString(actionRequest, IcecapSSSWebKeys.TERM_TYPE);
		String renderCommand = ParamUtil.getString(actionRequest, "mvcRenderCommandName");
		String termListViewURL = ParamUtil.getString(actionRequest, "termListViewURL" );
		
		System.out.println("Action termType: "+ termType);
		System.out.println("MVC Render Command: " + renderCommand);
		System.out.println("termListViewURL: " + termListViewURL);
		
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		PortletURL renderURL = PortletURLFactoryUtil.create(
				actionRequest, 
				themeDisplay.getPortletDisplay().getId(), 
				themeDisplay.getPlid(),
				PortletRequest.RENDER_PHASE);
		renderURL.setParameter(IcecapSSSWebKeys.TERM_TYPE, termType);
		renderURL.setParameter(IcecapSSSWebKeys.MVC_RENDER_COMMAND_NAME, renderCommand);
		renderURL.setParameter(IcecapSSSWebKeys.REDIRECT, termListViewURL);
		
		System.out.println("Render URL: "+renderURL.toString());
		
		actionResponse.sendRedirect(renderURL.toString());
	}

}
