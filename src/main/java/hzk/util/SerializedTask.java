package hzk.util;

/**
 * 表示子任务之间的逻辑串联
 * @author HZK
 * @version 0.3
 */
public class SerializedTask extends Task {
	private Task[] tasks;
	private int current;

	public SerializedTask(Task... subTasks) {
		tasks = subTasks;
	}

	@Override
	public void run() {
		try {
			int i;
			for (i=0;i<tasks.length;i++) {	
				if (isCancelled()){
					break;
				}
				current=i;
				tasks[i].start();
				tasks[i].join();
			}			
		} catch (InterruptedException e) {
			log.error(null,e);
		}

	}

	public Task currentSubTask(){
		return tasks[current];
	}
	
	public int currentSubTaskIndex(){
		return current;
	}

	public int subTasksCount(){
		return tasks.length;
	}
	
	@Override
	public void pause() {
		currentSubTask().pause();	
		super.pause();
	}

	@Override
	public void resume() {
		currentSubTask().resume();	
		super.resume();
	}

	@Override
	public void cancel() {
		currentSubTask().cancel();
		super.cancel();
	}

	/**
	 * cancel current sub task and continue from the next one.
	 */
	public void cancelCurrent() {
		currentSubTask().cancel();		
	}
	
	@Override
	public void join() throws InterruptedException {
		// TODO i am not sure this method does work!!!
		super.join();
	}
	
	
	
	

}
