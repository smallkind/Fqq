package com.zx.business.response;

import com.zx.dao.ado.ServerDaoAdo;
import com.zx.dao.factory.ServerDaoFactory;

/**
 * 获取头像编号响应，把从数据库中查询的头像编号返回给客户端
 * @author zx
 *
 */
public class GetPhotoIdResponse {
     private String qq;
     private String photoid;
//     public static GetPhotoIdResponse ghi = new GetPhotoIdResponse();
     
     public GetPhotoIdResponse(String qq){
    	 this.qq = qq;
     }
     
//     public void init(String qq){
//    	 this.qq = qq;
//     }
     
     public String deal(){
    	 ServerDaoAdo sda = ServerDaoFactory.newInstance();
    	 photoid = sda.getPhotoId(qq);
    	 return photoid;
     }
}
