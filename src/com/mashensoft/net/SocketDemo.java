package com.mashensoft.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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
			// 获取问候语
			Scanner s = new Scanner(socket.getInputStream());
			if (s.hasNextLine()) {
				String line = s.nextLine();
				System.out.println(line);
			}
			// 输出到服务端

			Scanner keybordIs = new Scanner(System.in);
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			while (keybordIs.hasNextLine()) {
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
			// 获取问候语
			Scanner s = new Scanner(socket.getInputStream());
			if (s.hasNextLine()) {
				String line = s.nextLine();
				System.out.println(line);
			}
			// 输出到服务端

			Scanner keybordIs = new Scanner(System.in);
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			while (keybordIs.hasNextLine()) {
				String line = keybordIs.nextLine();
				pw.append(line).append("\r\n");
				pw.flush();
				// 获取服务端的响应文字

				if (s.hasNextLine()) {
					String msg = s.nextLine();
					System.out.println(msg);
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 手工输入一个绝对路径，把它传递到服务端
	public static void test3() {
		try {
			Socket socket = new Socket("192.168.1.107", 11111);

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
			Socket socket = new Socket("localhost", 11111);

			String filePath = "e:/aaa.png";
			File file = new File(filePath);
			Long lo = new Long(file.length());
			byte[] b = new byte[lo.intValue()];
			FileInputStream fos = new FileInputStream(file);
			fos.read(b);
			FileObject fo = new FileObject(file.getName(), b);
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(fo);
			os.write(null);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void test5() {
		try {
			Socket socket = new Socket("localhost", 11111);

			String[] arr = { "e:/aaa.png", "My Movie.mp4" };
			for (String filePath : arr) {
				File file = new File(filePath);
				Long lo = new Long(file.length());
				byte[] b = new byte[lo.intValue()];
				FileInputStream fos = new FileInputStream(file);

				fos.read(b);
				FileObject fo = new FileObject(file.getName(), b);
				OutputStream out = socket.getOutputStream();
				PrintWriter pw = new PrintWriter(out);
				ObjectOutputStream os = new ObjectOutputStream(out);
				pw.append("file").append("\r\n");
				pw.flush();
				os.writeObject(fo);
				os.write(null);
				os.flush();

				pw.close();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void test6() {
		try {

			String[] arr = { "e:/aaa.png", "e:/My Movie.mp4" };
			for (String filePath : arr) {
				Socket socket = new Socket("localhost", 11111);
				File file = new File(filePath);
				Long lo = new Long(file.length());
				byte[] b = new byte[lo.intValue()];
				FileInputStream fos = new FileInputStream(file);
				fos.read(b);
				FileObject fo = new FileObject(file.getName(), b);
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(out);
				os.writeObject(fo);
				os.writeObject(null);
				os.flush();

				os.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void test7() {
		try {
			
			String[] arr = { "e:/aaa.png", "e:/My Movie.mp4" };
			Scanner s = new Scanner(System.in);
			
			
			while(s.hasNextLine()) {
				String line = s.nextLine();
				Socket socket = new Socket("localhost", 11111);
				File file = new File(line);
				Long lo = new Long(file.length());
				byte[] b = new byte[lo.intValue()];
				FileInputStream fos = new FileInputStream(file);
				fos.read(b);
				FileObject fo = new FileObject(file.getName(), b);
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(out);
				os.writeObject(fo);
				os.writeObject(null);
				os.flush();
				
				os.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 向服务器发送一个服务器端的文件路径，把它下载到客户端里来。
	 * 对应ServerSocketDemo里的test15
	 * 
	 */
	public static void test8() {
		try {
			
			Scanner s = new Scanner(System.in);
			Socket socket = new Socket("localhost", 11111);
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while(s.hasNextLine()) {
				String line = s.nextLine();
				pw.append(line).append("\r\n");
				pw.flush();
				
				Object obj = null;
				while((obj = ois.readObject())!=null) {
					FileObject fo = (FileObject)obj;
					OutputStream out = new FileOutputStream("d:/"+fo.fileName);
					out.write(fo.byteFile);
					out.flush();
					out.close();
				}
			}
			pw.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		test8();
	}
}
