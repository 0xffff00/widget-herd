package hzk.util;

/**
 * 表示若干个子任务的逻辑并联
 * 
 * @author HZK
 * @version 0.1
 */
public class TaskConcurrency extends Task {
	private Task[] tasks;

	public TaskConcurrency(Task... subTasks) {
		tasks = subTasks;
	}

	@Override
	public void run() {
		int i;
		for (i = 0; i < tasks.length; i++) {
			tasks[i].start();
		}
		try {
			for (i = 0; i < tasks.length; i++) {
				tasks[i].join();
			}
		} catch (InterruptedException e) {
			log.error(null, e);
		}

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	@Override
	public void cancel() {
		int i;
		for (i = 0; i < tasks.length; i++) {
			tasks[i].cancel();
		}
		super.cancel();
	}
	
	public void cancel(int index){
		tasks[index].cancel();
	}
	
	

	@Override
	public void pause() {
		int i;
		for (i = 0; i < tasks.length; i++) {
			tasks[i].pause();
		}
		super.cancel();
	}
	
	public void pause(int index){
		tasks[index].pause();
	}
	
	@Override
	public void resume() {
		int i;
		for (i = 0; i < tasks.length; i++) {
			tasks[i].resume();
		}
		super.cancel();
	}
	public void resume(int index){
		tasks[index].resume();
	}

}
