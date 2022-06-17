package com.simplektx.game.minigame

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.utils.intersects


class Swing(
    damage: Int,
    staminaCost: Int,
    prepTimeMs: Long,
    executionTimeMs: Long,
    val start: Vector2,
    val end: Vector2
) : Attack(damage, staminaCost, prepTimeMs, executionTimeMs) {
    val current: Vector2 get() = start.cpy().add(end.cpy().sub(start.cpy()).scl(executionProgress))
    val direction: Vector2 get() = end.cpy().sub(start.cpy())
    // TODO: Improve
    val force: Float get() = start.dst(end)

    override fun interact(other: Action): Interaction {
        if (isFinished || other.isFinished) {
            when (other) {
                is NoAction -> return NoInteraction(this, other)
                is Swing -> {
                    return if (intersects(this, other)) {
                        SwingParry(this, other, 500, Vector2(700f, 700f))
                    } else {
                        SwingHit(this, other, 500, Vector2(700f, 700f))
                    }
                }
                // TODO: IMplement
                is Stab -> {
                    return NoInteraction(this, other)
                }
            }
        }

        return NoInteraction(this, other)
    }
}