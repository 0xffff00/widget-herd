package hzk.util.hash;

import java.util.Collection;
import java.util.HashSet;

public class ProgressObservableImpl implements ProgressObservable {
	private Collection<ProgressObserver> observers;
	public ProgressObservableImpl(){
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
