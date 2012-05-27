package hzk.util.hash;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JFileHasher {
	private static Log log = LogFactory.getLog(JFileHasher.class);

	public static JFileHashTask createTask(String path) {
		JFileHashTask task=new JFileHashTask(path);				
		log.debug("createTask:" + path);		
		return task;
	}

}

