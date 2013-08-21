package hzk.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 表示子任务之间的逻辑串联
 * @author HZK
 * @version 0.3
 */
public class TaskSequence extends Task {
	private List<Task> tasks;
	private Task current;
	private int pos;

	public TaskSequence(Task... subTasks) {
		pos=0;
		tasks=new LinkedList<Task>();
		for (Task t:subTasks){
			if (t!=null){
				tasks.add(t);
			}
		}	
		log.debug(Arrays.toString(tasks.toArray()));
	}

	@Override
	public void run() {
		try {			
			for (Task t:tasks) {					
				if (isCancelled()){
					break;
				}
				current=t;
				t.start();
				t.join();
				pos++;
			}			
		} catch (InterruptedException e) {
			log.error(null,e);
		}

	}

	public Task currentSubTask(){
		return current;
	}
	
	public int currentSubTaskIndex(){
		return pos;
	}

	public int subTasksCount(){
		return tasks.size();
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
