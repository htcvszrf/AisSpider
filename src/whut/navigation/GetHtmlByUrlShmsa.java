package whut.navigation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class GetHtmlByUrlShmsa {
	public static String htmlBody = null;
//	public String imo;
//	public GetHtmlByUrl(String imo) {
//		this.imo = imo;
//	}
	public static String getHtmlByUrl(String imo) throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
        	URI uri = new URIBuilder()
        				.setScheme("http")
        				.setHost("www.shmsa.gov.cn")
        				.setPath("/SearchResultNoMaster.aspx")
        				.setParameter("type", "5")
        				.setParameter("IMO", imo)
        				.build();
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
            httpGet.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
            httpGet.setHeader("Accept-Charset", "utf-8");
            httpGet.setHeader("Keep-Alive", "300");
            httpGet.setHeader("Connection", "Keep-Alive");
            httpGet.setHeader("Cache-Control", "no-cache");
            
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                System.out.println(response1.getStatusLine());
                HttpEntity entity1 = response1.getEntity();
                // do something useful with the response body
                htmlBody = EntityUtils.toString(entity1, "UTF-8");
                //System.out.println(htmlBody);
                // and ensure it is fully consumed
                EntityUtils.consume(entity1);
                return htmlBody;
            } finally {
                response1.close();
            }
        } finally {
            httpclient.close();
        }
	}
}
