package com.zx.business.response;

import com.zx.dao.ado.ServerDaoAdo;
import com.zx.dao.factory.ServerDaoFactory;

/**
 * 下线响应，true下线成功，false下线失败，把结果返回给客户端
 * @author zx
 *
 */
public class OfflineResponse {
    private String qq;
    private boolean flag;
//    public static OfflineResponse off = new OfflineResponse();
    
    public OfflineResponse(String qq){
    	this.qq = qq;
    }
    
//    public void init(String qq){
//    	this.qq = qq;
//    }
    
    public boolean deal(){
    	ServerDaoAdo sda = ServerDaoFactory.newInstance();
    	flag = sda.offline(qq);
    	return flag;
    }
}
