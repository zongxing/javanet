package com.mashensoft.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerSocketDemo {
	public static void test1() {
		// 21 ftp; 80 httpd apache; 443 https;3306 mysql;1433 sqlserver
		try {
			ServerSocket serverSocket = new ServerSocket(11111);
			int i = 0;
			while (true) {
				Thread.sleep(1000);
				System.out.println(i++);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void test2() {
		// 21 ftp; 80 httpd apache; 443 https;3306 mysql;1433 sqlserver
		try {
			ServerSocket serverSocket = new ServerSocket(11111);

			// 阻塞状态
			Socket socket = serverSocket.accept();
			System.out.println(socket.getInetAddress());
			int i = 0;
			while (true) {
				Thread.sleep(1000);
				System.out.println(i++);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void test3() {
		try {
			ServerSocket serverSocket = new ServerSocket(11111);

			// 阻塞状态
			Socket socket = serverSocket.accept();
			System.out.println(socket.getInetAddress());
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.append("welcome to my restaurant!");
			pw.flush();
			int i = 0;
			while (true) {
				Thread.sleep(1000);
				System.out.println(i++);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void test4() {
		try {
			ServerSocket serverSocket = new ServerSocket(11111);
			// 阻塞状态
			Socket socket = serverSocket.accept();
			System.out.println(socket.getInetAddress());
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.append("welcome to my restaurant!\r\n");
			pw.flush();
			// 接收客户输入进来的内容

			Scanner s = new Scanner(socket.getInputStream());
			FileWriter fw = new FileWriter("日记.txt");

			// 这里是个死循环
			while (s.hasNextLine()) {
				String line = s.nextLine();
				System.out.println(line);
				fw.append(line).append("\r\n");
				fw.flush();
				// 如果客户端让我结束，我就结束
				if (line.equals("quit")) {
					break;
				}

			}
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test5() {
		try {
			ServerSocket serverSocket = new ServerSocket(11111);
			// 阻塞状态
			Socket socket = serverSocket.accept();
			System.out.println(socket.getInetAddress());
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.append("welcome to my restaurant!\r\n");
			pw.flush();
			// 接收客户输入进来的内容

			Scanner s = new Scanner(socket.getInputStream());
			FileWriter fw = new FileWriter("日记.txt");
			Scanner keybordIs = new Scanner(System.in);
			// 这里是个死循环
			while (s.hasNextLine()) {
				String line = s.nextLine();
				System.out.println(line);
				fw.append(line).append("\r\n");
				fw.flush();

				// 给客户返回一句话，通过键盘打上的话。
				if (keybordIs.hasNextLine()) {
					String msg = keybordIs.nextLine();
					pw.write(msg);
					pw.write("\r\n");
					pw.flush();
				}

				// 如果客户端让我结束，我就结束
				if (line.equals("quit")) {
					break;
				}

			}
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 运行一个程序
	 */
	public static void test7() {
		// String [] cmd={"cmd","/C","dir"};
		try {
//			Process p = Runtime.getRuntime().exec("cmd /C dir");
			Process p = Runtime.getRuntime().exec("cmd /C copy 日记.txt bbbb.txt");
			Scanner s = new Scanner(p.getInputStream(), "GBK");
			while (s.hasNextLine()) {
				String line = s.nextLine();
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void test8() {

		// String [] cmd={"cmd","/C","dir"};
		try {
			ServerSocket ss = new ServerSocket(11111);
			Socket socket = ss.accept();
			Scanner socketScanner = new Scanner(socket.getInputStream());
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			while (socketScanner.hasNextLine()) {
				String cmd = socketScanner.nextLine();
				Process p = Runtime.getRuntime().exec("cmd /C " + cmd);

				StringBuffer sb = new StringBuffer();
				Scanner s = new Scanner(p.getInputStream(), "GBK");
				while (s.hasNextLine()) {
					String line = s.nextLine();
					System.out.println(line);
					sb.append(line).append("\r\n");

				}
				pw.append(sb.toString());
				pw.flush();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * io流：ObjectInputStream ObjectOutputStream
	 */
	public static void test9() {
		Person p = new Person("星哥");
		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("object.txt"));
			os.writeObject(p);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件中的对象
	 */
	public static void test10() {
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream("object.txt"));
			Object obj = is.readObject();
			Person p = (Person) obj;
			System.out.println(p.name);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 接收客户端传递过来的文件
	 */
	public static void test11() {
		try {
			ServerSocket ss = new ServerSocket(11111);
			Socket socket = ss.accept();
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			Object obj = null;
			while ((obj = is.readObject()) != null) {
				File file = (File) obj;
				String fileName = file.getName();
				File destFile = new File("d:/" + fileName);
				FileOutputStream os = new FileOutputStream(destFile);
				FileInputStream fis = new FileInputStream(file);
				int a = 0;
				while ((a = fis.read()) != -1) {
					os.write(a);
				}
				os.close();
				fis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * 把文件的内容读取到一个byte数组
	 */
	public static void test12() {
		try {
			ServerSocket ss = new ServerSocket(11111);
			Socket socket = ss.accept();
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			Object obj = null;
			while ((obj = is.readObject()) != null) {
				File file = (File) obj;
				String fileName = file.getName();
				File destFile = new File("d:/" + fileName);
				FileOutputStream os = new FileOutputStream(destFile);
				FileInputStream fis = new FileInputStream(file);
				int a = 0;
				while ((a = fis.read()) != -1) {
					os.write(a);
				}
				os.close();
				fis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 接收客户端的文件
	 */
	public static void test13() {
		try {
			ServerSocket ss = new ServerSocket(11111);
			Socket socket = ss.accept();
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			Object obj = null;
			while ((obj = is.readObject()) != null) {
				FileObject fo = (FileObject) obj;
				FileOutputStream os = new FileOutputStream("d:/" + fo.fileName);
				os.write(fo.byteFile);
				os.flush();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 接收客户端的文件,可以一直接收
	 */
	public static void test14() {
		try {
			ServerSocket ss = new ServerSocket(11111);
			while (true) {
				Socket socket = ss.accept();
				ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
				Object obj = null;
				while ((obj = is.readObject()) != null) {
					FileObject fo = (FileObject) obj;
					FileOutputStream os = new FileOutputStream("d:/" + fo.fileName);
					os.write(fo.byteFile);
					os.flush();
					os.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 向服务器发送一个服务器端的文件路径，把它下载到客户端里来。
	 * 对应SocketDemo里的test8
	 * 
	 */
	public static void test15() {
		try {
			ServerSocket ss = new ServerSocket(11111);
			while (true) {
				Socket socket = ss.accept();
				Object obj = null;
				Scanner s = new Scanner(socket.getInputStream());
				ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
				while(s.hasNextLine()) {
					String line = s.nextLine();
					File file = new File(line);
					if(file==null) {
						continue;
					}
					FileInputStream fis = new FileInputStream(file);
					int len = new Long(file.length()).intValue();
					byte[] b = new byte[len];
					fis.read(b);
					FileObject fo = new FileObject(file.getName(), b);
					os.writeObject(fo);
					os.writeObject(null);
					os.flush();
				}
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		System.out.println("服务端已启动");
		test15();
	}
}

class Person implements Serializable {
	String name;

	public Person(String name) {
		super();
		this.name = name;
	}

}

/**
 * 1：创建一个服务端，在客户端输入一个文件路径，服务端把文本内容输出到客户端 2：创建一个服务端，一个客户端，两者可以互相对话
 * 3：客户端传输一个文件到服务端
 *
 */
class FileObject implements Serializable {
	String fileName;
	byte[] byteFile;

	public FileObject(String fileName, byte[] byteFile) {
		super();
		this.fileName = fileName;
		this.byteFile = byteFile;
	}

}
