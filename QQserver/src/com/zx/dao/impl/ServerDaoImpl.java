package com.zx.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.xd.bean.LoginVo;
import com.xd.bean.UserInfoVo;
import com.zx.dao.ado.ServerDaoAdo;
import com.zx.utils.JDBCUtil;
/**
 * 服务器数据层的实现类，实现ServerDaoAdo接口中的方法
 * @author zx
 *
 */
public class ServerDaoImpl implements ServerDaoAdo {
	private Connection conn;

	@Override
	public String getPhotoId(String qq) {
		String photoid = null;
		conn = JDBCUtil.getConnection();
		try {
			PreparedStatement stmt=conn.prepareStatement("select * from userinfo where qq = ?");//用于执行静态 SQL 语句并返回它所生成结果的对象。
			stmt.setString(1, qq);
			ResultSet i = stmt.executeQuery();
			if(i.next()){
				photoid = i.getString("photoid");
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return photoid;
	}

	@Override
	public List<Object> login(UserInfoVo uv, LoginVo lv) {
		UserInfoVo userInfo = null;
		List<Object> list = new ArrayList<Object>();
		conn = JDBCUtil.getConnection();
		try {
			PreparedStatement stmt=conn.prepareStatement("select * from userinfo where qq = ?");//用于执行静态 SQL 语句并返回它所生成结果的对象。
			stmt.setString(1,uv.getQq());
			ResultSet j = stmt.executeQuery();
			if(!j.next()){
				list.add(0);
				conn.close();//关闭与数据库的连接
				return list;
			}
			PreparedStatement stmt1=conn.prepareStatement("select * from userinfo where qq = ? and pwd=?");
			stmt1.setString(1, uv.getQq());
			stmt1.setString(2, uv.getPwd());
			ResultSet i = stmt1.executeQuery();
			if(i.next()){
				userInfo = new UserInfoVo();
				userInfo.setQq(i.getString("qq"));
				userInfo.setPwd(i.getString("pwd"));
				userInfo.setSign(i.getString("sign"));
				userInfo.setPhotoID(i.getString("photoid"));
				userInfo.setNickname(i.getString("nickname"));
				userInfo.setSex(i.getString("sex"));
				userInfo.setBirthday(i.getString("birthday"));
				userInfo.setConstellation(i.getString("constellation"));
				userInfo.setBloodType(i.getString("bloodtype"));
				userInfo.setDilloma(i.getString("diploma"));
				userInfo.setTelephone(i.getString("telephone"));
				userInfo.setEmail(i.getString("email"));
				userInfo.setMbg(i.getString("mbg"));
				userInfo.setBg(i.getString("bg"));
			}else if(!i.next()){
				list.add(1);
				conn.close();//关闭与数据库的连接
				return list;
			}
			ResultSet z = stmt.executeQuery("select * from login where lqq = '"+lv.getLqq()+"'");
			if(z.next()){
				String status = z.getString("lstatus");
				if(status.equals("yes")){
					list.add(2);
					conn.close();//关闭与数据库的连接
					return list;
				}else{
					stmt.executeUpdate("update login set lip ='"+lv.getLip()+"' , lport='"+lv.getLport()+"' , ldate='"+lv.getLdate()+"' , lstatus='"+lv.getLstatus()+"' where lqq = '"+lv.getLqq()+"'");
					stmt.executeUpdate("update friends set fstatus='yes' where fqq ='"+uv.getQq()+"'");
					list.add(3);
					list.add(userInfo);
					conn.close();//关闭与数据库的连接
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return list;
	}

	@Override
	public List<Object> getSubGroup(String qq) {
		List<Object> list = new ArrayList<Object>();
		conn = JDBCUtil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet i = stmt.executeQuery("select * from subgroup where qq='"+qq+"'");
			while(i.next()){
				String groupId = i.getString("sno");
				String groupName = i.getString("sname");
				list.add(groupName);
				list.add(getFriends(groupId,qq));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private List<Object> getFriends(String groupId,String qq){
		List<Object> list = new ArrayList<Object>();
		List<Object> l1 = new ArrayList<Object>();
		//l1.add("yes");
		List<Object> l2 = new ArrayList<Object>();
		//l2.add("no");
		conn = JDBCUtil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet i = stmt.executeQuery("select fqq,fstatus from friends where fsno='"+groupId+"' and qq='"+qq+"'");
			while(i.next()){
				String fqq = i.getString("fqq");
				String status = i.getString("fstatus");
				if(status.equals("yes")){
					l1.add(fqq);
				}
				if(status.equals("no")){
					l2.add(fqq);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for(int i=0;i<l1.size();i++){
			list.add(l1.get(i));
		}
		for(int i=0;i<l2.size();i++){
			list.add(l2.get(i));
		}
		return list;
	}

	@Override
	public UserInfoVo getFriendInfo(String qq) {
		UserInfoVo userInfo = new UserInfoVo();
		conn = JDBCUtil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet i = stmt.executeQuery("select * from userinfo where qq = '"+qq+"'");
			if(i.next()){
				userInfo.setQq(i.getString("qq"));
				userInfo.setPwd(i.getString("pwd"));
				userInfo.setSign(i.getString("sign")+"");
				userInfo.setPhotoID(i.getString("photoid"));
				userInfo.setNickname(i.getString("nickname"));
				userInfo.setSex(i.getString("sex"));
				userInfo.setBirthday(i.getString("birthday"));
				userInfo.setConstellation(i.getString("constellation"));
				userInfo.setBloodType(i.getString("bloodtype"));
				userInfo.setDilloma(i.getString("diploma"));
				userInfo.setTelephone(i.getString("telephone"));
				userInfo.setEmail(i.getString("email"));
				userInfo.setMbg(i.getString("mbg"));
				userInfo.setBg(i.getString("bg"));
			} 
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userInfo;
	}

	@Override
	public boolean offline(String qq) {
		boolean flag = true;
		conn = JDBCUtil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			int i =stmt.executeUpdate("update login set lstatus='no' where lqq ='"+qq+"'");
			if(i==0){
				flag = false;
			}
			int j =stmt.executeUpdate("update friends set fstatus='no' where fqq ='"+qq+"'");
			if(j==0){
				flag = false;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public boolean setBg(int id, String qq) {
		boolean flag = false;
		conn = JDBCUtil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet i = stmt.executeQuery("select bg from userinfo where qq='"+qq+"'");
			if(i.next()){
				int j = stmt.executeUpdate("update userinfo set bg='"+id+""+"' where qq='"+qq+"'");
				if(j>0){
					flag = true;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	@Override
	public List<Object> groupTable(String qq) {
		List<Object> list = new ArrayList<Object>();
		list.add("我的QQ群(狐朋狗友)");
		List<Object> lt = new ArrayList<Object>();
		Connection conn = JDBCUtil.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet i = stmt.executeQuery("select gname from grouptable,user_group where qq='"+qq+"' and user_group.gno=grouptable.gno");
			while(i.next()){
				lt.add(i.getString("gname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		list.add(lt);
		return list;
	}


}
