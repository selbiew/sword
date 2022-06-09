package com.simplektx.game.input

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.simplektx.game.Line

class CombatInputProcessor : InputProcessor {
    private var currentStart = Vector2()
    var lines: MutableList<Line> = mutableListOf()

    override fun keyDown(keycode: Int): Boolean {
        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        return false
    }

    override fun keyTyped(character: Char): Boolean {
        return false
    }

    override fun touchDown(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        currentStart = Vector2(x.toFloat(), y.toFloat())
        return false
    }

    override fun touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        lines.add(Line(currentStart, Vector2(x.toFloat(), y.toFloat())))
        return false
    }

    override fun touchDragged(x: Int, y: Int, pointer: Int): Boolean {
        return false
    }

    override fun mouseMoved(x: Int, y: Int): Boolean {
        return false
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        return false
    }
}