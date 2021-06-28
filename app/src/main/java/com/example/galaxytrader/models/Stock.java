package com.example.galaxytrader.models;

import android.content.Intent;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class Stock implements Serializable {

    public Long id;
    private String name;
    private Integer price;
    private Integer maxPrice;
    private Integer minPrice;
    private Integer amount;
    public String imageFileName;

    public Stock() {
    }

    public Stock(String name, Integer maxPrice, Integer minPrice, String imageFileName) {
        this.name = name;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.imageFileName = imageFileName;
    }

    public Stock(Long id, String name, Integer price, Integer maxPrice, Integer minPrice, Integer amount, String imageFileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.amount = amount;
        this.imageFileName = imageFileName;
    }

    public Stock(Long id, String name, Integer maxPrice, Integer minPrice, String imageFileName) {
        this.id = id;
        this.name = name;
        //this.price = generateRandomPrice(minPrice, maxPrice);
        generateRandomPrice(minPrice, maxPrice);
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.amount = 0;
        this.imageFileName = imageFileName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void addAmount(Integer amount) {
        this.amount += amount;
    }

    public void subtractAmount(Integer amount) {
        this.amount -= amount;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void generateRandomPrice(int min, int max) {
        Random random = new Random();
        price = random.nextInt((max +1) - min) + min;
    }

    public Stock copyStock(){
        return new Stock(this.id, this.name, this.price, this.maxPrice, this.minPrice, this.amount, this.imageFileName);
    }
}
