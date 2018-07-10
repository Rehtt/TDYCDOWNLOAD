package cn.rehtt;

import java.util.List;

/**
 * Created by Rehtt on 2018/2/13.
 * 下载器
 */

public class DownloaderListClass {

    /**
     * rtn : 0
     * peerList : [{"category":"","status":0,"name":"极路由 HIWIFI_26F4","vodPort":8002,"company":"极路由","pid":"D4EE074226F4680X0020","lastLoginTime":1516515342,"accesscode":"","localIP":"192.168.199.1","location":"","online":0,"path_list":"C:/","type":20,"deviceVersion":36802253},{"category":"","status":0,"name":"XUNLEI_ARM_LE_ARMV5TE_C24B","vodPort":8002,"company":"XUNLEI_ARM_LE_ARMV5TE","pid":"6FAE7B73C24B215X0001","lastLoginTime":1518455105,"accesscode":"","localIP":"192.168.0.116","location":"","online":1,"path_list":"C:/","type":30,"deviceVersion":22153310}]
     */

    private int rtn;
    private List<PeerListBean> peerList;

    public int getRtn() {
        return rtn;
    }

    public void setRtn(int rtn) {
        this.rtn = rtn;
    }

    public List<PeerListBean> getPeerList() {
        return peerList;
    }

    public void setPeerList(List<PeerListBean> peerList) {
        this.peerList = peerList;
    }

    public static class PeerListBean {
        /**
         * category :
         * status : 0
         * name : 极路由 HIWIFI_26F4
         * vodPort : 8002
         * company : 极路由
         * pid : D4EE074226F4680X0020
         * lastLoginTime : 1516515342
         * accesscode :
         * localIP : 192.168.199.1
         * location :
         * online : 0
         * path_list : C:/
         * type : 20
         * deviceVersion : 36802253
         */

        private String category;
        private int status;
        private String name;
        private int vodPort;
        private String company;
        private String pid;
        private int lastLoginTime;
        private String accesscode;
        private String localIP;
        private String location;
        private int online;
        private String path_list;
        private int type;
        private int deviceVersion;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVodPort() {
            return vodPort;
        }

        public void setVodPort(int vodPort) {
            this.vodPort = vodPort;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public int getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(int lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getAccesscode() {
            return accesscode;
        }

        public void setAccesscode(String accesscode) {
            this.accesscode = accesscode;
        }

        public String getLocalIP() {
            return localIP;
        }

        public void setLocalIP(String localIP) {
            this.localIP = localIP;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public String getPath_list() {
            return path_list;
        }

        public void setPath_list(String path_list) {
            this.path_list = path_list;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDeviceVersion() {
            return deviceVersion;
        }

        public void setDeviceVersion(int deviceVersion) {
            this.deviceVersion = deviceVersion;
        }
    }
}
