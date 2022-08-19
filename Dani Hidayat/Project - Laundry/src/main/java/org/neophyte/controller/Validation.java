package org.neophyte.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Validation {

    public static void numericOnly(final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    public static void textOnly(final TextField field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("[ .'a-zA-Z]+")) {
                    field.setText(newValue.replaceAll("[^ .'a-zA-Z]", ""));
                }
            }
        });
    }
    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override/*from ww  w  .  jav  a 2 s.co m*/
            public void changed(final ObservableValue<? extends String> ov,
                                final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    public static void addMask(final TextField tf, final String mask) {
        tf.setText(mask);
//         addTextLimiter(tf, mask.length());

        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov,
                                final String oldValue, final String newValue) {

            }
        });

        tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent e) {
                int caretPosition = tf.getCaretPosition();
                String value = stripMask(tf.getText(), mask);
                tf.setText(merge(value, mask));
                if (caretPosition < mask.length() - 1
                        && mask.charAt(caretPosition) != ' '
                        && e.getCode() != KeyCode.BACK_SPACE
                        && e.getCode() != KeyCode.LEFT) {
                    tf.positionCaret(caretPosition + 1);
                } else {
                    tf.positionCaret(caretPosition);
                }
            }
        });
    }

    static String stripMask(String text, final String mask) {
        final Set<String> maskChars = new HashSet<>();
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c != ' ') {
                maskChars.add(String.valueOf(c));
            }
        }
        for (String c : maskChars) {
            text = text.replace(c, "");
        }
        return text;
    }

    static String merge(final String value, final String mask) {
        final StringBuilder sb = new StringBuilder(mask);
        System.out.println(value);
        int k = 0;
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == ' ' && k < value.length()) {
                sb.setCharAt(i, value.charAt(k));
                k++;
            }
        }
        return sb.toString();
    }

}
