package hzk.util;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <h1>包装了一个线程的任务类</h1>
 * <p>
 * 包含以下操作方法：开始，取消，暂停，继续<br>
 * 有计时功能<br>
 * 本类采用包装机制和Proxy设计模式。
 * </p>
 * 使用Task代替原生的Thread的好处是：多了暂停和继续的功能，但这必须由Task的子类实现<br>
 * <code>run()</code>的具体实现示例：
 * </p>
 * 
 * <pre>
 * 	while (condition){
 *   	//检查当前任务是否需要被取消
 * 	 if (isStopped()) {
 * 		break;
 * 	 }
 * 	 //检查当前任务是否需要暂停
 * 	 if （isPaused()) { 		
 * 		letThreadWait();
 * 	 } 
 *   	...
 * 	 	...
 * 	 
 * 	}
 * </pre>
 * @author HZK
 * @version 201206
 */
public abstract class Task implements Runnable {
	protected Log log = LogFactory.getLog(this.getClass());
	private Thread thread;
	private long runMillisecBLS; // accumulated run milliseconds before last start
	private Calendar firstStartTime;
	private Calendar lastStartTime;
	private Calendar lastStopTime;
	private int resumeCount;

	private boolean paused = false;
	private boolean stopped = false;

	public Task() {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				run_proxy();
			}

		});
	}

	public void start() {
		runMillisecBLS = resumeCount = 0;
		lastStartTime = firstStartTime = Calendar.getInstance();
		thread.start();

	}

	public void stop() {
		calc_lastStopTime();
		stopped = true;
		if (paused)
			resume();
	}

	public void pause() {
		calc_lastStopTime();
		paused = true;

	}

	public void resume() {
		resumeCount++;
		lastStartTime = Calendar.getInstance();
		paused = false;
		synchronized (thread) {
			thread.notify();
		}
	}

	public void pauseOrResume() {
		if (paused)
			resume();
		else
			pause();
	}

	public void join() throws InterruptedException{
		thread.join();
	}
	
	private void calc_lastStopTime() {
		lastStopTime = Calendar.getInstance();
		runMillisecBLS += System.currentTimeMillis()
				- lastStartTime.getTimeInMillis();
	}

	private void run_proxy() {
		run();
		calc_lastStopTime();
	}
	
	public abstract void run();

	/**
	 * <h2>让当前任务线程一直等待</h2>
	 * <p>
	 * 如果被请求暂停，则中止当前线程（以调用<code>wait()</code>的方式），直到被唤醒。 <br>
	 * 如果没有被暂停，此方法无效果。<br>
	 * 这是一个重要的内部方法，建议在参与耗时的或需要计时的运行步骤<code>run()</code>中使用：<br>
	 * 比如在一个循环流程中检查当前任务是否需要暂停后调用,代码示例：
	 * 
	 * <pre>
	 *  while(true){
	 * 	 if (isPaused()){
	 * 	  letThreadWait();
	 * 	 }
	 * 	 ...
	 * 	}
	 * </pre>
	 * 
	 * </p>
	 */
	protected void letThreadWait() {

		try {
			synchronized (thread) {
				while (paused)
					thread.wait();
			}
		} catch (InterruptedException e) {
			log.error(null, e);
		}

	}

	public boolean isPaused() {
		return paused;
	}

	public boolean isStopped() {
		return stopped;
	}

	public boolean isAlive() {
		return thread.isAlive();
	}

	/**
	 * 获取该任务目前已经运行的累计时间
	 * @return 已运行累计时间的毫秒值
	 */
	public long getRunMillisec() {
		if (!isPaused() && lastStartTime != null)
			return runMillisecBLS + System.currentTimeMillis()
					- lastStartTime.getTimeInMillis();
		else
			return runMillisecBLS;
	}

	public Calendar getFirstStartTime() {
		return firstStartTime;
	}

	public Calendar getLastStartTime() {
		return lastStartTime;
	}

	public Calendar getLastStopTime() {
		return lastStopTime;
	}

	public int getResumeCount() {
		return resumeCount;
	}

}
