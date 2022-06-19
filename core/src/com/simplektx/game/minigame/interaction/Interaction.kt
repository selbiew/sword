package com.simplektx.game.minigame.interaction

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.action.Action
import kotlin.math.max

abstract class Interaction(val initiator: Action, val receiver: Action, private var executionTimeMs: Long, val displayText: String, val position: Vector2) {
    private var executionTimeRemainingMs: Long = executionTimeMs
    private var state: InteractionState = InteractionState.EXECUTING
    val isExecuting: Boolean get() = state == InteractionState.EXECUTING
    val isFinished: Boolean get() = state == InteractionState.FINISHED
    fun update(deltaMs: Long) {
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

enum class InteractionState {
    EXECUTING, FINISHED
}