package com.alvinalexander.bubbles

import java.awt.Color

case class Bubble (
    x: Int, 
    y: Int, 
    lastX: Int, 
    lastY: Int,
    circleDiameter: Int,
    fgColor: Color,
    bgColor: Color,
    char: Char
)

