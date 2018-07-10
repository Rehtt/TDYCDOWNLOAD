package cn.rehtt;

/**
 * Created by Rehtt on 2018/2/16.
 */

public class SettingsClass {

    /**
     * autoOpenVip : 1
     * uploadSpeedLimit : -1
     * slEndTime : 1440
     * slStartTime : 0
     * rtn : 0
     * downloadSpeedLimit : -1
     * autoOpenLixian : 1
     * syncRange : 0
     * msg :
     * maxRunTaskNumber : 3
     * defaultPath : C:/TDDOWNLOAD/
     * autoDlSubtitle : 0
     */

    private int autoOpenVip;
    private int uploadSpeedLimit;
    private int slEndTime;
    private int slStartTime;
    private int rtn;
    private int downloadSpeedLimit;
    private int autoOpenLixian;
    private int syncRange;
    private String msg;
    private int maxRunTaskNumber;
    private String defaultPath;
    private int autoDlSubtitle;

    public int getAutoOpenVip() {
        return autoOpenVip;
    }

    public void setAutoOpenVip(int autoOpenVip) {
        this.autoOpenVip = autoOpenVip;
    }

    public int getUploadSpeedLimit() {
        return uploadSpeedLimit;
    }

    public void setUploadSpeedLimit(int uploadSpeedLimit) {
        this.uploadSpeedLimit = uploadSpeedLimit;
    }

    public int getSlEndTime() {
        return slEndTime;
    }

    public void setSlEndTime(int slEndTime) {
        this.slEndTime = slEndTime;
    }

    public int getSlStartTime() {
        return slStartTime;
    }

    public void setSlStartTime(int slStartTime) {
        this.slStartTime = slStartTime;
    }

    public int getRtn() {
        return rtn;
    }

    public void setRtn(int rtn) {
        this.rtn = rtn;
    }

    public int getDownloadSpeedLimit() {
        return downloadSpeedLimit;
    }

    public void setDownloadSpeedLimit(int downloadSpeedLimit) {
        this.downloadSpeedLimit = downloadSpeedLimit;
    }

    public int getAutoOpenLixian() {
        return autoOpenLixian;
    }

    public void setAutoOpenLixian(int autoOpenLixian) {
        this.autoOpenLixian = autoOpenLixian;
    }

    public int getSyncRange() {
        return syncRange;
    }

    public void setSyncRange(int syncRange) {
        this.syncRange = syncRange;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getMaxRunTaskNumber() {
        return maxRunTaskNumber;
    }

    public void setMaxRunTaskNumber(int maxRunTaskNumber) {
        this.maxRunTaskNumber = maxRunTaskNumber;
    }

    public String getDefaultPath() {
        return defaultPath;
    }

    public void setDefaultPath(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public int getAutoDlSubtitle() {
        return autoDlSubtitle;
    }

    public void setAutoDlSubtitle(int autoDlSubtitle) {
        this.autoDlSubtitle = autoDlSubtitle;
    }
}
