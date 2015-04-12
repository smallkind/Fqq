package com.zx.business.request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.zx.utils.CloseUtil;

/**
 * 获取头像编号的请求，服务器返回头像编号
 * @author zx
 *
 */
public class GetPhotoIdRequest {
     private String qq;
     private String photoId;
//     public static GetPhotoIdRequest ghi = new GetPhotoIdRequest();
     
     public GetPhotoIdRequest(String qq){
    	 this.qq = qq;
     }
     
//     public void init(String qq){
//    	 this.qq = qq;
//     }
     
     public String getPhotoId(){
    	 try {
			Socket socket = new Socket("localhost",8888);
			List<Object> list = new ArrayList<Object>();
			list.add("CMDphotoid");
			list.add(qq);
			
			//发送数据
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(list);
			oos.flush();
			
			//接收数据
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			photoId =  (String) ois.readObject();
			CloseUtil.closeAll(oos,ois);
			socket.close();
		} catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
		}
    	 return photoId;
     }
}
