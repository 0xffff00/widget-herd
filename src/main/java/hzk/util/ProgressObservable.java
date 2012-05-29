package hzk.util;

/**
 * 进度可被观察的
 * 
 * @author HZK
 * 
 */
public interface ProgressObservable {
	public void notifyObservers(ProgressEvent e);

	public void addObserver(ProgressObserver o);

	public void deleteObserver(ProgressObserver o);

	public void deleteObservers();

}
