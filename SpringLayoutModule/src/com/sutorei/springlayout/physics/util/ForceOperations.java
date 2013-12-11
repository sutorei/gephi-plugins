/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sutorei.springlayout.physics.util;

import org.gephi.graph.api.Node;

/**
 *
 * @author sutorei
 */
public class ForceOperations {

    public static float distance(Node n1, Node n2) {
        return (float) Math.hypot(n1.getNodeData().x() - n2.getNodeData().x(), 
                n1.getNodeData().y() - n2.getNodeData().y());
    }
    
    public static float module (Node n){
        return (float) Math.sqrt(n.getNodeData().x() * n.getNodeData().x() +
                                n.getNodeData().y() * n.getNodeData().y());
                               
    }
    
    public static float xDistance(Node n1, Node n2){
        return n1.getNodeData().x() - n2.getNodeData().x();
    }
    
    public static float yDistance(Node n1, Node n2){
        return n1.getNodeData().y() - n2.getNodeData().y();
    }
}
