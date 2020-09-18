package com.tata.jiuye.common.utils;

import sun.misc.BASE64Decoder;

public class Base64Util {

	/**
	 * 
	 * <p>功能描述：通过base64编码</p>
	 * <p>创建人：</p>
	 * <p>创建日期：2018年3月7日 下午1:25:26</p>
	 *
	 * @param str
	 * @return value
	 * @throws Exception
	 */
	public static String encodeByBase64(String str) throws Exception {
		if (str == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(str.getBytes("utf-8"));
	}

	/**
	 * 
	 * <p>功能描述：通过base64解码</p>
	 * <p>创建人：</p>
	 * <p>创建日期：2018年3月7日 下午1:25:04</p>
	 *
	 * @param str
	 * @return value
	 */
	public static String decodeByBASE64(String str) {
		if (str == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(str);
			return new String(b, "utf-8");
		} catch (Exception e) {
			return null;
		}
	}

}
