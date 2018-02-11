package com.serviceapi.model;

import javax.persistence.*;

@Entity
@Table(name = "apimsg")
public class ApiMsg {

    private Integer id;
    private int i1;
    private int i2;
    private int gcd;

    public ApiMsg() { }

    public ApiMsg(int i1, int i2) {
        this.i1 = i1;
        this.i2 = i2;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public int getI1() { return i1; }

    public void setI1(int i1) { this.i1 = i1; }

    public int getI2() { return i2; }

    public void setI2(int i2) { this.i2 = i2; }

    public int getGcd() { return gcd; }

    public void setGcd(int gcd) { this.gcd = gcd; }

    @Override
    public String toString() {
        return "ApiMsg{" +
                "id=" + id +
                ", i1=" + i1 +
                ", i2=" + i2 +
                ", gcd=" + gcd +
                '}';
    }
}
