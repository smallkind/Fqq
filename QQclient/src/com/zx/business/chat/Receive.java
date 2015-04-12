package com.zx.business.chat;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xd.bean.UserInfoVo;
import com.zx.business.ebi.ClientBusinessEbi;
import com.zx.business.factory.ClientBusinessFactory;
import com.zx.ui.main.chat.PrivateChatUi;
import com.zx.utils.DateTransferUtil;
import com.zx.utils.PrivateChatMap;
import com.zx.utils.SocketMap;

/**
 * 与人聊完，发送和接受都是互相独立的，所以接受和发现应该是两个单独的线程体
 * 接受数据
 * @author zx
 *
 */
public class Receive extends Thread{
	//管道的输入流
    private ObjectInputStream ois;
    //线程的标识
    private boolean isRunning = true;
	private List<Object> list=null;
	private Map<String,PrivateChatUi> map=null;
	private Socket client;
	//标识 
	public Receive(String qq){
		this.client = SocketMap.map.get(qq);
	}
	
	/**
	 * 接收数据
	 */
	@SuppressWarnings("unchecked")
	public void receive(){
		try {
			ois = new ObjectInputStream(client.getInputStream());
			list = (List<Object>) ois.readObject();
		}catch (Exception e) {
			isRunning = false;
		}
	}
	
	@Override
	public void run() {
		while(isRunning){
			receive();
			if(!isRunning){
				return;
			}
			String cmd = (String) list.get(0);//指令
			if(!cmd.equals("chat")){
				return;
			}
			String sqq = (String) list.get(1);
			String rqq = (String) list.get(2);
			String msg = (String) list.get(3);
			map = PrivateChatMap.pcm.get(rqq);
			String time = DateTransferUtil.long2String(new Date().getTime());
			ClientBusinessEbi cbe = ClientBusinessFactory.newInstance();
			UserInfoVo uiv = cbe.getFriendInfo(sqq);
			if(map==null){
				map = new HashMap<String, PrivateChatUi>();
				PrivateChatUi pcu = new PrivateChatUi(uiv,rqq);
				map.put(sqq, pcu);
				PrivateChatMap.pcm.put(rqq, map);
				pcu.getShowmessage().append("\r\n"+uiv.getNickname()+"  "+time);
				pcu.getShowmessage().append("\r\n"+msg);
			}else{
				if(map.containsKey(sqq)){
					map.get(sqq).getShowmessage().append("\r\n"+uiv.getNickname()+"  "+time);
					map.get(sqq).getShowmessage().append("\r\n"+msg);
				}else{
					PrivateChatUi pcu = new PrivateChatUi(uiv,rqq);
					map.put(sqq, pcu);
					PrivateChatMap.pcm.remove(rqq);
					PrivateChatMap.pcm.put(rqq, map);
					pcu.getShowmessage().append("\r\n"+uiv.getNickname()+"  "+time);
					pcu.getShowmessage().append("\r\n"+msg);
				}
			}
			System.out.println(rqq+"消息接受完毕……"+msg);
		}
	}
}
