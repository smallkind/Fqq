package com.zx.business.request;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.xd.bean.LoginVo;
import com.xd.bean.UserInfoVo;
import com.zx.utils.CloseUtil;

/**
 * 登陆请求，发送给服务器，返回结果
 * @author zx
 *
 */
public class LoginRequest {
    private UserInfoVo uiv= null;//用户账号，密码
    private LoginVo lv = null;//用户IP，端口等信息
    private List<Object> ls =null;//接收服务器返回信息
//    public static LoginRequest lr = new LoginRequest();
    
    /**
     * 私有构造函数，不允许外面new这个类的对象
     */
    public LoginRequest(UserInfoVo uiv,LoginVo lv){
    	this.uiv = uiv;
    	this.lv = lv;
    }
    
//    public void init(UserInfoVo uiv,LoginVo lv){
//    	this.uiv = uiv;
//    	this.lv = lv;
//    }
    
    @SuppressWarnings("unchecked")
	public List<Object> login(){
    	try {
			Socket socket = new Socket("localhost",8888);
			lv.setLip(socket.getLocalAddress().getHostAddress());
			lv.setLport(socket.getLocalPort()+"");
			lv.setLstatus("yes");
			List<Object> list = new ArrayList<Object>();
			list.add("CMDlogin");
			list.add(uiv);
			list.add(lv);
			
			//发送数据
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(list);
			oos.flush();
			
			//接收数据
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ls = (List<Object>) ois.readObject();
			CloseUtil.closeAll(oos,ois);
			socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
    	return ls;
    }
}
