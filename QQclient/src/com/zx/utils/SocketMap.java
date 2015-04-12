package com.zx.utils;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 用Map<String,Socket>来保存该QQ与服务器的通信通道。(专用于接受信息的通道)
 * key:该用户的QQ号
 * value:该用户与服务器建立的Socket通道
 * @author zx
 *
 */
public class SocketMap {
    public static Map<String, Socket> map = new HashMap<String, Socket>();
}
