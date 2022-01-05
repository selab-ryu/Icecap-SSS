package osp.icecap.sss.web.command.action;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.service.TermLocalService;
import osp.icecap.sss.util.term.IcecapSSSTermAttributeUtil;

@Component(
		property = {
				"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_ADMIN,
				"mvc.command.name=" + MVCCommandNames.ACTION_ADMIN_TERM_UPDATE
		},
		service = MVCActionCommand.class
)
public class UpdateTermActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		String termType = ParamUtil.getString(actionRequest, IcecapSSSWebKeys.SELECTED_TERM_TYPE);
		long termId = ParamUtil.getLong(actionRequest, IcecapSSSWebKeys.TERM_ID );

		Enumeration<String> keys = actionRequest.getParameterNames();
		while( keys.hasMoreElements()) {
			String key = keys.nextElement();
			System.out.println("---"+key+": "+actionRequest.getParameter(key));
		}
		
		ServiceContext sc = ServiceContextFactory.getInstance(Term.class.getName(), actionRequest);
		long[] categoryIds = sc.getAssetCategoryIds();
		
		String name = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.TERM_NAME);
		String version = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.TERM_VERSION);
		Map<Locale, String> displayNameMap = LocalizationUtil.getLocalizationMap(actionRequest, IcecapSSSTermAttributes.DISPLAY_NAME);
		Map<Locale, String> definitionMap = LocalizationUtil.getLocalizationMap(actionRequest, IcecapSSSTermAttributes.DEFINITION);
		String synonyms = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.SYNONYMS);
		Map<Locale, String> tooltipMap = LocalizationUtil.getLocalizationMap(actionRequest, IcecapSSSTermAttributes.TOOLTIP);
		int status = ParamUtil.getInteger(actionRequest, IcecapSSSTermAttributes.STATUS);
		String dedicatedAttributes = IcecapSSSTermAttributeUtil.getTypeDedicatedAttributes(actionRequest, termType);
		
		System.out.println("=== Term Attributes ===");
		System.out.println("Term Type: "+termType);
		System.out.println(IcecapSSSTermAttributes.TERM_NAME+": "+name);
		System.out.println(IcecapSSSTermAttributes.TERM_VERSION+": "+version);
		System.out.println(IcecapSSSTermAttributes.DISPLAY_NAME+": "+IcecapSSSTermAttributeUtil.convertLocalizedMapToJson(displayNameMap));
		System.out.println(IcecapSSSTermAttributes.DEFINITION+": "+IcecapSSSTermAttributeUtil.convertLocalizedMapToJson(definitionMap));
		System.out.println(IcecapSSSTermAttributes.SYNONYMS+": "+synonyms);
		System.out.println(IcecapSSSTermAttributes.TOOLTIP+": "+IcecapSSSTermAttributeUtil.convertLocalizedMapToJson(tooltipMap));
		System.out.println("Dedicated Attributes: "+dedicatedAttributes);
		System.out.println("Category Count: "+categoryIds.length);
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		for( long categoryId : categoryIds ) {
			AssetCategory category = AssetCategoryLocalServiceUtil.getCategory(categoryId);
			System.out.println("Category["+categoryId+"]: "+category.getTitle(themeDisplay.getLocale()));
		}
		System.out.println("=== END Term Attributes ===");
		
		_termLocalService.updateTerm(termId, name, version, termType, displayNameMap, definitionMap, tooltipMap, synonyms, status, dedicatedAttributes, sc);
	}
	
	@Reference
	private TermLocalService _termLocalService;
}
