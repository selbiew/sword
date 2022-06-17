package com.simplektx.game.minigame

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.utils.intersects

class Stab(damage: Int, staminaCost: Int, prepTimeMs: Long, executionTimeMs: Long, val center: Vector2)
    : Attack(damage, staminaCost, prepTimeMs, executionTimeMs) {
    val endRadius: Int = 20
    val currentRadius: Int get() = (endRadius * executionProgress).toInt()
    override fun interact(other: Action): Interaction {
        if (isFinished || other.isFinished) {
            when (other) {
                // BUG?
                is NoAction -> return NoInteraction(this, other)
                is Swing -> {
                    return if (intersects(this, other)) {
                        StabParry(this, other, 500, Vector2(700f, 700f))
                    } else {
                        StabHit(this, other, 500, Vector2(700f, 700f))
                    }
                }
                // TODO: Implement
                is Stab -> {
                    return NoInteraction(this, other)
                }
            }
        }

        return NoInteraction(this, other)
    }
}