package com.simplektx.game.minigame

import com.badlogic.gdx.math.Vector2

class Swing(
    damage: Int,
    staminaCost: Int,
    prepTimeMs: Long,
    executionTimeMs: Long,
    val start: Vector2,
    val end: Vector2
) : Attack(damage, staminaCost, prepTimeMs, executionTimeMs) {
    val current: Vector2 get() = start.cpy().add(end.cpy().sub(start.cpy()).scl(executionProgress))
    // TODO: Improve
    val force: Float get() = start.dst(end)
}