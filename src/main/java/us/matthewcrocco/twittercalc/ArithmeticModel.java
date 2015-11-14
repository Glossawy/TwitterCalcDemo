package us.matthewcrocco.twittercalc;

import javax.swing.JButton;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

/**
 * Manages the state and mathematical data of the Calculator. Keeps track of current value and the operations being
 * conducted.
 *
 * @author Matthew Crocco
 */
public class ArithmeticModel {

    /**
     * Defines a mathematical operation on two double values.
     */
    public enum Operation implements DoubleBinaryOperator {
        ADDITION {
            @Override public double applyAsDouble(double left, double right) {
                return left + right;
            }
        },
        SUBTRACTION {
            @Override public double applyAsDouble(double left, double right) {
                return left - right;
            }
        },
        MULTIPLICATION {
            @Override public double applyAsDouble(double left, double right) {
                return left * right;
            }
        },
        DIVISION {
            @Override public double applyAsDouble(double left, double right) {
                return left / right;
            }
        }
    }


    private double currentValue = Double.NaN;
    private Operation currentOp = null;
    private boolean shouldClearInput = false;

    /**
     * Resets the state of the Calculator, as if no values were ever entered.
     */
    public void reset() {
        this.currentValue = Double.NaN;
        currentOp = null;
    }

    public void setValue(double value) {
        this.currentValue = value;
    }

    public void setState(double value) {
        this.setState(value, null);
    }

    public void setState(double value, Operation op) {
        this.currentValue = value;
        this.currentOp = op;
    }

    public double getValue() {
        return currentValue;
    }

    /**
     * Creates a {@link JButton} that when pressed prepares a specific mathematical operation to be conducted.
     *
     * @param comp {@link JTextComponent} this button affects
     * @param op   Mathematical Operation to be done when pressed
     * @return JButton that conducts on the given Arithmetic Operation on action
     */
    public JButton getArithmeticButton(JTextComponent comp, Operation op) {
        JButton button;
        switch (op) {
            case ADDITION:
                button = new JButton("+");
                break;
            case SUBTRACTION:
                button = new JButton("---");
                break;
            case MULTIPLICATION:
                button = new JButton("x");
                break;
            case DIVISION:
                button = new JButton("/");
                break;
            default:
                // If we create an N/A button then an exception would occur in operatorAction anyway.
                throw new UnsupportedOperationException();
        }

        button.addActionListener(this.operatorAction(comp, op));
        return button;
    }

    /**
     * Creates an ActionListener that returns the value of the next mathematical operation and does nothing else.
     *
     * @param comp {@link JTextComponent} that this action affects
     * @return ActionListener that does an 'equals' operation
     */
    public ActionListener equalAction(final JTextComponent comp) {
        return new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                // Ignore if Component Text Length == 0 (Nothing to Do)
                if (comp.getText().length() == 0)
                    return;

                // Parse text
                // We can assume this has to be a number because our input is restricted to designated buttons.
                double d = Double.parseDouble(comp.getText());

                // If No State Information is available, we are just starting so
                // just set the currentValue to the parsed value
                if (currentValue == Double.NaN || currentOp == null) {
                    currentValue = d;
                } else {
                    // Do the currently queued Math Operation and make that the current value
                    currentValue = currentOp.applyAsDouble(currentValue, d);
                }

                // Acknowledge that the input field should be cleared next button press
                // if we don't do this then entering 8, 9, 9, +, 9, = would parse as 899 + 8999 because we didn't clear
                // the field.
                shouldClearInput = true;

                comp.setText("" + currentValue);

                // Reset State
                ArithmeticModel.this.reset();
            }
        };
    }

    /**
     * Creates an ActionListener for a component that does an Operation on two doubles and modifes a Text Component.
     *
     * @param comp      {@link JTextComponent} that is affected
     * @param operation Math Operation to conduct
     * @return Operator ActionListener
     */
    public ActionListener operatorAction(final JTextComponent comp, final Operation operation) {
        return new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                // Ignore if Component Text Length == 0 (Nothing to Do)
                if (comp.getText().length() == 0)
                    return;

                // Parse text
                // We can assume this has to be a number because our input is restricted to designated buttons.
                double d = Double.parseDouble(comp.getText());

                // If No State Information is available, we are just starting so
                // just set the currentValue to the parsed value and the operation to the given operation.
                if (currentValue == Double.NaN || currentOp == null) {
                    currentValue = d;
                    currentOp = operation;
                } else if (shouldClearInput) {
                    // We previously set the operation and are changing it, don't touch the current value
                    // and change the desired current operation.
                    currentOp = operation;
                } else {
                    // We have all the state information we need
                    // Set current value to the result of the operation
                    // Set currentOperation to the new operation
                    currentValue = currentOp.applyAsDouble(currentValue, d);
                    currentOp = operation;
                    comp.setText(Double.toString(currentValue));
                }

                shouldClearInput = true;
            }
        };
    }

    /**
     * Creates a simple ActionListener that just appends some value onto the Text Component string value.
     *
     * @param comp  {@link JTextComponent} that is affected
     * @param value Value to append when pressed
     * @return Number ActionListener
     */
    public ActionListener numberAction(final JTextComponent comp, int value) {
        return new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String text;

                // if we are to clear input, start with a blank slate, otherwise
                // start with the current string value.
                if (shouldClearInput) {
                    text = "";
                    shouldClearInput = false;
                } else
                    text = comp.getText();

                // Append value and set text
                text += "" + value;
                comp.setText(text);

                // Caret == Cursor
                comp.setCaretPosition(text.length());
            }
        };
    }

}
