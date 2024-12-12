/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author aiden
 */
public class PizzaCrust {
    private String name;
    private int pizzaCrust_id;
    private double price;

    public PizzaCrust(String name, int pizzaCrust_id, double price) {
        this.name = name;
        this.pizzaCrust_id = pizzaCrust_id;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPizzaCrust_id() {
        return pizzaCrust_id;
    }

    public void setPizzaCrust_id(int pizzaCrust_id) {
        this.pizzaCrust_id = pizzaCrust_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
