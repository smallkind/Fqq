package com.zx.business.response;

import java.util.List;

import com.xd.bean.LoginVo;
import com.xd.bean.UserInfoVo;
import com.zx.dao.ado.ServerDaoAdo;
import com.zx.dao.factory.ServerDaoFactory;

/**
 * 服务器对客户端传来的登陆指令进行出行，并做出响应
 * @author zx
 *
 */
public class LoginResponse {
//	public static LoginResponse login = new LoginResponse();//单例思想，不允许其他类new这个类的实例
	private UserInfoVo uv;//用户信息表
	private LoginVo lv;//用户登陆信息表
    private List<Object> list = null;//接收数据层返回的信息
	public LoginResponse(UserInfoVo uv,LoginVo lv){
		this.uv = uv;
		this.lv = lv;
	}

	/**
	 * 获取UserInfoVo uv，LoginVo lv这两个参数
	 * @param uv
	 * @param lv
	 */
//	public void getInfo(UserInfoVo uv,LoginVo lv){
//		this.uv = uv;
//		this.lv = lv;
//	}

	/**
	 * 处理数据
	 */
	public List<Object> deal(){
 	    ServerDaoAdo sda = ServerDaoFactory.newInstance();
	    list = sda.login(uv, lv);
	    return list;
	}

}
