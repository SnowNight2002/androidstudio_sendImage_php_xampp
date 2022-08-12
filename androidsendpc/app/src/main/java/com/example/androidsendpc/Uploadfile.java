package com.example.androidsendpc;

/**
 * 实体类
 */
public class Uploadfile {

    public int fid;
    public String dateline;
    public String userip;
    public String uploadfile;
    public int status;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getUserip() {
        return userip;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getUploadfile() {
        return uploadfile;
    }

    public void setUploadfile(String uploadfile) {
        this.uploadfile = uploadfile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Uploadfile{" +
                "fid=" + fid +
                ", dateline='" + dateline + '\'' +
                ", userip='" + userip + '\'' +
                ", uploadfile='" + uploadfile + '\'' +
                ", status=" + status +
                '}';
    }
}
