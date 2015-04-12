package com.zx.business.factory;

import com.zx.business.ebi.ServerBusinessEbi;
import com.zx.business.ebo.ServerBusinessEbo;

/**
 * 工厂类，提供new ServerBusinessEbo的实例
 * @author zx
 *
 */
public class ServerBusinessFactory {
    public static ServerBusinessEbi newInstance(){
    	return new ServerBusinessEbo();
    }
}
