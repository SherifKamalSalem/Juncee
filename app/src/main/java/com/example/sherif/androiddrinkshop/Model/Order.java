package com.example.sherif.androiddrinkshop.Model;

public class Order {

    private String OrderId;
    private int OrderStatus;
    private float OrderPrice;
    private String OrderDetail, OrderComment, OrderAddress, UserPhone;

    public Order() {
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        OrderStatus = orderStatus;
    }

    public float getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getOrderDetail() {
        return OrderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        OrderDetail = orderDetail;
    }

    public String getOrderComment() {
        return OrderComment;
    }

    public void setOrderComment(String orderComment) {
        OrderComment = orderComment;
    }

    public String getOrderAddress() {
        return OrderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        OrderAddress = orderAddress;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }
}