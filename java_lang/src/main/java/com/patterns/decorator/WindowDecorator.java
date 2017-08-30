package com.patterns.decorator;

/**
 * Created by abelard on 9/4/16.
 */
abstract class WindowDecorator implements Window {

    protected Window windowToBeDecoratored;

    public WindowDecorator(Window windowToBeDecoratored) {
        this.windowToBeDecoratored = windowToBeDecoratored;
    }

    public void draw() {
        windowToBeDecoratored.draw();
    }

    public String getDescription() {
        return windowToBeDecoratored.getDescription();
    }
}
