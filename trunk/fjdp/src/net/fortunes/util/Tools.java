package net.fortunes.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * 一个工具类,用来处理日期等操作
 * @author Neo.Liao
 *
 */
public class Tools {
	
	/**
	 * 将日期字符串以特定格式(yyyy-MM-dd或者yyyy-MM-dd HH:mm:ss)转化为日期
	 * @param dateString 日期字符串
	 * @return 日期对象
	 */
	public static Date string2Date(String dateString)  {
		DateFormat df;
		try {
			if(dateString.trim().length() == 10)
				df = new SimpleDateFormat("yyyy-MM-dd");
			else
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (!StringUtils.isEmpty(dateString))
				return df.parse(dateString);
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 将日期以以特定格式((yyyy-MM-dd HH:mm:ss,yyyy-MM-dd或者HH:mm:ss)转化为字符串
	 * @param date 日期对象
	 * @return 日期字符串
	 */
	public static String date2String(Date date){
		if(date != null){
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = df.format(date);
			if(dateString.startsWith("1970-01-01")){
				return dateString.substring(11);
			}
			else if(dateString.endsWith("00:00:00")){
				return dateString.substring(0, 10);
			}else{
				return dateString;
			}
		}else{
			return "";
		}
	}
	
	
	/**
	 * 得到当天日期特定格式(yyyy-MM-dd)字符串
	 * @return 日期字符串
	 */
	public static String getDateString(){
		Date date= new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);

	}
	
	/**
	 * 在控制台打印调试消息
	 * @param msg 要打印的消息
	 */
	public static void println(String msg){
		System.out.println("\n");
		System.err.println("======== "+msg+" =======");
		System.out.println("\n");
	}
	
	
	/**
	 * 将中文汉字转化为首字母字符
	 * @param chinese 中文汉字
	 * @return 汉字首字母字符
	 */
	public static String chinese2PinYinShort(String chinese){
		return PinYin.toPYString(chinese);
	}
	
	/**
	 * MD5 加密
	 * @param password 明文密码
	 * @return 加密后的密码字符串
	 */
	public static String encodePassword(String password){
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(password.getBytes());
			byte[] temp = digest.digest();
			String returnString = "";
			for(int i=0;i<temp.length;i++){
				String plainText = Integer.toHexString(temp[i] & 0xEF);
				if (plainText.length() < 2) {
					plainText = "0" + plainText;
				}
				returnString += plainText;
			}
			return returnString;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * @return 一个uuid字符串
	 */
	public static String uuid(){
		return UUID.randomUUID().toString();
	}

	
	public static void main(String[] args) {
//		Calendar  c =  Calendar.getInstance();
//		Date date= new Date();
//		System.out.println(date.getTime());
//		System.out.println(date.getDay());
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-EE");
//		System.out.println("====="+deleteAllFiles("E:\\pic\\1\\2008-11-17"));
	
	}
}
