package com.simplektx.game.minigame

abstract class Action {
    var actionState: ActionState = ActionState.PREPARING
    val isPreparing: Boolean get() = actionState == ActionState.PREPARING
    val isExecuting: Boolean get() = actionState == ActionState.EXECUTING
    val isFinished: Boolean get() = actionState == ActionState.FINISHED
    abstract fun update(deltaMs: Long)
}

enum class ActionState {
    PREPARING, EXECUTING, FINISHED
}