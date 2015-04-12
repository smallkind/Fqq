package com.zx.business.ebo;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xd.bean.LoginVo;
import com.xd.bean.UserInfoVo;
import com.zx.business.chat.Receive;
import com.zx.business.ebi.ClientBusinessEbi;
import com.zx.business.request.GetFriendInfoRequest;
import com.zx.business.request.GetPhotoIdRequest;
import com.zx.business.request.GetSubGroupRequest;
import com.zx.business.request.GroupTableRequest;
import com.zx.business.request.LoginRequest;
import com.zx.business.request.OfflineRequest;
import com.zx.business.request.SkinRequest;
import com.zx.utils.DateTransferUtil;
import com.zx.utils.SocketMap;
/**
 * 客户端逻辑层的实现类,实现ClientBusinessEbi接口
 * @author zx
 *
 */
public class ClientBusinessEbo implements ClientBusinessEbi {

	@Override
	public List<Object> login(String name, String pwd) {
        UserInfoVo uv = new UserInfoVo();
        uv.setQq(name);
        uv.setPwd(pwd);
        LoginVo lv = new LoginVo();
        lv.setLqq(name);
        Date d = new Date();
        lv.setLdate(DateTransferUtil.long2String(d.getTime()));
        LoginRequest lr = new LoginRequest(uv,lv);
		return lr.login();
	}

	@Override
	public List<Object> getSubGroup(String qq) {
		GetSubGroupRequest gsg = new GetSubGroupRequest(qq);
		return gsg.getSbuGroup();
	}

	@Override
	public UserInfoVo getFriendInfo(String qq) {
		GetFriendInfoRequest gfi =new GetFriendInfoRequest(qq);
		return gfi.getFriendInfo();
	}

	@Override
	public boolean offline(String qq) {
		OfflineRequest off = new OfflineRequest(qq);
		return off.offline();
	}

	@Override
	public String getPhotoId(String qq) {
		GetPhotoIdRequest ghi =new GetPhotoIdRequest(qq);
		return ghi.getPhotoId();
	}

	@Override
	public boolean setBg(int id, String qq) {
		SkinRequest sr = new SkinRequest(id, qq);
		return sr.setBg();
	}

	@Override
	public List<Object> getGroupTable(String qq) {
        GroupTableRequest gtr = new GroupTableRequest(qq);
		return gtr.grouptable();
	}

	@Override
	public void startSocket(String qq) {
		try {
			Socket client = new Socket("localhost",6666);
			SocketMap.map.put(qq, client);
			System.out.println(client);
			//发送消息
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			List<Object> list = new ArrayList<Object>();
			list.add("start");
			list.add(qq);
			oos.writeObject(list);
			oos.flush();
			new Receive(qq).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
