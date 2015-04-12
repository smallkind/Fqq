package com.zx.business.factory;

import com.zx.business.ebi.ClientBusinessEbi;
import com.zx.business.ebo.ClientBusinessEbo;

/**
 * 提供new ClientBusinessEbo的工厂方法
 * @author zx
 *
 */
public class ClientBusinessFactory {
    public static ClientBusinessEbi newInstance(){
    	return new ClientBusinessEbo();
    }
}
