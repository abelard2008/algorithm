package com.annotation.todo;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
/**
 * Annotation type to indicate a task still needs to be
 *   completed.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface TODO {
  String value();
}
