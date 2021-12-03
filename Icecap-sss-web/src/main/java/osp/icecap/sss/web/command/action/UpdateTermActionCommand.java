package osp.icecap.sss.web.command.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import osp.icecap.sss.constants.IcecapSSSActionKeys;
import osp.icecap.sss.constants.IcecapSSSTermAttributes;
import osp.icecap.sss.constants.IcecapSSSTermTypes;
import osp.icecap.sss.constants.IcecapSSSWebKeys;
import osp.icecap.sss.constants.IcecapSSSWebPortletKeys;
import osp.icecap.sss.constants.MVCCommandNames;
import osp.icecap.sss.model.Term;
import osp.icecap.sss.service.TermLocalService;

@Component(
		property = {
				"javax.portlet.name=" + IcecapSSSWebPortletKeys.TERM_MANAGER,
				"mvc.command.name=" + MVCCommandNames.ACTION_TERM_UPDATE
		},
		service = MVCActionCommand.class
)
public class UpdateTermActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
		String termType = ParamUtil.getString(actionRequest, IcecapSSSWebKeys.SELECTED_TERM_TYPE);
		long termId = ParamUtil.getLong(actionRequest, IcecapSSSWebKeys.TERM_ID );
		
		System.out.println("CMD: "+cmd);
		System.out.println("Term Type: "+termType);
		
		ServiceContext sc = ServiceContextFactory.getInstance(Term.class.getName(), actionRequest);
		long[] categoryIds = sc.getAssetCategoryIds();
		
		String name = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.TERM_NAME);
		String version = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.TERM_VERSION);
		Map<Locale, String> displayNameMap = LocalizationUtil.getLocalizationMap(actionRequest, IcecapSSSTermAttributes.DISPLAY_NAME);
		Map<Locale, String> definitionMap = LocalizationUtil.getLocalizationMap(actionRequest, IcecapSSSTermAttributes.DEFINITION);
		String synonyms = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.SYNONYMS);
		Map<Locale, String> tooltipMap = LocalizationUtil.getLocalizationMap(actionRequest, IcecapSSSTermAttributes.TOOLTIP);
		int status = ParamUtil.getInteger(actionRequest, IcecapSSSTermAttributes.STATUS);
		String dedicatedAttributes = null;
		
		if( termType.equals(IcecapSSSTermTypes.STRING) ){
			dedicatedAttributes = getStringAttributesToJson(actionRequest);
		}
		else if( termType.equals(IcecapSSSTermTypes.NUMERIC) ){
			dedicatedAttributes = getNumericAttributesToJson(actionRequest);
		}
		else if( termType.equals(IcecapSSSTermTypes.BOOLEAN) ){
			dedicatedAttributes = getBooleanAttributesToJson(actionRequest);
		}
		else if( termType.equals(IcecapSSSTermTypes.DATA_LINK_ARRAY) ){
			dedicatedAttributes = getDataLinkArrayAttributesToJson(actionRequest);
		}
		
		System.out.println("=== Term Attributes ===");
		System.out.println(IcecapSSSTermAttributes.TERM_NAME+": "+name);
		System.out.println(IcecapSSSTermAttributes.TERM_VERSION+": "+version);
		System.out.println(IcecapSSSTermAttributes.DISPLAY_NAME+": "+convertLocalizedMapToJson(displayNameMap));
		System.out.println(IcecapSSSTermAttributes.DEFINITION+": "+convertLocalizedMapToJson(definitionMap));
		System.out.println(IcecapSSSTermAttributes.SYNONYMS+": "+synonyms);
		System.out.println(IcecapSSSTermAttributes.TOOLTIP+": "+convertLocalizedMapToJson(tooltipMap));
		System.out.println("Dedicated Attributes: "+dedicatedAttributes);
		System.out.println("=== END Term Attributes ===");
		
		try {
		if( cmd.equals(IcecapSSSActionKeys.ADD_TERM) ) {
			_termLocalService.addTerm(name, version, termType, displayNameMap, definitionMap, tooltipMap, synonyms, dedicatedAttributes, sc);
		}
		else if( cmd.equals(IcecapSSSActionKeys.UPDATE_TERM) ){
			_termLocalService.updateTerm(termId, name, version, termType, displayNameMap, definitionMap, tooltipMap, synonyms, status, dedicatedAttributes, sc);
		}
		} catch( PortalException e ) {
			e.printStackTrace();
			throw e;
		}
	}

	private String getDataLinkArrayAttributesToJson( ActionRequest actionRequest ) {
		
		int minLength = ParamUtil.getInteger(actionRequest, IcecapSSSTermAttributes.MIN_LENGTH);
		int maxLength = ParamUtil.getInteger(actionRequest, IcecapSSSTermAttributes.MAX_LENGTH);
		String refDatabases = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.REF_DATABASES);
		
		JSONObject jsonAttr = JSONFactoryUtil.createJSONObject();
		jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.MIN_LENGTH), minLength);
		jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.MAX_LENGTH), maxLength);
		jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.REF_DATABASES), refDatabases);
		
		return jsonAttr.toJSONString();
	}

	private String getBooleanAttributesToJson( ActionRequest actionRequest ) {
		
		int displayStyle = ParamUtil.getInteger(actionRequest, IcecapSSSTermAttributes.DISPLAY_STYLE);
		
		JSONObject jsonAttr = JSONFactoryUtil.createJSONObject();
		jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.DISPLAY_STYLE), displayStyle);
		
		return jsonAttr.toJSONString();
	}

	private String getNumericAttributesToJson( ActionRequest actionRequest ) {
		
		String minValue = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.MIN_VALUE);
		String maxValue = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.MAX_VALUE);
		boolean lowerBoundary = ParamUtil.getBoolean(actionRequest, IcecapSSSTermAttributes.LOWER_BOUNDARY);
		boolean upperBoundary = ParamUtil.getBoolean(actionRequest, IcecapSSSTermAttributes.UPPER_BOUNDARY);
		String unit = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.UNIT);
		boolean uncertainty = ParamUtil.getBoolean(actionRequest, IcecapSSSTermAttributes.UNCERTAINTY);
		boolean sweepable = ParamUtil.getBoolean(actionRequest, IcecapSSSTermAttributes.SWEEPABLE);
		
		JSONObject jsonAttr = JSONFactoryUtil.createJSONObject();
		if( !minValue.isEmpty() )
			jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.MIN_VALUE), minValue);
		if( !maxValue.isEmpty() )
			jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.MAX_VALUE), maxValue);
		if( !minValue.isEmpty() )
			jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.LOWER_BOUNDARY), lowerBoundary);
		if( !maxValue.isEmpty() )
			jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.UPPER_BOUNDARY), upperBoundary);
		if( !unit.isEmpty() )
			jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.UNIT), unit);
		jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.UNCERTAINTY), uncertainty);
		jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.SWEEPABLE), sweepable);
		
		return jsonAttr.toJSONString();
	}

	private String getStringAttributesToJson( ActionRequest actionRequest ) {
		
		int minLength = ParamUtil.getInteger(actionRequest, IcecapSSSTermAttributes.MIN_LENGTH);
		int maxLength = ParamUtil.getInteger(actionRequest, IcecapSSSTermAttributes.MAX_LENGTH);
		boolean newLine = ParamUtil.getBoolean(actionRequest, IcecapSSSTermAttributes.NEW_LINE);
		String validationRule = ParamUtil.getString(actionRequest, IcecapSSSTermAttributes.VALIDATION_RULE);
		
		JSONObject jsonAttr = JSONFactoryUtil.createJSONObject();
		if( minLength > 1 )
			jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.MIN_LENGTH), minLength);
		if( maxLength > minLength )
			jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.MAX_LENGTH), maxLength);
		jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.NEW_LINE), newLine);
		if( !validationRule.isEmpty() )
			jsonAttr.put(toJsonAttr(IcecapSSSTermAttributes.VALIDATION_RULE), validationRule);
		
		return jsonAttr.toJSONString();
	}
	
	private String convertLocalizedMapToJson( Map<Locale, String> map ) {
		Set<Locale> keySet = map.keySet();
		JSONObject jsonMap = JSONFactoryUtil.createJSONObject();
		for( Locale locale : keySet ) {
			jsonMap.put(locale.toString(), map.get(locale));
		}
		
		return jsonMap.toJSONString();
	}
	
	private String toJsonAttr ( String attr ) {
		return attr + "_";
	}
	
	@Reference
	private TermLocalService _termLocalService;
}
