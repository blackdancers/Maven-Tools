package com.tyue.build;

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
public class DeleteLastUpdated {

	private static PropertyHelper propHelper = new PropertyHelper("config");
	private static final String MAVEN_REPO_PATH = propHelper.getValue("maven.repo");
	private static final String FILE_SUFFIX = "lastUpdated";
	private static final Log logger = LogFactory.getLog(DeleteLastUpdated.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File mavenRep = new File(MAVEN_REPO_PATH);
		if (!mavenRep.exists()) {
			logger.warn("Maven repos is not exist.");
			return;
		}
		File[] files = mavenRep.listFiles((FilenameFilter) FileFilterUtils.directoryFileFilter());
		delFileRecr(files,null);
		logger.info("Clean lastUpdated files finished.");
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
				if(file.delete()){
					logger.info("File: ["+file.getName()+"] has been deleted.");
				}
			}
		}
	}

}
