package com.zx.business.request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.zx.utils.CloseUtil;

/**
 * 下线请求
 * @author zx
 *
 */
public class OfflineRequest {
   private String qq;
   private boolean flag;
 //  public static OfflineRequest off = new OfflineRequest();
   
   public OfflineRequest(String qq){
	   this.qq = qq;
   }
   
//   public void init(String qq){
//	   this.qq = qq;
//   }
   
   public boolean offline(){
	   try {
		Socket socket = new Socket("localhost",8888);
		List<Object> l = new ArrayList<Object>();
		l.add("CMDoffline");
		l.add(qq);
		
		//发送数据
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(l);
		oos.flush();
		
		//接受数据
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		flag = (Boolean) ois.readObject();
		CloseUtil.closeAll(oos,ois);
		socket.close();
	} catch (Exception e) {
		throw new RuntimeException(e.getMessage(),e);
	}
	   return flag;
   }
}
