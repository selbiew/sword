package com.simplektx.game

import com.badlogic.gdx.math.Vector2
import kotlin.math.max
import kotlin.math.min

class Line(val start: Vector2, val end: Vector2) {
    private val durationMs: Long = 1000
    var timeRemainingMs: Long = durationMs

    fun update(delta: Float) {
        val deltaMs = (delta * 1000).toLong()
        timeRemainingMs -= deltaMs
    }

    // Given three collinear points p, q, r, the function checks if
    // point q lies on line segment 'pr'
    private fun onSegment(p: Vector2, q: Vector2, r: Vector2): Boolean {
        return (q.x <= max(p.x, r.x)
                && q.x >= min(p.x, r.x)
                && q.y <= max(p.y, r.y)
                && q.y >= min(p.y, r.y))
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    private fun orientation(p: Vector2, q: Vector2, r: Vector2): Int {
        // See https://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        val v: Float = (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y)
        if (v == 0f) return 0 // collinear
        return if (v > 0) 1 else 2 // clock or counterclock wise
    }

    // The main function that returns true if line segment 'p1q1'
    // and 'p2q2' intersect.
    fun intersects(line: Line): Boolean {
        // Find the four orientations needed for general and special cases
        val o1 = orientation(start, end, line.start)
        val o2 = orientation(start, end, line.end)
        val o3 = orientation(line.start, line.end, start)
        val o4 = orientation(line.start, line.end, end)

        // General case
        if (o1 != o2 && o3 != o4) return true

        // Special Cases
        // p1, q1 and p2 are collinear and p2 lies on segment p1q1
        if (o1 == 0 && onSegment(start, line.start, end)) return true

        // p1, q1 and q2 are collinear and q2 lies on segment p1q1
        if (o2 == 0 && onSegment(start, line.end, end)) return true

        // p2, q2 and p1 are collinear and p1 lies on segment p2q2
        if (o3 == 0 && onSegment(line.start, start, line.end)) return true

        // p2, q2 and q1 are collinear and q1 lies on segment p2q2
        if (o4 == 0 && onSegment(line.start, end, line.end)) return true

        // Matches no above cases
        return false
    }

    override fun toString(): String {
        return "start: ${start}, end: ${end}, durationMs: $durationMs, timeRemainingMs: $timeRemainingMs"
    }
}