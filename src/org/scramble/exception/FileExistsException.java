/**
 * 
 */
package org.scramble.exception;

/**
 * Exception thrown to avoid overwriting a file
 * @author alan
 *
 */
public class FileExistsException extends Exception {
	private static final long serialVersionUID = -8669580002931281274L;

	public FileExistsException(String fileName) {
		super(fileName);
	}

}
