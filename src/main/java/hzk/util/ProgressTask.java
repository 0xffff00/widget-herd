package hzk.util;

import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <h1>有进度状态监测能力的任务线程类</h1>
 * <p>
 * 可以被暂停，继续，开始，取消。这些操作都应该在运行代码块内部控制。
 * </p>
 * <p>
 * 建议其子类实现<code>run()</code> 时，在耗时的或需要计时计数的每个循环块开始前，进行任务状态的例行检查 <br>
 * <code>run()</code>的具体实现示例：
 * </p>
 * 
 * <pre>
 * 	while (condition){
 *   	//检查当前任务是否需要被取消
 * 	 if (isCancelled()) {
 * 		break;
 * 	 }
 * 	 //检查当前任务是否需要暂停
 * 	 if （isPaused()) {
 * 		publish(PAUSE)
 * 		letThreadWaiting();
 * 	 } 
 *   	...
 * 	 	...
 * 	 //每次循环都发布一次更新事件
 * 	 publish(UPDATE);	
 * 	}
 *   	//循环结束时发布一个成功或取消事件
 *   	publish(COMPLETE)
 * </pre>
 * 
 * @version 0.7
 * 
 */
public abstract class ProgressTask extends Thread implements ProgressObservable {
	protected Log log = LogFactory.getLog(this.getClass());
	private boolean threadPaused = false;
	private boolean threadCancelled = false;

	private Collection<ProgressObserver> observers;

	public ProgressTask() {
		observers = new HashSet<ProgressObserver>();
	}

	@Override
	public void notifyObservers(ProgressEvent e) {
		for (ProgressObserver o : observers) {
			o.progressUpdated(e);
		}

	}

	@Override
	public void addObserver(ProgressObserver o) {
		observers.add(o);

	}

	@Override
	public void deleteObserver(ProgressObserver o) {
		observers.remove(o);

	}

	@Override
	public void deleteObservers() {
		observers.removeAll(observers);

	}

	public void pauseTask() {
		threadPaused = true;
	}

	public void cancelTask() {
		threadCancelled = true;
		if (threadPaused)
			resumeTask();
	}

	public void resumeTask() {
		threadPaused = false;
		synchronized (this) {
			notify();
		}
	}

	public void pauseOrResume() {
		threadPaused = !threadPaused;
		if (!threadPaused)
			resumeTask();
	}

	/**
	 * <h2>让当前任务线程一直等待</h2> 如果被请求暂停，则中止当前线程（以调用<code>wait()</code>的方式），直到被唤醒。 <br>
	 * 这是一个重要的内部方法，建议在参与耗时的或需要计时的运行步骤<code>run()</code>中使用：
	 * 比如在一个循环流程中检查当前任务是否需要暂停后调用。
	 */
	protected void letThreadWaiting() {

		try {
			synchronized (this) {
				while (threadPaused)
					wait();
			}
		} catch (InterruptedException e) {
			log.error(null, e);
		}

	}

	public boolean isCancelled() {
		return threadCancelled;

	}

	public boolean isPaused() {
		return threadPaused;

	}

	/**
	 * 发布一个更新事件，通告所有观察者
	 */
	public void publish() {
		publish(ProgressEvent.UPDATE);
	}

	/**
	 * 发布一个事件，通告所有观察者
	 * 
	 * @param type
	 *            事件类型，默认是UPDATE
	 */
	public void publish(char type) {
		ProgressEvent e = createEvent();
		e.setType(type);
		notifyObservers(e);
	}

	/**
	 * <h2>创建新的进程事件操作</h2> 新事件的创建依赖于具体执行上下文中的环境变量
	 * 
	 * @return 新进程事件
	 */
	protected abstract ProgressEvent createEvent();

	/**
	 * 运行操作
	 */
	public abstract void run();
}
