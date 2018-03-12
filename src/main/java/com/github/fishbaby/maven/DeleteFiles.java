package com.github.fishbaby.maven;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 删除本地MAVEN仓库中所有.lastUpdated文件
 *
 */
public class DeleteFiles {

	private static PropertyHelper propHelper = new PropertyHelper("config");
	private static final String RESOURCE_PATH = propHelper.getValue("resource.path");
	private static final String FILE_SUFFIX = "(1).mp4";
	private static final Log logger = LogFactory.getLog(DeleteFiles.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File resource = new File(RESOURCE_PATH);
		if (!resource.exists()) {
			logger.warn("resource path is not exist.");
			return;
		}
		File[] files = resource.listFiles((FilenameFilter) FileFilterUtils.directoryFileFilter());
		delFileRecr(files,null);
		logger.info("Delete files finished.");
	}

	private static void delFileRecr(File[] dirs, File[] files) {
		if (dirs != null && dirs.length > 0) {
			for(File dir: dirs){
				File[] childDir = dir.listFiles((FilenameFilter) FileFilterUtils.directoryFileFilter());
				File[] childFiles = dir.listFiles((FilenameFilter) FileFilterUtils.suffixFileFilter(FILE_SUFFIX));
				delFileRecr(childDir,childFiles);
			}
		}
		if(files!=null&&files.length>0){
			for(File file: files){
				System.out.println(file.getName());
				if(file.delete()){
					logger.info("File: ["+file.getName()+"] has been deleted.");
				}
			}
		}
	}

}
