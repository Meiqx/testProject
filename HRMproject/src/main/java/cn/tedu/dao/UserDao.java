package cn.tedu.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.tedu.entity.Users;
import cn.tedu.utils.DBUtils;
import cn.tedu.utils.DateUtils;

//用户管理
public class UserDao {

	//验证用户登录
	public Boolean login(String username, String password) {
		//获取连接,判断用户输入的用户名和密码是否验证通过
		try (Connection conn = DBUtils.getConn();) {
			String sql = "select count(*) from user where username=? and password=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int count=rs.getInt(1);
				if(count>0) {
					return true;
				}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//录入用户表单09.28
	public void save(Users user) {
		//获取连接
		try (Connection conn = DBUtils.getConn();) {
			String sql = "insert into user values(?,?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,0);
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setInt(4, user.getSex());
			ps.setDate(5, DateUtils.changeDate(user.getBirthday()));
			ps.setDate(6, DateUtils.changeDate(user.getCreatetime()));
			ps.setInt(7, user.getIsadmin());
			ps.setString(8, user.getContent());
			ps.executeUpdate();
			System.out.println("数据录入成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//查看人员信息09.28
	public List<Users> findAll() {
		List<Users> list = new ArrayList<>();
		//获取连接
		try (Connection conn = DBUtils.getConn();) {
			String sql = "select id,username,password,sex,birthday,createtime,isadmin,content from user order by createtime";
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				int sex = rs.getInt(4);
				Date birthday = rs.getDate(5);
				Date createtime = rs.getDate(6);
				int isadmin = rs.getInt(7);
				String content = rs.getString(8);
				Users user = new Users(id,username,password,sex,DateUtils.changeToUtilDate(birthday),
						DateUtils.changeToUtilDate(createtime),isadmin,content);
				
				list.add(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//09.29对用户信息进行编辑的方法，根据点击事件所处用户的id找到该用户所有其他信息返回给EditServlet并通过Thymeleaf重改editUser.html页面某些数据
	public Users findUserById(int id) {
		//获取连接
		try (Connection conn = DBUtils.getConn();) {
			String sql = "select id,username,password,sex,birthday,createtime,isadmin,content from user where id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id2 = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				int sex = rs.getInt(4);
				Date birthday = rs.getDate(5);//sql的date数据，实例化的时候要转换成util
				Date createtime = rs.getDate(6);
				int isadmin = rs.getInt(7);
				String content = rs.getString(8);
				Users user = new Users(id2,username,password,sex,DateUtils.changeToUtilDate(birthday),
						DateUtils.changeToUtilDate(createtime),isadmin,content);
				return user;	

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


	//09.30更新用户信息
	public void updateUser(Users user) {
		//获取连接
		try (Connection conn = DBUtils.getConn();) {
			String sql = "update user set username=?,password=?,sex=?,birthday=?,isadmin=?,content=? where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3,user.getSex());
			ps.setDate(4, DateUtils.changeDate(user.getBirthday()));
			ps.setInt(5, user.getIsadmin());
			ps.setString(6, user.getContent());
			ps.setInt(7,user.getId());

			ps.executeUpdate();
			System.out.println("数据修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//09.30实现员工信息页面删除按钮的点击删除用户信息的功能
	public void deleteUser(int id) {
		
		//获取连接
		try (Connection conn = DBUtils.getConn();) {
			String sql = "delete from user where id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			System.out.println("用户信息删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
	}
	
	
}
