/*
    TwitterCalculator.java
    
    Version:
        $Id$
    
    Revisions:
        $Log$
        
    $Author$
 */

package us.matthewcrocco.twittercalc;

import us.matthewcrocco.tclib.LookAndFeels;

/**
 * Program Entry Point
 *
 * @author Matthew Crocco
 */
public class TwitterCalculator {

    public static void main(String[] args) {
        // Set Look And Feel to native system look and feel.
        // if that fails then set it to the Cross Platform look and feel provided by Java.
        if (!LookAndFeels.SYSTEM.setLookAndFeel())
            LookAndFeels.CROSS_PLATFORM.setLookAndFeel();

        // Instantiating the window starts the GUI
        new CalculatorWindow();
    }

}
