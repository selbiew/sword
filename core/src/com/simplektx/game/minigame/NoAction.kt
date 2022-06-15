package com.simplektx.game.minigame

import com.badlogic.gdx.math.Vector2

class NoAction : Action() {
    override fun interact(other: Action): Interaction {
        return when (other) {
            is Swing -> {
                if (other.isFinished) {
                    SwingHit(this, other, 500, Vector2(700f, 700f))
                } else {
                    NoInteraction(this, other)
                }
            }
            else -> {
                NoInteraction(this, other)
            }
        }
    }
    override fun update(deltaMs: Long) {}
}