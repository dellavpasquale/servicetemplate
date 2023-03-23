package it.pdv.servicedomain.servicetemplate.domain;

public class LoggingUtil {
	
	private LoggingUtil() {
		
	}

	public static String formatKeyValuePairs(Object...args) {
		String result = null;
		if(args != null) {
			StringBuilder buffer = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				if(i%2 == 0) {
					buffer.append("'").append(arg).append("'").append(": <");					
				} else {
					buffer.append(arg).append(">").append(", ");
				}
			}
			result = buffer.toString();
			result = result.substring(0, result.length()-2);
		}
		return result;
	}
	
}
