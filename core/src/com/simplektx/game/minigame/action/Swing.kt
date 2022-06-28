package com.simplektx.game.minigame.action

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.interaction.Hit
import com.simplektx.game.minigame.interaction.Interaction
import com.simplektx.game.minigame.interaction.NoInteraction
import com.simplektx.game.minigame.interaction.Parry
import com.simplektx.game.utils.intersects
import ktx.math.ImmutableVector2
import ktx.math.dst


class Swing(
    damage: Int,
    staminaCost: Int,
    prepTimeMs: Long,
    executionTimeMs: Long,
    val start: ImmutableVector2,
    val end: ImmutableVector2
) : Attack(damage, staminaCost, prepTimeMs, executionTimeMs) {
    val current: ImmutableVector2 get() = start + ((end - start) * executionProgress)
    val direction: ImmutableVector2 get() = end - start
    // TODO: Improve
    val force: Float get() = start.dst(end)

    override fun interact(other: Action): Interaction {
        if (isFinished) {
            if (other.isPreparing) {
                return Hit(this, other, 500, Vector2(700f, 700f))
            }

            when (other) {
                is NoAction -> return Hit(this, other, 500, Vector2(700f, 700f))
                is Attack -> {
                    return if (intersects(this, other)) {
                        Parry(this, other, 500, Vector2(700f, 700f))
                    } else {
                        Hit(this, other, 500, Vector2(700f, 700f))
                    }
                }
            }
        }

        if (other.isFinished) {
            when (other) {
                is NoAction -> return NoInteraction(this, other)
                is Attack -> {
                    return when {
                        isPreparing -> {
                            Hit(other, this, 500, Vector2(700f, 700f))
                        }
                        intersects(this, other) -> {
                            Parry(other, this, 500, Vector2(700f, 700f))
                        }
                        else -> {
                            Hit(other, this, 500, Vector2(700f, 700f))
                        }
                    }
                }
            }
        }

        return NoInteraction(this, other)
    }
}