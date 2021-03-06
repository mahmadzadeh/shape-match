package com

import java.awt.Dimension

import com.GameLogic.REQUIRED_CORRECT_CONSECUTIVE_ANSWERES
import com.shape.{DisplayShapes, DisplayShapesPair, HollowCircle, HollowSquare}
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar

class GameLogicTest extends FunSuite with MockitoSugar {

    val gameLevelOne       = GameLevel(1)
    val LevelTwo           = GameLevel(1).nextLevel
    val LevelThree         = LevelTwo.nextLevel

    val displayWindow      = new DisplayWindow(new Dimension(10,10))

    val shapes             = Seq(new HollowSquare, new HollowSquare)
    val nonMatchingShapes  = Seq(new HollowSquare, new HollowCircle())

    val slots              = DisplayShapes.getSlotIndicesToPutShapesIn(shapes)

    val matchingDisplayShapesPair = new DisplayShapesPair(leftGrid = new DisplayGrid(displayWindow, shapes, slots),
                                                  rightGrid = new DisplayGrid(displayWindow, shapes, slots))

    val nonMatchingDisplayShapesPair = new DisplayShapesPair(leftGrid = new DisplayGrid(displayWindow, shapes, slots),
                                                  rightGrid = new DisplayGrid(displayWindow, nonMatchingShapes, slots))

    test("given a game then start it") {
        val game = new GameLogic(gameLevelOne,matchingDisplayShapesPair, displayWindow).start
    }

    test("given a game when user selects match and left and right match and first consecutive correct answer then isUserInputCorrect returns game logic with same level") {
        val userInput = Match

        val gl = new GameLogic(gameLevelOne, matchingDisplayShapesPair, displayWindow).evaluateUserInput(userInput)

        assert (gl.currentLevel == gameLevelOne)
        assert (1== gl.correctAnswers )
    }

    test("given a game when user chooses correctly then points added to score") {
        val userInput = Match
        val score     = Score()

        val gl = new GameLogic(gameLevelOne, matchingDisplayShapesPair, displayWindow, 0, score).evaluateUserInput(userInput)

        assert (gl.currentLevel == gameLevelOne)

        assert (1== gl.score.points)
    }

    test("given a game when user chooses wrongly then points deducted") {
        val userInput = Mismatch
        val score     = Score()

        val gl = new GameLogic(gameLevelOne, matchingDisplayShapesPair, displayWindow, 0, score).evaluateUserInput(userInput)

        assert (gl.currentLevel == gameLevelOne)

        assert (-1 == gl.score.points)
    }

    test("given a game when user selects match and left and right match and n consecutive correct answer then return GameLogic with level increased") {

        val gl = makeNConsecutiveCorrectGuess(REQUIRED_CORRECT_CONSECUTIVE_ANSWERES, new GameLogic(gameLevelOne, matchingDisplayShapesPair, displayWindow))

        assert(LevelTwo == gl.currentLevel)
    }

    test("given a game when user makes 4 consecutive correct guesses then level is increased to three") {

        val gl = makeNConsecutiveCorrectGuess(REQUIRED_CORRECT_CONSECUTIVE_ANSWERES *2 , new GameLogic(gameLevelOne, matchingDisplayShapesPair, displayWindow))

        assert(LevelThree == gl.currentLevel)
    }

    test("given a game when user selects mismatch and left and right match then game remains in the current level") {
        val userInput = Mismatch

        val gl = new GameLogic(gameLevelOne, matchingDisplayShapesPair, displayWindow).evaluateUserInput(userInput)

        assert (gl.currentLevel == gameLevelOne)
    }

    test("given a game when user selects correctly then the next level has shape count corresponding to the level") {
        val userInput = Match

        val gl = makeNConsecutiveCorrectGuess(REQUIRED_CORRECT_CONSECUTIVE_ANSWERES, new GameLogic(gameLevelOne, matchingDisplayShapesPair, displayWindow))

        assert (gl.shapesPair.leftGrid.shapesInGrid  === LevelTwo.shapeCount)
        assert (gl.shapesPair.rightGrid.shapesInGrid === LevelTwo.shapeCount)
    }

    test("given a game when game is not over then isGameOver returns false") {
        assertResult(false){
            new GameLogic(gameLevelOne, matchingDisplayShapesPair, displayWindow).isGameOver
        }
    }

    test("given game logic when gameOver is called return new instance that returns new instance ") {
        assert(new GameLogic(GameLevel(1), matchingDisplayShapesPair, displayWindow).markGameAsFinished.isGameOver)
    }

    test("given game logic when reset is called then a new instance of game logic is returned with initial state") {
        val newGame = new GameLogic(GameLevel(10), matchingDisplayShapesPair, displayWindow).reset

        assert( 1 == newGame.currentLevel.shapeCount)
        assert( 1 == newGame.shapesPair.leftGrid.shapesInGrid)
        assert( 1 == newGame.shapesPair.rightGrid.shapesInGrid)
    }

    private def makeNConsecutiveCorrectGuess(n:Int, gameLogic: GameLogic): GameLogic = {
        def recurse(num: Int, gameLogic: GameLogic): GameLogic = {

            if (num == 0) return gameLogic
            else {
                if (gameLogic.isMatchingPairShapes)
                    recurse(num - 1, gameLogic.evaluateUserInput(Match))
                else
                    recurse(num - 1, gameLogic.evaluateUserInput(Mismatch))
            }

        }

        recurse(n, gameLogic)
    }

}
