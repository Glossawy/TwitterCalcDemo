/*
    LookAndFeels.java
    
    Version:
        $Id$
    
    Revisions:
        $Log$
        
    $Author$
 */

package us.matthewcrocco.tclib;

import javax.swing.UIManager;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;

/**
 * Enumeration of {@link javax.swing.LookAndFeel LookAndFeels}
 * provided by Swing. That automatically sets the LookAndFeel of the
 * {@link UIManager} for Swing.
 * <p>
 * It is worth noting CROSS_PLATFORM == NEW_METAL.
 *
 * @author Matthew Crocco
 */
public enum LookAndFeels {

    SYSTEM(UIManager.getSystemLookAndFeelClassName()),
    CROSS_PLATFORM(UIManager.getCrossPlatformLookAndFeelClassName()),
    OLD_METAL("javax.swing.plaf.metal.MetalLookAndFeel") {
        @Override public boolean setLookAndFeel() {
            if (MetalLookAndFeel.getCurrentTheme().getClass() != DefaultMetalTheme.class)
                MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());

            return super.setLookAndFeel();
        }
    },
    NEW_METAL("javax.swing.plaf.metal.MetalLookAndFeel") {
        @Override public boolean setLookAndFeel() {
            if (MetalLookAndFeel.getCurrentTheme().getClass() != OceanTheme.class)
                MetalLookAndFeel.setCurrentTheme(new OceanTheme());

            return super.setLookAndFeel();
        }
    },
    MOTIF("com.sun.java.swing.plaf.motif.MotifLookAndFeel"),
    GTK("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");

    private final String lafName;

    LookAndFeels(String className) {
        this.lafName = className;
    }

    /**
     * Automatically sets  the Look and Feel of the Swing UIManager.
     *
     * @return true if succeeded, false otherwise
     */
    public boolean setLookAndFeel() {
        boolean success = true;
        try {
            UIManager.setLookAndFeel(lafName);
        } catch (Exception e) {
            success = false;
        }

        return success;
    }
}
