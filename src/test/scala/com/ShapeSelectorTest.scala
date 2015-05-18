package com

import com.shape.ShapeSelector
import org.scalatest.FunSuite

class ShapeSelectorTest extends FunSuite {

  test("given selector and game level then getShapes returns shapes for the game level") {
    List ( LevelOne, LevelSeven ) foreach { lvl =>

      assertResult(lvl.shapeCount) {
        ShapeSelector.select(lvl).size
      }
    }
  }
}
