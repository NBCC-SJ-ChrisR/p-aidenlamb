/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

/**
 *
 * @author aiden
 */
public class PizzaTopping_Map {
    private int pizzaTopping_id;
    private int pizzaTopping_map_id;
    private int pizza_id;

    public PizzaTopping_Map(int pizzaTopping_id, int pizzaTopping_map_id, int pizza_id) {
        this.pizzaTopping_id = pizzaTopping_id;
        this.pizzaTopping_map_id = pizzaTopping_map_id;
        this.pizza_id = pizza_id;
    }

    public int getPizzaTopping_id() {
        return pizzaTopping_id;
    }

    public void setPizzaTopping_id(int pizzaTopping_id) {
        this.pizzaTopping_id = pizzaTopping_id;
    }

    public int getPizzaTopping_map_id() {
        return pizzaTopping_map_id;
    }

    public void setPizzaTopping_map_id(int pizzaTopping_map_id) {
        this.pizzaTopping_map_id = pizzaTopping_map_id;
    }

    public int getPizza_id() {
        return pizza_id;
    }

    public void setPizza_id(int pizza_id) {
        this.pizza_id = pizza_id;
    }
    
    
}
