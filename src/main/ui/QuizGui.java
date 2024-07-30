package ui;

import model.Event;
import model.EventLog;
import model.Quiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// GUI framework code and comments taken from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git
// https://docs.oracle.com/javase/8/docs/api/java/awt/event/WindowListener.html#windowClosed-java.awt.event.WindowEvent-

// Account manager GUI: provides the same functionality as account manager but in graphical form
public class QuizGui extends JFrame {

    private static final int WIDTH = 450;
    private static final int HEIGHT = 200;
    private Quiz quiz;
    private JComboBox<String> printCombo;
    private JDesktopPane desktop;
    private JInternalFrame homeScreen;
    private JInternalFrame quizScreen;
    private JInternalFrame scoreScreen;
    private DefaultComboBoxModel<String> nameList;
    private DefaultComboBoxModel<String> octaveList;
    private boolean hasStarted = false;
    private JPanel currPanel;

    //EFFECTS: constructs the account manager application
    public QuizGui() {
        quiz = new Quiz();

        desktop = new JDesktopPane();
        homeScreen = new JInternalFrame("Home", false, false, false, false);
        quizScreen = new JInternalFrame("Quiz", false, false, false, false);
        scoreScreen = new JInternalFrame("Score", true, false, false, false);
        nameList = new DefaultComboBoxModel<>();
        octaveList = new DefaultComboBoxModel<>();

        setContentPane(desktop);
        setTitle("MIDI Note Quiz");
        setSize(WIDTH, HEIGHT);

        addHomePanel();
        addQuizPanel();
        addScorePanel();

        homeScreen.pack();
        homeScreen.setVisible(true);
        desktop.add(homeScreen);

        setInvisibleFrame(quizScreen, homeScreen);
        setInvisibleFrame(scoreScreen, quizScreen);

        setCloseSettings();
        centreOnScreen();
        setVisible(true);
    }

    //MODIFIES: desktop
    //EFFECTS: adds new invisible frame to desktop
    private void setInvisibleFrame(JInternalFrame frame, JInternalFrame prevFrame) {
        frame.pack();
        frame.setVisible(false);
        frame.setLocation(prevFrame.getLocation().x + prevFrame.getWidth(),
                prevFrame.getLocation().y);
        desktop.add(frame);
    }

    /**
     * Helper to add control buttons for home panel.
     */
    //MODIFIES: homeScreen
    //EFFECTS: adds buttons to homeScreen
    private void addHomePanel() {
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(2,1));
        homePanel.add(new JButton(new StartQuizAction()));
        homePanel.add(new JButton(new EndQuizAction()));
        homeScreen.add(homePanel, BorderLayout.WEST);
    }

    //MODIFIES: generalOptions
    //EFFECTS: adds buttons to generalOptions
    private void addQuizPanel() {
        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new GridLayout(4,1));
        quizPanel.add(new JButton(new PlaySoundAction()));
        for (String a : quiz.getNoteNames()) {
            nameList.addElement(a);
        }
        for (int i = 0; i <= 8; i++) {
            octaveList.addElement(Integer.toString(i));
        }
        quizPanel.add(new JComboBox(nameList));
        quizPanel.add(new JComboBox(octaveList));
        quizPanel.add(new JButton(new SubmitAnswerAction()));
        quizScreen.add(quizPanel, BorderLayout.WEST);
    }

    //MODIFIES: accOptions
    //EFFECTS: adds buttons to accOptions
    private void addScorePanel() {
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(2,1));
        scorePanel.add(new JLabel("Total correct: " + quiz.getScore() + "    ", SwingConstants.CENTER));
        scorePanel.add(new JLabel("Total answered: " + quiz.getAnswered() + "    ", SwingConstants.CENTER));
        scoreScreen.add(scorePanel, BorderLayout.WEST);
        currPanel = scorePanel;
    }

    private void changeScorePanel() {
        scoreScreen.remove(currPanel);
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(3,1));
        scorePanel.add(new JLabel("Total correct: " + quiz.getScore(), SwingConstants.CENTER));
        scorePanel.add(new JLabel("", SwingConstants.CENTER));
        scorePanel.add(new JLabel("Total answered: " + quiz.getAnswered(), SwingConstants.CENTER));
        scoreScreen.add(scorePanel, BorderLayout.WEST);
        currPanel = scorePanel;
        scoreScreen.setVisible(false);
        scoreScreen.setVisible(true);
    }

    //MODIFIES: generalOptions, accOptions
    //EFFECTS: toggles visibility of frames based on whether al is empty or not
    private void toggleHomeScreenExtension() {
        if (!hasStarted) {
            quizScreen.setVisible(false);
            scoreScreen.setVisible(false);
        } else {
            quizScreen.setVisible(true);
            scoreScreen.setVisible(true);
        }
    }

    /**
     * Helper to create print options combo box
     * @return  the combo box
     */
    //EFFECTS: creates and returns combo box for account selection options
    private JComboBox<String> createPrintCombo() {
        printCombo = new JComboBox<String>();
        for (String a : quiz.getNoteNames()) {
            printCombo.addItem(a);
        }
        return printCombo;
    }

    /**
     * Adds an item with given handler to the given menu
     * @param theMenu  menu to which new item is added
     * @param action   handler for new menu item
     * @param accelerator    keystroke accelerator for this menu item
     */
    //MODIFIES: theMenu
    //EFFECTS: adds item to menu
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    /**
     * Helper to centre main application window on desktop
     */
    //EFFECTS: centres main window on desktop
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    /**
     * Represents action to be taken when user wants to create a new account.
     */
    private class StartQuizAction extends AbstractAction {

        StartQuizAction() {
            super("Start Quiz");
        }

        //MODIFIES: account list (al)
        //EFFECTS: creates an account and adds it to the account list
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!hasStarted) {
                JOptionPane.showMessageDialog(null,
                        "You have successfully started the quiz.",
                        "Quiz Started",
                        JOptionPane.PLAIN_MESSAGE);
                hasStarted = true;
                quiz.nextQuestion();
                toggleHomeScreenExtension();
            } else {
                JOptionPane.showMessageDialog(null,
                        "Quiz already started.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Represents action to be taken when user wants to view all account details.
     */
    private class PlaySoundAction extends AbstractAction {

        PlaySoundAction() {
            super("Play Note");
        }

        //EFFECTS: displays all account details
        @Override
        public void actionPerformed(ActionEvent evt) {
            quiz.play();
        }
    }

    /**
     * Represents action to be taken when user wants to exit the app.
     */
    private class EndQuizAction extends AbstractAction {

        EndQuizAction() {
            super("End Quiz and Exit");
        }

        //EFFECTS: exits app after confirmation
        @Override
        public void actionPerformed(ActionEvent evt) {
            int confirmation = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to end the quiz and exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    }

    //EFFECTS: determines if given amount is valid based on transaction type and account balance
    private boolean validNote(String input, int octave) {
        return (octave != 0 || input.equals("A") || input.equals("A#/Bb") || input.equals("B"))
                && (octave != 8 || input.equals("C"));
    }

    /**
     * Represents action to be taken when user wants to deposit to an account.
     */
    private class SubmitAnswerAction extends AbstractAction {

        SubmitAnswerAction() {
            super("Submit Answer");
        }

        //MODIFIES: account (a)
        //EFFECTS: makes a deposit to an account (a)
        @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
        @Override
        public void actionPerformed(ActionEvent evt) {
            String name = (String) nameList.getSelectedItem();
            String octave = (String) octaveList.getSelectedItem();
            if (!validNote(name, Integer.parseInt(octave))) {
                JOptionPane.showMessageDialog(null,
                        "Invalid submission: note must be between A0 and C8.",
                        "Invalid Submission",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                boolean correct = quiz.checkAnswer(name, Integer.parseInt(octave));
                String correctName = quiz.getCurrNote().getNoteID().getKey();
                String correctOctave = Integer.toString(quiz.getCurrNote().getNoteID().getValue());
                quiz.nextQuestion();
                if (correct) {
                    quiz.addScore();
                    changeScorePanel();
                    if (!quiz.isFinished()) {
                        JOptionPane.showMessageDialog(null,
                                "Correct! Moving on to the next question...",
                                "Submission",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Correct! The quiz is now complete.",
                                "Submission",
                                JOptionPane.PLAIN_MESSAGE);
                        quizScreen.setVisible(false);
                        hasStarted = false;
                    }
                } else {
                    changeScorePanel();
                    if (!quiz.isFinished()) {
                        JOptionPane.showMessageDialog(null,
                                "Incorrect! The correct answer was " + correctName + " " + correctOctave
                                        + ". Moving on to the next question...",
                                "Submission",
                                JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Incorrect! The correct answer was " + correctName + " " + correctOctave
                                        + ". The quiz is now complete.",
                                "Submission",
                                JOptionPane.PLAIN_MESSAGE);
                        quizScreen.setVisible(false);
                        hasStarted = false;
                    }
                }
            }
        }
    }

    //EFFECTS: starts the application
    public static void main(String[] args) {
        new LoadingScreen();
        new QuizGui();
    }

    //EFFECTS: sets app exit settings: prints log when app is closed
    public void setCloseSettings() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                EventLog el = EventLog.getInstance();
                for (Event next : el) {
                    System.out.println("\n" + next.toString());
                }
                repaint();
                System.exit(0);
            }
        });
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
