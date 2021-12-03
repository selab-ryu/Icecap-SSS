package osp.icecap.sss.web.portlet;


import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

import osp.icecap.sss.constants.IcecapSSSJsps;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;

/**
 * @author Jerry H. Seo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=TermManager",
		"javax.portlet.init-param.template-path="+MVCCommandNames.RENDER_ROOT,
		"javax.portlet.init-param.view-template="+IcecapSSSJsps.TERM_LIST_JSP,
		"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_MANAGER,
		"javax.portlet.supported-locale=en_US, ko_KR",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
		// "javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class TermManagerPortlet extends MVCPortlet {
}