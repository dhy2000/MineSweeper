package org.minesweeper.GUI.GameBoard;

import org.jetbrains.annotations.NotNull;
import org.minesweeper.AI.AIOne;
import org.minesweeper.AI.AutoSweeper;
import org.minesweeper.Game.GameDifficulty;
import org.minesweeper.Game.GameUpdater;
import org.minesweeper.Game.MineSweeperGame;
import org.minesweeper.Options.GameOptions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoardController implements GameUpdater {
    private final GameBoard master;
    private @NotNull MineSweeperGame game;
    private AutoSweeper ai;
    public GameBoardController(GameBoard master) {
        this.master = master;
        GameDifficulty difficulty = GameOptions.getInstance().getGameDifficulty();
        this.master.setBoardSize(difficulty.getSizeX(), difficulty.getSizeY());
        // start Game at last
        this.game = new MineSweeperGame(GameOptions.getInstance().getGameDifficulty());
        this.game.setUpdater(this);
        this.game.enableTimer();
    }

    public MineSweeperGame getGame() {
        return game;
    }

    public void loadGame(MineSweeperGame game) {
        this.game = game;
        updateMap(game.getStringMap());
    }

    public void restartGame() {
        game.restart(GameOptions.getInstance().getGameDifficulty());
        master.getFather().getController().setWindowSize(
                game.getDifficulty().getSizeY() * GameBoard.BUTTON_SIZE,
                game.getDifficulty().getSizeX() * GameBoard.BUTTON_SIZE
        );
    }

    public void leftClick(int x, int y) {
        this.game.leftClick(x, y);
    }
    public void rightClick(int x, int y) {
        this.game.rightClick(x, y);
    }
    public void leftRightClick(int x, int y) {
        this.game.leftRightClick(x, y);
    }

    @Override
    public void initBoard(int rowNum, int colNum) {
        this.master.setBoardSize(rowNum, colNum);
        this.master.repaint();
        this.master.getFather().repaint();
    }

    @Override
    public void updateMap(String strMap) {
        this.master.updateMap(strMap);
        this.master.repaint();
        this.master.getFather().repaint();
    }

    @Override
    public void updateTimer(int count) {
        // @TODO: add Timer
    }

    @Override
    public void endGame(boolean win, int clicks, int time) {
        this.master.endGame(win, clicks, time);
    }

    public void enableAI() {
        ai = new AIOne(master.getFather().getGameBoard().getController().getGame());
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    JDialog dialog = new JDialog(master.getFather(), "停止自动扫雷", false);
                    dialog.setSize(300, 100);
                    dialog.setResizable(false);
                    dialog.setLocationRelativeTo(master.getFather());
                    JLabel msgLabel = new JLabel("点击停止自动扫雷");
                    JButton okBtn = new JButton("停止");
                    okBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ai.stop();
                            dialog.dispose();
                        }
                    });
                    JPanel dialogPanel = new JPanel();
                    dialogPanel.add(msgLabel);
                    dialogPanel.add(okBtn);
                    dialog.setContentPane(dialogPanel);
                    dialog.setVisible(true);
                    boolean flag = ai.playGame();
                    dialog.dispose();
                    if (!ai.isGameEnd() && !flag) {
//                        JOptionPane.showMessageDialog(master.getFather(), "当前已没有可以自动扫雷的局面");
                        JDialog dialogAsk = new JDialog(master.getFather(), "下一步", true);
                        dialogAsk.setSize(400, 150);
                        dialogAsk.setResizable(false);
                        dialogAsk.setLocationRelativeTo(null);
                        JLabel askMsgLabel = new JLabel("当前已没有确定的自动扫雷局面，请选择下一步策略");
                        JButton quitBtn = new JButton("停止");
                        JButton act1Btn = new JButton("策略1");
                        JButton act2Btn = new JButton("策略2");
                        JButton act3Btn = new JButton("策略3");
                        quitBtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                dialogAsk.dispose();
                            }
                        });
                        act1Btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ai.nextAction(1);
                                dialogAsk.dispose();
                                enableAI();
                            }
                        });
                        act2Btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ai.nextAction(2);
                                dialogAsk.dispose();
                                enableAI();
                            }
                        });
                        act3Btn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ai.nextAction(3);
                                dialogAsk.dispose();
                                enableAI();
                            }
                        });
                        JPanel panelAskLabel = new JPanel();
                        panelAskLabel.add(askMsgLabel);
                        JPanel panelButton = new JPanel();
                        panelButton.add(quitBtn);
                        panelButton.add(act1Btn);
                        panelButton.add(act2Btn);
                        panelButton.add(act3Btn);
                        JPanel panelAsk = new JPanel();
                        panelAsk.add(panelAskLabel);
                        panelAsk.add(panelButton);
                        dialogAsk.setContentPane(panelAsk);
                        dialogAsk.setVisible(true);

                    }
                    master.revalidate();
                    master.getFather().revalidate();
                    // ai = null;
                }
            }
        }).start();
    }

    public synchronized void stopAI() {
        if (this.ai != null) {
            ai.stop();
        }
//        this.ai = null;
    }
}
