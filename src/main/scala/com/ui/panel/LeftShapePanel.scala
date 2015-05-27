package com.ui.panel

import java.awt.Color

import org.jdesktop.layout.GroupLayout
import org.jdesktop.layout.GroupLayout.LEADING

class LeftShapePanel extends ShapeMatchPanel {

    def initComponents: Unit = {

        setBackground(Color.BLACK)

        val leftPanelLayout = new GroupLayout(this)

        setLayout(leftPanelLayout)

        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(GroupLayout.LEADING)
            .add(0, 286, Short.MaxValue))

        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(LEADING)
            .add(0, 0, Short.MaxValue))
    }
}