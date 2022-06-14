package com.simplektx.game.minigame

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

abstract class Attack(
    val damage: Int,
    val staminaCost: Int,
    val prepTimeMs: Long,
    val executionTimeMs: Long
) : Action() {
    var timeElapsedMs: Long = 0
    var prepTimeRemainingMs: Long = prepTimeMs
    var executionTimeRemainingMs: Long = executionTimeMs
    val prepProgress: Float get() = 1F - (prepTimeRemainingMs.toFloat() / prepTimeMs.toFloat())
    val executionProgress: Float get() = 1F - (executionTimeRemainingMs.toFloat() / executionTimeMs.toFloat())
    val totalTimeMs: Long get() = prepTimeMs + executionTimeMs
    val timeRemainingMs: Long get() = prepTimeRemainingMs + executionTimeRemainingMs

    override fun update(deltaMs: Long) {
        when (actionState) {
            ActionState.PREPARING -> {
                prepare(deltaMs)
            }
            ActionState.EXECUTING -> {
                execute(deltaMs)
            }
            ActionState.FINISHED -> {
                // TODO: Not sure
            }
        }
    }

    fun prepare(deltaMs: Long) {
        var deltaRemainderMs = abs(min(0, prepTimeRemainingMs - deltaMs))
        prepTimeRemainingMs =  max(0, prepTimeRemainingMs - deltaMs)
        if (prepTimeRemainingMs == 0L) {
            actionState = ActionState.EXECUTING
            execute(deltaRemainderMs)
        }
    }

    fun execute(deltaMs: Long) {
//        var deltaRemainderMs = abs(min(0, executionTimeRemainingMs - deltaMs))
        executionTimeRemainingMs =  max(0, executionTimeRemainingMs - deltaMs)
        if (executionTimeRemainingMs == 0L) {
            actionState = ActionState.FINISHED
        }
    }
}