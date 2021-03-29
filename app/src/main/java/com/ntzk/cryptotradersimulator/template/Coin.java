package com.ntzk.cryptotradersimulator.template;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Coin {
    private String name;
    private String image;
    private double amount;
    private double price;
    private double change;
    private String id;
    private String ticker;

    public Coin(String name,double amount) {

        this.name=name;
        this.amount = amount;
    }
    public Coin(String name,double price,double change,String image)
    {
        this.name=name;
        this.price=price;
        this.change=change;
        this.image=image;
    }
    public Coin(String name)
    {
        this.name=name;
    }
    public Coin(String name,String id,String image,String ticker)
    {
        this.name = name;
        this.image= image;
        this.ticker = ticker;
        this.id=id;
    }
    public Coin(){}

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTicker() {
        return ticker;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.name.equals(((Coin)obj).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
    public void incrementAmount(double amount)
    {
        this.amount=this.amount+amount;
    }
    public void decrementAmount(double amount)
    {
        this.amount=this.amount-amount;
    }
}
