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
	 * CTOR using default locale
	 */
	protected I18NMessageCapable(final String bundleName) {
		super();
		Locale locale = getLocale();
		this.translator = ResourceBundle.getBundle(bundleName, locale);
	}

	/**
	 * @return the locale to use
	 */
	private Locale getLocale() {
		if(null != globalLocale) {
			return globalLocale;
		}
		Locale locale = null;
		String[] parts = getLocaleFromFile(); 
		
		if(parts.length > 0){
	        if(parts.length > 2) {
	        	locale = new Locale(parts[0], parts[1], parts[2]);
	        } else if(parts.length > 1) {
	        	locale = new Locale(parts[0], parts[1]);
	        } else {
	        	locale = new Locale(parts[0]);	        	
	        }
		} else {
			locale = Locale.getDefault();
			writeLocaleToFile(locale);
		}
		
		globalLocale = locale;
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
	private String[] getLocaleFromFile() {
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
