package com.zx.dao.ado;

import java.util.List;

import com.xd.bean.LoginVo;
import com.xd.bean.UserInfoVo;

/**
 * 服务器数据层接口
 * @author zx
 *
 */
public interface ServerDaoAdo {
	
	/**
	 *从数据库获取头像id 
	 */
	public String getPhotoId(String qq);
	
	/**
	 * 用户验证，数据库有该用户，则返回该用户的信息，没有则返回空
	 * @return
	 */
    public List<Object> login(UserInfoVo uv,LoginVo lv);
    
    /**
     * 获取用户分组，返回该用户的所有分组，以及每个组的好友
     */
    public List<Object> getSubGroup(String qq);
    
    /**
     * 获取分组好友的信息
     */
    public UserInfoVo getFriendInfo(String qq);
    
    /**
     * 下线
     */
    public boolean offline(String qq);
    
    /**
     * 换肤，设置背景
     */
    public boolean setBg(int id,String qq);
    
    /**
     * 获取群分组
     */
    public List<Object> groupTable(String qq);
}
