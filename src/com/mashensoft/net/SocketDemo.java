package com.mashensoft.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketDemo {
	/**
	 * 客户端
	 */
	public static void test1() {
		System.out.println("客户端已启动");
		try {
			Socket socket = new Socket("localhost", 11111);
			//获取问候语
			Scanner s = new Scanner(socket.getInputStream());
			if(s.hasNextLine()) {
				String line = s.nextLine();
				System.out.println(line);
			}
			//输出到服务端
			
			Scanner keybordIs= new Scanner(System.in);
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			while(keybordIs.hasNextLine()) {
				String line = keybordIs.nextLine();
				pw.append(line).append("\r\n");
				pw.flush();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void test2() {
		System.out.println("客户端已启动");
		try {
			Socket socket = new Socket("localhost", 11111);
			//获取问候语
			Scanner s = new Scanner(socket.getInputStream());
			if(s.hasNextLine()) {
				String line = s.nextLine();
				System.out.println(line);
			}
			//输出到服务端
			
			Scanner keybordIs= new Scanner(System.in);
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			while(keybordIs.hasNextLine()) {
				String line = keybordIs.nextLine();
				pw.append(line).append("\r\n");
				pw.flush();
				//获取服务端的响应文字
				
				if(s.hasNextLine()) {
					String msg =  s.nextLine();
					System.out.println(msg);
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//手工输入一个绝对路径，把它传递到服务端
	public static void test3() {
		try {
			Socket socket = new Socket("192.168.1.107",11111);
			
			String filePath = "e:/aaa.png";
			File file = new File(filePath);
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(file);
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void test4() {
		try {
			Socket socket = new Socket("localhost",11111);
			
			String filePath = "e:/aaa.png";
			File file = new File(filePath);
			Long lo = new Long(file.length());
			byte[] b= new byte[lo.intValue()];
			FileInputStream fos = new FileInputStream(file);
			fos.read(b);
			FileObject fo = new FileObject(file.getName(), b);
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(fo);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		test4();
	}
}
