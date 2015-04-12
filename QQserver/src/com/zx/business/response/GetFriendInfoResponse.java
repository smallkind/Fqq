package com.zx.business.response;

import com.xd.bean.UserInfoVo;
import com.zx.dao.ado.ServerDaoAdo;
import com.zx.dao.factory.ServerDaoFactory;

/**
 * 从数据库中获取好友信息，返回结果给客户端
 * @author zx
 *
 */
public class GetFriendInfoResponse {
     private String qq;
     private UserInfoVo uv;
//     public static GetFriendInfoResponse gfi = new GetFriendInfoResponse(); 
     
     public GetFriendInfoResponse(String qq){
    	 this.qq = qq;
     }
     
//     public void init(String qq){
//    	 this.qq = qq;
//     }
     
     public UserInfoVo deal(){
    	 ServerDaoAdo sda = ServerDaoFactory.newInstance();
    	 uv = sda.getFriendInfo(qq);
    	 return uv;
     }
}
