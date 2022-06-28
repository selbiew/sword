package com.simplektx.game.input

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.utils.TimeUtils
import com.simplektx.game.minigame.CombatMinigame
import ktx.math.ImmutableVector2
import ktx.math.dst

class CombatInputProcessor(private val combatMinigame: CombatMinigame) : InputProcessor {
    private var currentStart = ImmutableVector2.ZERO
    private var inputStartTimeMs = TimeUtils.millis()
    private var touchDownButton: Int? = null
    private var isTouchDown: Boolean = false

    private val inputDurationMs: Long get() = TimeUtils.millis() - inputStartTimeMs

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
            emitPlannedActionInput(CombatInput.StabInput(currentStart, inputDurationMs))
        }
        return false
    }

    override fun touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean {
        if (button == touchDownButton) {
            val end = ImmutableVector2(x.toFloat(), y.toFloat())
            if (touchDownButton == Input.Buttons.RIGHT) {
                emitCombatInput(CombatInput.BlockInput(currentStart, end, inputDurationMs))
            } else if (touchDownButton == Input.Buttons.LEFT) {
                if (currentStart.dst(end) <= 5.0) {
                    emitCombatInput(
                        CombatInput.StabInput(
                            end,
                            inputDurationMs)
                        )
                } else {
                    emitCombatInput(CombatInput.SwingInput(currentStart, end, inputDurationMs))
                }
            }
            isTouchDown = false
        }
        emitPlannedActionInput(null)

        return false
    }

    override fun touchDragged(x: Int, y: Int, pointer: Int): Boolean {
        val currentEnd = ImmutableVector2(x.toFloat(), y.toFloat())
        if (touchDownButton == Input.Buttons.RIGHT) {
            emitPlannedActionInput(CombatInput.BlockInput(currentStart, currentEnd, inputDurationMs))
        } else if (touchDownButton == Input.Buttons.LEFT) {
            if (currentStart.dst(currentEnd) <= 5.0) {
                emitPlannedActionInput(
                    CombatInput.StabInput(
                        currentEnd,
                        inputDurationMs
                    )
                )
            } else {
                emitPlannedActionInput(CombatInput.SwingInput(currentStart, currentEnd, inputDurationMs))
            }
        }
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

    private fun emitPlannedActionInput(combatInput: CombatInput?) {
        combatMinigame.updatePlannedAction(combatInput)
    }
}

sealed class CombatInput {
    class SwingInput(val start: ImmutableVector2, val end: ImmutableVector2, val duration: Long): CombatInput() {
        val distance: Float get() = start.dst(end)
    }

    class StabInput(val center: ImmutableVector2, var duration: Long): CombatInput() {
        fun update(deltaMs: Long) {
            duration += deltaMs
        }
    }


    class BlockInput(val start: ImmutableVector2, val end: ImmutableVector2, val duration: Long): CombatInput() {
        val distance: Float get() = start.dst(end)
    }
}