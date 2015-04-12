package com.zx.business.request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.xd.bean.UserInfoVo;
import com.zx.utils.CloseUtil;

/**
 * 获取好友信息的请求，往服务器发送，返回结果
 * @author zx
 *
 */
public class GetFriendInfoRequest {
    private String qq;//好友qq
    private UserInfoVo uv;//接受服务器返回的结果
//    public static GetFriendInfoRequest gfi = new GetFriendInfoRequest();
    
    public GetFriendInfoRequest(String qq){
    	this.qq = qq;
    }
    
//    public void init(String qq){
//    	this.qq = qq;
//    }
    
    public UserInfoVo getFriendInfo(){
    	try {
			Socket socket = new Socket("localhost",8888);
			List<Object> l = new ArrayList<Object>();
			l.add("CMDfriendInfo");
			l.add(qq);
			
			//发送数据
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(l);
			oos.flush();
			
			//接受数据
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			uv = (UserInfoVo) ois.readObject();
			CloseUtil.closeAll(oos,ois);
			socket.close();
		}catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
		}
    	return uv;
    }
}
