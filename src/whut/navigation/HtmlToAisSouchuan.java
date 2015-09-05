package whut.navigation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.omg.CORBA.PUBLIC_MEMBER;
/**
 * Get the AIS information from the html text, the html is from www.souchuan.com
 * @author weichen
 *
 */
public class HtmlToAisSouchuan {
	
	public static String htmlToAis(String mmsiString, int maxTryCount) {
		StringBuffer reStringBuffer = new StringBuffer();
		
		if(mmsiString.equals(null)||mmsiString.equals("")) {
			System.out.println("MMSI number is null");
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int tryCount = 0;// count retry time
		while(tryCount < maxTryCount) {
	    	try {
	    		reStringBuffer.delete(0, reStringBuffer.length());
    			String htmlBody = GetHtmlByUrlSouchuan.getHtmlByUrl(mmsiString);
    			//System.out.println(htmlBody);
    			if(htmlBody != null&&!"".equals(htmlBody)) {
    				Document document = Jsoup.parse(htmlBody);
    				//Get the basic information about the ship
    				Elements elements = document.getElementsByAttributeValue("class", "dw-r");
//    				Elements elements = document.getElementsByClass("dw-r");
    				Elements elements2 = elements.get(0).getElementsByClass("longtd");
    				
    				for (int i = 0; i < elements2.size(); i++) {
    					String item = elements2.get(i).text();
    					//System.out.println(item);
    					int beginIndex = item.indexOf("ฃบ");
    					if(beginIndex == -1) beginIndex = item.indexOf(":");
    					item = item.substring(beginIndex + 1, item.length());
    					reStringBuffer.append(item + ",");
						//System.out.println(item);
					}
    				//get the static information about the ship
    				Elements elements00 = document.getElementsByAttributeValue("class", "box-nav box-nav3");
    				Elements elements11 = elements00.get(0).select("td");
    				for (Element element99 : elements11) {
    					//System.out.println(element99.text());
    					String item = element99.text();
    					int beginIndex = item.indexOf("ฃบ");
    					int lastIndex = item.length();
    					if(item.contains("ถึ") && item.lastIndexOf("ถึ") > beginIndex) {
    						lastIndex = item.lastIndexOf("ถึ");
    					} else if(item.contains("00:00:00.0")) {
    						lastIndex = item.lastIndexOf("00:00:00.0");
    					}
    					
    					item = item.substring(beginIndex + 1, lastIndex);
    					//System.out.println(item);
    					reStringBuffer.append(item + " ,");
					}
    				
    			}
    			return reStringBuffer.toString();
			} catch (Exception e) {
				tryCount++;
				e.printStackTrace();
			}
		}
		return reStringBuffer.toString();
	}
	public static void main(String[] args) {
		String infoString = HtmlToAisSouchuan.htmlToAis("636091668", 1);
		System.out.println(infoString);
		String[] in = infoString.split(",");
		for (String string : in) {
			System.out.println(string);
		}
				
	}
}
