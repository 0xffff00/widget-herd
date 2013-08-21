package hzk.herd.bean;

public class RatingForGame extends Rating {
	private double playableScore;
	private double visualScore;
	public double getCalculatedScore(){
		setScore((playableScore+visualScore*3)/4);
		return getScore();		
	}
}
