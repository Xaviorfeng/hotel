package com.example.hotel.po;
import java.time.LocalDateTime;


public class Coupon {                   // 优惠券难道不是 每个人有的优惠券种类不一样吗？？？？？？？？？？？？？？？？？？？好像没实现？
    /**
     * 优惠券id
     */
    private int id;
    /**
     * 优惠券描述
     */
    private String description;

    /**
     * 如果为-1 代表是网站推出的优惠
     */
    private Integer hotelId;                                    //用于 考虑发放优惠券的主体包括酒店与网站两个主体

    /**
     * 优惠券类型 1生日特惠 2多间特惠 3满减优惠 4限时优惠
     */
    private Integer couponType;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券使用门槛
     */
    private double targetMoney;

    /**
     * 折扣
     */
    private double discount;                // 折扣 和 优惠金额 的 区别使用 是怎么实现的 ？？
    /**
     * 优惠券优惠金额
     */
    private double discountMoney;
    /**
     * 可用时间
     */
    private LocalDateTime startTime;
    /**
     * 失效时间
     */
    private LocalDateTime endTime;

    /**
     * 优惠券状态 是否已经失效 1可用 0失效
     */
    private Integer status;                                     // 优惠券难道不是 每个人有的优惠券种类不一样吗？？？？？？？？？？？？？？？？？？？




    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public double getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(double targetMoney) {
        this.targetMoney = targetMoney;
    }

    public double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Coupon() {
    }
}
