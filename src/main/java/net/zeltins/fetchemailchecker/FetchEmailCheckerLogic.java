package net.zeltins.fetchemailchecker;

import java.util.HashSet;
import org.apache.logging.log4j.util.Strings;

/**
 * Implements back-end logic for checking emails
 *
 */
public class FetchEmailCheckerLogic {

	private HashSet<String> seenEmails = new HashSet<String>();
	private static String GmailDomain = "gmail.com";
	
	/**
	 * Returns normalized version of email: all lowercase, for Gmail domains does 
	 * post-processing to eliminate local-part after + and also all "."
	 * Assumes email is valid
	 *
	 */
	public static String NormalizeEmail(String email) throws Exception {
		email = email.toLowerCase();
		String[] emailParts = email.split("@");
		if ( emailParts[1].equals(GmailDomain) ) {
			// remove anything after + in localpart
			String[] localParts = emailParts[0].split("\\+");
			// remove all "." characters and return
			return RemoveCharacter(localParts[0], '.') + "@" + emailParts[1];
		}
		return email;
	}
	
	/**
	 * Removes all instances of specified character from string
	 * @param String
	 * @param Character to remove
	 * @return String with characters removed
	 */
	public static String RemoveCharacter(String s,char c) {
		StringBuilder sb = new StringBuilder();
		for (char stringChar : s.toCharArray() ) {
			if ( stringChar != c ) {
				sb.append(stringChar);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Checks if email is valid 
	 * Not RFC822 compliant due to complexity and general dislike of regex
	 * http://www.ex-parrot.com/~pdw/Mail-RFC822-Address.html 
	 */
	public static Boolean IsValidEmail(String email) {
		// at most one @, at least something before and after, at least one . after @
		if ( email == null ) {
			return false;
		}
		// we must have exactly one @ in the string
		if ( !email.contains("@") || email.indexOf("@") != email.lastIndexOf("@") ) {
			return false;
		}
		String[] emailParts = email.split("@"); // we know it has exactly 2 parts now
		
		if ( Strings.isBlank(emailParts[0]) || Strings.isBlank(emailParts[1]) ) {
			return false;
		}
		// At least two "." in the string will result first index to be different than last 
		if ( !emailParts[1].contains(".") || emailParts[1].indexOf(".") != emailParts[1].lastIndexOf(".") ) {
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * Adds provided email to list of emails 
	 * @throws Exception 
	 */
	public synchronized void AddEmail(String email) throws Exception {
		if ( IsValidEmail(email) ) {
			String normalizedEmail = NormalizeEmail(email);
			if ( IsValidEmail(normalizedEmail) ) {
				seenEmails.add(normalizedEmail);
			}
		}
	}
	
	/**
	 * Returns number of unique emails  
	 */
	public int GetUniqueEmails() {
		return seenEmails.size();
	}

}

