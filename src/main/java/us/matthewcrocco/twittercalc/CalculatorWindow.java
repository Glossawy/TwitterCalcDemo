package us.matthewcrocco.twittercalc;

import us.matthewcrocco.tclib.SwingUtils;
import us.matthewcrocco.tclib.TwitterButton;
import us.matthewcrocco.tclib.TwitterDialog;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Primary {@link JFrame} that acts as the main window in our program. Really,
 * it is a calculator. But it manages state using a data model and spins off
 * a {@link javax.swing.JDialog JDialog} called {@link TwitterDialog} on button press.
 *
 * @author Matthew
 */
public class CalculatorWindow extends JFrame {

    public CalculatorWindow() {
        super(Ref.APP_TITLE);

        /*
            When modifying the style, dimensions, layout or content
            of a GUI it should be ensure it is all done on the AWT Event Thread
            using EventQueue or SwingUtilities.

            invokeAndWait() will wait until the code is executed which is when
            the Event Thread is finished with it. Blocks the main thread.

            invokeLater() will queue the action and the Event Thread will run it
            when it can, it does not block the main thread.

            isDispatchThread() is also useful, it allows you to check if
            Thread.currentThread() is the AWT Event Thread.
         */
        EventQueue.invokeLater(() -> {
            // EXIT_ON_CLOSE - Immediately Exit Program when Closed
            // DISPOSE_ON_CLOSE - Dispose only this window
            // HIDE_ON_CLOSE - Make Invisible but Do Not Dispose on Close
            /// DO_NOTHING_ON_CLOSE - Closing does nothing
            /*
                Typically you want to DISPOSE_ON_CLOSE. EXIT_ON_CLOSE will
                force the JVM to exit, even if the Main Thread or other important
                threads aren't finished. When all windows are disposed the AWT Event Thread
                is finished and the program can exit normally when all other threads finish
                as well.
             */
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            init();

            setSize(400, 300);
            setResizable(false);
            // setting this to null places the window in the cetner of the screen
            setLocationRelativeTo(null);

            // Is equivalent to show() [show() calls setVisible(true)] but
            // not deprecated
            setVisible(true);
        });
    }

    /**
     * Initialize window components for showing.
     */
    private void init() {
        // Get the priamry content pane of the window
        final Container cont = getContentPane();
        final ArithmeticModel arithmetic = new ArithmeticModel();

        // We are using a top, center and bottom
        // We could use a Vertical BoxLayout
        // Using BorderLayout ensures we fill all space
        cont.setLayout(new BorderLayout());

        // Top Pane will fill all the space with a button on the 'east' side
        JPanel topPane = new JPanel(new BorderLayout());
        // Buttons are ina 4x4 grid
        JPanel middlePane = new JPanel(new GridLayout(4, 4, 5, 5));
        // Just for fun. Copyright statement and a JSpinner
        // flowing from Right to Left
        JPanel bottomPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        cont.add(topPane, BorderLayout.NORTH);
        cont.add(middlePane, BorderLayout.CENTER);
        cont.add(bottomPane, BorderLayout.SOUTH);

        // Top Section
        JTextField input = new JTextField("");
        JButton twitButton = new TwitterButton();
        topPane.add(input, BorderLayout.CENTER);
        topPane.add(twitButton, BorderLayout.EAST);

        // Adding padding creates space between the input field
        // and the window border and Buttons
        SwingUtils.addPadding(topPane, 10, 5);

        // Using a Larger Font makes the Input Field larger.
        // 20 works in this case.
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 20);
        input.setFont(font);

        // Making a field uneditable will normally gray-out the background.
        // To prevent that we set the background to WHITE beforehand.
        // Swing will not change the background if you set it explicitly.
        input.setBackground(Color.WHITE);
        input.setEditable(false);

        // Add padding to the input field creats some room between the
        // border of the Input Field and the text inside.
        SwingUtils.addPadding(input, 0, 10);

        // When the Twitter Button is pressed, create a new
        // Twitter Dialog that blocks input to the main window until it
        // closes.
        twitButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                new TwitterDialog(CalculatorWindow.this);
            }
        });

        // Middle Section
        // Go row -by-row, column-by-column and dcreate number buttons.
        for (int i = 2; i >= 0; i--) {
            // creates 3 buttons, the 4th is special cased below
            for (int j = 1; j < 4; j++) {
                JButton button = new JButton(Integer.toString(3 * i + j));
                button.addActionListener(arithmetic.numberAction(input, 3 * i + j));
                middlePane.add(button);
            }

            switch (i) {
                case 2:
                    // Top Row Addition Button
                    middlePane.add(arithmetic.getArithmeticButton(input, ArithmeticModel.Operation.ADDITION));
                    break;
                case 1:
                    // Second Row Subtraction Button
                    middlePane.add(arithmetic.getArithmeticButton(input, ArithmeticModel.Operation.SUBTRACTION));
                    break;
                case 0:
                    // Third Row Multiplication Button
                    middlePane.add(arithmetic.getArithmeticButton(input, ArithmeticModel.Operation.MULTIPLICATION));
                    break;
                default:
                    // Shouldn't happen, but just do nothing
                    break;
            }
        }

        // More padding
        SwingUtils.addPadding(middlePane, 0, 10);

        // Last 4 buttons below
        JButton zeroButton = new JButton("0");
        JButton clearButton = new JButton("CE");
        JButton equalButton = new JButton("=");

        // 0 Button action
        zeroButton.addActionListener(arithmetic.numberAction(input, 0));
        // Clear Button action
        clearButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                arithmetic.reset();
                input.setText("");
            }
        });
        // Equal Button action
        equalButton.addActionListener(arithmetic.equalAction(input));

        middlePane.add(zeroButton);
        middlePane.add(clearButton);
        middlePane.add(equalButton);
        // Division Button
        middlePane.add(arithmetic.getArithmeticButton(input, ArithmeticModel.Operation.DIVISION));

        // Bottom Pane

        // Certain components like Spinner need a backing Model that
        // can be manipulated and holds it's data.
        // In this case it's just all color names.
        JSpinner colorSpinner = new JSpinner(new SpinnerListModel(SwingUtils.getAllColors()));

        // HTML JLabel for Copyright
        bottomPane.add(new JLabel("<html><b>Copyright (c) 2015</b> - I Didn't Know What to Put Here...</html>"));
        bottomPane.add(colorSpinner);

        // When the value changes, set background color to the given color.
        // There are some weird ones, like TRANSPARENT (which does nothing)
        // because the color names are retrieved reflectively.
        colorSpinner.addChangeListener(new ChangeListener() {
            @Override public void stateChanged(ChangeEvent e) {
                SwingUtils.setBackgroundColorByName(bottomPane, (String) colorSpinner.getValue());
            }
        });
    }
}
