/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author aiden
 */
public class Pizza {
    private int order_id;
    private int pizzaCrust_id;
    private int pizzaSize_id;
    private int pizza_id;
    private double priceEach;
    private int quantity;
    private double totalPrice;

    public Pizza(int order_id, int pizzaCrust_id, int pizzaSize_id, int pizza_id, double priceEach, int quantity, double totalPrice) {
        this.order_id = order_id;
        this.pizzaCrust_id = pizzaCrust_id;
        this.pizzaSize_id = pizzaSize_id;
        this.pizza_id = pizza_id;
        this.priceEach = priceEach;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getPizzaCrust_id() {
        return pizzaCrust_id;
    }

    public void setPizzaCrust_id(int pizzaCrust_id) {
        this.pizzaCrust_id = pizzaCrust_id;
    }

    public int getPizzaSize_id() {
        return pizzaSize_id;
    }

    public void setPizzaSize_id(int pizzaSize_id) {
        this.pizzaSize_id = pizzaSize_id;
    }

    public int getPizza_id() {
        return pizza_id;
    }

    public void setPizza_id(int pizza_id) {
        this.pizza_id = pizza_id;
    }

    public double getPriceEach() {
        return priceEach;
    }

    public void setPriceEach(double priceEach) {
        this.priceEach = priceEach;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    
}
