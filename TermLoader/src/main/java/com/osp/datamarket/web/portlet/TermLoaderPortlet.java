package com.osp.datamarket.web.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.osp.datamarket.web.constants.TermLoaderPortletKeys;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.service.TermLocalService;

/**
 * @author selab-ryu
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.css-class-wrapper=icecap-web-std-term",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=TermLoader",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/html/term_manager/manager_view.jsp",
		"javax.portlet.name=" + TermLoaderPortletKeys.TERMLOADER,
		"javax.portlet.supported-locale=ko",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class TermLoaderPortlet extends MVCPortlet {
		
	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {		
	
		
		// send local service to view.jsp
		renderRequest.setAttribute("TERM_LOCAL_SERVICE", _termLocalService);
		
		super.render(renderRequest, renderResponse);
	}

	@Reference
	private TermLocalService _termLocalService;
}