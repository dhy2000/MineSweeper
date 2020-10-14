package org.minesweeper.GUI.Menu;

import org.minesweeper.GUI.Main.MainWindow;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {
    private final MainWindow father;
    public MainWindow getFather() {
        return father;
    }
    public MenuBar(MainWindow father) {
        this.father = father;
        add(createGameMenu());
        add(createSettingsMenu());
        add(createFunctionMenu());
        setVisible(true);
    }

    private final MenuController controller = new MenuController(this);

    private JMenu createGameMenu() {
        JMenu menu = new JMenu("游戏(G)");
        menu.setMnemonic(KeyEvent.VK_G);

        JMenuItem item = new JMenuItem("新游戏(N)", KeyEvent.VK_N);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        item.addActionListener(controller.getNewGameListener());
        menu.add(item);
        menu.addSeparator();

        item = new JMenuItem("退出游戏(E)", KeyEvent.VK_E);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
        item.addActionListener(controller.getExitGameListener());
        menu.add(item);

        return menu;
    }

    private JMenu createSettingsMenu() {
        JMenu menu = new JMenu("选项(S)");
        menu.setMnemonic(KeyEvent.VK_S);

        JMenuItem item = new JMenuItem("调整难度(D)", KeyEvent.VK_D);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
        item.addActionListener(controller.getOpenSettingsListener());
        menu.add(item);

        return menu;
    }

    private JMenu createFunctionMenu() {
        JMenu menu = new JMenu("功能(F)");
        menu.setMnemonic(KeyEvent.VK_F);

        JMenuItem item = new JMenuItem("自动扫雷(A)", KeyEvent.VK_A);
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        item.addActionListener(controller.getGameAIListener());
        menu.add(item);

        return menu;
    }

    public MenuController getController() {
        return controller;
    }


}
