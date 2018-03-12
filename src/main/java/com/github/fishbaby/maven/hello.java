package com.github.fishbaby.maven;

import java.io.File;

public class hello {
	
	public static void main(String[] args) {
		String fileName = "E:\\Videos\\老男孩-2016最新shell高级编程\\14-AWK数组国内企业案例视频课程" + File.separator;
		File f = new File(fileName);
		print(f);
	}

	
	public static void print(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				File[] fileArray = f.listFiles();
				if (fileArray != null) {
					for (int i = 0; i < fileArray.length; i++) {
						File file = fileArray[i];
						if (!file.getName().contains(".")) {
//							String absolutePath = file.getAbsolutePath();
//							System.err.println(absolutePath);
							File f1 = new File(file.getParent()+"//"+file.getName().replaceAll(file.getName(), file.getName()+".mp4"));
							boolean renameTo = file.renameTo(f1);
							System.err.println(renameTo);
						}
						
//						String absolutePath = file.getAbsolutePath();
//						System.err.println(absolutePath);
						// 递归调用
						print(fileArray[i]);
					}
				}
			} else {
				//System.out.println(f);
			}
		}
	}
}
