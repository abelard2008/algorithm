package com.patterns.decorator;

/**
 * Created by abelard on 9/4/16.
 */
public class VerticalScrollBarDecorator extends WindowDecorator {
    public VerticalScrollBarDecorator(Window windowToBeDecoratored) {
        super(windowToBeDecoratored);
    }

    @Override
    public void draw() {
        super.draw();
        drawVericalScrollBar();
    }

    private void drawVericalScrollBar() {

    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", including vertical scrollbars";
    }
}
