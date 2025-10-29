package com.sg.flooringmastery.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Order {

    private int orderNumber;
    private String customerName;
    private String state;
    private LocalDate orderDate;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal costPerSquareFoot;
    private BigDecimal laborCostPerSquareFoot;
    private BigDecimal materialCost;
    private BigDecimal area;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;


    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Order(){
        //empty
    }

    //Setters
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    //Getters


    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getState() {
        return state;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderNumber == order.orderNumber && Objects.equals(customerName, order.customerName) && Objects.equals(state, order.state) && Objects.equals(orderDate, order.orderDate) && Objects.equals(taxRate, order.taxRate) && Objects.equals(productType, order.productType) && Objects.equals(costPerSquareFoot, order.costPerSquareFoot) && Objects.equals(laborCostPerSquareFoot, order.laborCostPerSquareFoot) && Objects.equals(materialCost, order.materialCost) && Objects.equals(area, order.area) && Objects.equals(laborCost, order.laborCost) && Objects.equals(tax, order.tax) && Objects.equals(total, order.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, customerName, state, orderDate, taxRate, productType, costPerSquareFoot, laborCostPerSquareFoot, materialCost, area, laborCost, tax, total);
    }
}
