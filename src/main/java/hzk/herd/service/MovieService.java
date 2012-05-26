package hzk.herd.service;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MovieService {
	private Log log = LogFactory.getLog(this.getClass());
	String path1="E:\\Videos";
	String[] v_suffixes={"mkv","rmvb","rm","avi","mp4"};
	
	public Collection<File> listMovieFiles(File directory){		
		IOFileFilter videoFileFilter=new SuffixFileFilter(v_suffixes);
		IOFileFilter fileFilter=videoFileFilter;
		IOFileFilter dirFilter=FileFilterUtils.trueFileFilter();
		
		Collection<File> files=FileUtils.listFiles(directory, fileFilter, dirFilter);
		
		log.debug(files.size());
		return files;
	}
}
