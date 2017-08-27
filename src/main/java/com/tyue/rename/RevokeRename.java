package com.tyue.rename;

import java.io.File;

public class RevokeRename {
	public static void revoke(String s1) throws Exception {
		// 重新赋回原来的名字
		File file = new File(s1);
		File[] files = file.listFiles();
		for (int i = 0; i < RenameFunction.test1.length;) {
			for (File f : files) {
				if (f.isFile()) {
					// 注意是test1
					f.renameTo(new File(s1 + "/" + RenameFunction.test1[i++]));
				}
			}
		}
	}
}