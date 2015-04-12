package com.zx.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 * 此类是用来与数据库链接
 */
/*JDBC链接数据库的工具类*/
public class JDBCUtil {
	private static final int NUM =2;
    private static String dbName = "root";
    private static String dbPwd = "root";
    private static String dbUrl = "jdbc:mysql://localhost:3306/iqq";
    private static String dbDriver = "com.mysql.jdbc.Driver";
    private static List<Connection> pool = new ArrayList<Connection>();//数据库的连接
	static{
		//1加载驱动
		try{
			Class.forName(dbDriver);
			for(int i=0;i<NUM;i++){
				final Connection conn = DriverManager.getConnection(dbUrl, dbName, dbPwd);
				Object o = Proxy.newProxyInstance(
						ClassLoader.getSystemClassLoader(), 
						new Class[]{Connection.class}, 
						new InvocationHandler(){
							@Override
							public Object invoke(Object proxy, Method method,
									Object[] args) throws Throwable {
								if(method.getName().equals("close")){
//									System.out.println("还回去...");
									pool.add((Connection)proxy);
									return null;
								}else{
									return method.invoke(conn, args);
								}
							}
						});
				Connection con = (Connection)o;
				pool.add(con);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//获取数据库的连接
	public static synchronized Connection getConnection(){
		if(pool.size()<=0){
//			System.out.println("池里没有了....请稍候..."+pool.size());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return getConnection();
		}else{
		    return pool.remove(0);
		}
	}
	
}

