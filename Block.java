// Block class extends JButton to represent each block in the minefield

import javax.swing.JButton;

public class Block extends JButton {
    public int rows;
    public int columns;

    public Block(int r, int c) {
        this.rows = r;
        this.columns = c;
    }
}