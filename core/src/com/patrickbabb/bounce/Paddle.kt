package com.patrickbabb.bounce

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

class Paddle: ShapeRenderer() {
    var yPosition = 0f
    var xPosition = 0f
    var ySpeed = 5
    var width = 16f
    var height = 100f
}