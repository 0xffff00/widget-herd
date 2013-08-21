package hzk.util.nomin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WikisNominations extends WikisNominationsConstants {
	private static Log log = LogFactory.getLog(WikisNominations.class);
	private static String re_version, re_year, re_years;
	private static String re_warezSource, re_warezStandard, re_warezProvider;

	private static String buildRegExp(String[] keys, String REdlmtrL,
			String REdlmtrR) {

		StringBuilder sb = new StringBuilder();
		for (String src : keys) {
			if (src.charAt(0) != ESCAPE_DELIMIT)
				sb.append(REdlmtrL).append(src);
			else
				sb.append(src.substring(1));
			if (src.charAt(src.length() - 1) != ESCAPE_DELIMIT)
				sb.append(REdlmtrR);
			else
				sb.deleteCharAt(sb.length() - 1);
			sb.append('|');

		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();

	}

	private static void loadReExps() {
		if (re_year == null)
			re_year = RE_DELIMITER_L + "\\d{4}" + RE_DELIMITER_R;
		if (re_years == null)
			re_years = RE_DELIMITER_L + "(\\d{4})(?:[\\-~])(\\d{4})"
					+ RE_DELIMITER_R;
		if (re_version == null)
			re_version = buildRegExp(versions, RE_DELIMITER_L, RE_DELIMITER_R);
		if (re_warezSource == null)
			re_warezSource = buildRegExp(warezSources, RE_DELIMITER_L,
					RE_DELIMITER_RJ);
		if (re_warezStandard == null)
			re_warezStandard = buildRegExp(warezStandards, RE_DELIMITER_LJ,
					RE_DELIMITER_RJ);
		if (re_warezProvider == null)
			re_warezProvider = buildRegExp(warezProviders, RE_DELIMITER_LJ,
					RE_DELIMITER_RJ);
	}

	public static WikisNomination parse(String arg) {
		WikisNomination wn = new WikisNomination();
		boolean nothingFound = true;
		Matcher m;
		String names, others; // 用于缩小查询范围
		names = others = arg;
		loadReExps();

		/* find YEAR */
		m = Pattern.compile(re_year).matcher(arg);
		if (m.find()) {
			wn.year1 = m.group();
			names = arg.substring(0, m.start());
			others = arg.substring(m.end());
			nothingFound = false;
		}

		m = Pattern.compile(re_years).matcher(arg);
		if (m.find()) { // if表示匹配第一个结果
			wn.year1 = m.group(1);
			wn.year2 = m.group(2);
			names = arg.substring(0, m.start(1));
			others = arg.substring(m.end(2));
			nothingFound = false;
		}

		if (nothingFound) {
			log.warn("YEAR not found: " + arg);
		}
		/* find VERSION */
		m = Pattern.compile(re_version, Pattern.CASE_INSENSITIVE).matcher(
				others);
		if (m.find()) {
			wn.version = m.group();
		}

		/* find warezSource, */
		m = Pattern.compile(re_warezSource, Pattern.CASE_INSENSITIVE).matcher(
				others);
		while (m.find()) { // while表示匹配最后一个结果
			wn.warezSource = m.group();
			if (nothingFound) {
				names = arg.substring(0, m.start());
				others = arg.substring(m.end());
				nothingFound = false;
			}

		}

		/* find warezStandard, */
		m = Pattern.compile(re_warezStandard, Pattern.CASE_INSENSITIVE)
				.matcher(others);
		while (m.find()) {
			wn.warezStandard = m.group();
			if (nothingFound) {
				names = arg.substring(0, m.start());
				others = arg.substring(m.end());
				nothingFound = false;
			}
		}

		/* find warezProvider */
		m = Pattern.compile(re_warezProvider, Pattern.CASE_INSENSITIVE)
				.matcher(others);
		if (m.find()) {
			wn.warezProvider = m.group();
		}

		String[] words = names.split(RE_DELIMITER);
		/*
		 * find CHI NAME in String 'names' 找出最前面和最后面的含汉字的word，然后连接中间所有的word
		 */
		int i, min = 100, max = 0;
		for (i = 0; i < words.length; i++) {
			if (Pattern.compile("[" + CS_ASIAN + "]").matcher(words[i]).find()) {
				if (i < min)
					min = i;
				max = i;
			}
		}
		StringBuilder sb = new StringBuilder();
		for (i = min; i <= max; i++)
			sb.append(words[i]).append(' ');
		wn.nameChi = sb.toString();

		/* find ENG NAME */
		sb = new StringBuilder();
		for (i = max + 1; i < words.length; i++)
			sb.append(words[i]).append(' ');
		wn.nameEng = sb.toString();
		return wn;

	}

}
