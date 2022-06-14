package com.simplektx.game.entity

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.input.CombatInput
import com.simplektx.game.minigame.Swing

class Player : Entity() {
    override val name: String = "Player"
    override val maxHealth: Int = 10
    override val currentHealth: Int = maxHealth

    fun swing(swingInput: CombatInput.SwingInput): Swing {
        return Swing(1, 1, 1000, 10 * swingInput.distance.toLong(),
                     swingInput.start, swingInput.end)
    }
}
