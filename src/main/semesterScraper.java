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

class semesterScraper{
	public static void main(String[] args) throws Exception{
		System.out.print("Enter autonomy roll no : ");	
		long rolls = (new Scanner(System.in)).nextLong();
		String link = "";
		String roll = rolls+"";
		int year = 10*Integer.parseInt(roll.charAt(3)+"")+Integer.parseInt(roll.charAt(4)+"");
		int[] year1 = {year+1,year+2,year+3,year+4};
		String[] semyear = {year1[0]+"o",year1[0]+"",year1[1]+"o",year1[1]+"",year1[2]+"o",year1[2]+"",year1[3]+"o",year1[3]+""};
		System.out.println(year);
		int cyear = Calendar.getInstance().get(Calendar.YEAR);
		boolean flag = false;
		boolean lateral = false;
		double[] sgpa = new double[8];
		for(int i=1;i<=8;i++){
			int curyear = Integer.parseInt(semyear[i-1].charAt(0)+""+semyear[i-1].charAt(1)+"") + 2000;
			if(curyear <= (cyear-4)){
				System.out.println("You passed out long time ago.");
				break;
			}
			if(curyear > cyear){
				break;
			}
			if(flag){
				//System.out.println(i+"th semester exam is not happened till now!");
				break;
			}
			if(year+2000 > cyear){
				System.out.println("You should not travel against time!");
				break;
			}
			if(lateral){
				boolean flag2 = false;
				System.out.println("Lateral detected!");
				year++;
				int[] year2 = {year+1,year+2,year+3};
				String[] semyear2 = {year2[0]+"o",year2[0]+"",year2[1]+"o",year2[1]+"",year2[2]+"o",year2[2]+""};
				for(int l=3;l<=8;l++){
					if(flag2){
						break;
					}
					link = "http://136.232.2.202:8084/heresult"+semyear2[l-3]+".aspx";
					HttpClientBuilder clientBuilder = HttpClients.custom();
					clientBuilder = clientBuilder.setUserAgent("Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0");
					CloseableHttpClient httpclient = clientBuilder.build();
					try{
						RequestBuilder requestBuilder = RequestBuilder.post();
						requestBuilder = requestBuilder.setUri(link);
						requestBuilder = requestBuilder.addParameter("roll",roll).addParameter("sem",l+"");
						HttpUriRequest httppost = requestBuilder.build();
						HttpResponse response = httpclient.execute(httppost);
						String data = EntityUtils.toString(response.getEntity());
						Document htmlSnippet = Jsoup.parseBodyFragment(data);
						boolean stat = (htmlSnippet.select("body>table>tbody>tr>td").text()+"").equals("No such student exists in this database or the student has not given the particular semester exam");
						if(data.equals("No such student exists in this database") || stat){
							System.out.println("Make sure you have given "+l+"th semester or contact college for more info");
						}
						if((htmlSnippet.select("body>span>H1").text()+"").equals("Server Error in '/' Application.")){
							System.out.println(l+"th semester result is not out yet!");
							flag2 = true;
						}
						if(htmlSnippet.select("body>table>tbody>tr>td>span#lblbottom2")!=null){
							String sgp = htmlSnippet.select("body>table>tbody>tr>td>span#lblbottom2").text()+"";
							if(sgp.equals("")){
								sgp = htmlSnippet.select("body>table>tbody>tr>td>span#lblbottom1").text()+"";
								String sgp1=sgp.charAt(sgp.length()-4)+""+sgp.charAt(sgp.length()-3)+""+sgp.charAt(sgp.length()-2)+""+sgp.charAt(sgp.length()-1)+"";
								if((sgp.charAt(sgp.length()-3)+"").equals(":")||(sgp.charAt(sgp.length()-4)+"").equals(":")){
									sgpa[l-1]=Double.parseDouble(sgp.charAt(sgp.length()-1)+"");
									System.out.println(sgp.charAt(sgp.length()-1)+"");
								}else{
									sgpa[l-1]=Double.parseDouble(sgp1);
									System.out.println(sgp1);
								}
							}else{
								sgp = htmlSnippet.select("body>table>tbody>tr>td>span#lblbottom2").text()+"";
								String sgp1=sgp.charAt(sgp.length()-4)+""+sgp.charAt(sgp.length()-3)+""+sgp.charAt(sgp.length()-2)+""+sgp.charAt(sgp.length()-1)+"";
								if((sgp.charAt(sgp.length()-3)+"").equals(":")||(sgp.charAt(sgp.length()-4)+"").equals(":")){
									sgpa[l-1]=Double.parseDouble(sgp.charAt(sgp.length()-1)+"");
									System.out.println(sgp.charAt(sgp.length()-1)+"");
								}else{
									sgpa[l-1]=Double.parseDouble(sgp1);
									System.out.println(sgp1);
								}
							}
						}
					}catch(Exception e){
			
					}finally{
						httpclient.close();	
					}
				}
				break;
			}else{
				link = "http://136.232.2.202:8084/heresult"+semyear[i-1]+".aspx";
				HttpClientBuilder clientBuilder = HttpClients.custom();
				clientBuilder = clientBuilder.setUserAgent("Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/60.0");
				CloseableHttpClient httpclient = clientBuilder.build();
				try{
					RequestBuilder requestBuilder = RequestBuilder.post();
					requestBuilder = requestBuilder.setUri(link);
					requestBuilder = requestBuilder.addParameter("roll",roll).addParameter("sem",i+"");
					HttpUriRequest httppost = requestBuilder.build();
					HttpResponse response = httpclient.execute(httppost);
					String data = EntityUtils.toString(response.getEntity());
					Document htmlSnippet = Jsoup.parseBodyFragment(data);
					boolean stat = (htmlSnippet.select("body>table>tbody>tr>td").text()+"").equals("No such student exists in this database or the student has not given the particular semester exam");
					if(data.equals("No such student exists in this database") || stat){
						System.out.println("Make sure you have given "+i+"th semester or contact college for more info");
					}
					if((htmlSnippet.select("body>span>H1").text()+"").equals("Server Error in '/' Application.")){
						System.out.println(i+"th semester result is not out yet!");
						flag = true;
					}
					if(htmlSnippet.select("body>table>tbody>tr>td>span#lblbottom2")!=null){
						String sgp = htmlSnippet.select("body>table>tbody>tr>td>span#lblbottom2").text()+"";
						if(sgp.equals("")){
							sgp = htmlSnippet.select("body>table>tbody>tr>td>span#lblbottom1").text()+"";
							String sgp1=sgp.charAt(sgp.length()-4)+""+sgp.charAt(sgp.length()-3)+""+sgp.charAt(sgp.length()-2)+""+sgp.charAt(sgp.length()-1)+"";
							if((sgp.charAt(sgp.length()-3)+"").equals(":")||(sgp.charAt(sgp.length()-4)+"").equals(":")){
								sgpa[i-1]=Double.parseDouble(sgp.charAt(sgp.length()-1)+"");
								System.out.println(sgp.charAt(sgp.length()-1)+"");
							}else{
								sgpa[i-1]=Double.parseDouble(sgp1);
								System.out.println(sgp1);
							}
						}else{
							sgp = htmlSnippet.select("body>table>tbody>tr>td>span#lblbottom2").text()+"";
							String sgp1=sgp.charAt(sgp.length()-4)+""+sgp.charAt(sgp.length()-3)+""+sgp.charAt(sgp.length()-2)+""+sgp.charAt(sgp.length()-1)+"";
							if((sgp.charAt(sgp.length()-3)+"").equals(":")||(sgp.charAt(sgp.length()-4)+"").equals(":")){
								sgpa[i-1]=Double.parseDouble(sgp.charAt(sgp.length()-1)+"");
								System.out.println(sgp.charAt(sgp.length()-1)+"");
							}else{
								sgpa[i-1]=Double.parseDouble(sgp1);
								System.out.println(sgp1);
							}
						}
					}
				}catch(Exception e){
		
				}finally{
					httpclient.close();	
				}
			}
			int[] intsem = new int[8];
			for(int j=0;j<8;j++){
				intsem[j] = (int) sgpa[j];
			}
			if(intsem[0]==0&&intsem[1]==0&&intsem[2]==0&&intsem[3]==0&&intsem[4]==0&&intsem[5]==0&&intsem[6]==0&&intsem[7]==0){
				lateral = true;
				i = 1;
			}
		}
		for(int k=0;k<sgpa.length;k++){
			int j=k+1;
			System.out.println(j+"th semester : "+sgpa[k]);
		}
	}
}
