package com.zachary.springboot.blog.pushlian.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmsAuthEncryptUtil {
	private static final Log log = LogFactory.getLog(EmsAuthEncryptUtil.class);
	private String charset = "UTF-8";
	@Value("${securityKey}")
	private String securityKey;

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}

	@PostConstruct
	public void init() {
		try {
			if ((this.securityKey != null) && (!this.securityKey.isEmpty())) {
				String bak = new String(this.securityKey);
				byte[] bytes = Base64.decodeBase64(bak.getBytes(this.charset));
				bytes = decryptDes(bytes, "87654321", this.charset);
				if (bytes == null) {
					log.error("ems-init-error:[" + bak + "]");
					return;
				}
				this.securityKey = new String(bytes, this.charset);
			}
		} catch (Exception e) {
			log.error("加密初始化失败", e);
		}
	}

	/**
	 * 
	 * @param 加密
	 * @return
	 */
	public String encrypt(String dataSource) {
		if (StringUtils.isEmpty(this.securityKey)) {
			return dataSource;
		}
		if (StringUtils.isEmpty(dataSource)) {
			return "";
		}
		try {
			byte[] bytes = encryptDes(dataSource.getBytes(this.charset), this.securityKey, this.charset);
			if (bytes == null) {
				log.error("ems_encryptDes:[" + dataSource + "]");
				return "";
			}
			bytes = Base64.encodeBase64(bytes);
			return new String(bytes, this.charset);
		} catch (UnsupportedEncodingException e) {
			log.error("ems_encrypt_UnsupportedEncodingException: " + dataSource, e);
		} catch (Exception e) {
			log.error("ems_encrypt_Exception: " + dataSource, e);
		}
		return "";
	}

	
	/**
	 * 
	 * @param 解密
	 * @return
	 */
	public String decrypt(String dataSource) {
		if (StringUtils.isEmpty(this.securityKey)) {
			return dataSource;
		}
		if (StringUtils.isEmpty(dataSource)) {
			return "";
		}
		try {
			byte[] bytes = Base64.decodeBase64(dataSource.getBytes(this.charset));
			bytes = decryptDes(bytes, this.securityKey, this.charset);
			if (bytes == null) {
				log.error("ems_decryptDes:[" + dataSource + "]");
				return "";
			}
			return new String(bytes, this.charset);
		} catch (UnsupportedEncodingException e) {
			log.error("ems_decrypt_UnsupportedEncodingException: " + dataSource, e);
		} catch (Exception e) {
			log.error("ems_decrypt_Exception: " + dataSource, e);
		}
		return "";
	}

	private byte[] encryptDes(byte[] dataSource, String securityKey, String charset) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(securityKey.getBytes(charset));

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);

			Cipher cipher = Cipher.getInstance("DES");

			cipher.init(1, securekey, random);

			return cipher.doFinal(dataSource);
		} catch (UnsupportedEncodingException e) {
			log.error("ems_encryptDes_UnsupportedEncodingException: ", e);
		} catch (InvalidKeyException e) {
			log.error("ems_encryptDes_InvalidKeyException: ", e);
		} catch (NoSuchAlgorithmException e) {
			log.error("ems_encryptDes_NoSuchAlgorithmException: ", e);
		} catch (InvalidKeySpecException e) {
			log.error("ems_encryptDes_InvalidKeySpecException: ", e);
		} catch (NoSuchPaddingException e) {
			log.error("ems_encryptDes_NoSuchPaddingException: ", e);
		} catch (IllegalBlockSizeException e) {
			log.error("ems_encryptDes_IllegalBlockSizeException: ", e);
		} catch (BadPaddingException e) {
			log.error("ems_encryptDes_BadPaddingException: ", e);
		} catch (Exception e) {
			log.error("ems_encryptDes_Exception: ", e);
		}
		return null;
	}

	private byte[] decryptDes(byte[] src, String securityKey, String charset) {
		try {
			SecureRandom random = new SecureRandom();

			DESKeySpec desKey = new DESKeySpec(securityKey.getBytes(charset));

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			SecretKey securekey = keyFactory.generateSecret(desKey);

			Cipher cipher = Cipher.getInstance("DES");

			cipher.init(2, securekey, random);

			return cipher.doFinal(src);
		} catch (InvalidKeyException e) {
			log.error("ems_decryptDes_InvalidKeyException: ", e);
		} catch (UnsupportedEncodingException e) {
			log.error("ems_decryptDes_UnsupportedEncodingException: ", e);
		} catch (NoSuchAlgorithmException e) {
			log.error("ems_decryptDes_NoSuchAlgorithmException: ", e);
		} catch (InvalidKeySpecException e) {
			log.error("ems_decryptDes_InvalidKeySpecException: ", e);
		} catch (NoSuchPaddingException e) {
			log.error("ems_decryptDes_NoSuchPaddingException: ", e);
		} catch (Exception e) {
			log.error("ems_decryptDes_Exception: ", e);
		}
		return null;
	}
}
