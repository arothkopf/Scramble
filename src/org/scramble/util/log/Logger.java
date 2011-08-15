/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
 */
package org.scramble.util.log;

/**
 * @author alan
 *
 */
public class Logger {

	/**
	 * Logging call
	 * @param error
	 * @param e
	 */
	public void logError(String error, Exception e) {
		System.out.println(error);
		e.printStackTrace();
	}
}
