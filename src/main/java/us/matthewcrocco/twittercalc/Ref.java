/*
    Ref.java
    
    Version:
        $Id$
    
    Revisions:
        $Log$
        
    $Author$
 */

package us.matthewcrocco.twittercalc;

/**
 * Reference Value Class
 * <p>
 * Typically I use classes liek these to store all the constants so that if I need to change say the title, I don't
 * need to find every place I used it, I just modify it here and it changes everywhere.
 * <p>
 * Since this is a static utility class, you can't instantiate. Doing what I did is just good practice.
 *
 * @author Matthew Crocco
 */
public final class Ref {

    // Can't instantiate this class
    // An exception is added because with reflection this can still be instantiated
    // but even then that is probably not desired.
    private Ref() {
        throw new UnsupportedOperationException();
    }

    public static final String APP_NAME = "Calculator Demo";
    public static final String APP_VERSION = "1.0.0";

    public static final String APP_TITLE = APP_NAME + " | " + APP_VERSION;

}
