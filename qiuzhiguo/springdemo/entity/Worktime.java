package com.qiuzhiguo.springdemo.entity;


public class Worktime {

  private long id;
  private java.sql.Timestamp inTime;
  private java.sql.Timestamp outTime;
  private java.sql.Date timeDate;
  private long status;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public java.sql.Timestamp getInTime() {
    return inTime;
  }

  public void setInTime(java.sql.Timestamp inTime) {
    this.inTime = inTime;
  }


  public java.sql.Timestamp getOutTime() {
    return outTime;
  }

  public void setOutTime(java.sql.Timestamp outTime) {
    this.outTime = outTime;
  }


  public java.sql.Date getTimeDate() {
    return timeDate;
  }

  public void setTimeDate(java.sql.Date timeDate) {
    this.timeDate = timeDate;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

}
