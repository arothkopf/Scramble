/**
 * 
 */
package org.scramble.util.file;

/**
 * Tristate result for file save attempt with no overwrite
 * @author alan
 *
 */
public enum FileSaveResult {
	SUCCESS, FAILURE, BLOCKED_FILE_EXISTS;
}
