/**
 * 
 */
package org.scramble.util.file;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.scramble.util.I18NMessageCapable;


/**
 * PNG-only file filter
 * @author alan
 *
 */
public class PNGFilter extends ImageFileFilter {		
	
	
	public PNGFilter(I18NMessageCapable translator) {
		super(translator);
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File file) {
		if(file.isDirectory()) {
			return true;
		}
		String namelc = file.getName().toLowerCase();
		if(namelc.toLowerCase().endsWith(".png")) {
			return true;
		}
		
		return false;

	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return getI18NString("image.filter.png.description");
	}

}
