package com.pm.page;

public class PageModule {
	public static String makePaging(String pagename, int totalCnt, int listSize, int pageSize, int cp) {
		// 총 페이지 수
		int totalPage = (totalCnt / listSize) + 1;
		if (totalCnt % listSize == 0) {
			totalPage--;
		}
		int userGroup = cp / pageSize;
		if (cp % pageSize == 0) {
			userGroup--;
		}

		// 페이지 구하기
		StringBuffer sb = new StringBuffer();

		if (userGroup != 0) {
			sb.append("<a href='");
			sb.append(pagename);
			sb.append("?cp=");
			int temp = (userGroup-1)*pageSize+pageSize;
			sb.append(temp);
			sb.append("'>&lt; &lt;</a>");
		}

		for (int i = (userGroup * pageSize + 1); i <= (userGroup * pageSize + pageSize); i++) { // 그룹화페이지*보여줄 페이지 사이즈 +1
																								// = 처음 보여주는 페이지 숫자
			sb.append("&nbsp;&nbsp;<a href='");
			sb.append(pagename);
			sb.append("?cp=");
			sb.append(i);
			sb.append("'>");
			sb.append(i);
			sb.append("</a>&nbsp;&nbsp;");
			if (i == totalPage) {
				break;
			}
		}

		if (((totalPage / pageSize) - (totalPage % pageSize == 0 ? 1 : 0)) != userGroup) {
			sb.append("<a href='");
			sb.append(pagename);
			sb.append("?cp=");
			int temp = (userGroup+1)*pageSize+1;
			sb.append(temp);
			sb.append("'>&gt; &gt;</a>");
		}
		return sb.toString();
	}
}
