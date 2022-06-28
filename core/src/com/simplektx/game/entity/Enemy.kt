package com.simplektx.game.entity

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.action.Action
import com.simplektx.game.minigame.action.NoAction
import com.simplektx.game.minigame.action.Stab
import com.simplektx.game.minigame.action.Swing
import ktx.math.ImmutableVector2
import ktx.math.dst
import kotlin.random.Random

class Enemy : Entity() {
    override val name: String = "Enemy"
    override val maxHealth: Int = 5
    override val currentHealth: Int = maxHealth
    var action: Action = NoAction()

    fun update(deltaMs: Long) {
        if (action is NoAction) {
            action = if (Random.nextBoolean()) {
                swing()
            } else {
                stab()
            }
        }
    }

    private fun swing(): Swing {
        val start = ImmutableVector2(Random.nextFloat() * 800, Random.nextFloat() * 800)
        val end = ImmutableVector2(Random.nextFloat() * 800, Random.nextFloat() * 800)
        return Swing(1, 1, 1000, 10 * start.dst(end).toLong(), start, end)
    }

    private fun stab(): Stab {
        val center = ImmutableVector2(Random.nextFloat() * 800, Random.nextFloat() * 800)
        return Stab(1, 1, 1000, 1000, center)
    }
}