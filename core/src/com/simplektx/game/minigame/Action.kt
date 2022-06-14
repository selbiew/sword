package com.simplektx.game.minigame

abstract class Action {
    var actionState: ActionState = ActionState.PREPARING
    abstract fun update(deltaMs: Long)
}

enum class ActionState {
    PREPARING, EXECUTING, FINISHED
}