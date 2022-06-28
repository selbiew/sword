package com.simplektx.game.minigame.action

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

abstract class Attack(
    val damage: Int,
    val staminaCost: Int,
    private val prepTimeMs: Long,
    private val executionTimeMs: Long
) : Action() {
    var timeElapsedMs: Long = 0
    private var prepTimeRemainingMs: Long = prepTimeMs
    private var executionTimeRemainingMs: Long = executionTimeMs
    val prepProgress: Float get() = 1F - (prepTimeRemainingMs.toFloat() / prepTimeMs.toFloat())
    val executionProgress: Float get() = 1F - (executionTimeRemainingMs.toFloat() / executionTimeMs.toFloat())
    val totalTimeMs: Long get() = prepTimeMs + executionTimeMs
    val timeRemainingMs: Long get() = prepTimeRemainingMs + executionTimeRemainingMs

    override fun update(deltaMs: Long) {
        when (state) {
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

    private fun prepare(deltaMs: Long) {
        val deltaRemainderMs = abs(min(0, prepTimeRemainingMs - deltaMs))
        prepTimeRemainingMs =  max(0, prepTimeRemainingMs - deltaMs)
        if (prepTimeRemainingMs == 0L) {
            state = ActionState.EXECUTING
            execute(deltaRemainderMs)
        }
    }

    private fun execute(deltaMs: Long) {
        executionTimeRemainingMs =  max(0, executionTimeRemainingMs - deltaMs)
        if (executionTimeRemainingMs == 0L) {
            state = ActionState.FINISHED
        }
    }
}