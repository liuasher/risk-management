package com.wjl.commom.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 交易上下文
 */
@Slf4j
public class TxnContext extends ConcurrentHashMap {

    protected static Class<? extends TxnContext> contextClass = TxnContext.class;
    private static TxnContext testContext = null;
    protected static final ThreadLocal<? extends TxnContext> threadLocal = new ThreadLocal() {
        @Override
        protected TxnContext initialValue() {
            try {
                return TxnContext.contextClass.newInstance();
            } catch (Throwable var2) {
                throw new RuntimeException(var2);
            }
        }
    };
    public static TxnContext getCurrentContext() {
        if(testContext != null) {
            return testContext;
        } else {
            TxnContext context = threadLocal.get();
            return context;
        }
    }
    public TxnContext() {

    }
    public static void setContextClass(Class<? extends TxnContext> clazz) {
        contextClass = clazz;
    }
    public static void testSetCurrentContext(TxnContext context) {
        testContext = context;
    }

    public void set(String key, Object value) {
        if(value != null) {
            this.put(key, value);
        } else {
            this.remove(key);
        }
    }

    public void unset() {
        threadLocal.remove();
    }
    public void setDataLose(boolean losed){
        this.set("dataLose",losed);
    }
    public boolean getDataLose(){
        if(this.get("dataLose") == null) {
            this.putIfAbsent("dataLose", false);
        }
        return (Boolean) this.get("datalose");
    }


    public void setRemark(String remark){
        this.set("remark",remark);
    }
    public String getRemark(){
        return (String)this.get("remark");
    }
}
