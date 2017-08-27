package com.tyue.build;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

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
			Process process = rt.exec("cmd.exe /C attrib -s -h -r F:\\Videos\\Downloads");
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
	
	/**
	 * 批量重命名文件
	 */
	public static void renameFile() {
		String dir = "F:\\JAVA\\hibernate\\";    //选择要批量更改文件的文件夹，记得后面要加上\\表示文件夹下面的文件
        File file = new File(dir);
        String fileName[] = file.list();
        int number = fileName.length;        //获取文件数量
        File newFile[] = new File[number];    


        for(int i=0; i<number; i++) {
            System.out.println("第"+(i+1)+"个文件名" + ":" + fileName[i]);    //查看所有文件
            newFile[i] = new File(dir+fileName[i]);
        }
        System.out.println("============分割线=============");
        for(int i=0; i<number;i++){
            String fn = fileName[i];
            String newFileName = fn.substring(0, 3)+fn.substring(22);    //根据原来文件名来更改，用substring(begin,end)来截取字符串
            System.out.println(newFileName);
            boolean flag = newFile[number - (i+1)].renameTo(new File(dir + newFileName+".avi"));
            
            if(flag){
                System.out.println("重命名成功！"+":"+newFileName);
                System.out.println("重命名成功"+(i+1));
            } else {
                System.out.println("失败");
            }
        }
    }

	
	
	
	public static void main(String[] args) {
		Path path = FileSystems.getDefault().getPath("F:","/Videos/Downloads");
		System.out.println(path.toString());
		try {
			Files.setAttribute(path,"dos:hidden",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//hiddenFolders();
	}

}
