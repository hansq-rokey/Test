package JsoupDemo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DBUtil;

import entity.StaffRegion;

public class saveStaffRegion {
	
	public static void main(String[] args) {
		List<StaffRegion> list0 = getList();//从表中查询所有的0
		for(StaffRegion s0 : list0){
			List<StaffRegion> list1 = getByInt(s0.getId());//从表中查询所有的1
			save(s0);//主表中插入0
			for(StaffRegion s1 : list1){
				List<StaffRegion> list2 = getByInt(s1.getId());
				StaffRegion ss0 = getByName(s0.getName(),"");//主表中查找0
				update(ss0, s0.getId());//从表中更新1
				StaffRegion s11 = getByCode1(s1.getCode());//从表中查询更新之后的数据
				save(s11);//主表中插入1
				for(StaffRegion s2 : list2){
					List<StaffRegion> list3 = getByInt(s2.getId());
					StaffRegion ss1 = getByCode(s1.getCode());
					update(ss1, s1.getId());
					StaffRegion s22 = getByCode1(s2.getCode());
					save(s22);
					for(StaffRegion s3 : list3){
						List<StaffRegion> list4 = getByInt(s3.getId());
						StaffRegion ss2 = getByCode(s2.getCode());
						update(ss2, s2.getId());
						StaffRegion s33 = getByCode1(s3.getCode());
						save(s33);
						for(StaffRegion s4 : list4){
							StaffRegion ss3 = getByCode(s3.getCode());
							update(ss3,s3.getId());
							StaffRegion s44 = getByCode1(s4.getCode());
							save(s44);
						}
					}
				}
			}
		}
	}
	
	public static List<StaffRegion> getList(){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "select * from tbl_staff_region_hhhh where parent = 0";
//		String sql = "select * from t1 where parent = 0";
		System.out.println(sql);
		List<StaffRegion> lists = new ArrayList<StaffRegion>();
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				StaffRegion region = setData(rs);
				lists.add(region);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
		return lists;
	}
	
	public static void save(StaffRegion region){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "insert into tbl_staff_region_new(order_tag,invalid,created_datetime,grade,parent,name,content,code) values(" +
				"0,0,?,"+region.getGrade()+","+region.getParent()+",'"+region.getName()+"','"+region.getContent()+"','"+region.getCode()+"')";
//		String sql = "insert into t2(order_tag,invalid,created_datetime,grade,parent,name,content,code) values(" +
//				"0,0,?,"+region.getGrade()+","+region.getParent()+",'"+region.getName()+"','"+region.getContent()+"','"+region.getCode()+"')";
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
	
	public static StaffRegion getByName(String name,String code){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "select * from tbl_staff_region_new where name = '"+name+"'and code='"+code+"'";
//		String sql = "select * from t2 where name = '"+name+"'and code='"+code+"'";
		System.out.println(sql);
		StaffRegion region = null;
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				region = setData(rs);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
		return region;
	}
	
	public static StaffRegion getByCode(String code){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "select * from tbl_staff_region_new where code='"+code+"'";
//		String sql = "select * from t2 where code='"+code+"'";
		System.out.println(sql);
		StaffRegion region = null;
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				region = setData(rs);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
		return region;
	}
	public static StaffRegion getByCode1(String code){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "select * from tbl_staff_region_hhhh where code='"+code+"'";
//		String sql = "select * from t1 where code='"+code+"'";
		System.out.println(sql);
		StaffRegion region = null;
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				region = setData(rs);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
		return region;
	}
	/**
	 * 从表中查数据
	 * @param intValue
	 * @return
	 */
	public static List<StaffRegion> getByInt(int intValue){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "select * from tbl_staff_region_hhhh where parent = "+intValue+"";
//		String sql = "select * from t1 where parent = "+intValue+"";
		System.out.println(sql);
		List<StaffRegion> lists = new ArrayList<StaffRegion>();
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				StaffRegion region = setData(rs);
				lists.add(region);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
		return lists;
	}
	
	/**
	 * 更新从表
	 * @param region
	 */
	public static void update(StaffRegion region,int id){
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update tbl_staff_region_hhhh set parent = "+region.getId()+" where parent = "+id+"";
//		String sql = "update t1 set parent = "+region.getId()+" where parent = "+id+"";
		System.out.println(sql);
		try {
			conn = DBUtil.getConnect();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(conn, ps);
		}
	}
	
	public static StaffRegion setData(ResultSet rs){
		StaffRegion region = new StaffRegion();
		try {
			region.setId(rs.getInt("id"));
			region.setOrderTag(rs.getInt("order_tag"));
			region.setInvalid(rs.getInt("invalid"));
			region.setCreatedDatetime(rs.getTimestamp("created_datetime"));
			region.setGrade(rs.getInt("grade"));
			region.setParent(rs.getInt("parent"));
			region.setName(rs.getString("name"));
			region.setContent(rs.getString("content"));
			region.setCode(rs.getString("code"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return region;
	}
}
