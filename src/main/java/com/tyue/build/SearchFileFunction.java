package com.tyue.build;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SearchFileFunction {
	static int count = 1;

	public static void startCopy(String source_path, String target_path, String file_Type) {
		// 启动循环函数
		xunHuan(source_path, target_path, file_Type);
	}

	public static void xunHuan(String source_path, String target_path, String file_Type) {
		File file = new File(source_path);
		String names[] = file.list();
		// 判断是不是文件以及是否以你想要的文件类型结尾
		if (file.isFile() && file.getAbsolutePath().endsWith(file_Type)) {
			String new_path = target_path + "/" + file.getName();
			File file1 = new File(new_path);
			if (!file1.exists()) {
				try {
					file1.createNewFile();
				} catch (IOException e) {
				}
			} else {
				// 如果文件名字相同，在点前面加数字进行区分
				// 注意用\\.进行分隔，而不是.
				String[] arr = new_path.split("\\.");
				String new_path1 = arr[0] + count + "." + arr[1];
				file1.renameTo(new File(new_path1));
			}
			// 是文件，所以开始复制文件
			fileCopyByBufferStreamArray(file.getAbsolutePath(), new_path);
		} else if (file.isFile() && !file.getAbsolutePath().endsWith(file_Type)) {
			// 注意这个方法体中什么都不写，就是不做处理
		} else {
			for (int i = 0; i < names.length; i++) {
				// 不是文件，进行迭代
				xunHuan(file.getAbsolutePath() + "/" + names[i], target_path, file_Type);
			}
		}
	}

	public static void fileCopyByBufferStreamArray(String srcFile, String targetFile) {
		// 用流的知识进行写文件
		File file = new File(srcFile);
		File file1 = new File(targetFile);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(file1);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(fos);
			int len = 0;
			byte[] b = new byte[10];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
		} catch (IOException e) {
		} finally {
			try {
				fis.close();
				fos.close();
				bis.close();
				bos.close();
			} catch (IOException e) {
			}
		}
	}
}