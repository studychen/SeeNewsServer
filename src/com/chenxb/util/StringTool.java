package com.chenxb.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class StringTool {

	public static String createUUID() {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}

	public static String createMD5(String plaintext) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(plaintext.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			return bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
		}
		return plaintext;
	}
}
