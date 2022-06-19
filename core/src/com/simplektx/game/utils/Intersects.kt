package com.simplektx.game.utils

import com.badlogic.gdx.math.Vector2
import com.simplektx.game.minigame.action.Attack
import com.simplektx.game.minigame.action.Stab
import com.simplektx.game.minigame.action.Swing
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

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
fun intersects(swing: Swing, otherSwing: Swing): Boolean {
    // Find the four orientations needed for general and special cases
    val o1 = orientation(swing.start, swing.end, otherSwing.start)
    val o2 = orientation(swing.start, swing.end, otherSwing.end)
    val o3 = orientation(otherSwing.start, otherSwing.end, swing.start)
    val o4 = orientation(otherSwing.start, otherSwing.end, swing.end)

    // General case
    if (o1 != o2 && o3 != o4) return true

    // Special Cases
    // p1, q1 and p2 are collinear and p2 lies on segment p1q1
    if (o1 == 0 && onSegment(swing.start, otherSwing.start, swing.end)) return true

    // p1, q1 and q2 are collinear and q2 lies on segment p1q1
    if (o2 == 0 && onSegment(swing.start, otherSwing.end, swing.end)) return true

    // p2, q2 and p1 are collinear and p1 lies on segment p2q2
    if (o3 == 0 && onSegment(otherSwing.start, swing.start, otherSwing.end)) return true

    // p2, q2 and q1 are collinear and q1 lies on segment p2q2
    if (o4 == 0 && onSegment(otherSwing.start, swing.end, otherSwing.end)) return true

    // Matches no above cases
    return false
}

// https://stackoverflow.com/questions/1073336/circle-line-segment-collision-detection-algorithm
fun intersects(stab: Stab, swing: Swing): Boolean {
    val f = swing.start.cpy().sub(stab.center.cpy())
    val a = swing.direction.dot(swing.direction)
    val b = 2 * f.dot(swing.direction)
    val c = f.dot(f) - (stab.endRadius * stab.endRadius)

    var discriminant = b * b - 4 * a * c
    if (discriminant < 0) {
        return false
    } else {
        discriminant = sqrt(discriminant)

        // either solution may be on or off the ray so need to test both
        // t1 is always the smaller value, because BOTH discriminant and
        // a are nonnegative.
        val t1 = (-b - discriminant) / (2 * a)
        val t2 = (-b + discriminant) / (2 * a)

        // 4x HIT cases:
        //          -o->             --|-->  |            |  --|->                  | -> |
        // Impale(t1 hit,t2 hit), Poke(t1 hit,t2>1), ExitWound(t1<0, t2 hit), CompletelyInside(t1<0, t2>1)

        // 2x MISS cases:
        //       ->  o                     o ->
        // FallShort (t1>1,t2>1), Past (t1<0,t2<0)

        if (t1 in 0.0..1.0) {
            // t1 is the intersection, and it's closer than t2
            // (since t1 uses -b - discriminant)
            // Impale, Poke
            println("Impale, poke")
            return true
        }

        // here t1 didn't intersect so we are either started
        // inside the sphere or completely past it
        if (t2 in 0.0..1.0) {
            // ExitWound
            println("exit wound")
            return true
        }

        // Check for completely inside
        if (swing.start.dst(stab.center) <= stab.endRadius && swing.end.dst(stab.center) <= stab.endRadius) {
            println("Inside")
            return true
        }

        // no intn: FallShort, Past
        println("fallshort or past")
        return false
    }
}

fun intersects(stab: Stab, otherStab: Stab): Boolean {
    return (stab.center.dst(otherStab.center) <= (stab.endRadius + otherStab.endRadius))
}

fun intersects(attack: Attack, otherAttack: Attack): Boolean {
    return when (attack) {
        is Swing -> {
            return when (otherAttack) {
                is Swing -> intersects(attack, otherAttack)
                is Stab -> intersects(otherAttack, attack)
                else -> false
            }
        }
        is Stab -> {
            return when (otherAttack) {
                is Swing -> intersects(attack, otherAttack)
                is Stab -> intersects(attack, otherAttack)
                else -> false
            }
        }
        else -> false
    }
}
