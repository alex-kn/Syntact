package com.alexkn.syntact.crosswordpuzzle.common;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction {

    // use magic numbers to set the ordinal (used for rotation),
    // and the dx and dy of each direction.
    NORTH(0, 0, -1),
    EAST(1, 1, 0),
    SOUTH(2, 0, 1),
    WEST(3, -1, 0);

    private static final ThreadLocalRandom rnd = ThreadLocalRandom.current();

    static public Direction randomDirection() {
        return Direction.values()[rnd.nextInt(4)];
    }

    private final int r90index, r180index, r270index;
    private final boolean horizontal, vertical;
    private final int dx, dy;

    private Direction(int ordinal, int dx, int dy) {
        // from the ordinal, dx, and dy, we can calculate all the other constants.
        this.dx = dx;
        this.dy = dy;
        this.horizontal = dx != 0;
        this.vertical = !horizontal;
        this.r90index  = (ordinal + 1) % 4;
        this.r180index = (ordinal + 2) % 4;
        this.r270index = (ordinal + 3) % 4;
    }


    // Rotate 90 degrees clockwise
    public Direction rotate90() {
        return values()[r90index];
    }

    // Rotate 180 degrees
    public Direction rotate180() {
        return values()[r180index];
    }

    // Rotate 270 degrees clockwise (90 counterclockwise)
    public Direction rotate270() {
        return values()[r270index];
    }

    public Boolean isHorizontal() {
        return horizontal;
    }

    public Boolean isVertical() {
        return vertical;
    }

    public int dx(int steps) {
        return dx * steps;
    }

    public int dy(int steps) {
        return dy * steps;
    }

    public int dx(){
        return dx(1);
    }

    public int dy(){
        return dy(1);
    }
    public int forwards_x(int n) {
        return n + dx;
    }

    public int forwards_y(int n) {
        return n + dy;
    }

    public int backwards_x(int n) {
        return n - dx;
    }

    public int backwards_y(int n) {
        return n - dy;
    }

    public int left_x(int n) {
        // if we are E/W facing, our left/right 'x' co-ords are still 'x'
        // if we are N/S facing, our left/right 'x' is `-dy`
        return n - dy;
    }

    public int left_y(int n) {
        // see left_x comments for the idea
        return n + dx;
    }

    public int right_x(int n) {
        // see left_x comments for the idea
        return n + dy;
    }

    public int right_y(int n) {
        // see left_x comments for the idea
        return n - dx;
    }
}