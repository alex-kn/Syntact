package com.alexkn.syntact.crosswordpuzzle.model;


enum Axis {
    X {
        @Override
        public Axis opposite() {
            return Axis.Y;
        }

        @Override
        public int index() {
            return 0;
        }
    },

    Y {
        @Override
        public Axis opposite() {
            return Axis.X;
        }

        @Override
        public int index() {
            return 1;
        }
    },

    XY {
        @Override
        public Axis opposite() {
            return Axis.NONE;
        }

        @Override
        public int index() {
            return -1;
        }
    },

    NONE {
        @Override
        public Axis opposite() {
            return Axis.XY;
        }

        @Override
        public int index() {
            return -1;
        }
    };

    public abstract Axis opposite();

    public abstract int index();
}

