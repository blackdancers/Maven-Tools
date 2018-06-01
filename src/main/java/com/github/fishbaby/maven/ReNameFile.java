package com.github.fishbaby.maven;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 【递归】重命名文件夹和文件
 *
 */
public class ReNameFile {

	private static PropertyHelper propHelper = new PropertyHelper("config");
	private static final String RESOURCE_PATH = propHelper.getValue("resource.path");
//	private static final String FILE_SUFFIX = "【需要修改的部分】";
	private static final String FILE_SUFFIX = "_rec.avi";
	private static final Log logger = LogFactory.getLog(ReNameFile.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File resourceDir = new File(RESOURCE_PATH);
		if (!resourceDir.exists()) {
			logger.warn("resourceDir is not exist.");
			return;
		}
		File[] files = resourceDir.listFiles((FilenameFilter) FileFilterUtils.directoryFileFilter());
		renameFileRecr(files,null);
	}

	private static void renameFileRecr(File[] dirs, File[] files) {
		if (dirs != null && dirs.length > 0) {
			for(File dir: dirs){
//				if (dir.isDirectory() && dir.getName().contains(FILE_SUFFIX)) {
//					File f1 = new File(dir.getParent()+"//"+dir.getName().replaceAll(FILE_SUFFIX, ".mp4"));
//					boolean renameTo = dir.renameTo(f1);
//					logger.info("Rename Directory: ["+renameTo+"] ");
//				}
				File[] childDir = dir.listFiles((FilenameFilter) FileFilterUtils.directoryFileFilter());
				File[] childFiles = dir.listFiles((FilenameFilter) FileFilterUtils.suffixFileFilter(FILE_SUFFIX)); //后缀
//				File[] childFiles = dir.listFiles((FilenameFilter) FileFilterUtils.prefixFileFilter(FILE_SUFFIX));	//前缀
				renameFileRecr(childDir,childFiles);
			}
		}
		if(files!=null&&files.length>0){
			for(File file: files){
				File f1 = new File(file.getParent()+"//"+file.getName().replaceAll(FILE_SUFFIX, ".avi"));
				boolean renameTo = file.renameTo(f1);
				logger.info("Rename File: ["+renameTo+"] ");
			}
		}
	}

}
