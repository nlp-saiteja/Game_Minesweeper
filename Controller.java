// Controller class to manage the game logic

import javax.swing.JPanel;

public class Controller {
    private GameGUI gameGUI;
    private GamePanel gamePanel;

    public Controller(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
    }

    public void startGame(String mode, int rows, int cols, int mines) {
        gamePanel = new GamePanel();
        MineField game = new MineField(rows, cols, mines);
        game.addObserver(gamePanel);
        JPanel gamePanelUI = gamePanel.createPanel(mode, game);
        gameGUI.replacePanel(gamePanelUI);
    }
}