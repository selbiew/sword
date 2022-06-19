package com.simplektx.game.input

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.simplektx.game.minigame.CombatMinigame
import kotlin.math.min

class CombatInputProcessor(private val combatMinigame: CombatMinigame) : InputProcessor {
    private var currentStart = Vector2()
    private var inputStartTimeMs = TimeUtils.millis()
//    var lines: MutableList<Line> = mutableListOf()

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
        inputStartTimeMs = TimeUtils.millis()
        return false
    }

    override fun touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        val end = Vector2(x.toFloat(), y.toFloat())
        if (currentStart.dst(end) <= 5.0) {
            emitCombatInput(CombatInput.StabInput(end, min(500f, (TimeUtils.timeSinceMillis(inputStartTimeMs) / 50.0).toFloat())))
        } else {
            emitCombatInput(CombatInput.SwingInput(currentStart, end))
        }
//        lines.add(Line(currentStart, Vector2(x.toFloat(), y.toFloat())))
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

    private fun emitCombatInput(combatInput: CombatInput) {
        combatMinigame.receive(combatInput)
    }
}

sealed class CombatInput {
    class SwingInput(val start: Vector2, val end: Vector2): CombatInput() {
        val distance: Float get() = start.dst(end)
    }

    class StabInput(val center: Vector2, val force: Float): CombatInput()
}