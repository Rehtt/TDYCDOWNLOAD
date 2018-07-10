package cn.rehtt;

import java.util.List;

/**
 * Created by Rehtt on 2018/2/13.
 * 下载文件
 */

public class DownloadListClass {

    /**
     * recycleNum : 0
     * serverFailNum : 0
     * rtn : 0
     * completeNum : 16
     * sync : 0
     * tasks : [{"failCode":0,"vipChannel":{"available":0,"failCode":0,"opened":0,"type":0,"dlBytes":0,"speed":0},"name":"阳光电影www.ygdy8.com.追捕.BD.720p.中文字幕.mkv","url":"ftp://ygdy8:ygdy8@yg45.dydytt.net:8123/阳光电影www.ygdy8.com.追捕.BD.720p.中文字幕.mkv","speed":2730,"lixianChannel":{"failCode":0,"serverProgress":0,"dlBytes":0,"state":0,"serverSpeed":0,"speed":0},"downTime":29,"subList":[],"createTime":1518510565,"state":0,"remainTime":566762,"progress":0,"path":"/tmp/thunder/volumes/C:/TDDOWNLOAD/","type":1,"id":"17","completeTime":1519077356,"size":1547279292}]
     * dlNum : 1
     */

    private int recycleNum;
    private int serverFailNum;
    private int rtn;
    private int completeNum;
    private int sync;
    private int dlNum;
    private List<TasksBean> tasks;

    public int getRecycleNum() {
        return recycleNum;
    }

    public void setRecycleNum(int recycleNum) {
        this.recycleNum = recycleNum;
    }

    public int getServerFailNum() {
        return serverFailNum;
    }

    public void setServerFailNum(int serverFailNum) {
        this.serverFailNum = serverFailNum;
    }

    public int getRtn() {
        return rtn;
    }

    public void setRtn(int rtn) {
        this.rtn = rtn;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getDlNum() {
        return dlNum;
    }

    public void setDlNum(int dlNum) {
        this.dlNum = dlNum;
    }

    public List<TasksBean> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksBean> tasks) {
        this.tasks = tasks;
    }

    public static class TasksBean {
        /**
         * failCode : 0
         * vipChannel : {"available":0,"failCode":0,"opened":0,"type":0,"dlBytes":0,"speed":0}
         * name : 阳光电影www.ygdy8.com.追捕.BD.720p.中文字幕.mkv
         * url : ftp://ygdy8:ygdy8@yg45.dydytt.net:8123/阳光电影www.ygdy8.com.追捕.BD.720p.中文字幕.mkv
         * speed : 2730
         * lixianChannel : {"failCode":0,"serverProgress":0,"dlBytes":0,"state":0,"serverSpeed":0,"speed":0}
         * downTime : 29
         * subList : []
         * createTime : 1518510565
         * state : 0
         * remainTime : 566762
         * progress : 0
         * path : /tmp/thunder/volumes/C:/TDDOWNLOAD/
         * type : 1
         * id : 17
         * completeTime : 1519077356
         * size : 1547279292
         */

        private int failCode;
        private VipChannelBean vipChannel;
        private String name;
        private String url;
        private int speed;
        private LixianChannelBean lixianChannel;
        private int downTime;
        private int createTime;
        private int state;
        private long remainTime;
        private int progress;
        private String path;
        private int type;
        private String id;
        private int completeTime;
        private long size;
        private List<?> subList;

        public int getFailCode() {
            return failCode;
        }

        public void setFailCode(int failCode) {
            this.failCode = failCode;
        }

        public VipChannelBean getVipChannel() {
            return vipChannel;
        }

        public void setVipChannel(VipChannelBean vipChannel) {
            this.vipChannel = vipChannel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public LixianChannelBean getLixianChannel() {
            return lixianChannel;
        }

        public void setLixianChannel(LixianChannelBean lixianChannel) {
            this.lixianChannel = lixianChannel;
        }

        public int getDownTime() {
            return downTime;
        }

        public void setDownTime(int downTime) {
            this.downTime = downTime;
        }

        public int getCreateTime() {
            return createTime;
        }

        public void setCreateTime(int createTime) {
            this.createTime = createTime;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getRemainTime() {
            return remainTime;
        }

        public void setRemainTime(long remainTime) {
            this.remainTime = remainTime;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(int completeTime) {
            this.completeTime = completeTime;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public List<?> getSubList() {
            return subList;
        }

        public void setSubList(List<?> subList) {
            this.subList = subList;
        }

        public static class VipChannelBean {
            /**
             * available : 0
             * failCode : 0
             * opened : 0
             * type : 0
             * dlBytes : 0
             * speed : 0
             */

            private int available;
            private int failCode;
            private int opened;
            private int type;
            private int dlBytes;
            private int speed;

            public int getAvailable() {
                return available;
            }

            public void setAvailable(int available) {
                this.available = available;
            }

            public int getFailCode() {
                return failCode;
            }

            public void setFailCode(int failCode) {
                this.failCode = failCode;
            }

            public int getOpened() {
                return opened;
            }

            public void setOpened(int opened) {
                this.opened = opened;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getDlBytes() {
                return dlBytes;
            }

            public void setDlBytes(int dlBytes) {
                this.dlBytes = dlBytes;
            }

            public int getSpeed() {
                return speed;
            }

            public void setSpeed(int speed) {
                this.speed = speed;
            }
        }

        public static class LixianChannelBean {
            /**
             * failCode : 0
             * serverProgress : 0
             * dlBytes : 0
             * state : 0
             * serverSpeed : 0
             * speed : 0
             */

            private int failCode;
            private int serverProgress;
            private int dlBytes;
            private int state;
            private int serverSpeed;
            private int speed;

            public int getFailCode() {
                return failCode;
            }

            public void setFailCode(int failCode) {
                this.failCode = failCode;
            }

            public int getServerProgress() {
                return serverProgress;
            }

            public void setServerProgress(int serverProgress) {
                this.serverProgress = serverProgress;
            }

            public int getDlBytes() {
                return dlBytes;
            }

            public void setDlBytes(int dlBytes) {
                this.dlBytes = dlBytes;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getServerSpeed() {
                return serverSpeed;
            }

            public void setServerSpeed(int serverSpeed) {
                this.serverSpeed = serverSpeed;
            }

            public int getSpeed() {
                return speed;
            }

            public void setSpeed(int speed) {
                this.speed = speed;
            }
        }
    }
}
