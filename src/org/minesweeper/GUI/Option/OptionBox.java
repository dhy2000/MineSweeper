package org.minesweeper.GUI.Option;

import org.minesweeper.GUI.Main.MainWindow;
import org.minesweeper.Game.GameDifficulty;
import org.minesweeper.Options.GameOptions;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionBox extends JDialog {

    private final MainWindow father;
    public MainWindow getFather() {
        return father;
    }

    private final JPanel optionPanel;
    private final JPanel keyPanel;
    private final ButtonGroup optionGroup;



    private final OptionController controller = new OptionController(this);
    public OptionController getController() {
        return controller;
    }

    private void loadOptions() {
        JRadioButton jrEasy = new JRadioButton("简单("
                + GameDifficulty.EASY.getSizeX()
                + "*"
                + GameDifficulty.EASY.getSizeY()
                + ", "
                + GameDifficulty.EASY.getMineNumber()
                + "雷)");
        JRadioButton jrMedium = new JRadioButton("中档("
                + GameDifficulty.MEDIUM.getSizeX()
                + "*"
                + GameDifficulty.MEDIUM.getSizeY()
                + ", "
                + GameDifficulty.MEDIUM.getMineNumber()
                + "雷)");
        JRadioButton jrHard = new JRadioButton("困难("
                + GameDifficulty.HARD.getSizeX()
                + "*"
                + GameDifficulty.HARD.getSizeY()
                + ", "
                + GameDifficulty.HARD.getMineNumber()
                + "雷)");
        jrEasy.setActionCommand("Easy");
        jrMedium.setActionCommand("Medium");
        jrHard.setActionCommand("Hard");
        optionPanel.setLayout(new GridLayout(3, 1));
        optionPanel.add(jrEasy);
        optionPanel.add(jrMedium);
        optionPanel.add(jrHard);
        optionGroup.add(jrEasy);
        optionGroup.add(jrMedium);
        optionGroup.add(jrHard);
    }

    private void loadKeyPanel() {
        JButton buttonOK = new JButton("OK");
        JButton buttonCancel = new JButton("Cancel");
        buttonOK.addActionListener(e -> {
            String selCmd = optionGroup.getSelection().getActionCommand();
            controller.saveDifficultyConfig(selCmd);
            dispose();
        });
        buttonCancel.addActionListener(e -> dispose());

        keyPanel.add(buttonOK);
        keyPanel.add(buttonCancel);
    }

    public OptionBox(MainWindow father) {
        super(father, "游戏选项", true);
        this.father = father;
        this.setSize(250, 200);

        this.setLocationRelativeTo(father);

        optionPanel = new JPanel();
        optionGroup = new ButtonGroup();
        loadOptions();
        keyPanel = new JPanel();
        loadKeyPanel();


        JPanel contentPanel = new JPanel();
        contentPanel.add(optionPanel);
        contentPanel.add(keyPanel);

        this.setContentPane(contentPanel);

        this.setResizable(false);
        this.setVisible(true);
    }


    public JPanel getOptionPanel() {
        return optionPanel;
    }

    public JPanel getKeyPanel() {
        return keyPanel;
    }

    public ButtonGroup getOptionGroup() {
        return optionGroup;
    }



}
