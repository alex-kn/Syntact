package com.alexkn.syntact.hangwords.util;

public class Payload {

    private boolean solved;

    public Payload(boolean solved) {
        this.solved = solved;
    }

    public boolean isSolved() {
        return solved;
    }
}
