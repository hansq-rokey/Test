package JsoupDemo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.DBUtil;

public class Demo {
	private static String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/";
	private static String host = "www.stats.gov.cn";
	private static String userAgent ="User-Agent";
	private static String value="Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36";
	public static void main(String[] args) throws SQLException, IOException, Exception {
//		System.out.println(getData("东城区","110101000000"));
//		readProvince("/index.html");
//		readCity("/11.html");
//		readQu("/11/1101.html");
//		readCounty("/11/01/110101.html");
//		readVillagetr("/11/01/01/110101001.html");
		
		int index = 15;//省下标
		String u = "41";
		String provinceName = "河南省";
		String codeCity="411700000000";
		String codeCou="411723000000";
		String codeS="";
		boolean flagCity=false;
		boolean flagCou=false;
		boolean flagS=false;
		FileOutputStream fos1 = new FileOutputStream("F:\\省.txt", false);  
        OutputStreamWriter osw1 = new OutputStreamWriter(fos1, "gb2312");  
        FileOutputStream fos2 = new FileOutputStream("F:\\"+provinceName+".txt", false);  
        OutputStreamWriter osw2 = new OutputStreamWriter(fos2, "gb2312");  
//		Elements e1 = readProvince("/index.html",index);
//		for(Element e11 : e1){//省
//			osw1.write(e11.text()+"\r\n");
//		}
//		osw1.close();
		Elements e2 = readCity(u+".html");
		for(Element e22 : e2){//市
			Thread.sleep(2000);
			osw2.write(e22.text()+"\r\n");
			Elements e3 = readQu(e22.getElementsByIndexEquals(0).attr("href"));
			String[] txt2 = e22.text().split(" ");
			
			if(StringUtil.isBlank(codeCity)){
				flagCity=true;
			}else{
				if(codeCity.equals(txt2[0])){
					flagCity=true;
				}
			}
			if(!flagCity){
				continue;
			}
//			if("市辖区".equals(txt2[1])){
			int id1 = getData(provinceName, "");
			int id11 = getData(txt2[1], txt2[0]);
			if(id11>0){
				
			}else{
				saveData(2, id1, txt2[1], "0,1", txt2[0]);
			}
//			}
			for(Element e33 : e3){//区
				Thread.sleep(2000);
				if("市辖区".equals(e33.text().split(" ")[1])){
					continue;
				}
				osw2.write(e33.text()+"\r\n");
//				if(StringUtil.isBlank(e33.getElementsByIndexEquals(0).attr("href"))){
//					String[] txt3 = e33.text().split(" ");
//					int id2 = getData(txt2[1], txt2[0]);
////					saveData(3, id2, txt3[1], "0,1,2", txt3[0]);
//					continue;
//				}
				String[] s = e22.getElementsByIndexEquals(0).attr("href").split("/");
				Elements e4 = readCounty(s[0]+"/"+e33.getElementsByIndexEquals(0).attr("href"));
				String[] txt3 = e33.text().split(" ");
				
				if(StringUtil.isBlank(codeCou)){
					flagCou=true;
				}else{
					if(codeCou.equals(txt3[0])){
						flagCou=true;
					}
				}
				if(!flagCou){
					continue;
				}
				int id2 = getData(txt2[1], txt2[0]);
				int id22 = getData(txt3[1],txt3[0]);
				if(id22>0){
					
				}else{
					saveData(3, id2, txt3[1], "0,1,2", txt3[0]);					
				}
				for(Element e44 : e4){//镇、办事处
					Thread.sleep(2000);
					osw2.write(e44.text()+"\r\n");
					
					String[] ss = e33.getElementsByIndexEquals(0).attr("href").split("/");
					Elements e5 = readVillagetr(u+"/"+ss[0]+"/"+e44.getElementsByIndexEquals(0).attr("href"));
					String[] txt4 = e44.text().split(" ");
					if(StringUtil.isBlank(codeS)){
						flagS=true;
					}else{
						if(codeS.equals(txt4[0])){
							flagS=true;
						}
					}
					if(!flagS){
						continue;
					}
					int id3 = getData(txt3[1], txt3[0]);
					int id33 = getData(txt4[1], txt4[0]);
					if(id33>0){
						
					}else{
						saveData(4, id3, txt4[1], "0,1,2,3", txt4[0]);
					}
					for(Element e55 : e5){//社区
//						Thread.sleep(1000);
						osw2.write(e55.text()+"\r\n");
						String[] txt5 = e55.text().split(" ");
						int id4 = getData(txt4[1], txt4[0]);
						int id44 = getData(txt5[2],txt5[0]);
						if(id44>0){
							continue;
						}else{
							saveData(5, id4, txt5[2], "0,1,2,3,4", txt5[0]);
						}
					}
				}
			}
		}
		osw2.close();
	}
	//省
	public static Elements readProvince(String strUrl,int index) throws IOException{
		String proUrl = url+strUrl;
		Document doc = Jsoup.connect(proUrl).header(userAgent,value).timeout(10000).get();
		Elements links = doc.select("td>a");
		System.out.println("省："+links.size());
		saveData(1, 0, links.get(index).text(),"0", "");
		return links;
	}
	//市
	public static Elements readCity(String strUrl) throws IOException{
		String proUrl = url+strUrl;
		Document doc = Jsoup.connect(proUrl).header(userAgent,value).timeout(10000).get();
		Elements links = doc.getElementsByClass("citytr");
		System.out.println("市："+links.size());
		return links;
	}
	//区
	public static Elements readQu(String strUrl) throws IOException{
		String proUrl = url+strUrl;
		Document doc = Jsoup.connect(proUrl).header(userAgent,value).timeout(10000).get();
		Elements links = doc.getElementsByClass("countytr");
		System.out.println("区："+links.size());
		return links;
	}
	//镇
	public static Elements readCounty(String strUrl) throws IOException{
		String proUrl = url+strUrl;
		Document doc = Jsoup.connect(proUrl).header(userAgent,value).timeout(10000).get();
		Elements links = doc.getElementsByClass("towntr");
		System.out.println("镇："+links.size());
		return links;
	}
	//社区
	public static Elements readVillagetr(String strUrl) throws IOException{
		String proUrl = url+strUrl;
		Document doc = Jsoup.connect(proUrl).header(userAgent,value).timeout(10000).get();
		Elements links = doc.getElementsByClass("villagetr");
		System.out.println("社区："+links.size());
		return links;
	}
	//存数据
	public static void saveData(int grade,int parent,String name,String content,String code){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = 
				"insert into tbl_staff_region_hsq(order_tag,invalid,created_datetime,grade,parent,name,content,code) values(" +
				"0,0,?,"+grade+","+parent+",'"+name+"','"+content+"','"+code+"')";
		System.out.println(sql);
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ps.setDate(1, new Date(System.currentTimeMillis()));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
	}
	//存数据
		public static int getData(String name,String code){
			Connection conn = null;
			PreparedStatement ps = null;
			String sql = "select * from tbl_staff_region_hsq where name='"+name+"' and code='"+code+"'";
			System.out.println(sql);
			int id = 0;;
			try {
				conn = DBUtil.getConnect();
				ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					id=rs.getInt("id");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				DBUtil.close(conn, ps);
			}
			return id;
		}
}
