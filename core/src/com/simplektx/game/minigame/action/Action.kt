package com.simplektx.game.minigame.action

import com.simplektx.game.minigame.interaction.Interaction

abstract class Action {
    var state: ActionState = ActionState.PREPARING
    val isPreparing: Boolean get() = state == ActionState.PREPARING
    val isExecuting: Boolean get() = state == ActionState.EXECUTING
    val isFinished: Boolean get() = state == ActionState.FINISHED
    abstract fun update(deltaMs: Long)
    abstract fun interact(other: Action): Interaction
}

enum class ActionState {
    PREPARING, EXECUTING, FINISHED
}