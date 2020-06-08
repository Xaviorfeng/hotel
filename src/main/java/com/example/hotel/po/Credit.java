package com.example.hotel.po;

import java.time.LocalDateTime;
public class Credit {

    private int id;

    private LocalDateTime changeTime;

    private Integer userId;

    private Integer orderId;

    /**
     * 行为类型 1订单执行 2订单异常 3订单撤销 4充值
     */
    private Integer actionType;

    private double creditChange;

    private double creditResult;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(LocalDateTime changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public double getCreditChange() {
        return creditChange;
    }

    public void setCreditChange(double creditChange) {
        this.creditChange = creditChange;
    }

    public double getCreditResult() {
        return creditResult;
    }

    public void setCreditResult(double creditResult) {
        this.creditResult = creditResult;
    }
}
