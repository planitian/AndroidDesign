package com.zfh.Activity;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018\3\4 0004.
 */

public class Myapplication extends Application {

    private Map map;
    private static String gg;//静态方法 可以不实例化对象 直接调用 系统在初始时候 就把静态方法实例了
    private  static  Map mapshuju;

    public static Map getMapshuju() {
        return mapshuju;
    }

    public static void setMapshuju(Map mapshuju) {
        Myapplication.mapshuju = mapshuju;
    }

    public static String getGg() {
        return gg;
    }

    public static void setGg(String gg) {
        Myapplication.gg = gg;
    }

    private  String test;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }




    @Override
    public void onCreate() {
        super.onCreate();
       // myapplication=this;
    }

}
