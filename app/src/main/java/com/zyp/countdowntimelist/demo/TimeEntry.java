package com.zyp.countdowntimelist.demo;

public class TimeEntry {
    private long id;
    private long time;

    public TimeEntry(long id, long time) {
        this.id = id;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
