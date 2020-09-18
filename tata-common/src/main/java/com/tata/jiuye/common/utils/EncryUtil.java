package com.tata.jiuye.common.utils;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * <p>文件名称：EncryUtil</p>
 * <p>文件描述：签名验签</p>
 * <p>版权所有： 版权所有(C)2017-2099</p>
 * <p>公   司：  </p>
 * <p>内容摘要： </p>
 * <p>其他说明： </p>
 *
 */
public class EncryUtil {

	private static final String CHAR_ENCODING = "UTF-8";

	/**
	 * 签名
	 * @param map
	 * @param privateKey
	 * @return
	 */
	public static String handleRSA(TreeMap<String, Object> map, String privateKey) {

		String signTemp = formatSignContent(map);

		String sign = "";
		if (StrUtil.isNotEmpty(privateKey)) {
			sign = sign(signTemp, privateKey);
		}
		return sign;
	}

	/**
	 * 验签
	 * @param map
	 * @param publickKey
	 * @return
	 * @throws Exception
	 */
	public static boolean checkDecryptAndSign(TreeMap<String, Object> map, String publickKey) throws Exception {


		String sign = StrUtil.trimToEmpty(String.valueOf(map.get("sign")));

        String signTemp = formatSignContent(map);

		/** result为true时表明验签通过 */
		boolean result = checkSign(signTemp, sign, publickKey);

		return result;
	}

	private static String formatSignContent(TreeMap<String, Object> params) {

		StringBuilder sbuffer = new StringBuilder();
		for (Entry<String, Object> entry : params.entrySet()) {

            if (StrUtil.equals((String) entry.getKey(), "sign")) {
                continue;
            }

			if (entry.getValue() == null || StrUtil.isBlank(String.valueOf(entry.getValue()))) {
				continue;
			}

			sbuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}

		String tempSign = sbuffer.toString();
		if (StrUtil.isNotBlank(tempSign) && tempSign.endsWith("&")) {
			tempSign = tempSign.substring(0, tempSign.length() - 1);
		}

        System.out.println("===buffer===: " + tempSign);
		return tempSign;
	}

	private static String sign(String content, String privateKey) {

		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decodeBase64(privateKey.getBytes()));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			Signature signature = Signature.getInstance("SHA1WithRSA");

			signature.initSign(priKey);
			signature.update(content.getBytes(CHAR_ENCODING));

			byte[] signed = signature.sign();

			return new String(Base64.encodeBase64(signed));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println(e.getMessage());
		}
		return null;
	}

	private static boolean checkSign(String content, String sign, String publicKey) {
		try	{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.decode2(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


			Signature signature = Signature
					.getInstance("SHA1WithRSA");

			signature.initVerify(pubKey);
			signature.update( content.getBytes("utf-8") );

			boolean bverify = signature.verify( Base64.decode2(sign) );
			return bverify;

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return false;
	}

	public static Map<String, String> genKeyPair(int keySize) throws Exception {
		Map<String, String> keyMap = Maps.newHashMap();

		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		keyPairGen.initialize(keySize, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		String publicKeyString = java.util.Base64.getEncoder().encodeToString(publicKey.getEncoded());
		String privateKeyString = java.util.Base64.getEncoder().encodeToString(privateKey.getEncoded());
		keyMap.put("publicKey", publicKeyString);
		keyMap.put("privateKey", privateKeyString);

		return keyMap;
	}
}
