package hzk.util;

import java.util.Calendar;
/**
 * <h1>关于进度发生变化的事件</h1>
 * 主要分以下类型：更新，错误，完成，取消，暂停，继续
 * @author HZK
 *
 */
public class ProgressEvent {
	private char type;
	private int newValue,maximum=100;
	private String result;
	private Calendar beginTime;
	private Calendar occurredTime;	
	private String status;
	
	public static final char UPDATE = 0;
	public static final char ERROR = 4;
	public static final char COMPLETE = 12;
	public static final char CANCEL = 15;
	public static final char PAUSE = 21;
	public static final char RESUME = 23;
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
		
	public boolean isCompleted(){
		return type==COMPLETE;
	}
	
	public boolean isCancelled(){
		return type==CANCEL;
	}
	
	public boolean isErrorOccured(){
		return type==ERROR;
	}
	
	public boolean isPaused(){
		return type==PAUSE;
	}
	
	public boolean isResumed(){
		return type==RESUME;
	}
	
	public boolean isTerminated(){
		return type==ERROR || type==COMPLETE || type==CANCEL;
	}
	
	
	public void setType(char type) {
		this.type = type;
	}
	
	public double getProgressRate(){
		if (maximum>0){
			return newValue/(double)maximum;
		}else{
			return 0;
		}
	}
}
