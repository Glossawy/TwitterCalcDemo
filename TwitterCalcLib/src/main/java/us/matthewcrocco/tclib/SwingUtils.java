package us.matthewcrocco.tclib;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Various Swing Utilities I hid away as kind of long for what they did.
 * <p>
 * Or kind of hacky.
 *
 * @author Matthew Crocco
 */
public final class SwingUtils {

    private SwingUtils() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds padding to a component by creating a Compound Border using
     * the current border and a new empty border with the specified side widths.
     *
     * @param comp   Component to Pad
     * @param top    Top Padding
     * @param right  Right Padding
     * @param bottom Bottom Padding
     * @param left   Left Padding
     */
    public static void addPadding(JComponent comp, int top, int right, int bottom, int left) {
        comp.setBorder(BorderFactory.createCompoundBorder(comp.getBorder(), BorderFactory.createEmptyBorder(top, left, bottom, right)));
    }

    /**
     * Adds padding to a component by creating a Compound Border using
     * the current border and a new empty border with the specified side widths.
     *
     * @param comp      Component to Pad
     * @param topbottom Top and Bottom Padding
     * @param leftright Left and Right Padding
     */
    public static void addPadding(JComponent comp, int topbottom, int leftright) {
        addPadding(comp, topbottom, leftright, topbottom, leftright);
    }

    /**
     * Adds padding to a component by creating a Compound Border using
     * the current border and a new empty border with the specified side widths.
     *
     * @param comp Component to Pad
     * @param all  Padding for All Sides
     */
    public static void addPadding(JComponent comp, int all) {
        addPadding(comp, all, all, all, all);
    }

    /**
     * Parses a 6-character (RGB) or 8-character (RGBA) hexadecimal string
     * with or without a '#' prefix into an RGBA {@link Color}.
     *
     * @param hex Hexadecimal String
     * @return RGBA {@link java.awt.Color}
     */
    public static Color hexColor(String hex) {
        hex = hex.trim();

        // Remove Prefixed #
        while (hex.startsWith("#"))
            hex = hex.substring(1);

        // Must be 6 or 8 characters
        if (hex.length() < 6)
            throw new IllegalArgumentException("Hexadecimal String must be of length >5, current length: " + hex.length());
        else if (hex.length() != 6 && hex.length() != 8)
            throw new IllegalArgumentException("Hexadecimal Color String must be of length 6 (rgb) or 8 (rgba)");

        float r, g, b, a = 1.0f;

        // Convert RGB Hex to RGB values from 0 to 1.0
        r = Integer.parseInt(hex.substring(0, 2), 16) / 255.f;
        g = Integer.parseInt(hex.substring(2, 4), 16) / 255.f;
        b = Integer.parseInt(hex.substring(4, 6), 16) / 255.f;

        // Convert Alpha Hex to between 0 and 1.0
        if (hex.length() == 8)
            a = Integer.parseInt(hex.substring(6, 8), 16) / 255.f;

        // Clamp all values between 0 and 1
        // Equivalent to min(max, max(min, value))
        r = clamp(r, 0, 1.f);
        g = clamp(g, 0, 1.f);
        b = clamp(b, 0, 1.f);
        a = clamp(a, 0, 1.f);

        return new Color(r, g, b, a);
    }

    /**
     * Reflectively gets all Fields in {@link Color} and grabs the names
     * of all the color fields.
     *
     * @return List of Color Names
     */
    public static List<String> getAllColors() {
        Field[] fields = Color.class.getDeclaredFields();
        Set<String> colorNames = new HashSet<>();

        for (Field f : fields)
            if (f.getType() == Color.class)
                colorNames.add(f.getName().toUpperCase());
        return new ArrayList<>(colorNames);
    }

    /**
     * Takes a component and a color name, gets the color name from {@link Color}
     * reflectively and sets the background to that color.
     *
     * @param component Component to Modify
     * @param name      Color Name
     */
    public static void setBackgroundColorByName(Frame component, String name) {
        try {
            component.setBackground((Color) Color.class.getField(name).get(null));
        } catch (Exception e) {
            throw new IllegalArgumentException("Color by name '" + name + "' does not exist in java.awt.Color!");
        }
    }

    /**
     * Takes a component and a color name, gets the color name from {@link Color}
     * reflectively and sets the background to that color.
     *
     * @param component Component to Modify
     * @param name      Color Name
     */
    public static void setBackgroundColorByName(Container component, String name) {
        try {
            component.setBackground((Color) Color.class.getField(name).get(null));
        } catch (Exception e) {
            throw new IllegalArgumentException("Color by name '" + name + "' does not exist in java.awt.Color!");
        }
    }

    /**
     * Takes a component and a color name, gets the color name from {@link Color}
     * reflectively and sets the background to that color.
     *
     * @param component Component to Modify
     * @param name      Color Name
     */
    public static void setBackgroundColorByName(JComponent component, String name) {
        try {
            component.setBackground((Color) Color.class.getField(name).get(null));
        } catch (Exception e) {
            throw new IllegalArgumentException("Color by name '" + name + "' does not exist in java.awt.Color!");
        }
    }

    /**
     * Constrains a value between the given min (lower bound) and max
     * (upper bound) values. Equivalent to max(minValue, min(maxValue, value)).
     *
     * @param val Value to Constrain
     * @param min Minimum Value (Lower Bound)
     * @param max Maximum Value (Upper Bound)
     * @return Constrained Value
     */
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(val, max));
    }

    /**
     * Constrains a value between the given min (lower bound) and max
     * (upper bound) values. Equivalent to max(minValue, min(maxValue, value)).
     *
     * @param val Value to Constrain
     * @param min Minimum Value (Lower Bound)
     * @param max Maximum Value (Upper Bound)
     * @return Constrained Value
     */
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(val, max));
    }

    /**
     * Constrains a value between the given min (lower bound) and max
     * (upper bound) values. Equivalent to max(minValue, min(maxValue, value)).
     *
     * @param val Value to Constrain
     * @param min Minimum Value (Lower Bound)
     * @param max Maximum Value (Upper Bound)
     * @return Constrained Value
     */
    public static long clamp(long val, long min, long max) {
        return Math.max(min, Math.min(val, max));
    }

    /**
     * Constrains a value between the given min (lower bound) and max
     * (upper bound) values. Equivalent to max(minValue, min(maxValue, value)).
     *
     * @param val Value to Constrain
     * @param min Minimum Value (Lower Bound)
     * @param max Maximum Value (Upper Bound)
     * @return Constrained Value
     */
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(val, max));
    }

}
