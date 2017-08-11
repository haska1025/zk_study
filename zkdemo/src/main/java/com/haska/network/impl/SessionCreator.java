package com.haska.network.impl;

import com.haska.network.Session;
import com.haska.network.SessionCallback;

public class SessionCreator {
    public static Session create(SessionCallback cb){
    	return new TPSession(cb);
    }
}
