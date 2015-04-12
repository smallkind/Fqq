package com.zx.business.response;

import com.zx.dao.ado.ServerDaoAdo;
import com.zx.dao.factory.ServerDaoFactory;

/**
 * 换肤响应，从数据库中找到到相应结果，返回客服端
 * @author zx
 *
 */
public class SkinResponse {
   private boolean flag;
   private String qq;
   private int id;
  // public static SkinResponse sr = new SkinResponse();
   
   public SkinResponse(int id,String qq){
	   this.id = id;
	   this.qq = qq;
   }
   
//   public void init(int id,String qq){
//	   this.id = id;
//	   this.qq = qq;
//   }
   
   public boolean deal(){
	   ServerDaoAdo sda = ServerDaoFactory.newInstance();
	   flag = sda.setBg(id,qq);
	   return flag;
   }
}
