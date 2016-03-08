package com.eastflag.game.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringUtil extends StringUtils {
	
	protected static final Log log = LogFactory.getLog(StringUtil.class);

  public static final String[] EMPTY_ARRAY = new String[0];
  
  public static String lpad(String s, int n) {
    return paddingString(s, n, ' ', true);
  }

  public static String lpad(String s, int n, char c) {
    return paddingString(s, n, c, true);
  }

  public static String rpad(String s, int n) {
    return paddingString(s, n, ' ', false);
  }

  public static String rpad(String s, int n, char c) {
    return paddingString(s, n, c, false);
  }
	
  private static String paddingString(String s, int n, char c, boolean paddingLeft) {
    if (s == null) {
      return s;
    }

    int add = n - s.length();

    if (add <= 0) {
      return s;
    }

    StringBuffer str = new StringBuffer(s);
    char[] ch = new char[add];
    Arrays.fill(ch, c);

    if (paddingLeft)
      str.insert(0, ch);
    else {
      str.append(ch);
    }
    return str.toString();
  }
  
	public static List<String> parseString(String openToken, String closeToken, String content) {
    List listParse = new ArrayList();
    String after = content;
    String temp = "";

    int start = after.indexOf(openToken);
    int end = after.indexOf(closeToken);
    int tmpStart = 0;

    while ((start > -1) && 
      (end > start)) {
      temp = after.substring(start + openToken.length(), end);
      after = after.substring(end, after.length());

      tmpStart = after.indexOf(openToken);
      listParse.add(temp);

      if (tmpStart <= 0) break;
      after = after.substring(tmpStart, after.length());

      start = after.indexOf(openToken);
      end = after.indexOf(closeToken);
    }

    return listParse;
  }
}
