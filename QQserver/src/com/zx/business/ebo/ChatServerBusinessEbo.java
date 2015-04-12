package com.zx.business.ebo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zx.business.ebi.ChatServerBusinessEbi;
import com.zx.ui.ServerUi;
import com.zx.utils.CloseUtil;
/**
 * 实现ChatServerBusinessEbi接口
 * @author zx
 *
 */
public class ChatServerBusinessEbo implements ChatServerBusinessEbi {
	private Map<String,Socket> map = new HashMap<String, Socket>();//存储每个客户端和服务器的通道
	@Override
	public void startServer() {
		try {
			ServerSocket server = new ServerSocket(6666);//在6666端口监听从客户端传来的消息
			ServerUi.smu.area.append("\r\n 启动聊天服务:" + server);
			new startChatThrea(server).start();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	/**
	 * 开启监听客户端的线程
	 * @author TuoTuo
	 *
	 */
	class startChatThrea extends Thread{
		private ServerSocket server;
		public startChatThrea(ServerSocket server){
			this.server = server;
		}
		@Override
		public void run() {
			while(true){
				try {
					Socket client = server.accept();
					System.out.println("服务器收到信息"+client);
					new chatThread(client).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 聊天线程，一个客户端一条通道
	 * @author TuoTuo
	 *
	 */
	class chatThread extends Thread{
		private Socket client;
		private ObjectInputStream ois;
		private List<Object> list =null;
		public chatThread(Socket client){
			this.client = client;
		}

		/**
		 * 接受信息
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public List<Object> receive(){
			List<Object> list =null;
			try {
				ois = new ObjectInputStream(client.getInputStream());
				list = (List<Object>) ois.readObject();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return list;	
		}

		/**
		 * 转发信息
		 */
		public void send(){
			list = receive();
			String cmd = (String) list.get(0);
			System.out.println(cmd);
			if(cmd.equals("start")){
				String sqq= (String) list.get(1); 
				map.put(sqq, client);
				ServerUi.smu.area.append("聊天通道已建立:" + sqq);
				System.out.println(sqq+"聊天通道建立成功……");
				list =null;
				return;
			}else if(cmd.equals("end")){
				try {
					CloseUtil.closeAll(ois);
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			String rqq = (String) list.get(2);
			Socket s = map.get(rqq);
			System.out.println(s);
			if(list!=null){
				try {
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					oos.writeObject(list);
					oos.flush();
					System.out.println(list.get(1)+"发送消息给"+list.get(2)+"成功:"+list.get(3));
					list =null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else{
				System.out.println("消息发送失败……");
			}
 
		}

		@Override
		public void run() {
			send();
		}
	}
}
