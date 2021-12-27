package osp.icecap.sss.web.command.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Arrays;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.service.TermLocalService;

@Component(
		property = {
				"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN,
				"mvc.command.name=" + MVCCommandNames.ACTION_ADMIN_TERM_DELETE
		},
		service = MVCActionCommand.class
)
public class DeleteTermsActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		String strTermIds = ParamUtil.getString(actionRequest, IcecapSSSWebKeys.TERM_IDS );
		String CMD = ParamUtil.getString(actionRequest, Constants.CMD);
		String redirect = ParamUtil.getString(actionRequest, IcecapSSSWebKeys.REDIRECT);
		
		JSONArray jsonTermIds = JSONFactoryUtil.createJSONArray(strTermIds);
		for( int i=0; i<jsonTermIds.length(); i++) {
			_termLocalService.removeTerm(jsonTermIds.getLong(i));
		}
	}

	@Reference
	private TermLocalService _termLocalService;
}
