/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import java.sql.Timestamp;

/**
 *
 * @author aiden
 */
public class Order {
    private double amountPaid;
    private int customer_id;
    private int delivery;
    private Timestamp deliveryDate;
    private double hst;
    private Timestamp orderPlacedDate;
    private String orderStatus;
    private double orderTotal;
    private int order_id;
    private double subTotal;

    public Order(double amountPaid, int customer_id, int delivery, Timestamp deliveryDate, double hst, Timestamp orderPlacedDate, String orderStatus, double orderTotal, int order_id, double subTotal) {
        this.amountPaid = amountPaid;
        this.customer_id = customer_id;
        this.delivery = delivery;
        this.deliveryDate = deliveryDate;
        this.hst = hst;
        this.orderPlacedDate = orderPlacedDate;
        this.orderStatus = orderStatus;
        this.orderTotal = orderTotal;
        this.order_id = order_id;
        this.subTotal = subTotal;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public double getHst() {
        return hst;
    }

    public void setHst(double hst) {
        this.hst = hst;
    }

    public Timestamp getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(Timestamp orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
    
    
    
}
