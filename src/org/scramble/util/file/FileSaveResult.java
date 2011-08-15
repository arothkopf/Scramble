/**
 * Scramble - Copyright (c) 2011 Alan Rothkopf 
 * Source code released under GPLv3 ( http://www.gnu.org/licenses/gpl-3.0.html )
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
