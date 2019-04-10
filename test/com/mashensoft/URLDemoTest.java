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
	 * 测试读取文件
	 */
	@Test
	void testGetContentFromFile() {
		String content = URLDemo.getContentFromFile();
		System.out.println(content);
	}
	/**
	 * 批量获取文件中的ip地址的运营商名称
	 */
	@Test
	void testBatchGetIp() {
		String content = URLDemo.getContentFromFile();
		URLDemo.batchGetIp(content);
	}

}
