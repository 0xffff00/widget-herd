package hzk.util.hash;

public interface ProgressObservable{
	public void notifyObservers(ProgressEvent e);
	public void addObserver(ProgressObserver o);
	public void deleteObserver(ProgressObserver o);
	public void deleteObservers();
	
	
}
