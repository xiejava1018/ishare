package com.ishareread.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Md5加密方法
 * 
 *
 */
public class Md5Utils {
	private static final Logger log = LoggerFactory.getLogger(Md5Utils.class);
	
	public static String md5Str(String s) {
		return toHex(md5(s));
	}

	private static byte[] md5(String s) {
		MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(s.getBytes("UTF-8"));
			byte[] messageDigest = algorithm.digest();
			return messageDigest;
		} catch (Exception e) {
			log.error("MD5 Error...", e);
		}
		return null;
	}

	private static final String toHex(byte hash[]) {
		if (hash == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;

		for (i = 0; i < hash.length; i++) {
			if ((hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	public static String hash(String s) {
		try {
			return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
		} catch (Exception e) {
			log.error("not supported charset...{}", e);
			return s;
		}
	}

	/**
	 * 获取文件的md5
	 * 
	 * @param filePath 文件地址
	 * @param fileName 文件名
	 * @return
	 */
	public static String getFileMd5Value(String filePath, String fileName) {
		File file = new File(filePath + File.separator + fileName);
		if (!file.exists() || !file.isFile()) {
			return "";
		}
		byte[] buffer = new byte[2048];
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			FileInputStream in = new FileInputStream(file);
			while (true) {
				int len = in.read(buffer, 0, 2048);
				if (len != -1) {
					digest.update(buffer, 0, len);
				} else {
					break;
				}
			}
			in.close();

			byte[] md5Bytes = digest.digest();
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
    /**
     * 
     * @desc 字节数组转换为16进制字符串<br>
     * @param bytes
     * @return
     */
    private static final String byte2str(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        int j = bytes.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = bytes[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
    
    /**
     * 
     * @desc 加密<br>
     * @create Mar 28, 2012 11:05:14 AM by qzp<br>
     * @param src
     * @return
     */
    public static final String encrypt(String src) {
        byte[] bytes = eccrypt(src);
        String str = byte2str(bytes);
        return str;
    }

    private synchronized static final byte[] eccrypt(String info) {
        try {
            // 根据MD5算法生成MessageDigest对象
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] srcBytes = info.getBytes();
            // 使用srcBytes更新摘要
            md5.update(srcBytes);
            // 完成哈希计算，得到result
            byte[] resultBytes = md5.digest();
            return resultBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static void main(String[] args) {
//	    System.out.println(Md5Utils.getFileMd5Value("D:\\", "114424001b5aeb7b77261b33a9477488.apk"));
//	    System.out.println("2ba0e12da3cffa7f2245bc580f7398ed");
//	    System.out.println("2ba0e12da3cffa7f2245bc580f7398ed");
		
		System.out.println(md5Str("123"));
	}
}
