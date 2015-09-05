package whut.navigation;

import java.io.IOException;

import javax.xml.soap.Text;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UrlToTextUseJsoupConnect {
	public String urlString = null;
	public UrlToTextUseJsoupConnect(String url) {
		urlString = url;
	}
	
	private Text getHtmlText() {
		try {
			Document document = Jsoup.connect(urlString).get();
			String titleString = document.title();
			String textString = document.text();
			System.out.println(titleString);
			System.out.println(textString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[]  args) {
//		String urlString  = "http://www.baidu.com";
//		String urlString  = "http://www.chinaports.com/souchuan/search/1/10/477441700";
		String urlString  = "http://www.chinaports.com/souchuan/view/477441700";
//		String urlString  = "http://www.chinaports.com/souchuan";
		UrlToTextUseJsoupConnect urlToText = new UrlToTextUseJsoupConnect(urlString);
		urlToText.getHtmlText();
	}
}
