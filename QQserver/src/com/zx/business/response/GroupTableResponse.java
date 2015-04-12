package com.zx.business.response;

import java.util.List;

import com.zx.dao.ado.ServerDaoAdo;
import com.zx.dao.factory.ServerDaoFactory;

/**
 * »∫œÏ”¶
 * @author zx
 *
 */
public class GroupTableResponse {
	private String qq;
    private List<Object> list;
    
    public GroupTableResponse(String qq){
    	this.qq = qq;
    }
    
    public List<Object> deal(){
    	ServerDaoAdo sda = ServerDaoFactory.newInstance();
    	list = sda.groupTable(qq);
    	return list;
    }
}
