package com.simplektx.game.enemy

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.Line
import kotlin.random.Random

class AttackGenerator {
    var lines: MutableList<Line> = mutableListOf()
    var timeSinceLastLineMs: Long = 0
    // Per Second
    var attackFrequency: Float = 0.5F

    fun update(delta: Float) {
        var deltaMs = (1000 * delta).toLong()
        timeSinceLastLineMs += deltaMs
        if (timeSinceLastLineMs > (1000 / attackFrequency)) {
            val start = Vector2(Random.nextFloat() * 800, Random.nextFloat() * 800)
            val end = Vector2(Random.nextFloat() * 800, Random.nextFloat() * 800)
            lines.add(Line(start, end))
            timeSinceLastLineMs = 0
        }
    }
}