package hitk;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class semParser{
	public String[][] result = new String[50][6];
	private static String[][] i_result = new String[50][6];
	private static int r1 = 0;
	public int c=0,r=0;
	public boolean exception = false;
	public void showResult(String roll,int sm,String yer){
		choiceActivity cact = new choiceActivity();
		try{
			for(int i=0;i<50;i++){
				for(int j=0;j<6;j++){
					result[i][j] = "";
				}
			}
			String link = "";
			String data = "";
			System.out.println("Roll : "+roll+" Sem : "+sm+" Year "+yer);
			int year = Integer.parseInt(yer)-2000;
			if((Integer.parseInt(yer)==2019 && (sm%2==1)) || Integer.parseInt(yer)>2019){
				if(sm%2==0){
					link = "http://136.232.2.202:8084/hresult"+year+".aspx";
				}else{
					int year1 = year+1;
					link = "http://136.232.2.202:8084/hresult"+year1+"o.aspx";
				}
			}else{
				if(sm%2==0){
					link = "http://136.232.2.202:8084/heresult"+year+".aspx";
				}else{
					int year1 = year+1;
					link = "http://136.232.2.202:8084/heresult"+year1+"o.aspx";
				}
			}
			HttpClientBuilder clientBuilder = HttpClients.custom();
			clientBuilder = clientBuilder.setUserAgent("Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0");
			CloseableHttpClient httpclient = clientBuilder.build();
			try{
				RequestBuilder requestBuilder = RequestBuilder.post();
				requestBuilder = requestBuilder.setUri(link);
				requestBuilder = requestBuilder.addParameter("roll",roll).addParameter("sem",sm+"");
				HttpUriRequest httppost = requestBuilder.build();
				HttpResponse response = httpclient.execute(httppost);
				data = EntityUtils.toString(response.getEntity());
				/*File file = new File("12616001001_"+sm+".html");
				if(!file.exists()){
					System.out.println("File does not exist");
				}
				Scanner sc = new Scanner(file);
				String data = "";
				while(sc.hasNextLine()){
					data += sc.nextLine()+"\n";	
				}*/
			}finally{
				httpclient.close();
			}
			Document htmlSnippet = Jsoup.parseBodyFragment(data);
			final Elements myElem=htmlSnippet.select("table>tbody>tr>td");
			//System.out.println(htmlSnippet.select("table>tbody>tr>td>span#lblname").text());
			//System.out.println(htmlSnippet.select("table>tbody>tr>td>span#lblroll").text());
			//System.out.println(htmlSnippet.select("table>tbody>tr>td>span#lblrg").text());
			r = 0;c = 0;
			if((Integer.parseInt(yer)==2019 && (sm%2==1)) || Integer.parseInt(yer)>2019){
				for(Element i:htmlSnippet.select("table>tbody>tr>td>table>tbody>tr>td>table>tbody>tr>td")){
					try{
						result[r][c] = i.text()+"";
					}catch(Exception e){
						System.out.println("r : "+r+" c : "+c);
					}
					if(c == 5){
						r++;
						c = -1;
					}
					c++;
				}
			}else{
				for(Element i:htmlSnippet.select("table>tbody>tr>td>table>tbody>tr>td")){
					try{
						result[r][c] = i.text()+"";
					}catch(Exception e){
						System.out.println("r : "+r+" c : "+c);
					}
					if(c == 5){
						r++;
						c = -1;
					}
					c++;
				}
			}
			for(int i=0;i<r;i++){
				//System.out.print(i+"th row\t");
				for(int j=0;j<6;j++){
					//System.out.print(result[i][j]+"\t");
				}
				//System.out.println("\n");
			}
			//System.out.println("Credit : "+result[r-1][4]);
			//System.out.println("Credit points : "+result[r-1][5]);
			//System.out.println(htmlSnippet.select("table>tbody>tr>td>span#lblbottom1").text());
			i_result = result;
			r1 = r;
		}catch(Exception e){
			exception = true;
			//e.printStackTrace();
		}
	}public static String[][] setIntent(){
		return i_result;
	}public static int expNum(){
		return r1;
	}
}
