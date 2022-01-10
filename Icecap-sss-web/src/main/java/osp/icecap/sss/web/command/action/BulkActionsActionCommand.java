package osp.icecap.sss.web.command.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Arrays;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.debug.Debug;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.service.TermLocalService;

@Component(
		property = {
				"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN,
				"mvc.command.name=" + MVCCommandNames.ACTION_ADMIN_BULK_ACTIONS
		},
		service = MVCActionCommand.class
)
public class BulkActionsActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		Debug.printHeader("BulkActionsActionCommand");
		
		long[] termIds = ParamUtil.getLongValues(actionRequest, IcecapSSSWebKeys.ROW_IDS);
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		String redirect = ParamUtil.getString(actionRequest, IcecapSSSWebKeys.REDIRECT);

		if( cmd.equals(IcecapSSSActionKeys.DELETE_TERMS) ) {
			_termLocalService.removeTerms(termIds);
		}
		
		actionResponse.sendRedirect(redirect);
		
		Debug.printFooter("BulkActionsActionCommand");
	}

	@Reference
	private TermLocalService _termLocalService;
}
