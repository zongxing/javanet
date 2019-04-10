package com.mashensoft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class URLDemo {
	public static String getContentFromURL(String myurl) {
		StringBuffer sb = new StringBuffer();
		InputStream is = null;
		try {
			URL url = new URL(myurl);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();

			// url.openStream() 可以取代上面两步
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
//			while(br.read()){
//				br.readLine();
//			}
			Scanner s = new Scanner(is);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				// System.out.println(line);
				sb.append(line).append("\r\n");
			}
			s.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		}
		return sb.toString();
	}

	/**
	 * 功能：从一段文本里取出一个ip地址
	 * 
	 * @param content 一段文本，其中可能包含一个ip地址
	 * @return 一个ip地址的字符串：类似：188.188.188.188
	 */
	public static String getIpAddressFromContent(String content) {
		String result = "";
		int beginIndex = content.indexOf("[");
		int endIndex = content.indexOf("]");
		if (beginIndex != -1) {
			result = content.substring(beginIndex + 1, endIndex);
		}
		return result;
	}

	/**
	 * 功能：从一个网页里获取运营商名称（地点）
	 * 
	 * @param content 带ip的内容
	 * @return 运营商的名称（地理位置）
	 */
	public static String getSpNameFromContent(String content) {
		String result = null;
		int beginIndex = content.indexOf("本站数据：");
		int endIndex = content.indexOf("</li>");
		if (beginIndex != -1) {
			result = content.substring(beginIndex + 5, endIndex);
		}
		return result;
	}

	/**
	 * 功能：根据一个ip地址，获取它的位置名称（运营商名称）
	 * 
	 * @param ip 类似：100.100.1.1
	 * @return 运营商名称
	 */
	public static String getSpName(String ip) {
		String spName = null;
		String myurl = "http://ip138.com/ips138.asp?ip=" + ip + "&action=2";
		String content = getContentFromURL(myurl);
		spName = getSpNameFromContent(content);
		return spName;
	}

	/**
	 * 读取文件中的内容
	 * 
	 * @return
	 */
	public static String getContentFromFile() {
		StringBuffer sb = new StringBuffer();
		Scanner s = null;
		try {
			s = new Scanner(new FileInputStream("ip.txt"));
			while (s.hasNextLine()) {
				String line = s.nextLine();
				sb.append(line);
				sb.append(",");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			s.close();
		}
		return sb.toString();
	}

	public static void batchGetIp(String content) {
		String ipArray[] = content.split(",");
		try (FileWriter fw = new FileWriter("result.txt");) {
			for (String ip : ipArray) {
				String spName = getSpName(ip);
				fw.write(ip + ";    " + spName+"\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// String content = getContentFromURL("http://2019.ip138.com/ic.asp");

		// String ip = getIpAddressFromContent(content);
		// System.out.println(ip);

//		String content = getContentFromURL("http://ip138.com/ips138.asp?ip=110.222.222.222&action=2");
//		String result = getSpNameFromContent(content);
//		System.out.println(result);
		if (args.length == 1) {
			String result = getSpName(args[0]);
			System.out.println(result);
		}

	}
}
