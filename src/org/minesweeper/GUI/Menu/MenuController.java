package org.minesweeper.GUI.Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController {

    private final JMenuBar master;

    public MenuController(JMenuBar master) {
        this.master = master;
    }

    private final ActionListener newGameListener = e -> {
        System.out.println("Clicked New Game Menu");
    };
    public ActionListener getNewGameListener() {
        return newGameListener;
    }

    private final ActionListener exitGameListener = e -> System.exit(0);

    public ActionListener getExitGameListener() {
        return exitGameListener;
    }
}
