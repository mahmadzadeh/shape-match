package com

import java.awt.Dimension

import com.shape.DisplayShapes
import org.scalatest.FunSuite

class DisplayShapesTest extends FunSuite {

    val testDisplayWindow = new DisplayWindow(new Dimension(10,10))
    val levelOne = GameLevel(1)
    
    test("given display shape and game level one then getShapes returns the shapes to be displayed") {
        DisplayShapes.getShapesForLevel(levelOne, testDisplayWindow)
    }

    test("given display shape object and level one then getShapes returns shapes to be displayed on the right as well as left screen") {
        val shapes = DisplayShapes.getShapesForLevel(levelOne, testDisplayWindow)

        assert(shapes.leftGrid != Nil)
        assert(shapes.rightGrid != Nil)
    }

    test("given display shape object and level one then getShapes returns one ") {
        val shapes = DisplayShapes.getShapesForLevel(levelOne, testDisplayWindow)

        assert(shapes.leftGrid.shapesInGrid == shapes.rightGrid.shapesInGrid)

        assert(shapes.leftGrid.shapesInGrid == levelOne.shapeCount)
    }
}
