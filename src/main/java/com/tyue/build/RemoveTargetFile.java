package com.tyue.build;

import java.io.File;

public class RemoveTargetFile {
	public static void startDelete(String path) {
		File file = new File(path);
		deleteFile(file);
	}

	private static void deleteFile(File file) {
		// 记住不要把路径的那个文件夹删掉了
		if (file.exists()) {
			if (file.isFile()) {
				// 是文件，直接删除
				file.delete();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					// 如果不是文件，进行迭代
					deleteFile(files[i]);
				}
			}
		}
	}
}