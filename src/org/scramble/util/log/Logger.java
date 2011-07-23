/**
 * 
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
