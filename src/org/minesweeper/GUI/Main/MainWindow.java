package org.minesweeper.GUI.Main;

import org.minesweeper.GUI.GameBoard.GameBoard;
import org.minesweeper.GUI.Menu.MenuBar;
import org.minesweeper.Options.GameOptions;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final JMenuBar menuBar;
    private final GameBoard gameBoard;

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    private final MainController controller = new MainController(this);
    public MainController getController() {
        return this.controller;
    }
    // setSize

    public MainWindow() {
        this.setTitle("Mine Sweeper");
        this.menuBar = new MenuBar(this);
        this.setLayout(new BorderLayout());
        this.setJMenuBar(this.menuBar);

        GameOptions options = GameOptions.getInstance();

        this.gameBoard = new GameBoard(this);

        this.add(gameBoard);

        this.setSize(this.gameBoard.getRowNum() * GameBoard.BUTTON_SIZE,
                this.gameBoard.getColNum() * GameBoard.BUTTON_SIZE);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
