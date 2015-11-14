package us.matthewcrocco.tclib;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.undo.UndoManager;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

// TODO Live Status Feed -- Maybe?


/**
 * A Dialog that communicates with the Twitter API to
 * post statuses. Has a text field that limits the status to 140 characters.
 * <p>
 * Has several success and failure conditions, if succeeded the
 * Dialog will ask to redirect you to the twitter page using your
 * default browser, or will give you the link if the other way
 * is not possible/unsupported.
 *
 * @author Matthew
 */
public class TwitterDialog extends JDialog {

    /*
        Preferred way to create KeyStrokes.

        Composed of a Key Code and modifiers. Modifiers are things like
        having ctrl down, alt down or shift down.

        If you want to mix modifiers you bitwise-OR them.
        i.e.
        ctrl + shift + Z = Key Code Z and Modifiers CTRL_DOWN | SHIFT_DOWN
     */
    private static final KeyStroke undo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
    private static final KeyStroke redo = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK);

    public TwitterDialog(JFrame parent) {
        // Setting a parent means if the parent closes, this closes
        super(parent);

        EventQueue.invokeLater(() -> {
            init();

            // Could porbably also Hide in this case if we override
            // default hide behavior.
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setTitle(String.format("Tweeting as: %s (@%s)", DemoConstants.getTwitterName(), DemoConstants.getTwitterHandle()));
            setModal(true);

            setSize(400, 150);
            setLocationRelativeTo(null);
            setVisible(true);
        });
    }

    private void init() {
        final Container content = getContentPane();
        final Twitter twitter = DemoConstants.getTwitter();

        content.setBackground(SwingUtils.hexColor("#2AA8E0"));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        final JPanel inputPanel = new JPanel(new BorderLayout());
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        content.add(inputPanel);
        content.add(buttonPanel);

        // Input
        final UndoManager manager = new UndoManager();
        JTextArea statusArea = new JTextArea("Hey! I did Maths!");
        JScrollPane statusScrollPane = new JScrollPane(statusArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        inputPanel.add(statusScrollPane, BorderLayout.CENTER);

        statusArea.setDocument(new TextLimitDocument(140));
        statusArea.setLineWrap(true);
        statusArea.getDocument().addUndoableEditListener(manager);

        // Key Bindings
        statusArea.getKeymap().addActionForKeyStroke(undo, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (manager.canUndo())
                    manager.undo();
            }
        });

        statusArea.getKeymap().addActionForKeyStroke(redo, new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {
                if (manager.canRedo())
                    manager.redo();
            }
        });

        // Button Panel
        JButton submitButton = new JButton("Submit");
        JButton cancelbutton = new JButton("Cancel");
        JButton clearFeedButton = new JButton("Clear Feed");

        submitButton.addActionListener((e) -> {
            StatusUpdate update = new StatusUpdate(statusArea.getText());
            boolean attempt = true;
            while (attempt) {
                URI twitterUri = null;
                try {
                    twitter.tweets().updateStatus(update);

                    try {
                        twitterUri = new URL(DemoConstants.TWITTER_URL).toURI();
                    } catch (MalformedURLException | URISyntaxException exception) {
                        exception.printStackTrace();
                    }

                    if (twitterUri != null && Desktop.isDesktopSupported()) {
                        int choice = JOptionPane.showConfirmDialog(TwitterDialog.this, "Twitter Status Update Succeeded! Go to twitter? (Using Default Browser!)", "Twitter Success", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (choice == JOptionPane.YES_OPTION) {
                            try {
                                Desktop.getDesktop().browse(twitterUri);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(TwitterDialog.this, "Twitter Status Update Succeeded! Find it at: " + DemoConstants.TWITTER_URL + "!", "Twitter Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                    statusArea.setText("");
                    attempt = false;
                } catch (TwitterException exception) {
                    exception.printStackTrace();

                    int choice = JOptionPane.showConfirmDialog(TwitterDialog.this, "Twitter Status Update Failed! Retry?", "Twitter Failure", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                    if (choice == JOptionPane.NO_OPTION)
                        attempt = false;
                }
            }
        });

        cancelbutton.addActionListener((e) -> TwitterDialog.this.dispose());

        buttonPanel.add(cancelbutton);
        buttonPanel.add(clearFeedButton);
        buttonPanel.add(submitButton);

    }
}
