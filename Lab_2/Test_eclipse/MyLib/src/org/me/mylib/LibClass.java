/**@
 * @author Tymoshenko Oleh <adress>@o.tymoshenko@student.csn.khai.edu</adress>
 * @version 1.0
 * @since 2020-10-12
 */

package org.me.mylib;

/**
 * Class for working with acrostic
 * @author Tymoshenko Oleh
 *
 */
public class LibClass {
	
	/**
	 * Returns acrostic created from words in args
	 * @param args words for creating an acrostic
	 * @return created acrostic from args
	 */
	public static String acrostic(String[] args) {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			if (args[i].length() > i) {
				b.append(args[i].charAt(i));
			} else {
				b.append('?');
			}
		}
		return b.toString();
	}
}
