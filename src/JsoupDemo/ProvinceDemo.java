package JsoupDemo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.DBUtil;

public class ProvinceDemo {
	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String sql = "insert into tbl_staff_region_new(order_tag,invalid,created_datetime,grade,parent,name,content,code) values(?,?,?,?,?,?,?,?)";
			Document doc = Jsoup.connect("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2015/index.html").get();
			Elements links = doc.select("td>a");
			System.out.println(links.size());
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			for(Element e : links){
				String linkText = e.text();
				System.out.println(linkText);
				ps.setInt(1, 0);
				ps.setInt(2, 0);
				ps.setDate(3, new Date(System.currentTimeMillis()));
				ps.setInt(4, 1);
				ps.setInt(5, 0);
				ps.setString(6, linkText);
				ps.setString(7, "0");
				ps.setString(8, null);
//				ps.execute();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
