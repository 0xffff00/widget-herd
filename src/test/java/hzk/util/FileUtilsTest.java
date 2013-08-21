package hzk.util;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileFilter;
import java.util.Collection;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
public class FileUtilsTest {
	private Log log = LogFactory.getLog(this.getClass());
	String path1="E:\\Videos";
	String[] v_suffixes={"mkv","rmvb","rm","avi","mp4"};
	@Test
	public void test(){
		File directory=new File(path1);
		IOFileFilter videoFileFilter=new SuffixFileFilter(v_suffixes);
		IOFileFilter fileFilter=videoFileFilter;
		IOFileFilter dirFilter=FileFilterUtils.trueFileFilter();
		
		Collection<File> files=FileUtils.listFiles(directory, fileFilter, dirFilter);
		for (File file:files){
			log.info(file);
		}
		log.info(files.size());
		
	}

}
