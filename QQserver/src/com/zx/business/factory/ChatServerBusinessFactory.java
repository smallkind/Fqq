package com.zx.business.factory;

import com.zx.business.ebi.ChatServerBusinessEbi;
import com.zx.business.ebo.ChatServerBusinessEbo;

/**
 * 工厂类，提供new ChatServerBusinessEbo的实例
 * @author zx
 *
 */
public class ChatServerBusinessFactory {
    public static ChatServerBusinessEbi newInstance(){
    	return new ChatServerBusinessEbo();
    }
}
