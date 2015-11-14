/*
    DemoConstants.java
    
    Version:
        $Id$
    
    Revisions:
        $Log$
        
    $Author$
 */

package us.matthewcrocco.tclib;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Constants used in the Live Demo.
 * <p>
 * Authentication stuff kept since I am never using that account
 * realistically.
 * <p>
 * I can also just reset the authentication.
 *
 * @author Matthew Crocco
 */
public final class DemoConstants {

    private DemoConstants() {
        throw new UnsupportedOperationException();
    }

    private static final Twitter twitterInstance;

    public static final String TWITTER_USERNAME = "RIT CS Calc Demo Bot";
    public static final String TWITTER_HANDLE = "RITCS_CalcDemo";
    public static final String TWITTER_URL = "https://twitter.com/RITCS_CalcDemo";
    public static final ImageIcon TWITTER_ICON;

    static {
        try {
            // Get Twitter Image in the root directory and scale it to 40 pixels
            BufferedImage image = ImageIO.read(Files.newInputStream(Paths.get("twitter.png")));
            Image tmp = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            BufferedImage fImg = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = fImg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            TWITTER_ICON = new ImageIcon(fImg);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }

        // Initialize Twitter API object
        TwitterFactory tFactory;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey("3Vw9YAnJtzEYGK1VDaxpFNFpp");
        cb.setOAuthConsumerSecret("oYyjieR88uBkWKB72XA8gikn6887EqIqWdO9Fc3rf8TrCnOZPY");
        cb.setOAuthAccessToken("4176270563-yKz8oT4qKQnnwu67eLNbOq68881UzQCAxMtXPnl");
        cb.setOAuthAccessTokenSecret("wuD0tHmL4IumSlKw9c9cUWkKTo163UJTX5pA2uetSSjGv");

        Configuration config = cb.build();
        tFactory = new TwitterFactory(config);
        twitterInstance = tFactory.getInstance();
    }

    public static String getTwitterName() {
        return TWITTER_USERNAME;
    }

    public static String getTwitterHandle() {
        return TWITTER_HANDLE;
    }

    public static Twitter getTwitter() {
        return twitterInstance;
    }
}
