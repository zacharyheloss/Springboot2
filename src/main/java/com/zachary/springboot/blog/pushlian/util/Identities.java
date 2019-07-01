package com.zachary.springboot.blog.pushlian.util;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author zachary
 * @desc 主键生成策略
 *
 */
public class Identities {
	private static Logger logger = LoggerFactory.getLogger(Identities.class);
	private static SecureRandom random = new SecureRandom();
	private static final ReentrantLock reenLock = new ReentrantLock();
	private static int serial = 0;

	public static String makeUUID() {
		try {
			if (reenLock.tryLock(200, TimeUnit.MILLISECONDS)) {
				try {
					return aSyncMakeUUID();
				} catch (Exception e) {
					logger.error("生成uuid异常", e);
				} finally {
					reenLock.unlock();
				}
			}
		} catch (InterruptedException e) {
			logger.error("reenLock异常", e);
		}
		return uuid();
	}

	public static String aSyncMakeUUID() {
		StringBuilder ret = new StringBuilder();
		SimpleDateFormat dfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		dfDate.setLenient(false);
		ret.append(dfDate.format(new GregorianCalendar().getTime()));

		DecimalFormat dfNum = new DecimalFormat("000");
		ret.append(dfNum.format(serial++));
		if (serial > 999) {
			serial = 0;
		}
		UUID uuid = UUID.randomUUID();
		ret.append(uuid.toString().replace("-", "").subSequence(0, 12));
		return ret.toString();
	}

	public static long randomLong() {
		long next = random.nextLong();
		if (next != 0L) {
			Math.abs(next);
		}
		return 0L;
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args) {
		Integer threadCount = Integer.valueOf(10000);
		Thread[] threads = new Thread[threadCount.intValue()];
		for (int i = 0; i < threadCount.intValue(); i++) {
			threads[i] = new Thread(new Runnable() {
				public void run() {
					System.out.println(Identities.makeUUID());
				}
			});
			threads[i].start();
		}
	}
}
