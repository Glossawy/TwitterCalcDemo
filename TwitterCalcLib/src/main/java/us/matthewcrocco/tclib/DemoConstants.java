package us.matthewcrocco.tclib;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
            BufferedImage raw = ImageIO.read(Files.newInputStream(Paths.get("twitter.png")));
            Image iconImage = raw.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

            TWITTER_ICON = new ImageIcon(iconImage);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        // Initialize Twitter API object
        TwitterFactory tFactory;

        // Hardcoded Authentication for RITCS Calc Demo Bot
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
