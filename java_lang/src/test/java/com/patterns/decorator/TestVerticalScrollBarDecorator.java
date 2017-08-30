package com.patterns.decorator;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Created by abelard on 9/4/16.
 */
public class TestVerticalScrollBarDecorator {
    @Test
    public void testWindowDecoratorTest() {
        Window decoratedWindow = new HorizontalScrollBarDecorator(new VerticalScrollBarDecorator(new SimpleWindow()));
        assertEquals("simple window, including vertical scrollbars, including horizontal scrollbars", decoratedWindow.getDescription());
    }
}