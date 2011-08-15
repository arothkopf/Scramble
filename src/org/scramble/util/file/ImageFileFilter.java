/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.util.file;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.scramble.util.I18NMessageCapable;


/**
 * @author alan
 *
 */
public class ImageFileFilter extends FileFilter {
	private I18NMessageCapable translator;
	
	/**
	 * CTOR
	 * @param translator
	 */
	public ImageFileFilter(I18NMessageCapable translator) {
		super();
		this.translator = translator;
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
		if(this.hasImageExtension(namelc)) {
			return true;
		}
		
		return false;
	}


	private boolean hasImageExtension(String namelc) {
		return namelc.endsWith(".gif") || namelc.endsWith(".jpg") || namelc.endsWith(".png");
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return getI18NString("image.filter.all.description");
	}


	/**
	 * @param descriptionKey
	 * @return
	 */
	protected String getI18NString(String descriptionKey) {
		return this.translator.getString(descriptionKey);
	}

}
