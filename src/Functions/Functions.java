package Functions;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class Functions {
	public static String md5Java(String message) {
		String digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(message.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2*hash.length);
			for(byte b : hash) {
				sb.append(String.format("%02x", b&0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {

		} catch (NoSuchAlgorithmException ex) {

		}
		return digest;
	}

	public static String getRandom(int length) {
		String randomNumber = "";
		Random random = new Random();
		while (randomNumber.length() < length) {
			randomNumber += Long.toString(random.nextLong());
		}
		return randomNumber.substring(0, length);
	}

	public static String getAccessTokent(long userId, String phone) {
		String key = userId + ' ' + phone + ' ' + Long.toString(new Date().getTime());
		Random random = new Random();
		key += random.nextLong();

		return md5Java(key);
	}
}
