package com.shijia.rsa;

import net.iharder.Base64;




public class RSADemo {
	public static void main(String[] args) throws Exception {
		String info="wo zheng de hao xiang 你,ni 知道吗？这里的雨季\r只有一两天 \r  白昼很长也很短";
		byte[] encrypt=RSAUtil.getInstance().encryptSendData(info.getBytes("UTF-8"));
		System.out.println("加密后的数据：");
		String jiamihou=Base64.encodeBytes(encrypt);
		System.out.println("===============");
		System.out.println(jiamihou);
		System.out.println(Base64Util.encode(encrypt));
		System.out.println("===============");
		System.out.println("加签：---------");
		byte[] sign=RSAUtil.getInstance().signData(encrypt);
		String signStr=Base64.encodeBytes(sign);
		System.out.println(signStr);
		System.out.println("解签：---------");
		System.out.println(RSAUtil.getInstance().validateSign(jiamihou, signStr));
		System.out.println("数据解密后结果：");
		byte[] decrypt=RSAUtil.getInstance().decryptReciveData(jiamihou);
		System.out.println(new String(decrypt));
	}
}
