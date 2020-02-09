package com.qiuzhiguo.springdemo.entity;


public class PersistentLogins {

  private long id;
  private long personId;
  private java.sql.Timestamp inTime;
  private String lateReason;
  private java.sql.Timestamp outTime;
  private java.sql.Date nowTime;
  private long status;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }


  public java.sql.Timestamp getInTime() {
    return inTime;
  }

  public void setInTime(java.sql.Timestamp inTime) {
    this.inTime = inTime;
  }


  public String getLateReason() {
    return lateReason;
  }

  public void setLateReason(String lateReason) {
    this.lateReason = lateReason;
  }


  public java.sql.Timestamp getOutTime() {
    return outTime;
  }

  public void setOutTime(java.sql.Timestamp outTime) {
    this.outTime = outTime;
  }


  public java.sql.Date getNowTime() {
    return nowTime;
  }

  public void setNowTime(java.sql.Date nowTime) {
    this.nowTime = nowTime;
  }


  public long getStatus() {
    return status;
  }

  public void setStatus(long status) {
    this.status = status;
  }

}
