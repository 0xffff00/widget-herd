package hzk.util.nomin;

public class WikisNominationsConstants {
	
	protected static String[] versions = { "DC", "Extended", "CC", "IMAX", "2D",
			"3D", "limitted", "limited", "unrated" };
	protected static String[] warezSources = { "bd", "bdrip", "d5", "d9", "dd51",
			"bluray", "remux", "R5", "dvd", "dvdscr", "dvdrip", "HDTV",
			"HDTVRip", "*cam", "BD-RMVB*", "DVD-RMVB*" };
	protected static String[] warezStandards = { "a720", "miniSD", "halfCD",
			"iNT", "MNHD", "480p", "720p", "360p", "1080p", "1080i" };
	protected static String[] warezProviders = { "TLF", "CHD", "Silu", "Wiki",
			"YYeTs", "人人影视*", "XTM", "NORM", "frds" };

	
	public static final char DELIMITER = '.';
	public static final char ESCAPE_DELIMIT = '*';
	public static final String RE_DELIMITER = "\\.";
	public static final String RE_DELIMITER_L = "(?<=\\.)";
	public static final String RE_DELIMITER_R = "(?=\\.)";
	public static final String RE_DELIMITER_LJ = "(?<=[\\.\\-~])";
	public static final String RE_DELIMITER_RJ = "(?=[\\.\\-~])";
	public static final String CS_ASIAN = "\u2E80-\u9FFF";
	public static final String CS_LATIN = "\u00C0-\u00FF";
	public static final String CS_ASCII_GRAPH ="\\p{Graph}";
	public static final String CS_ASCII_COMMON = "\u0020-\u007E";
}
