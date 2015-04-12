package com.zx.business.request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.zx.utils.CloseUtil;

/**
 * 获取组名，以及该组的好友列表请求。发送给服务器，返回结果
 * @author zx
 *
 */
public class GetSubGroupRequest {
    private String qq;//QQ号
    private List<Object> list;//接受服务器返回的结果
//    public static GetSubGroupRequest gsg = new GetSubGroupRequest();
    
    /**
     * 私有构造函数
     */
    public GetSubGroupRequest(String qq){
    	this.qq = qq;
    }
    
    /**
     * 初始化参数
     */
//    public void init(String qq){
//    	this.qq = qq;
//    }
//    
    @SuppressWarnings("unchecked")
	public List<Object> getSbuGroup(){
    	try {
			Socket socket = new Socket("localhost",8888);
			List<Object> l = new ArrayList<Object>();
			l.add("CMDsubgroup");
			l.add(qq);
			
			//发送数据
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(l);
			oos.flush();
			
			//接受数据
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			list = (List<Object>) ois.readObject();
			CloseUtil.closeAll(oos,ois);
			socket.close();
		}catch (Exception e) {
           throw new RuntimeException(e.getMessage(),e);
		}
    	return list;
    } 
}
