package com.simplektx.game.minigame

import com.badlogic.gdx.math.Vector2

abstract class Interaction(val action: Action, val other: Action, var executionTimeMs: Long, val displayText: String, val position: Vector2) {
    var executionTimeRemainingMs: Long = executionTimeMs
    var state: InteractionState = InteractionState.EXECUTING
    val isPreparing: Boolean get() = state == InteractionState.PREPARING
    val isExecuting: Boolean get() = state == InteractionState.EXECUTING
    val isFinished: Boolean get() = state == InteractionState.FINISHED
    abstract fun update(deltaMs: Long)
}

enum class InteractionState {
    PREPARING, EXECUTING, FINISHED
}