package com.ld43.game.entity.component;

import com.badlogic.ashley.core.Component;

public class FocusableComponent implements Component {

    public float radius;
    public boolean focused;

    public FocusableComponent(boolean focused, float radius) {
        this.focused = focused;
        this.radius = radius;
    }
}
