package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;

/**
 * Created by Lucky on 2016/8/30.
 */

public class LoginUserInfoBean implements Serializable {

    private String user_id;
    private String account;
    private String password;
    private String headimg;
    private String name;
    private String sex;
    private String briday;
    private String balance;
    private String check;
    private String can_authentication;
    private String huanxin_uuid;
    private String huanxin_username;
    private String huanxin_pwd;

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBriday() {
        return briday;
    }

    public void setBriday(String briday) {
        this.briday = briday;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public String getCan_authentication() {
        return can_authentication;
    }

    public void setCan_authentication(String can_authentication) {
        this.can_authentication = can_authentication;
    }

    public String getHuanxin_uuid() {
        return huanxin_uuid;
    }

    public void setHuanxin_uuid(String huanxin_uuid) {
        this.huanxin_uuid = huanxin_uuid;
    }

    public String getHuanxin_username() {
        return huanxin_username;
    }

    public void setHuanxin_username(String huanxin_username) {
        this.huanxin_username = huanxin_username;
    }

    public String getHuanxin_pwd() {
        return huanxin_pwd;
    }

    public void setHuanxin_pwd(String huanxin_pwd) {
        this.huanxin_pwd = huanxin_pwd;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
