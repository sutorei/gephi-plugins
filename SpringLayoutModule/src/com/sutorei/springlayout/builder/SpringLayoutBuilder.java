/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sutorei.springlayout.builder;

import com.sutorei.springlayout.layout.SpringLayout;
import javax.swing.Icon;
import javax.swing.JPanel;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutUI;
import org.openide.util.NbBundle;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author sutorei
 */
@ServiceProvider(service = LayoutBuilder.class)
public class SpringLayoutBuilder implements LayoutBuilder {

    private SpringLayoutUI ui = new SpringLayoutUI();

    @Override
    public String getName() {
        return NbBundle.getMessage(SpringLayoutBuilder.class, "name");
    }

    @Override
    public LayoutUI getUI() {
        return ui;
    }

    @Override
    public Layout buildLayout() {
        return new SpringLayout(this);
    }

    public class SpringLayoutUI implements LayoutUI {

        @Override
        public String getDescription() {
            return NbBundle.getMessage(SpringLayout.class, "description");
        }

        @Override
        public Icon getIcon() {
            return null;
        }

        @Override
        public JPanel getSimplePanel(Layout layout) {
            return null;
        }

        @Override
        public int getQualityRank() {
            return 2;
        }

        @Override
        public int getSpeedRank() {
            return 3;
        }

    }

}
