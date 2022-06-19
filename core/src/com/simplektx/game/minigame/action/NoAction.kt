package com.simplektx.game.minigame.action

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.interaction.Hit
import com.simplektx.game.minigame.interaction.Interaction
import com.simplektx.game.minigame.interaction.NoInteraction

class NoAction : Action() {
    override fun interact(other: Action): Interaction {
        if (other.isFinished) {
            return when (other) {
                is Attack -> {
                    Hit(other, this, 500, Vector2(700f, 700f))
                } else -> {
                    // TODO: Include block interaction
                    NoInteraction(this, other)
                }
            }
        }

        return NoInteraction(this, other)
    }

    override fun update(deltaMs: Long) {}
}