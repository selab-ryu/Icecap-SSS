package osp.icecap.sss.constants;

public class IcecapSSSTermAttributes {
	public static final String TERM_NAME = "termName";
	public static final String TERM_VERSION = "termVersion";
	public static final String TERM_TYPE = "termType";
	public static final String DISPLAY_NAME = "displayName";
	public static final String DEFINITION = "definition";
	public static final String TOOLTIP = "tooltip";
	public static final String SYNONYMS = "synonyms";
	public static final String VALUE = "value";
	public static final String MIN_VALUE = "minValue";
	public static final String MAX_VALUE = "maxValue";
	public static final String LOWER_BOUNDARY = "lowerBoundary";
	public static final String UPPER_BOUNDARY = "upperBoundary";
	public static final String UNIT = "unit";
	public static final String UNCERTAINTY = "uncertainty";
	public static final String SWEEPABLE = "sweepable";
	public static final String MIN_LENGTH = "minLength";
	public static final String MAX_LENGTH = "maxLength";
	public static final String NEW_LINE = "newLine";
	public static final String VALIDATION_RULE = "validationRule";
	public static final String DISPLAY_STYLE = "displayStyle";
	public static final String REF_DATABASES = "refDatabases";
	public static final String REF_DATATYPES = "refDataTypes";

	public static final String STANDARDIZED = "standardized";
	
	public static String toJsonAttr( final String attr) {
		return attr + "_";
	}
}
