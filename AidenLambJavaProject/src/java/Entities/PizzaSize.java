/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author aiden
 */
public class PizzaSize {
    private String name;
    private int pizzaSize_id;
    private double price;

    public PizzaSize(String name, int pizzaSize_id, double price) {
        this.name = name;
        this.pizzaSize_id = pizzaSize_id;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPizzaSize_id() {
        return pizzaSize_id;
    }

    public void setPizzaSize_id(int pizzaSize_id) {
        this.pizzaSize_id = pizzaSize_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
    
}
