package com.evandbrown.levenshtein.util;

import java.security.*;
import java.math.*;

/**
 * A simple utility for computing the MD5 digest value
 * of a string.
 */
public class MD5Digest {
	
	/**
	 * Compute the MD5 digest of a string.
	 * 
	 * @param input String for which to compute digest.
	 * @return MD5 digest.
	 */
	public static String get(String input) {
		try {
			MessageDigest lDigest = MessageDigest.getInstance("MD5");
			lDigest.update(input.getBytes());
			BigInteger lHashInt = new BigInteger(1, lDigest.digest());
			return String.format("%1$032X", lHashInt);
		} catch (NoSuchAlgorithmException lException) {
			throw new RuntimeException(lException);
		}
	}
}
