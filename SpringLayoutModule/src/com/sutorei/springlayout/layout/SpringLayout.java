/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sutorei.springlayout.layout;


import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;

import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutProperty;

/**
 *
 * @author sutorei
 */
public class SpringLayout implements Layout{
    
     private final LayoutBuilder layoutBuilder;
    protected GraphModel graphModel;
    private boolean converged;
    
    private Graph graph;
    
    public SpringLayout(LayoutBuilder layoutBuilder) {
        this.layoutBuilder = layoutBuilder;
    }
    
     @Override
    public LayoutBuilder getBuilder() {
        return layoutBuilder;
    }

    @Override
    public void setGraphModel(GraphModel graphModel) {
        this.graphModel = graphModel;
    }

    public void setConverged(boolean converged) {
        this.converged = converged;
    }

    public boolean isConverged() {
        return converged;
    }

    @Override
    public void initAlgo() {
           //Empty on intention
    }
    
    @Override
    public boolean canAlgo() {
        return true;
    }

    @Override
    public void goAlgo() {
        graph = graphModel.getHierarchicalGraphVisible();
    }

    @Override
    public void endAlgo() {
         for (Node n : graph.getNodes()) {
        }
    }

    @Override
    public LayoutProperty[] getProperties() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetPropertiesValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
