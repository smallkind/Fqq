package com.zx.utils;

import java.util.HashMap;
import java.util.Map;

import com.zx.ui.main.chat.PrivateChatUi;

/**
 * 用Map<String,Map<String,PrivateChatUi>>来储存用户正在与哪些好友聊天
 * key:该用户的QQ号
 * value:该用户聊天好友的聊天界面，因为一个用户可以同时和多个好友聊天，所以用List来存储。
 * @author zx
 *
 */
public class PrivateChatMap {
   public static Map<String,Map<String,PrivateChatUi>> pcm = new HashMap<String, Map<String,PrivateChatUi>>();//单例思想
}
