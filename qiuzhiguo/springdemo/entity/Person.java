package com.qiuzhiguo.springdemo.entity;


public class Person {

  private long id;
  private String name;
  private java.sql.Date birth;
  private String sex;
  private String department;
  private String nativePlace;
  private String personNumber;
  private long del;

  public long getDel() {
    return del;
  }

  public void setDel(long del) {
    this.del = del;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public java.sql.Date getBirth() {
    return birth;
  }

  public void setBirth(java.sql.Date birth) {
    this.birth = birth;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }


  public String getNativePlace() {
    return nativePlace;
  }

  public void setNativePlace(String nativePlace) {
    this.nativePlace = nativePlace;
  }


  public String getPersonNumber() {
    return personNumber;
  }

  public void setPersonNumber(String personNumber) {
    this.personNumber = personNumber;
  }

}
