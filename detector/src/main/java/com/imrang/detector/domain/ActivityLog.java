package com.imrang.detector.domain;

public class ActivityLog {

    private String ip;
    private long time;
    private Action action;
    private String userName;

    public ActivityLog(String ip, long timeMilliseconds, Action action, String userName) {
        this.ip = ip;
        this.time = timeMilliseconds;
        this.action = action;
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public long getTime() {
        return time;
    }

    public Action getAction() {
        return action;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivityLog)) return false;

        ActivityLog that = (ActivityLog) o;

        if (action != that.action) return false;
        if (time != that.time) return false;
        if (!ip.equals(that.ip)) return false;
        if (!userName.equals(that.userName)) return false;

        return true;
    }

    @Override
    public final int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    /*
     * For unit testing
     */
    ActivityLog() {

    }
}
