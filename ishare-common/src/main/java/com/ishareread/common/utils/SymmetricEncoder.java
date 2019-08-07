package com.ishareread.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.alibaba.fastjson.JSONObject;

public class SymmetricEncoder {

	 /*
	   * 加密
	   * 1.构造密钥生成器
	   * 2.根据ecnodeRules规则初始化密钥生成器
	   * 3.产生密钥
	   * 4.创建和初始化密码器
	   * 5.内容加密
	   * 6.返回字符串
	   */
	    public static String AESEncode(String encodeRules,String content){
	        try {
	            //1.构造密钥生成器，指定为AES算法,不区分大小写
	            KeyGenerator keygen=KeyGenerator.getInstance("AES");
	            
	            //2.根据ecnodeRules规则初始化密钥生成器
	            //生成一个128位的随机源,根据传入的字节数组
	            //keygen.init(128, new SecureRandom(encodeRules.getBytes()));
	            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
	            random.setSeed(encodeRules.getBytes());
	            keygen.init(128, random);
	              //3.产生原始对称密钥
	            SecretKey original_key=keygen.generateKey();
	              //4.获得原始对称密钥的字节数组
	            byte [] raw=original_key.getEncoded();
	            //5.根据字节数组生成AES密钥
	            SecretKey key=new SecretKeySpec(raw, "AES");
	              //6.根据指定算法AES自成密码器
	            Cipher cipher=Cipher.getInstance("AES");
	              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
	            cipher.init(Cipher.ENCRYPT_MODE, key);
	            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
	            byte [] byte_encode=content.getBytes("utf-8");
	            //9.根据密码器的初始化方式--加密：将数据加密
	            byte [] byte_AES=cipher.doFinal(byte_encode);
	            
	            String AES_encode = parseByte2HexStr(byte_AES); 
	          //10.将加密后的数据转换为字符串
	            //这里用Base64Encoder中会找不到包
	            //解决办法：
	            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
	            //String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
	          //11.将字符串返回
	            return AES_encode;
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (NoSuchPaddingException e) {
	            e.printStackTrace();
	        } catch (InvalidKeyException e) {
	            e.printStackTrace();
	        } catch (IllegalBlockSizeException e) {
	            e.printStackTrace();
	        } catch (BadPaddingException e) {
	            e.printStackTrace();
	        } catch (UnsupportedEncodingException e) {
	            e.printStackTrace();
	        }
	        
	        //如果有错就返加nulll
	        return null;         
	    }
	    /*
	     * 解密
	     * 解密过程：
	     * 1.同加密1-4步
	     * 2.将加密后的字符串反纺成byte[]数组
	     * 3.将加密内容解密
	     */
	    public static String AESDncode(String encodeRules,String content){
	        try {
	            //1.构造密钥生成器，指定为AES算法,不区分大小写
	            KeyGenerator keygen=KeyGenerator.getInstance("AES");
	           
	            //2.根据ecnodeRules规则初始化密钥生成器
	            //生成一个128位的随机源,根据传入的字节数组
	            //keygen.init(128, new SecureRandom(encodeRules.getBytes()));
	            SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
	            random.setSeed(encodeRules.getBytes());
	            keygen.init(128, random);
	              //3.产生原始对称密钥
	            SecretKey original_key=keygen.generateKey();
	              //4.获得原始对称密钥的字节数组
	            byte [] raw=original_key.getEncoded();
	            //5.根据字节数组生成AES密钥
	            SecretKey key=new SecretKeySpec(raw, "AES");
	              //6.根据指定算法AES自成密码器
	            Cipher cipher=Cipher.getInstance("AES");
	              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
	            cipher.init(Cipher.DECRYPT_MODE, key);
	            //8.将加密并编码后的内容解码成字节数组
	            //byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
	            
	            byte[] byte_content = parseHexStr2Byte(content); 
	            /*
	             * 解密
	             */
	            byte [] byte_decode=cipher.doFinal(byte_content);
	            String AES_decode=new String(byte_decode,"utf-8");
	            return AES_decode;
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        } catch (NoSuchPaddingException e) {
	            e.printStackTrace();
	        } catch (InvalidKeyException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (IllegalBlockSizeException e) {
	            e.printStackTrace();
	        } catch (BadPaddingException e) {
	            e.printStackTrace();
	        }
	        
	        //如果有错就返加nulll
	        return null;         
	    }
	    
	    
	    /**将二进制转换成16进制 
	     * @param buf 
	     * @return 
	     */  
	    public static String parseByte2HexStr(byte buf[]) {  
	            StringBuffer sb = new StringBuffer();  
	            for (int i = 0; i < buf.length; i++) {  
	                    String hex = Integer.toHexString(buf[i] & 0xFF);  
	                    if (hex.length() == 1) {  
	                            hex = '0' + hex;  
	                    }  
	                    sb.append(hex.toUpperCase());  
	            }  
	            return sb.toString();  
	    } 
	    
	    
	    /**将16进制转换为二进制 
	     * @param hexStr 
	     * @return 
	     */  
	    public static byte[] parseHexStr2Byte(String hexStr) {  
	            if (hexStr.length() < 1)  
	                    return null;  
	            byte[] result = new byte[hexStr.length()/2];  
	            for (int i = 0;i< hexStr.length()/2; i++) {  
	                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
	                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
	                    result[i] = (byte) (high * 16 + low);  
	            }  
	            return result;  
	    } 
	    
	    public static void main(String[] args) {
	        SymmetricEncoder se=new SymmetricEncoder();
//	        Scanner scanner=new Scanner(System.in);
//	        /*
//	         * 加密
//	         */
//	        System.out.println("使用AES对称加密，请输入加密的规则");
//	        String encodeRules=scanner.next();
//	        System.out.println("请输入要加密的内容:");
//	        System.out.println("根据输入的规则"+encodeRules+"加密后的密文是:"+se.AESEncode(encodeRules, content));
	       
	        /*
	         * 解密
	         */
//	        String content = "ACBB87209F2CD319B19B214805B441E5DD8B79D3686D64DD9BC1DBCE2555835504D93FA92E9E66EA4845FCAD05F8C2E87A0ED90457"
//	        		+ "06C44A6AE2B12BCD845EB5A9096ED3EAF3E0CC3E81F52F0B2844CFECAA9C4D8A94676CD3A1606CC5B98380A76E46EC67E2DCE20F8ADB7DEBF"
//	        		+ "C8E20FE2CEE2167386F9319B8582B5658FA54E31DD1683B6E3C59B25956EACABDA9749063CEFD6E0B00BF3CFC608DD84EE619E8A9E4B29CBA"
//	        		+ "8B81F6C7221F6E6C15F8F9FF0E8AAF577B30CF0EE0F3823AFF7708D7B24BA88D2273D9EECE8E33788217619A6BA9F8715C5CC77E5C934C7EF"
//	        		+ "A2143FB20D0AEE128FC173253E549EB7D0C0A8C8F4DF2F9A9F39C0D717579165423D47B4FEB162D61F5061C61993CC5AD5EF9238EE426504A"
//	        		+ "9F5A2F984D5B628F18BF8F63F8D183B0BE635E3A8E79FC1E5C9AEDCFE39E307E2E62BFA3861BFBD1F16609CD50E159E572F117890EC7F8C7B"
//	        		+ "D2C0E437FF658331961C386646BBF0B6B86B30B6F750EE75A900B7B1077886F211F291833913635D5C2C44115A7DAEB1DA641E5905363D0C4"
//	        		+ "CB2F4428EE3C3C27C70977A8EAB3B903203A771CA03A4935F4DE3CC4A3E45E97420C11427D9A5A5616AD262F29D8D962E438B8EE5989C1B15"
//	        		+ "9872006B5908A5675F223A5965BAE35EF53511B32D894832191035BCAEFCA102F6412C376362F31E7067F1E79526B28B0A73FA114B8CA14B5"
//	        		+ "9E740221566AFB43783CADA35B15B94C76FC95FD1C77EE7265C271A37F54A95E2E4862714EE8445A899FAEB20565D6C5E58B29ACF0C9E76BE"
//	        		+ "691222A82E42C51F6C4FBDF4858D0E114D0E491EE177512744A056C8E07CA417804B672A31799278971E1ADF319B3F59748B2DD94FC60E419"
//	        		+ "21405BF960FCE724D5DBB67763545D00D4F3A8E35DE3412BAC44EDB96E4EBFE7A4D10A481459301C264636D7A7658AC1587721DA8CBB4CBD1"
//	        		+ "03CA523A40B680AB52EA25F96F903299514BD60E2E5E6CF3F9F6D630D13F3F8524B27D6AFE06DADA8D773F631928D0C76C3660E6B18BB8815"
//	        		+ "CF5138D8FE5185A15AA1BB79E08A28F5F92D132384F542CFCE54579569BCA6807E8B1C65F9F81C9F40FD8CD8AE94F087B8D0A53A33F16912C"
//	        		+ "5FB851E5BF3274659C745E6A17C33782EFCEBB98CA5105EB6555E559EED389635A7976B10CADEC3242C1F9385D5E14B3757550929AED29EF5"
//	        		+ "08C5716EDC750CF567CD4CFA4A217460919BC6976814297FDCED3ABA730F12980FD517ACCC7820BEE7402BA57EAD5CC9EC5B26F42AFEEDC00"
//	        		+ "8EF025A32B508067EF7F3CF795E4CE81F9566D9AAA7703908AD19916565A22BE6BB0B8F56DF724339C45F21E5E03B7D9A091D53BD5B3C5F73"
//	        		+ "97B0553836F2868A9D9B5A6D6194A8AE28D1CA82FCA3B59806EB88F6ED27D5F619E3198D06D28BAF88E8D2214B6FCB43B8942AAA3C758E231"
//	        		+ "2014C60E7EB7A6B23BD057BE8CA5DCFA08F628E4EE1224B518772651309A3DB4F09DADA0F2243359386FF75FA18D9EDE1C1F1A64E36BF8D45"
//	        		+ "B0037C56301B26C3E08752FB45D09421AA6235CA251D4E0BC875D5220520E36F7B07D98243B3F5CB5EAC6174C7C76337D159A479E9368BF9D"
//	        		+ "431C2A530F31BD12851EB3E03ED26167D31746FF2DE8405DF1B2A542A1E97E0960FC4FC8990F0CC2BA820DB2018C39543B24F487A68CAEB42"
//	        		+ "452059B7543B9B85CC214A7A40C78AA442991494F149BA154B548E378CCD671FB535CA251D4E0BC875D5220520E36F7B073A0148EB432E5C8"
//	        		+ "1E8843AF1BF148A7C7B8C6C9C484EBE2B809D4AAAE0F75AA5FE7E663CC9E381616DF66C2BFF8B85CB67BA1D799DFED5645AE80E4FCE4EA214"
//	        		+ "C664550893D90AD68753A05693F5D97C48C1AF315FAE75B52193E962B39036824D2386BE019818BE0F7E8AD2C78A2C003C1D2EDA793B74DA1"
//	        		+ "C04AE7B969365D9764AFC1FFEA912C77C4429D3EF201FD296E2D706E561F095CBAD614AF24F7B328ADE8AE57B91BEB0B97AF5F7A6A72DE115"
//	        		+ "9FA061B968845F5B2B31288EDFE3E49A182E3B7639822BB44D27D106297AB1";

	        
	        JSONObject obj = new JSONObject();
			obj.put("systemId", "isat#system01");
			obj.put("loginName", "isat_test");
			obj.put("productCode", "4973eb336a1511e9bb9a005056994385");
			obj.put("flowInstanceCode", "0");//
			
	        String content = "334E72FA92B8D9B534C1E3AE19DA4DAB4AA9D939BFBAC5EF2A99C9E1A4925C98BB6743F921164AABECA0CCC79E8C32CBA80A2F18DEC8F70948A5DFA8F14D3726DD32D8770D2D730CE819E26B13343D363D994B84B6766E8676D47D7B124F2DC3EFA42F6F4E31713C2FC144BAE0CBA27ECD9C182CCB4DAA1F9F39B0B8AD856D6A";
	        
	        String encodeRules = "isat#system01";
	        System.out.println("根据输入的规则"+encodeRules+"解密后的明文是:"+se.AESDncode(encodeRules, content));
	        System.out.println(se.AESEncode(encodeRules, obj.toJSONString()));
	    }
}