package com.tyue.build;

/**
 * 
 * 设置隐藏文件夹 Process process = Runtime.getRuntime().exec("cmd.exe /C attrib -s -h -r your_path");
 * 
 * @see https://stackoverflow.com/questions/1999437/how-to-make-a-folder-hidden-using-java
 * @see https://stackoverflow.com/questions/1294989/make-a-file-folder-hidden-on-windows-with-java
 * @author black
 *
 */
public class FileUtils {

	/**
	 * 
	 */
	public static void hiddenFolders() {
		try {
			Runtime rt = Runtime.getRuntime();
			// put your directory path instead of your_directory_path
			Process process = rt.exec("attrib -s -h -r your_directory_path");
			//阻塞处理
			StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");  
			errorGobbler.start(); 
			StreamGobbler outGobbler = new StreamGobbler(process.getInputStream(), "STDOUT");  
			outGobbler.start();  
			//wait处理，等待执行完成
			process.waitFor();
			
			int exitVal = process.exitValue();
			if (exitVal == 0) {
				System.out.println(" -> 执行完成");
			} else {
				System.out.println(" -> 执行失败");
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void main(String[] args) {
		hiddenFolders();
	}

}
