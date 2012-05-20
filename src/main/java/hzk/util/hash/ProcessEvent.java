package hzk.util.hash;

import java.util.Calendar;

public class ProcessEvent {
	private int newValue,maximum;
	private boolean isCompleted;
	private String result;
	private Calendar beginTime;
	private Calendar occurredTime;	
	private String status;
	
	public Calendar getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Calendar beginTime) {
		this.beginTime = beginTime;
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
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
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
		
	public String getTimeElapsed(){
		long milli=occurredTime.getTimeInMillis()-beginTime.getTimeInMillis();
		return String.valueOf(milli/1000.0)+"sec";
		
		
	}
	
	
}
