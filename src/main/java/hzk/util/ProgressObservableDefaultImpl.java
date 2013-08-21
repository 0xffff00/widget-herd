package hzk.util;

import java.util.Collection;
import java.util.HashSet;
/**
 * 进度可观察接口的一个简单实现
 * @author HZK
 *
 */
public class ProgressObservableDefaultImpl implements ProgressObservable {
	private Collection<ProgressObserver> observers;
	public ProgressObservableDefaultImpl(){
		observers=new HashSet<ProgressObserver>();
	}
	
	@Override
	public void notifyObservers(ProgressEvent e) {
		for (ProgressObserver o:observers){
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
}
