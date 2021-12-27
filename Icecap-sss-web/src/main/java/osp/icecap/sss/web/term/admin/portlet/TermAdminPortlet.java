package osp.icecap.sss.web.term.admin.portlet;


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
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"javax.portlet.display-name=Term Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.portlet-title-based-navigation=true",
		"javax.portlet.init-param.template-path="+MVCCommandNames.RENDER_ROOT,
		"javax.portlet.init-param.view-template="+IcecapSSSJsps.ADMIN_TERM_LIST_JSP,
		"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html",
		"com.liferay.portlet.add-default-resource=true"
	},
	service = Portlet.class
)
public class TermAdminPortlet extends MVCPortlet {
}