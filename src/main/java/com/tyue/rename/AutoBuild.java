
package com.tyue.rename;

import com.tyue.build.PropertyHelper;
import com.tyue.build.StreamGobbler;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
/**
 * 
 * MAVEN工程自动编译打包到底 ^_^
 *
 */
public class AutoBuild {
	
	private static PropertyHelper prop = new PropertyHelper("config");
	//父工程名称
	private static final String PROJECT_NAME = prop.getValue("PROJECT_PARENT_NAME");
	private static final String PROJECT_NAME_PREFIX = PROJECT_NAME+"-";
	private static final String PROJECT_DIR = prop.getValue("PROJECT_DIR");

	private static Map<String, String> dirsMap = new HashMap<>();
	private static List<String> rootDirsList = new ArrayList<>();
	private static List<String> commonDirsList = new ArrayList<>();
	private static List<String> baseDirsList = new ArrayList<>();
	private static List<String> apiDirsList = new ArrayList<>();
	private static List<String> serviceDirsList = new ArrayList<>();
	private static List<String> webDirsList = new ArrayList<>();
	private static List<String> commonServiceDirsList = new ArrayList<>();

	/**
	 * 获取项目的所有目录结构 <br>
	 * 目录名称和目录相对路径(PROJECT_DIR)
	 */
	private static Map<String, String> getDirsMap(File projectDir) {
		if (projectDir.exists()) {
			File[] listFiles = projectDir.listFiles();
			for (File file : listFiles) {
				// 是目录且前缀限定
				if (file.isDirectory()&& file.getName().startsWith(PROJECT_NAME)) {
					dirsMap.put(file.getName(), file.getAbsolutePath());
					getDirsMap(file);
				}
			}
		}
		return dirsMap;
	}



	public static void main(String[] args) {
		//MAVEN命令集合
		String[] cmd = prop.getValue("MVN_COMMON_CMD").split(",");
		String[] webCmd = prop.getValue("MVN_WEB_CMD").split(",");
		// 目录集合
		List<String> dirsDirName = new ArrayList<String>();
		Map<String, String> dirsMap = getDirsMap(new File(PROJECT_DIR));
		Set<Entry<String, String>> entrySet = dirsMap.entrySet();
		for (Entry<String, String> entry : entrySet) {
			dirsDirName.add(entry.getValue());
		}
		// 目录集合
		for (String dirStr : dirsDirName) {
			File file = new File(dirStr);
			if (file.isDirectory()) {
				if (PROJECT_NAME.equals(file.getName())) {
					// 父工程
					rootDirsList.add(file.getAbsolutePath());
				}
				boolean starts = file.getName().startsWith(PROJECT_NAME_PREFIX);
				if (starts && file.getName().equals(PROJECT_NAME_PREFIX + "common")) {
					commonDirsList.add(file.getAbsolutePath());
				}
				if (starts && file.getName().equals(PROJECT_NAME_PREFIX + "message-producer")) {
					commonDirsList.add(file.getAbsolutePath());
				}
				if (starts && file.getName().equals(PROJECT_NAME_PREFIX + "common-service")) {
					commonServiceDirsList.add(file.getAbsolutePath());
				}

				if (starts&& (file.getName().equals(PROJECT_NAME_PREFIX + "base") ||
						file.getName().equals(PROJECT_NAME_PREFIX + "goods"))
						|| file.getName().equals(PROJECT_NAME_PREFIX + "order")
						|| file.getName().equals(PROJECT_NAME_PREFIX + "oms")) {
					baseDirsList.add(file.getAbsolutePath());
				}
				if (starts && file.getName().endsWith("-api")) {
					apiDirsList.add(file.getAbsolutePath());
				}
				if (starts && file.getName().endsWith("-service")&& !file.getName().contains("common")) {
					serviceDirsList.add(file.getAbsolutePath());
				}
				if (starts&& (file.getName().endsWith("-web") || file.getName().endsWith("-consumer"))) {
					webDirsList.add(file.getAbsolutePath());
				}
			}
		}

		/**
		 * 3、设置排序目录，用于MVN编译顺序
		 */
		Map<Integer, List<String>> temp = new TreeMap<>();
		temp.put(1, rootDirsList);
		temp.put(2, commonDirsList);
		temp.put(3, baseDirsList);
		temp.put(4, apiDirsList);
		temp.put(5, commonServiceDirsList);
		temp.put(6, serviceDirsList);

		Set<Integer> keySet = temp.keySet();
		for (Integer idKey : keySet) {
			List<String> sortDirsList = temp.get(idKey);
			exec(sortDirsList, cmd);
		}
		//清空，web目录
		temp.clear();
		temp.put(7, webDirsList);

		keySet = temp.keySet();
		for (Integer idKey : keySet) {
			List<String> sortDirsList = temp.get(idKey);
			exec(sortDirsList, webCmd);
		}
	}

	/**
	 * 调用CMD执行MVN命令，注意目录切换和多个命令链接符 '&&',<br>
	 * **** 重点是阻塞处理 {@link StreamGobbler} ****
	 * @param sortDirsList
	 * @param cmds
	 */
	private static void exec(List<String> sortDirsList, String[] cmds) {
		StringBuilder sb = new StringBuilder();
		for (String cmd : cmds) {
			sb.append(" && mvn ");
			sb.append(cmd);
		}
		// TODO 需要做文件存在判断pom.xml
		for (String sortDir : sortDirsList) {
			String cmd = "cmd.exe /c cd " + sortDir + sb.toString();
			Runtime run = Runtime.getRuntime();
			try {
				long beginTime = System.currentTimeMillis();
				Process process = run.exec(cmd);
				//阻塞处理
				StreamGobbler errorGobbler = new StreamGobbler(process.getErrorStream(), "ERROR");  
				errorGobbler.start(); 
				StreamGobbler outGobbler = new StreamGobbler(process.getInputStream(), "STDOUT");  
				outGobbler.start();  
				//wait处理，等待执行完成
				process.waitFor();
				int i = process.exitValue();
				long endTime = System.currentTimeMillis();
				if (i == 0) {
					System.out.println(sortDir + " -> 执行完成，耗时："+(endTime - beginTime)/1000+"秒");
				} else {
					System.out.println(sortDir + " -> 执行失败，耗时："+(endTime - beginTime)/1000+"秒");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
