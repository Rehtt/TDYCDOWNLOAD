package cn.rehtt;

import java.util.List;

/**
 * Created by Rehtt on 2018/2/16.
 */

public class CreateTaskClass {

    /**
     * tasks : [{"name":"阳光电影www.ygdy8.com.三块广告牌.HD.720p.中英双字幕.rmvb","url":"ftp://ygdy8:ygdy8@yg45.dydytt.net:4031/阳光电影www.ygdy8.com.三块广告牌.HD.720p.中英双字幕.rmvb","result":0,"taskid":"0","msg":"","id":1}]
     * rtn : 0
     */

    private int rtn;
    private List<TasksBean> tasks;

    public int getRtn() {
        return rtn;
    }

    public void setRtn(int rtn) {
        this.rtn = rtn;
    }

    public List<TasksBean> getTasks() {
        return tasks;
    }

    public void setTasks(List<TasksBean> tasks) {
        this.tasks = tasks;
    }

    public static class TasksBean {
        /**
         * name : 阳光电影www.ygdy8.com.三块广告牌.HD.720p.中英双字幕.rmvb
         * url : ftp://ygdy8:ygdy8@yg45.dydytt.net:4031/阳光电影www.ygdy8.com.三块广告牌.HD.720p.中英双字幕.rmvb
         * result : 0
         * taskid : 0
         * msg :
         * id : 1
         */

        private String name;
        private String url;
        private int result;
        private String taskid;
        private String msg;
        private int id;

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

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getTaskid() {
            return taskid;
        }

        public void setTaskid(String taskid) {
            this.taskid = taskid;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
