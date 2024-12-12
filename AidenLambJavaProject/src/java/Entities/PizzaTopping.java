/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;
import java.sql.Timestamp;
import java.util.Date;
/**
 *
 * @author aiden
 */
public class PizzaTopping {
    private Date createDate;
    private int empAddedBy;
    private int isActive;
    private String name;
    private int pizzaTopping_id;
    private double price;

    public PizzaTopping(Date createDate, int empAddedBy, int isActive, String name, int pizzaTopping_id, double price) {
        this.createDate = createDate;
        this.empAddedBy = empAddedBy;
        this.isActive = isActive;
        this.name = name;
        this.pizzaTopping_id = pizzaTopping_id;
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getEmpAddedBy() {
        return empAddedBy;
    }

    public void setEmpAddedBy(int empAddedBy) {
        this.empAddedBy = empAddedBy;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPizzaTopping_id() {
        return pizzaTopping_id;
    }

    public void setPizzaTopping_id(int pizzaTopping_id) {
        this.pizzaTopping_id = pizzaTopping_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
