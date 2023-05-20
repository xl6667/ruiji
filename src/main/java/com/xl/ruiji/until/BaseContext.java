package com.xl.ruiji.until;
/*
    基于ThreadLocal为id赋值
 */
public class BaseContext {
    private static ThreadLocal<String> threadLocal= new ThreadLocal<>();
    public  static void setId(String id){
        threadLocal.set(id);
    }
    public static String getId(){
        return threadLocal.get();
    }

    public static String getCurrentId() {
        return threadLocal.get();
    }
}
