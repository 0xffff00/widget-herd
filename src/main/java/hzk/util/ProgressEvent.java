package hzk.util;

import java.util.Calendar;

/**
 * <h1>关于进度发生变化的事件</h1>
 * <p>
 * 主要分以下类型：更新(UPDATE)，错误，完成，取消，暂停，继续，开始<br>
 * 所有事件都是UPDATE事件，这里UPDATE=1的事件只表示非特殊的UPDATE事件<br>
 * </p>
 * 
 * @author HZK
 * 
 */
public class ProgressEvent {
	private char type;
	private int newValue, maximum = 100;
	private String result;
	private long taskRunMillisec;
	private Calendar occurredTime;
	private String status;

	public static final char BEGIN = 0;
	public static final char UPDATE = 1;
	public static final char ERROR = 4;
	public static final char COMPLETE = 12;
	public static final char CANCEL = 14;
	public static final char STOP = 15;
	public static final char PAUSE = 21;
	public static final char RESUME = 23;

	public ProgressEvent() {
		occurredTime = Calendar.getInstance();
	}

	public int getNewValue() {
		return newValue;
	}

	public void setNewValue(int newValue) {
		this.newValue = newValue;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Calendar getOccurredTime() {
		return occurredTime;
	}

	public void setOccurredTime(Calendar occurredTime) {
		this.occurredTime = occurredTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskRunTimeInSec() {
		return String.valueOf(taskRunMillisec / 1000f) + "sec";

	}
	
	public boolean isBegan() {
		return type == BEGIN;
	}
	
	public boolean isCompleted() {
		return type == COMPLETE;
	}

	public boolean isCancelled() {
		return type == CANCEL;
	}

	public boolean isErrorOccured() {
		return type == ERROR;
	}

	public boolean isPaused() {
		return type == PAUSE;
	}

	public boolean isResumed() {
		return type == RESUME;
	}

	public boolean isTerminated() {
		return type == ERROR || type == COMPLETE || type == CANCEL
				|| type == STOP;
	}

	public void setType(char type) {
		this.type = type;
	}

	public double getProgressRate() {
		if (maximum > 0) {
			return newValue / (double) maximum;
		} else {
			return 0;
		}
	}

	public long getTaskRunMillisec() {
		return taskRunMillisec;
	}

	public void setTaskRunMillisec(long taskRunMillisec) {
		this.taskRunMillisec = taskRunMillisec;
	}
}
