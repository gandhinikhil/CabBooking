package com.example.cabbooking.model;

import com.example.cabbooking.vo.LocationDetailsVO;

public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int distanceTo(LocationDetailsVO other) {
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
    // Getters and Setters
}
