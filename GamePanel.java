// GamePanel class responsible for displaying the game state and handling the UI updates
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GamePanel extends JPanel implements Observer {
    private JPanel gamePanel;
    private JPanel modeLabelPanel;
    private JLabel modeLabel;
    private JPanel containerPanel;
    private JPanel labelsPanel;
    private JPanel timeScoreLabelPanel;
    private JLabel timeScoreLabel;
    private JLabel scoreLabel;
    private Timer timer;
    private long startTime;
    private SimpleDateFormat timeFormat;
    private int score;

    public GamePanel() {
        containerPanel = new JPanel(new BorderLayout());
        labelsPanel = new JPanel(new BorderLayout());
        modeLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timeScoreLabelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        modeLabel = new JLabel();
        timeScoreLabel = new JLabel("Time: 00:00:00");
        scoreLabel = new JLabel("Score: 1000", SwingConstants.CENTER);

        timeFormat = new SimpleDateFormat("mm:ss");
        timer = new Timer(1000, e -> updateTimer());
        startTime = 0;

        timer.start();

        modeLabelPanel.add(modeLabel);
        timeScoreLabelPanel.add(timeScoreLabel);

        labelsPanel.add(modeLabelPanel, BorderLayout.WEST);
        labelsPanel.add(scoreLabel, BorderLayout.CENTER);
        labelsPanel.add(timeScoreLabelPanel, BorderLayout.EAST);
        containerPanel.add(labelsPanel, BorderLayout.NORTH);
    }

    // Update the timer and score
    private void updateTimer() {
        if (!isGameOver()) {
            startTime += 1000;
            Date date = new Date(startTime);
            String timeString = timeFormat.format(date);
            timeScoreLabel.setText("Time: " + timeString);

            score = Math.max(0, 1000 - (int) (startTime / 1000));
            scoreLabel.setText("Score: " + score);
        } else {
            timer.stop();
        }
    }

    // Check if the game is over
    private boolean isGameOver() {
        return false;
    }

    // Create the game panel based on the selected mode and game state
    public JPanel createPanel(String mode, MineField game) {
        modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        modeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        modeLabel.setText("Mines: " + game.getMines());
        modeLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        timeScoreLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        timeScoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        if (mode.equals("easy")) {
            gamePanel = new JPanel(new GridLayout(5, 5, 2, 2));
        } else if (mode.equals("normal")) {
            gamePanel = new JPanel(new GridLayout(7, 7, 2, 2));
        } else {
            gamePanel = new JPanel(new GridLayout(11, 11, 2, 2));
            gamePanel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 40));
        }

        for (int i = 0; i < game.getRowSize(); i++) {
            for (int j = 0; j < game.getColumnSize(); j++) {
                Block block = new Block(i, j);

                if (game.blocksArray[i][j] == null) {
                    game.blocksArray[i][j] = block;
                } else {
                    block = game.blocksArray[i][j];
                }

                block.setFocusable(false);
                if (!mode.equals("hard")) {
                    block.setFont(new Font("Arial Unicode MS", Font.PLAIN, 40));
                } else {
                    block.setFont(new Font("Arial Unicode MS", Font.PLAIN, 15));
                }
                block.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (game.gameOver) {
                            return;
                        }
                        Block block = (Block) e.getSource();
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (block.getText().equals("")) {
                                if (game.minesList.contains(block)) {
                                    game.revealMines();
                                    modeLabel.setForeground(Color.RED);
                                    game.gameOver = true;
                                    timer.stop();
                                } else {
                                    game.checkMines(block.rows, block.columns);
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (block.getText().equals("") && block.isEnabled()) {
                                game.totalMinesCount -= 1;
                                modeLabel.setText("Mines: " + game.totalMinesCount);
                                block.setText("🚩");
                            } else if (block.getText().equals("🚩")) {
                                game.totalMinesCount += 1;
                                modeLabel.setText("Mines: " + game.totalMinesCount);
                                block.setText("");
                            }
                        }
                    }
                });

                gamePanel.add(block);
            }
        }

        containerPanel.add(gamePanel, BorderLayout.CENTER);
        return containerPanel;
    }

    @Override
    public void update(String result) {
        modeLabel.setText(result);
    }
}