package org.minesweeper.GUI.Main;

import org.minesweeper.GUI.GameBoard.GameBoard;
import org.minesweeper.GUI.Menu.MenuBar;
import org.minesweeper.Options.GameOptions;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private JMenuBar menuBar;
    private GameBoard gameBoard;

    public MainWindow() {
        this.setTitle("Mine Sweeper");

        this.menuBar = new MenuBar();
        this.setLayout(new BorderLayout());
        this.setJMenuBar(this.menuBar);

        GameOptions options = GameOptions.getInstance();

        this.gameBoard = new GameBoard();

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
