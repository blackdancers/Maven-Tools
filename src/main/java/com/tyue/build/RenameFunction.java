package com.tyue.build;

import java.io.File;
import java.util.Scanner;

public class RenameFunction {
	static Scanner input = new Scanner(System.in);
	public static String test1[];

	public static void sure(String s1, String s2, String s3) throws Exception {
		File file = new File(s1);
		String test[];
		test = file.list();
		// 遍历文件的名字
		for (int i = 0; i < test.length; i++) {
			// 判断是不是有你想去除的关键字
			if (test[i].indexOf(s2) != -1) {
				// 保存重命名后的文件名
				test[i] = test[i].replace(s2, s3);
			}
		}
		File[] files = file.listFiles();
		for (int i = 0; i < test.length;) {
			for (File f : files) {
				if (f.isFile()) {
					// 循环赋重命名后的名字
					f.renameTo(new File(s1 + "/" + test[i++]));
				}
			}
		}
	}
}