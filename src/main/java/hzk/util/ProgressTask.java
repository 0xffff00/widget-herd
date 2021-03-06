package hzk.util;

import java.util.Collection;
import java.util.HashSet;

/**
 * <h1>有进度状态监测能力的任务类</h1>
 * <p>
 * 要拥有进度状态监测能力，必须在运行代码块中进行事件发布,即<code>publish(char)</code>方法 <br>
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
 * 		letThreadWait();
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
 * @author HZK
 * @version 0.7
 * 
 */
public abstract class ProgressTask extends Task implements ProgressObservable {
	private Collection<ProgressObserver> observers;
	public static final char BEGIN = 0;
	public static final char UPDATE = 1;
	public static final char ERROR = 4;
	public static final char COMPLETE = 12;
	public static final char CANCEL = 14;
	public static final char STOP = 15;
	public static final char PAUSE = 21;
	public static final char RESUME = 23;

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

	/**
	 * 发布一个更新事件，通告所有观察者
	 */
	public void publish() {
		publish(UPDATE);
	}

	/**
	 * 发布一个事件，通告所有观察者
	 * 
	 * @param type
	 *            事件类型，默认是UPDATE
	 */
	public void publish(char type) {
		ProgressEvent e = createEvent();
		if (e==null)
			e=new ProgressEvent();
		e.setType(type);
		notifyObservers(e);
	}

	/**
	 * <h2>创建新的进程事件操作</h2> 新事件的创建依赖于具体执行上下文中的环境变量
	 * 
	 * @return 新进程事件
	 */
	protected abstract ProgressEvent createEvent();

}
