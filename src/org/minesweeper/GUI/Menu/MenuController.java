package org.minesweeper.GUI.Menu;

import org.jetbrains.annotations.NotNull;
import org.minesweeper.AI.AIOne;
import org.minesweeper.AI.AutoSweeper;
import org.minesweeper.GUI.Option.OptionBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController {

    private final @NotNull MenuBar master;

    public MenuController(@NotNull MenuBar master) {
        System.out.println("MenuController Constructed!");
        this.master = master;
    }

    private final ActionListener newGameListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            master.getFather().getController().restartGame();
            System.out.println("Clicked New Game Menu");
        }
    };
    public ActionListener getNewGameListener() {
        return newGameListener;
    }

    private final ActionListener exitGameListener = e -> System.exit(0);

    public ActionListener getExitGameListener() {
        return exitGameListener;
    }

    private final ActionListener openSettingsListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new OptionBox(master.getFather());
        }
    };

    public ActionListener getOpenSettingsListener() {
        return openSettingsListener;
    }

    private final ActionListener gameAIListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
//            JOptionPane.showConfirmDialog(master.getFather());
            int result = JOptionPane.showConfirmDialog(master.getFather(), "启动自动扫雷吗？", "自动扫雷", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                master.getFather().getGameBoard().getController().enableAI();
            }
        }
    };
    public ActionListener getGameAIListener() {
        return gameAIListener;
    }

}
