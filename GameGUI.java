// GUI class to manage the display of the game
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class GameGUI {
    private JFrame frame;
    private JPanel containerPanel;
    private Controller controller;

    public GameGUI() {
        this.controller = new Controller(this);
    }

    public void run() {
        // Setup game frame
        JFrame gameFrame = new JFrame("Minesweeper");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 30));
        titleLabel.setText("Minesweeper");

        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);
        titlePanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Menu panel for selecting difficulty
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        JButton easyMode = new JButton("Easy");
        JButton normalMode = new JButton("Normal");
        JButton hardMode = new JButton("Hard");

        menuPanel.add(Box.createVerticalStrut(20));
        easyMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(easyMode);
        menuPanel.add(Box.createVerticalStrut(10));
        normalMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(normalMode);
        menuPanel.add(Box.createVerticalStrut(10));
        hardMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(hardMode);

        // Container panel to hold title and menu
        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.add(titlePanel);
        containerPanel.add(menuPanel);
        containerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        gameFrame.getContentPane().add(containerPanel);

        gameFrame.setPreferredSize(new Dimension(600, 800));
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        // Action listeners for the difficulty buttons
        easyMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.startGame("easy", 5, 5, 3);
            }
        });

        normalMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.startGame("normal", 7, 7, 7);
            }
        });

        hardMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.startGame("hard", 11, 11, 20);
            }
        });
    }

    // Replace the current panel with a new one
    public void replacePanel(JPanel newPanel) {
        containerPanel.removeAll();
        containerPanel.add(newPanel);
        containerPanel.revalidate();
        containerPanel.repaint();
    }
}