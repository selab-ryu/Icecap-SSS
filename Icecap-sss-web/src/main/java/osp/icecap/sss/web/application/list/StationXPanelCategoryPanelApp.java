package osp.icecap.sss.web.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.panel.category.constants.StationXPanelCategoryKeys;

/**
 * @author DELL
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=300",
		"panel.category.key=" + StationXPanelCategoryKeys.ICECAP_SSS
	},
	service = PanelApp.class
)
public class StationXPanelCategoryPanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return IcecapSSSWebPortletKeys.TERM_ADMIN;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}