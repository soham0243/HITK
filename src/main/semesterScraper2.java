import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.http.util.EntityUtils;

public class semesterScraper2{
	public static void main(String[] args) throws Exception{
		final String roll = "12617003120";
		final String sem = "1";
		String[][] result = new String[20][6];
		HttpClientBuilder clientBuilder = HttpClients.custom();
		clientBuilder = clientBuilder.setUserAgent("Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0");
		CloseableHttpClient httpclient = clientBuilder.build();
		try{
			RequestBuilder requestBuilder = RequestBuilder.post();
			requestBuilder = requestBuilder.setUri("http://136.232.2.202:8084/heresult18o.aspx");
			requestBuilder = requestBuilder.addParameter("roll",roll).addParameter("sem",sem);
			HttpUriRequest httppost = requestBuilder.build();
			HttpResponse response = httpclient.execute(httppost);
			String data = EntityUtils.toString(response.getEntity());
			Document htmlSnippet = Jsoup.parseBodyFragment(data);
			final Elements myElem=htmlSnippet.select("table>tbody>tr>td");
			System.out.println(htmlSnippet.select("table>tbody>tr>td>span#lblname").text());
			System.out.println(htmlSnippet.select("table>tbody>tr>td>span#lblroll").text());
			System.out.println(htmlSnippet.select("table>tbody>tr>td>span#lblrg").text());
			int c=0,r=0;
			for(Element i:htmlSnippet.select("table>tbody>tr>td>table>tbody>tr>td")){
				result[r][c] = i.text()+"";
				if(c == 5){
					r++;
					c = -1;
				}
				c++;
			}
			for(int i=0;i<r;i++){
				System.out.print(i+"th row\t");
				for(int j=0;j<6;j++){
					System.out.print(result[i][j]+"\t");
				}
				System.out.println("\n");
			}
			System.out.println(htmlSnippet.select("table>tbody>tr>td>span#lblbottom1").text());
		}finally{
			httpclient.close();
		}
	}
}
