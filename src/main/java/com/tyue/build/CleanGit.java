package com.tyue.build;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CleanGit {

    static List<File> result = new ArrayList<>();

    public static void main(String[] args) {
        File folder = new File("E:\\git-repository\\Temp\\20170721");
        listDirectory(folder);
        //result.forEach(file -> System.out.println(file));
        for (File dir : result) {
            String cmd ;
            if(dir.isFile()){
                cmd = "cmd.exe /c del /q/a/f/s "+dir.getAbsolutePath();
            }else{
                cmd = "cmd.exe /c rd /s/q "+dir.getAbsolutePath();
            }

            Runtime run = Runtime.getRuntime();
            try {
                Process process = run.exec(cmd);
                //阻塞处理
                StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");
                errorGobbler.start();
                StreamGobbler outGobbler = new StreamGobbler(process.getInputStream(), "STDOUT");
                outGobbler.start();
                //wait处理，等待执行完成
                process.waitFor();
                int i = process.exitValue();
                if (i == 0) {
                    System.out.println(dir.getAbsolutePath() + " -> 执行完成");
                } else {
                    System.out.println(dir.getAbsolutePath() + " -> 执行失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void listDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    if (file.getName().equals(".idea")) {
                        result.add(file);
                    } else {
                        listDirectory(file);
                    }
                }
            }
        }
    }

}
