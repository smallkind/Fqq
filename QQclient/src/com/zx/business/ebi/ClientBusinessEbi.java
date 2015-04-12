package com.zx.business.ebi;

import java.util.List;

import com.xd.bean.UserInfoVo;

/**
 * 客户端逻辑层的接口类
 * @author zx
 *
 */
public interface ClientBusinessEbi {
	
    /**
     *获取头像编号 
     */
	public String getPhotoId(String qq);
	
	/**
      * 登陆，把账号信息收集，发送给服务器
      */
	public List<Object> login(String name,String pwd);
	
	/*
	 * 登陆成功后，获取好友分组及分组中的好友名单
	 */
	public List<Object> getSubGroup(String qq);
	
	/**
	 * 获取组内好友的信息
	 */
	public UserInfoVo getFriendInfo(String qq);
	
	/**
	 * 下线
	 */
	public boolean offline(String qq);
	
	/**
	 * 换肤
	 */
	public boolean setBg(int id,String qq);
	
	/**
	 * 群
	 */
	public List<Object> getGroupTable(String qq);
	
	/**
	 * 开启客服端与服务器的通信通道
	 */
	public void startSocket(String qq);
}
