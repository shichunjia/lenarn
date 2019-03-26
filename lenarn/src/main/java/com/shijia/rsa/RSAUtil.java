package com.shijia.rsa;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.iharder.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;



public class RSAUtil {
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtil.class);
	
	private static final String PUBLIC_KEY_PATH="C:\\Users\\shichunjia\\Desktop\\tempFile\\rsa_public_key.pem";
	
	private static final String PRIVATE_KEY_PATH="C:\\Users\\shichunjia\\Desktop\\tempFile\\rsa_private_key.pem";
	
	private final  int KEY_LENGTH=1024;
	
	private RSAPublicKey publicKey;
	
	private RSAPrivateKey privateKey;
	
	private static volatile RSAUtil rsaUtil;
	
	
	private RSAUtil(){
		init();
	}
	
	/**
	 * 单例模式构造实体类
	 * @return
	 */
	public static RSAUtil getInstance(){
		if(rsaUtil==null){
			synchronized (RSAUtil.class) {
				if(rsaUtil==null){
					rsaUtil=new RSAUtil();
				}
				
			}
		}
		return rsaUtil;
	}

	/**
	 * 初始化  建立 公私钥
	 */
	private void init(){
		publicKey=buildPublicKey(new File(PUBLIC_KEY_PATH));
		privateKey=buildPrivateKey(new File(PRIVATE_KEY_PATH));
	}


	/**
	 * 建立私钥
	 * @param file
	 * @return RSAPrivateKey
	 */
	private RSAPrivateKey buildPrivateKey(File file)  {
		if (file == null||!file.exists()) {
			LOGGER.error("rsa key file not exists");
			throw new RuntimeException("private key read fail");
		}
		try {
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(readKeyFile(new FileInputStream(file)));
			return  (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (IOException e) {
			LOGGER.error("public key read fail",e);
			throw new RuntimeException("public key read fail");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Rsa not exists",e);
			throw new RuntimeException("Rsa not exists");
		} catch (InvalidKeySpecException e) {
			LOGGER.error("private key file format error",e);
			throw new RuntimeException("private key file format error");
		}  catch (Exception e) {
			LOGGER.error("private key file format error",e);
			throw new RuntimeException("private key file format error");
		}finally {
		}
	}


	/**
	 * 建立公钥
	 * @param file
	 * @return RSAPublicKey
	 */
	private RSAPublicKey buildPublicKey(File file){
		if (file == null||!file.exists()) {
			LOGGER.error("rsa key file not exists");
			throw new RuntimeException("public key read fail");
		}
		try {
			KeyFactory keyFactory= KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec= new X509EncodedKeySpec(readKeyFile(new FileInputStream(file)));
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (IOException e) {
			LOGGER.error("public key read fail",e);
			throw new RuntimeException("public key read fail");
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Rsa not exists",e);
			throw new RuntimeException("Rsa not exists");
		} catch (InvalidKeySpecException e) {
			LOGGER.error("public key file format error",e);
			throw new RuntimeException("public key file format error");
		} catch (Exception e) {
			LOGGER.error("public key file format error",e);
			throw new RuntimeException("public key file format error");
		} finally {
		}
	}

	/**
	 * 读取 key文件 
	 * @param in
	 * @return byte[]
	 * @throws Exception
	 */
	private static byte[] readKeyFile(InputStream in) throws Exception {
		BufferedReader bufferedReader=null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(in);
			bufferedReader = new BufferedReader(inputStreamReader);
			String readLine;
			StringBuilder sb = new StringBuilder();
			while ((readLine = bufferedReader.readLine()) != null) {
				if (readLine.charAt(0) != '-') {
					sb.append(readLine);
				}
			}
			BASE64Decoder base64Decoder= new BASE64Decoder();
			return base64Decoder.decodeBuffer(sb.toString());
			//return Base64Util.decode(sb.toString());
		}finally {
			bufferedReader.close();
			inputStreamReader.close();
			in.close();
			/*CleanUp.clean(bufferedReader);
			CleanUp.clean(inputStreamReader);
			CleanUp.clean(in);*/
		}
	}
	
	
	/** 
	 * 对数据进行加密
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptSendData(byte[] datas) throws Exception {
		if(rsaUtil== null){
			throw new Exception("public  file is null");
		}
		Cipher cipher= null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			cipher= Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			processData(out,cipher,datas,KEY_LENGTH/8-11);
			return out.toByteArray();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("",e);
			throw new Exception("Rsa not exists");
		} catch (NoSuchPaddingException e) {
			LOGGER.error("",e);
			return null;
		}catch (InvalidKeyException e) {
			LOGGER.error("",e);
			throw new Exception("publickey is invalid");
		} catch (IllegalBlockSizeException e) {
			LOGGER.error("",e);
			throw new Exception("data Error");
		} catch (BadPaddingException e) {
			LOGGER.error("",e);
			throw new Exception("data error");
		}finally {
			out.close();
			//CleanUp.clean(out);
		}
	}
	
	
	/**
	 * 对接收的数据进行解密
	 * @param data
	 * @return byte[]
	 * @throws Exception
	 */
	public byte[] decryptReciveData(String data)  throws Exception{
		if(privateKey== null){
			throw new Exception("private key file is null");
		}
		Cipher cipher= null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			cipher= Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			processData(out,cipher,Base64Util.decode(data),KEY_LENGTH/8);
			return out.toByteArray();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("",e);
			throw new Exception("Rsa not exists");
		} catch (NoSuchPaddingException e) {
			LOGGER.error("",e);
			return null;
		}catch (InvalidKeyException e) {
			LOGGER.error("",e);
			throw new Exception("privatekey is invalid");
		} catch (IllegalBlockSizeException e) {
			LOGGER.error("",e);
			throw new Exception("data Error");
		} catch (BadPaddingException e) {
			LOGGER.error("",e);
			throw new Exception("data error");
		}finally {
			out.close();
			//CleanUp.clean(out);
		}
	}
	
	
	/**
	 * 对加密后的数据进行加签
	 * @param datas
	 * @return  byte[]
	 * @throws Exception
	 */
	public byte[] signData(byte[] datas) throws Exception{
		try {
			//加签验签方式采用   SHA1withRSA
			Signature sign = Signature.getInstance("SHA1withRSA");
			//使用己方私钥进行加签
			sign.initSign(privateKey);
			sign.update(datas);
			return sign.sign();
		}catch (Exception e){
			LOGGER.error("",e);
			throw e;
		}
	}
	
	/**
	 * 对加密后的数据进行验签
	 * @param datas
	 * @param signDatas
	 * @return boolean
	 * @throws Exception
	 */
	public boolean validateSign(String datas,String signDatas) throws Exception{
		try {
			//验签方式 SHA1withRSA
			Signature verifySign = Signature.getInstance("SHA1withRSA");
			//使用对方公钥进行验签
			verifySign.initVerify(publicKey);
			verifySign.update(Base64.decode(datas));
			return verifySign.verify(Base64.decode(signDatas));
		}catch (Exception e){
			LOGGER.error("",e);
			throw e;
		}
	}
	
	public static void processData(ByteArrayOutputStream out,Cipher cipher,byte[] datas,int maxBlockSize) throws BadPaddingException, IllegalBlockSizeException {
		int inputLength = datas.length;
		int offSet = 0;
		for(int i = 0; inputLength - offSet > 0; offSet = i * maxBlockSize) {
			byte[] cache;
			if(inputLength - offSet > maxBlockSize) {
				cache = cipher.doFinal(datas, offSet, maxBlockSize);
			} else {
				cache = cipher.doFinal(datas, offSet, inputLength - offSet);
			}
			out.write(cache, 0, cache.length);
			++i;
		}
	}
	
}
