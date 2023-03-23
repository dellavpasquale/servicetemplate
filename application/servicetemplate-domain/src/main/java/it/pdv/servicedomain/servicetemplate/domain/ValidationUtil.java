package it.pdv.servicedomain.servicetemplate.domain;

public class ValidationUtil {
	
	private ValidationUtil() {
		
	}

	/**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs  the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNotBlank(CharSequence cs) {
    	return !isBlank(cs);
    }

	public static boolean enumNotIn(Enum<?> value, Enum<?>... valueList) {
		return !enumIn(value, valueList);
	}
	
	public static boolean enumIn(Enum<?> value, Enum<?>... valueList) {
		boolean found = false;
		for (Enum<?> currentValue : valueList) {
			if(currentValue.equals(value)) {
				found = true;
				break;
			}
		}
		return found;
	}
	
}
