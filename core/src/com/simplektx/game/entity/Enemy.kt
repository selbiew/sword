package com.simplektx.game.entity

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.Action
import com.simplektx.game.minigame.Swing
import kotlin.random.Random

class Enemy : Entity() {
    override val name: String = "Enemy"
    override val maxHealth: Int = 5
    override val currentHealth: Int = maxHealth
    var action: Action? = null

    fun update(deltaMs: Long) {
        action?.update(deltaMs)
        if (action == null || action!!.isFinished) {
            action = swing()
        }
    }

    fun swing(): Swing {
        val start = Vector2(Random.nextFloat() * 800, Random.nextFloat() * 800)
        val end = Vector2(Random.nextFloat() * 800, Random.nextFloat() * 800)
        return Swing(1, 1, 1000, 10 * start.dst(end).toLong(), start, end)
    }
}