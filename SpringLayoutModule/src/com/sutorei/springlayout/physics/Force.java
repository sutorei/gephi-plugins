/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sutorei.springlayout.physics;

import org.gephi.graph.spi.LayoutData;

/**
 *
 * @author sutorei
 */
public class Force implements LayoutData {

    protected float x, y;

    public Force() {
        this.x = this.y = 0;
    }

    public Force(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Force(Force other) {
        this.x = other.x;
        this.y = other.y;
    }

    public float x() {
        return x;
    }

    public float y() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void add(Force other) {
        if (other != null) {
            x += other.x();
            y += other.y();
        }
    }

    public void subtract(Force other) {
        if (other != null) {
            x -= other.x();
            y -= other.y();
        }
    }

    public void multiply(float lambda) {
        x *= lambda;
        y *= lambda;
    }

    public float getEnergy() {
        return x * x + y * y;
    }

    public float getNorm() {
        return (float) Math.sqrt(getEnergy());
    }

    public Force normalize() {
        float norm = getNorm();
        return new Force(x / norm, y / norm);
    }

}
