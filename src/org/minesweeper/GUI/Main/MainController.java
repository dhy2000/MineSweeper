package org.minesweeper.GUI.Main;

public class MainController {
    private final MainWindow master;
    MainController(MainWindow master) {
        this.master = master;
    }

    public void restartGame() {
        this.master.getGameBoard().getController().restartGame();
    }
    public void setWindowSize(int width, int height) {
        master.setSize(width, height);
    }

}
