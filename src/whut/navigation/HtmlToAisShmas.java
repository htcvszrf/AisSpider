package whut.navigation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlToAisShmas {
	public static void htmlToAis(String iMOString, int maxTryCount) {
		if(iMOString.equals(null)||iMOString.equals("")) {
			System.out.println("imo number is null");
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int tryCount = 0;
		while(tryCount < maxTryCount) {
	    	try {
    			String htmlBody = GetHtmlByUrlShmsa.getHtmlByUrl(iMOString);
    			
    			if(htmlBody != null&&!"".equals(htmlBody)) {
    				Document document = Jsoup.parse(htmlBody);
    				Element element = document.getElementById("div_Data");
    				Elements trs = element.select("tr");
    				Elements tds = trs.get(1).select("td");
    				for(int i = 0; i < tds.size(); i++) {
    					String value = tds.get(i).toString();
    					value = value.substring(4, value.length() - 11);
    					System.out.println(value);
    				}
    			}
    			break;
			} catch (Exception e) {
				tryCount++;
				e.printStackTrace();
			}
		}
	}
}
