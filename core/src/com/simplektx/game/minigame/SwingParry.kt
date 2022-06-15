package com.simplektx.game.minigame

import com.badlogic.gdx.math.Vector2
import kotlin.math.max

class SwingParry(action: Action, other: Action, executionTimeMs: Long, position: Vector2) :
    Interaction(action, other, executionTimeMs, "Parry", position) {
    override fun update(deltaMs: Long) {
        when (state) {
            InteractionState.EXECUTING -> {
                executionTimeRemainingMs = max(0, executionTimeRemainingMs - deltaMs)
                if (executionTimeRemainingMs == 0L) {
                    state = InteractionState.FINISHED
                }
            }
        }
    }
}