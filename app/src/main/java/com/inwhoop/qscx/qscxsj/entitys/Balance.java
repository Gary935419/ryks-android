package com.inwhoop.qscx.qscxsj.entitys;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lucky on 2016/12/14.
 */

public class Balance implements Serializable {

    private String balance;
    private List<BalanceDetail> lists;

    public List<BalanceDetail> getLists() {
        return lists;
    }

    public void setLists(List<BalanceDetail> lists) {
        this.lists = lists;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public static class BalanceDetail implements Serializable {

        private String id;
        private String driver_id;
        private String content;
        private String type;
        private String money;
        private String add_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
    }
}
