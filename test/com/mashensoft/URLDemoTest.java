package com.mashensoft;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class URLDemoTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	/**
	 * ���Զ�ȡ�ļ�
	 */
	@Test
	void testGetContentFromFile() {
		String content = URLDemo.getContentFromFile();
		System.out.println(content);
	}
	/**
	 * ������ȡ�ļ��е�ip��ַ����Ӫ������
	 */
	@Test
	void testBatchGetIp() {
		String content = URLDemo.getContentFromFile();
		URLDemo.batchGetIp(content);
	}

}
