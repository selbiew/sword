package com.simplektx.game

import com.badlogic.gdx.math.Vector2

class Line(val start: Vector2, val end: Vector2) {
    private val durationMs: Long = 1000
    var timeRemainingMs: Long = durationMs

    fun update(delta: Float) {
        val deltaMs = (delta * 1000).toLong()
        timeRemainingMs -= deltaMs
    }

    override fun toString(): String {
        return "start: ${start}, end: ${end}, durationMs: $durationMs, timeRemainingMs: $timeRemainingMs"
    }
}