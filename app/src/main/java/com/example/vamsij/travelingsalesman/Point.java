package com.example.vamsij.travelingsalesman;

/**
 * Created by VamsiJ on 2/8/15.
 */
public class Point {

    private float x;
    private float y;

    public Point(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object O){
        Point p2 = (Point) O;
        if (this.x == p2.getX() && this.y == p2.getY()){
            return true;
        }
        return false;
    }
    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
}
