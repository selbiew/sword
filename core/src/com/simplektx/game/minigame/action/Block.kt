package com.simplektx.game.minigame.action

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.interaction.*
import com.simplektx.game.utils.intersects
import ktx.math.ImmutableVector2
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Block(val force: Float, val start: ImmutableVector2, val end: ImmutableVector2) : Action() {
    var prepTimeMs: Long = 500
    var executionTimeMs: Long = 1000
    var timeElapsedMs: Long = 0
    private var prepTimeRemainingMs: Long = prepTimeMs
    private var executionTimeRemainingMs: Long = executionTimeMs
    val prepProgress: Float get() = 1F - (prepTimeRemainingMs.toFloat() / prepTimeMs.toFloat())
    val executionProgress: Float get() = 1F - (executionTimeRemainingMs.toFloat() / executionTimeMs.toFloat())
    val totalTimeMs: Long get() = prepTimeMs + executionTimeMs
    val timeRemainingMs: Long get() = prepTimeRemainingMs + executionTimeRemainingMs
    val current: ImmutableVector2 get() = start + ((end - start) * executionProgress)
    val direction: ImmutableVector2 get() = end - start

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

    override fun interact(other: Action): Interaction {
        if (other.isFinished) {
            when (other) {
                is NoAction -> return NoInteraction(this, other)
                is Attack -> {
                    return when {
                        isPreparing -> {
                            Hit(other, this, 500, Vector2(700f, 700f))
                        }
                        intersects(this, other) -> {
                            BlockHit(other, this, 500, Vector2(700f, 700f))
                        }
                        else -> {
                            Hit(other, this, 500, Vector2(700f, 700f))
                        }
                    }
                }
            }
        }

        if (isFinished) {
            return BlockMiss(this, other)
        }

        return NoInteraction(this, other)
    }
}