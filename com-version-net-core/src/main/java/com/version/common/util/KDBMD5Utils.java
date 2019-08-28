package com.version.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class KDBMD5Utils {
	public static boolean isOpenDebug = true;

	public static String getPassword(String pwd, String encryt) {
		return string2MD5(string2MD5(pwd) + encryt);
	}

	/**
	 * 生成指定范围内随机数
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @return
	 */
	public static int random(int min, int max) {
		if (max == min)
			return min;
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		int value = random.nextInt(max) % (max - min + 1) + min;

		return value;
	}

	/**
	 * 生成指定长度随机字符串
	 * 
	 * @param length
	 *            字符串长度
	 * @return
	 */
	public static String randomStr(int length) {
		String info = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmopqrst1234567890";
		String back = "";
		for (int i = 0; i < length; i++) {
			int idx = random(1, info.length(), i + 123) - 1;
			back += info.substring(idx, idx + 1);
		}
		return back;
	}

	/**
	 * 生成指定长度大写+数字字符串
	 * 
	 * @param length
	 *            字符长度
	 * @return
	 */
	public static String randomBigStr(int length) {
		String info = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String back = "";
		for (int i = 0; i < length; i++) {
			int idx = random(1, info.length(), i + 123) - 1;
			back += info.substring(idx, idx + 1);
		}
		return back;
	}

	/**
	 * 生成指定长度随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String randomBigStr(int length, int random) {
		String info = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String back = "";
		for (int i = 0; i < length; i++) {
			int idx = random(1, info.length(), i + 123 * random) - 1;
			back += info.substring(idx, idx + 1);
		}
		return back;
	}

	/**
	 * 获取随机数
	 * 
	 * @param min
	 *            最小值
	 * @param max
	 *            最大值
	 * @param seed
	 *            随机种子
	 * */
	public static int random(int min, int max, int seed) {
		Random random = new Random();
		if (seed == 0) {
			random.setSeed(System.currentTimeMillis() / (seed + 1));
		} else {
			random.setSeed(System.currentTimeMillis() / seed);
		}
		int value = random.nextInt(max) % (max - min + 1) + min;

		return value;
	}

	/**
	 * 把固定的数组打乱,随机生成新数组
	 * 
	 * @param seed
	 *            源数据
	 * @return
	 */
	public static int[] randoms(int[] seed) {
		int[] ranArr = new int[seed.length];
		Random ran = new Random();

		// 数量你可以自己定义。
		for (int i = 0; i < seed.length; i++) {
			// 得到一个位置
			int j = ran.nextInt(seed.length - i);
			// 得到那个位置的数值
			ranArr[i] = seed[j];
			// 将最后一个未用的数字放到这里
			seed[j] = seed[seed.length - 1 - i];
		}
		return ranArr;
	}

	public static String toRadix(int data, int radix) {
		char[] digits = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z' };
		char buf[] = new char[33];
		int charPos = 32;

		while (data > radix) {
			buf[charPos--] = digits[data % radix];
			data = data / radix;
		}
		buf[charPos] = digits[data];
		return new String(buf, charPos, (33 - charPos));
	}

	/**
	 * 生成MD5字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String string2MD5(String str) {
		if (str == null) {
			return "a";
		}
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			LoggerUtil.error("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			LoggerUtil.info(ExceptionUtils.getStackTrace(e));
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
}
