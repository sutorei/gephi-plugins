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
public class ForceNodeLayoutData implements LayoutData {

    public float dx = 0;
    public float dy = 0;
    public float old_dx = 0;
    public float old_dy = 0;
    public float freeze = 0f;
    public float force;
}
