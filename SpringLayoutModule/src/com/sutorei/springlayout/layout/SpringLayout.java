/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sutorei.springlayout.layout;

import com.sutorei.springlayout.physics.ForceNodeLayoutData;
import com.sutorei.springlayout.physics.util.ForceOperations;
import java.util.ArrayList;
import java.util.List;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;

import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutProperty;
import org.openide.util.NbBundle;

/**
 *
 * @author sutorei
 */
public class SpringLayout implements Layout {

    private static final float SPEED_DIVISOR = 800;
    private static final float AREA_MULTIPLICATOR = 10000;
    
    //Properties
    private float area;
    private double gravity;
    private double speed;

    private final LayoutBuilder layoutBuilder;
    protected GraphModel graphModel;
    private boolean converged;

    private Graph graph;
    
    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Double getGravity() {
        return gravity;
    }

    public void setGravity(Double gravity) {
        this.gravity = gravity;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

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
        this.graph = graphModel.getGraphVisible();
        graph.readLock();
        Node[] nodes = graph.getNodes().toArray();
        Edge[] edges = graph.getEdges().toArray();

        for (Node n : nodes) {
            //Initialize nodes
            if (n.getNodeData().getLayoutData() == null
                    || !(n.getNodeData().getLayoutData() instanceof ForceNodeLayoutData)) {
                n.getNodeData().setLayoutData(new ForceNodeLayoutData());
            }
            ForceNodeLayoutData layoutData = n.getNodeData().getLayoutData();
            layoutData.dx = 0;
            layoutData.dy = 0;
        }
        
        float maxDisplace = (float) (Math.sqrt(AREA_MULTIPLICATOR * area) / 10f);       
        float k = (float) Math.sqrt((AREA_MULTIPLICATOR * area) / (1f + nodes.length));

        for (Node n1 : nodes) {
            for (Node n2 : nodes) {
                if (n1 != n2){
                    float dist = ForceOperations.distance(n1, n2);
                    
                    if (dist > 0) {
                        float repulsiveF = k * k / dist;
                        ForceNodeLayoutData layoutData = n1.getNodeData().getLayoutData();
                        layoutData.dx += ForceOperations.xDistance(n1, n2) / dist * repulsiveF;
                        layoutData.dy += ForceOperations.yDistance(n1, n2) / dist * repulsiveF;
                    }
                }
            }
        }
        
        for (Edge e : edges) {

            Node nSource = e.getSource();
            Node nTarget = e.getTarget();

            float dist = ForceOperations.distance(nSource, nTarget);

            float attractiveF = dist * dist / k;

            if (dist > 0) {
                ForceNodeLayoutData sourceLayoutData = nSource.getNodeData().getLayoutData();
                ForceNodeLayoutData targetLayoutData = nTarget.getNodeData().getLayoutData();
                sourceLayoutData.dx -= ForceOperations.xDistance(nSource, nTarget) / dist * attractiveF;
                sourceLayoutData.dy -= ForceOperations.yDistance(nSource, nTarget) / dist * attractiveF;
                targetLayoutData.dx += ForceOperations.xDistance(nSource, nTarget) / dist * attractiveF;
                targetLayoutData.dy += ForceOperations.yDistance(nSource, nTarget) / dist * attractiveF;
            }
        }
        
        // gravity
        for (Node n : nodes) {
            ForceNodeLayoutData layoutData = n.getNodeData().getLayoutData();
            float d = ForceOperations.module(n);
            float gf = 0.01f * k * (float) gravity * d;
            layoutData.dx -= gf * n.getNodeData().x() / d;
            layoutData.dy -= gf * n.getNodeData().y() / d;
        }
        // speed
        for (Node n : nodes) {
            ForceNodeLayoutData layoutData = n.getNodeData().getLayoutData();
            layoutData.dx *= speed / SPEED_DIVISOR;
            layoutData.dy *= speed / SPEED_DIVISOR;
        }
        for (Node n : nodes) {
            ForceNodeLayoutData layoutData = n.getNodeData().getLayoutData();
            float xDist = layoutData.dx;
            float yDist = layoutData.dy;
            float dist = (float) Math.sqrt(layoutData.dx * layoutData.dx + layoutData.dy * layoutData.dy);
            if (dist > 0 && !n.getNodeData().isFixed()) {
                float limitedDist = Math.min(maxDisplace * ((float) speed / SPEED_DIVISOR), dist);
                n.getNodeData().setX(n.getNodeData().x() + xDist / dist * limitedDist);
                n.getNodeData().setY(n.getNodeData().y() + yDist / dist * limitedDist);
            }
        }
        graph.readUnlock();
    }

    @Override
    public void endAlgo() {
        for (Node n : graph.getNodes()) {
            n.getNodeData().setLayoutData(null);
        }
    }

    @Override
    public LayoutProperty[] getProperties() {
       List<LayoutProperty> properties = new ArrayList<LayoutProperty>();
        final String SPRING_LAYOUT = "Spring embedder";

        try {
            properties.add(LayoutProperty.createProperty(
                    this, Float.class,
                    NbBundle.getMessage(SpringLayout.class, "fruchtermanReingold.area.name"),
                    SPRING_LAYOUT,
                    "fruchtermanReingold.area.name",
                    NbBundle.getMessage(SpringLayout.class, "fruchtermanReingold.area.desc"),
                    "getArea", "setArea"));
            properties.add(LayoutProperty.createProperty(
                    this, Double.class,
                    NbBundle.getMessage(SpringLayout.class, "fruchtermanReingold.gravity.name"),
                    SPRING_LAYOUT,
                    "fruchtermanReingold.gravity.name",
                    NbBundle.getMessage(SpringLayout.class, "fruchtermanReingold.gravity.desc"),
                    "getGravity", "setGravity"));
            properties.add(LayoutProperty.createProperty(
                    this, Double.class,
                    NbBundle.getMessage(SpringLayout.class, "fruchtermanReingold.speed.name"),
                    SPRING_LAYOUT,
                    "fruchtermanReingold.speed.name",
                    NbBundle.getMessage(SpringLayout.class, "fruchtermanReingold.speed.desc"),
                    "getSpeed", "setSpeed"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties.toArray(new LayoutProperty[0]);
    }

    @Override
    public void resetPropertiesValues() {
        speed = 1;
        area = 10000;
        gravity = 10;
    }

}
