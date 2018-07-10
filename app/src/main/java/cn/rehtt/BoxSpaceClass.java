package cn.rehtt;

import java.util.List;

/**
 * Created by Rehtt on 2018/2/16.
 */

public class BoxSpaceClass {

    /**
     * msg :
     * rtn : 0
     * space : [{"path":"C","remain":"84114870272"}]
     */

    private String msg;
    private int rtn;
    private List<SpaceBean> space;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRtn() {
        return rtn;
    }

    public void setRtn(int rtn) {
        this.rtn = rtn;
    }

    public List<SpaceBean> getSpace() {
        return space;
    }

    public void setSpace(List<SpaceBean> space) {
        this.space = space;
    }

    public static class SpaceBean {
        /**
         * path : C
         * remain : 84114870272
         */

        private String path;
        private String remain;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getRemain() {
            return remain;
        }

        public void setRemain(String remain) {
            this.remain = remain;
        }
    }
}
