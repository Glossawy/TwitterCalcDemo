package us.matthewcrocco.tclib;

import javax.swing.JButton;

/**
 * A JButton that just removes borders and painted areas besides the
 * Image Icon. Also sets the Icon to the loaded Twitter Logo.
 *
 * @author Matthew
 */
public class TwitterButton extends JButton {

    public TwitterButton() {
        super(DemoConstants.TWITTER_ICON);

        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
    }

}
