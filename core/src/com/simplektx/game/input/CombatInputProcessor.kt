package com.simplektx.game.input

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.simplektx.game.minigame.CombatMinigame
import ktx.math.ImmutableVector2
import ktx.math.dst
import kotlin.math.min

class CombatInputProcessor(private val combatMinigame: CombatMinigame) : InputProcessor {
    private var currentStart = ImmutableVector2.ZERO
    private var inputStartTimeMs = TimeUtils.millis()
    private var touchDownButton: Int? = null
    private var isTouchDown: Boolean = false

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
        if (!isTouchDown) {
            isTouchDown = true
            currentStart = ImmutableVector2(x.toFloat(), y.toFloat())
            inputStartTimeMs = TimeUtils.millis()
            touchDownButton = button
        }
        return false
    }

    override fun touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        if (button == touchDownButton) {
            val end = ImmutableVector2(x.toFloat(), y.toFloat())
            if (touchDownButton == Input.Buttons.RIGHT) {
                emitCombatInput(CombatInput.BlockInput(currentStart, end))
            } else if (touchDownButton == Input.Buttons.LEFT) {
                if (currentStart.dst(end) <= 5.0) {
                    emitCombatInput(
                        CombatInput.StabInput(
                            end,
                            min(500f, (TimeUtils.timeSinceMillis(inputStartTimeMs) / 50.0).toFloat())
                        )
                    )
                } else {
                    emitCombatInput(CombatInput.SwingInput(currentStart, end))
                }
            }
            isTouchDown = false
        }

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
    class SwingInput(val start: ImmutableVector2, val end: ImmutableVector2): CombatInput() {
        val distance: Float get() = start.dst(end)
    }

    class StabInput(val center: ImmutableVector2, val force: Float): CombatInput()

    class BlockInput(val start: ImmutableVector2, val end: ImmutableVector2): CombatInput() {
        val distance: Float get() = start.dst(end)
    }
}