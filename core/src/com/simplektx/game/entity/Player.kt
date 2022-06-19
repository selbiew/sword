package com.simplektx.game.entity

import com.simplektx.game.input.CombatInput
import com.simplektx.game.minigame.action.Stab
import com.simplektx.game.minigame.action.Swing

class Player : Entity() {
    override val name: String = "Player"
    override val maxHealth: Int = 10
    override val currentHealth: Int = maxHealth

    fun swing(swingInput: CombatInput.SwingInput): Swing {
        return Swing(1, 1, 1000, 10 * swingInput.distance.toLong(),
                     swingInput.start, swingInput.end)
    }

    fun stab(stabInput: CombatInput.StabInput): Stab {
        // TODO: Fix executionTime
        return Stab(1, 1, 1000, 1000,
            stabInput.center)
    }
}
