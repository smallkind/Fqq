package com.zx.dao.factory;

import com.zx.dao.ado.ServerDaoAdo;
import com.zx.dao.impl.ServerDaoImpl;

/**
 * 提供new ServerDaoImpl实例的工厂方法
 * @author zx
 *
 */
public class ServerDaoFactory {
    public static ServerDaoAdo newInstance(){
    	return new ServerDaoImpl();
    }
}
