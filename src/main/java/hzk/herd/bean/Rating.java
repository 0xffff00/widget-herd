package hzk.herd.bean;

public abstract class Rating {
	private static double minScore;
	private static double maxScore;
	private double score;
	private Object author;
	
	public static double getMinScore() {
		return minScore;
	}
	public static void setMinScore(double minScore) {
		Rating.minScore = minScore;
	}
	public static double getMaxScore() {
		return maxScore;
	}
	public static void setMaxScore(double maxScore) {
		Rating.maxScore = maxScore;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public Object getAuthor() {
		return author;
	}
	public void setAuthor(Object author) {
		this.author = author;
	}
	
	
}
