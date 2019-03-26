package com.shijia.rsa;

/**
* @description:
*
* @author: shichunjia
*
* @create: 2019/3/26 15:09
**/
public class Base64Util {
	private final static char[] URL_SAFE_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-@".toCharArray();
	private final static char[] COMMON_ALPHABET   = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	private static int[]  toInt   = new int[128];
	private static int[]  commonToInt   = new int[128];

    static {
        for(int i=0; i< URL_SAFE_ALPHABET.length; i++){
            toInt[URL_SAFE_ALPHABET[i]]= i;
        }
        for(int i=0; i< COMMON_ALPHABET.length; i++){
            commonToInt[COMMON_ALPHABET[i]]= i;
        }
    }

    /** 
    * @Description:  
    * @Param:  
    * @return:  
    * @Author: shichunjia
    * @Date: 14:41
    */ 
	private static String encode(byte[] buf, char[] targetCharArray){
        int size = buf.length;
        char[] ar = new char[((size + 2) / 3) * 4];
        int a = 0;
        int i=0;
        while(i < size){
            byte b0 = buf[i++];
            byte b1 = (i < size) ? buf[i++] : 0;
            byte b2 = (i < size) ? buf[i++] : 0;

            int mask = 0x3F;
            ar[a++] = targetCharArray[(b0 >> 2) & mask];
            ar[a++] = targetCharArray[((b0 << 4) | ((b1 & 0xFF) >> 4)) & mask];
            ar[a++] = targetCharArray[((b1 << 2) | ((b2 & 0xFF) >> 6)) & mask];
            ar[a++] = targetCharArray[b2 & mask];
        }
        switch(size % 3){
            case 1: ar[--a]  = '=';
            case 2: ar[--a]  = '=';
        }
        return new String(ar);
    }


	/**
	 * Decode byte [ ].
	 *
	 * @param s         the s
	 * @param targetInt the target int
	 * @return the byte [ ]
	 */
	private static byte[] decode(String s, int[] targetInt){
        int delta = s.endsWith( "==" ) ? 2 : s.endsWith( "=" ) ? 1 : 0;
        byte[] buffer = new byte[s.length()*3/4 - delta];
        int mask = 0xFF;
        int index = 0;
        for(int i=0; i< s.length(); i+=4){
            int c0 = targetInt[s.charAt( i )];
            int c1 = targetInt[s.charAt( i + 1)];
            buffer[index++]= (byte)(((c0 << 2) | (c1 >> 4)) & mask);
            if(index >= buffer.length){
                return buffer;
            }
            int c2 = targetInt[s.charAt( i + 2)];
            buffer[index++]= (byte)(((c1 << 4) | (c2 >> 2)) & mask);
            if(index >= buffer.length){
                return buffer;
            }
            int c3 = targetInt[s.charAt( i + 3 )];
            buffer[index++]= (byte)(((c2 << 6) | c3) & mask);
        }
        return buffer;
    }


	/**
	 * Encode string.
	 *
	 * @param buf the buf
	 * @return the string
	 */
	public static String encode(byte[] buf){
    	return encode(buf,COMMON_ALPHABET);
    }


	/**
	 * Url safe encode string.
	 *
	 * @param buf the buf
	 * @return the string
	 */
	public static String urlSafeEncode(byte[] buf){
		return encode(buf,URL_SAFE_ALPHABET);
	}


	/**
	 * Decode byte [ ].
	 *
	 * @param s the s
	 * @return the byte [ ]
	 */
	public static byte[] decode(String s){
    	return decode(s,commonToInt);
	}

	public static byte[] urlSafeDecode(String s){
		return decode(s,toInt);
	}
}
