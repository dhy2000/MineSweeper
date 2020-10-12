package org.minesweeper.GUI;

import org.minesweeper.Controller.GameController;
import org.minesweeper.Game.GameDifficulty;
import org.minesweeper.Game.MineSweeperGame;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private JMenuBar menuBar;
    private GamePanel gamePanel;

    private GameController controller;

    public MainWindow() {
        this.setTitle("Mine Sweeper");
        // this.setSize(400, 300);

        this.menuBar = new MenuBar();
        this.setLayout(new BorderLayout());
        this.setJMenuBar(this.menuBar);

        this.controller = GameController.getInstance();

        MineSweeperGame game = new MineSweeperGame(GameDifficulty.EASY);

        this.gamePanel = new GamePanel(game);
        this.add(gamePanel);

        this.setSize(this.gamePanel.getRowNum() * GamePanel.BUTTON_SIZE,
                this.gamePanel.getColNum() * GamePanel.BUTTON_SIZE);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
