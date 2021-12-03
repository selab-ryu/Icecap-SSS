package osp.icecap.sss.workflow;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.model.Term;
import osp.icecap.sss.service.TermLocalService;

@Component(
		immediate = true, 
		service = WorkflowHandler.class
)
public class TermWorkflowHandler extends BaseWorkflowHandler<Term> {

	@Override
	public String getClassName() {
		return Term.class.getName();
	}

	@Override
	public String getType(Locale locale) {
		return _resourceActions.getModelResource(locale, getClassName());
	}

	@Override
	public Term updateStatus(int status, Map<String, Serializable> workflowContext) throws PortalException {
		long userId = GetterUtil.getLong(
				(String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
		long resourcePrimKey = GetterUtil.getLong(
				(String)workflowContext.get(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

		ServiceContext serviceContext = (ServiceContext)workflowContext.get("serviceContext");

		return _termLocalService.updateStatus(userId, resourcePrimKey, status, serviceContext);
	}
	
	@Reference(unbind = "-")
	protected void setResourceActions(ResourceActions resourceActions) {
		_resourceActions = resourceActions;
	}
	private ResourceActions _resourceActions;
	
	@Reference(unbind = "-")
	protected void setTermLocalService(TermLocalService termLocalService) {
		_termLocalService = termLocalService;
	}
	private TermLocalService _termLocalService;

}