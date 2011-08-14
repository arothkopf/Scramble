/**
 * 
 */
package org.scramble.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * @author alan
 *
 */
public abstract class I18NMessageCapable {
	private ResourceBundle translator;
	
	private static Locale globalLocale = null;
	
	private static final String LOCALE_FILE_NAME = "Scramble.loc";
	
	/**
	 * CTOR using locale specified either in the commandline or in a local file (file is higher priority)
	 * @param bundleName
	 * @param commandLineLocale
	 */
	protected I18NMessageCapable(final String bundleName, final String commandLineLocale) {
		super();
		Locale locale = getLocale(commandLineLocale);
		this.translator = ResourceBundle.getBundle(bundleName, locale);
	}

	/**
	 * CTOR using locale specified in file
	 * @param bundleName
	 */
	protected I18NMessageCapable(final String bundleName) {
		this(bundleName, null);
	}
	
	/**
	 * @return the locale to use
	 */
	private Locale getLocale(final String commandLineLocale) {
		if(null != globalLocale) {
			return globalLocale;
		}
		Locale locale = null;
		String[] parts = getLocalePartsFromFile(); 
		
		// Top priority is locale file
		if(parts.length > 0){
	        locale = localeFromIsoStringParts(parts);
		} else {
			if(null != commandLineLocale) {
				try {
					locale = localeFromIsoStringParts(commandLineLocale.split("_"));
				}catch(Exception e) {
					locale = null;
				}				
			}
			if(null == locale) {
				locale = Locale.getDefault();
			}
			writeLocaleToFile(locale);
		} 
		
		globalLocale = locale;
		return locale;
	}

	/**
	 * @param localeParts
	 * @return
	 */
	private Locale localeFromIsoStringParts(String[] localeParts) {
		Locale locale;
		if(localeParts.length > 2) {
			locale = new Locale(localeParts[0], localeParts[1], localeParts[2]);
		} else if(localeParts.length > 1) {
			locale = new Locale(localeParts[0], localeParts[1]);
		} else {
			locale = new Locale(localeParts[0]);	        	
		}
		return locale;
	}

	/**
	 * @param locale
	 */
	private void writeLocaleToFile(Locale locale) {
		File localeFile = new File(LOCALE_FILE_NAME);
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(localeFile));
		    out.write(locale.toString());
		    out.close();
		} catch (IOException e) {
			System.out.println("Error writing locale file: " + localeFile.getAbsolutePath());
		}
	}

	/**
	 * @return the locale parts (from the ISO code), or an empty array if not available
	 */
	private String[] getLocalePartsFromFile() {
		String[] parts = new String[0];
		File localeFile = new File(LOCALE_FILE_NAME);

		if(localeFile.exists()) {
			try {
			    BufferedReader in = new BufferedReader(new FileReader(localeFile));
			    String str;
			    while ((str = in.readLine()) != null) {
			    	if(str.trim().length() > 0) {
			    		parts = str.split("_");
			    	}
			    }
			    in.close();
			} catch (IOException e) {
				System.out.println("Error reading locale file: " + localeFile.getAbsolutePath());
			}
		}
		return parts;
	}

	/**
	 * CTOR with injection
	 * @param translator
	 */
	protected I18NMessageCapable(ResourceBundle translator) {
		super();
		this.translator = translator;
	}
	
	/**
	 * Get I18N string
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return this.translator.getString(key);
	}

	/**
	 * Get I18N string
	 * @param key
	 * @return
	 */
	public String[] getLines(String key) {
		String decoded = this.translator.getString(key);
		StringTokenizer st = new StringTokenizer(decoded, "\n");
		ArrayList<String> list = new ArrayList<String>();
		while(st.hasMoreTokens()) {
			list.add(st.nextToken());
		}
		return list.toArray(new String[0]);
	}
	
	/**
	 * @return the translator
	 */
	public ResourceBundle getTranslator() {
		return translator;
	}
}
