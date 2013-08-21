package hzk.util.hash;

public class TEST_DATA {

	public static String[] params = new String[20], answers = new String[20];

	static {
		params[1] = "e:/tmp/notice.txt";
		answers[1] = "2EC85F8AA6C99B044B387EA5E4FBA589772C85E6";
		params[2] = "E:\\tmp\\anthem-russia.mp3"; // 6m
		answers[2] = "7320C9DE8FF28F802EF7083DC99357EE30BD493F";
		params[3] = "E:\\tmp\\tgwtdt2011.mkv"; // 2.7g
		answers[3] = "BBF62F836DE67EC0741C1F984315EDF41C7437FF";
		params[4] = "E:/Videos/[跨国银行].The.International.2009.720p.LSM-SCG.rmvb";
		answers[4] = "62AD8E24EC08019BD3E09BA1BA3521B92FCDE132";
		params[5] = "E:/Videos/[狗舍].Doghouse.2009.BD-RMVB-人人调整双语字幕-爱你一生.rmvb";
		answers[5] = "53E06B1F11F0CD1B7085D496B35C78CF47BA81FA";
		params[6] = "E:\\软件安装包\\AdobeAudition3.exe";
		answers[6] = "7B2580E1686800F17E0D4B48FBCEE6E63B9AC94A";
/**
 * @see http://en.wikipedia.org/wiki/SHA-1
 */
		params[8] = "The quick brown fox jumps over the lazy dog";
		answers[8] = "2fd4e1c67a2d28fced849ee1bb76e7391b93eb12";
		params[9] = "";
		answers[9] = "da39a3ee5e6b4b0d3255bfef95601890afd80709";

	}
}
