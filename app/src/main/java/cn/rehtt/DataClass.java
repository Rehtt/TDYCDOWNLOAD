package cn.rehtt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rehtt on 2018/2/13.
 */

public class DataClass {
    static boolean stop = false;
    static String downloaderList = "";
    static boolean downloaderLoadDone = false;
    static List<String> cookie = new ArrayList<>();
    static String deviceid = "";
    static String userid = "";
    static String sessionid = "";
    static String usernick = "";
    static String isvip = "";
    static String logintype = "";
    static String usernewno = "";
    static String score = "";
    static String username = "";
    static String order = "";
    static String usertype ="";

    public static List<String> getCookie() {
        return cookie;
    }

    public static void setCookie(List<String> cookie) {
        DataClass.cookie = cookie;
    }

    public static String getDownloaderList() {
        return downloaderList;
    }

    public static void setDownloaderList(String downloaderList) {
        DataClass.downloaderList = downloaderList;
    }


    public static boolean isDownloaderLoadDone() {
        return downloaderLoadDone;
    }

    public static void setDownloaderLoadDone(boolean downloaderLoadDone) {
        DataClass.downloaderLoadDone = downloaderLoadDone;
    }

    public static String getDeviceid() {
        return deviceid;
    }

    public static void setDeviceid(String deviceid) {
        DataClass.deviceid = deviceid;
    }

    public static String getUserid() {
        return userid;
    }

    public static void setUserid(String userid) {
        DataClass.userid = userid;
    }

    public static String getSessionid() {
        return sessionid;
    }

    public static void setSessionid(String sessionid) {
        DataClass.sessionid = sessionid;
    }

    public static String getUsernick() {
        return usernick;
    }

    public static void setUsernick(String usernick) {
        DataClass.usernick = usernick;
    }

    public static String getIsvip() {
        return isvip;
    }

    public static void setIsvip(String isvip) {
        DataClass.isvip = isvip;
    }

    public static String getLogintype() {
        return logintype;
    }

    public static void setLogintype(String logintype) {
        DataClass.logintype = logintype;
    }

    public static String getUsernewno() {
        return usernewno;
    }

    public static void setUsernewno(String usernewno) {
        DataClass.usernewno = usernewno;
    }

    public static String getScore() {
        return score;
    }

    public static void setScore(String score) {
        DataClass.score = score;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DataClass.username = username;
    }

    public static String getOrder() {
        return order;
    }

    public static void setOrder(String order) {
        DataClass.order = order;
    }

    public static String getUsertype() {
        return usertype;
    }

    public static void setUsertype(String usertype) {
        DataClass.usertype = usertype;
    }

    public static boolean isStop() {
        return stop;
    }

    public static void setStop(boolean stop) {
        DataClass.stop = stop;
    }
}




