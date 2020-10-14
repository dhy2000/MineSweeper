package org.minesweeper.GUI.GameBoard;

import org.jetbrains.annotations.NotNull;
import org.minesweeper.GUI.Main.MainWindow;
import org.minesweeper.Options.GameOptions;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class GameBoard extends JPanel {

    public static final int BUTTON_SIZE = 30;

    private final MainWindow father;
    public MainWindow getFather() {
        return father;
    }

    private int rowNum;
    private int colNum;

    private MineCell [][]mines;

    private final GameBoardController controller = new GameBoardController(this);
    public GameBoardController getController() {
        return this.controller;
    }

    // Icon
    private final Icon mineIcon = createIcon(getClass().getResource("/img/mine.png"));
    private final Icon flagIcon = createIcon(getClass().getResource("/img/flag.png"));
    private Icon createIcon(URL filename) {
        ImageIcon iconRaw = new ImageIcon(filename);
        Image img = iconRaw.getImage();
        Image imgResized = img.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH);
        return new ImageIcon(imgResized);
    }



    class MineCell extends JPanel {
        private final int rowIndex;
        private final int colIndex;

        private final JButton button;
        private final JLabel label;
        private boolean opened;

        private void initButton() {
            this.button.setToolTipText("(" + rowIndex + "," + colIndex + ")");
            this.button.setVisible(true);
            this.button.addMouseListener(this.buttonMouseAdapter);
        }
        private void initLabel() {
            this.label.setHorizontalAlignment(JLabel.CENTER);
            this.label.setVerticalAlignment(JLabel.CENTER);
            this.label.setVisible(true);
            this.label.addMouseListener(this.labelMouseAdapter);
        }

        public MineCell(int r, int c) {
            this.rowIndex = r;
            this.colIndex = c;

            this.setBorder(new LineBorder(Color.BLACK, 1));
            this.setLayout(new CardLayout());

            this.button = new JButton();
            this.label = new JLabel();

            this.initButton();
            this.initLabel();

            this.add(button, "button");
            this.add(label, "label");

            this.setVisible(true);
            this.opened = false;
        }

        public JButton getButton() {
            return this.button;
        }
        public JLabel getLabel() {
            return this.label;
        }

        private void showButton() {
            CardLayout card = (CardLayout) this.getLayout();
            card.show(this, "button");
        }
        private void showLabel() {
            CardLayout card = (CardLayout) this.getLayout();
            card.show(this, "label");
        }

        public boolean isOpened() {
            return this.opened;
        }

        private void setOpened(boolean opened) {
            this.opened = opened;
            if (opened)
                showLabel();
            else
                showButton();
        }

        public void update(char status) {
            switch (status) {
                case '-':
                    this.setOpened(false);
                    getButton().setText("");
                    getButton().setIcon(null);
                    break;
                case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8':
                    this.setOpened(true);
                    getLabel().setText(String.valueOf(status));
                    getLabel().setIcon(null);
                    break;
                case '*':
                    this.setOpened(true);
                    getLabel().setText("");
                    getLabel().setIcon(mineIcon);
                    break;
                case 'P':
                    this.setOpened(false);
                    getButton().setText("");
                    getButton().setIcon(flagIcon);
                    break;
                case ' ':
                    this.setOpened(true);
                    getLabel().setText(" ");
                    getLabel().setIcon(null);
                default:
            }
        }

        /* Mouse Click Event Listeners */
        // Use Finite State Machine to distinguish Different Behaviors

        private abstract class GameMouseAdapter extends MouseAdapter {
            private int mouseMask;
            private int state;
            private String description;

            public GameMouseAdapter(@NotNull String description) {
                this.description = description;
            }

            protected int getMouseMask() { return this.mouseMask; }
            protected int getState() { return this.state; }
            protected String getDescription() { return this.description; }

            protected void setMouseMask(int mouseMask) { this.mouseMask = mouseMask; }
            protected void setState(int state) { this.state = state; }
            protected void setDescription(String description) { this.description = description; }

            protected abstract void updateState();

            private void mouseOnChange(int eventType, int buttonCode) {
                // eventType: 0 - Pressed, 1 - Released
                // buttonCode: BUTTON1, BUTTON3
                boolean displayMessage = GameOptions.getInstance()
                        .getDebugMessageConfig().isShowMouseEvent();
                if (displayMessage) {
                    if (eventType == 0)
                        System.out.print("Pressed ");
                    else
                        System.out.print("Released ");
                    System.out.print(getDescription() + "@(" + rowIndex + "," + colIndex + "): ");
                    System.out.print("[" + buttonCode + "] ");
                }

                switch (buttonCode) {
                    case MouseEvent.BUTTON1:
                        if (eventType == 0)
                            mouseMask |= (1);
                        else
                            mouseMask &= ~(1);
                        if (displayMessage)
                            System.out.println("LEFT");
                        break;
                    case MouseEvent.BUTTON3:
                        if (eventType == 0)
                            mouseMask |= (1 << 1);
                        else
                            mouseMask &= ~(1 << 1);
                        if (displayMessage)
                            System.out.println("RIGHT");
                        break;
                    default:
                }
                // System.out.println("(" + (mouseMask & 1) + "" + (mouseMask & (1 << 1)) + ")");
                updateState();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int buttonCode = e.getButton();
                mouseOnChange(0, buttonCode);
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int buttonCode = e.getButton();
                mouseOnChange(1, buttonCode);
                super.mouseReleased(e);
            }
        }

        private final GameMouseAdapter buttonMouseAdapter = new GameMouseAdapter("Button") {
            // state: 0 - Idle, 1 - Left Clicked, 2 - Other Action Performed
            @Override
            protected void updateState() {
                switch (getState()) {
                    case 0:
                        switch (getMouseMask()) {
                            case 0: // Idle -> Idle
                                break;
                            case 1: // Idle -> Left Pressed
                                setState(1);
                                break;
                            case 2: // Idle -> Other Action
                                controller.rightClick(rowIndex, colIndex);
                                // GameBoard.this.update();
                                setState(2);
                                break;
                            case 3:
                                setState(2);
                                break;
                            default:
                        }
                        break;
                    case 1:
                        switch (getMouseMask()) {
                            case 0: // Left Pressed -> Released [OKAY]
                                setState(0);
                                controller.leftClick(rowIndex, colIndex);
                                // GameBoard.this.update();
                                break;
                            case 1: // Left Pressed -> Left Pressed
                                break;
                            case 2: case 3: // Left Pressed -> Others
                                setState(2);
                                break;
                            default:
                        }
                        break;
                    case 2:
                        if (getMouseMask() == 0) { // Other Action -> Idle
                            setState(0);
                        }
                        break;
                }
            }
        };

        private final GameMouseAdapter labelMouseAdapter = new GameMouseAdapter("Label") {
            // assign state = mouseMask;
            // always @ (state == 3 -> Others)
            @Override
            protected void updateState() {
                int mask = getMouseMask();
                if (getState() == 3 && mask != 3) {
                    controller.leftRightClick(rowIndex, colIndex);
//                     GameBoard.this.update();
                }
                setState(mask);
            }
        };
    }

    public int getRowNum() {
        return this.rowNum;
    }
    public int getColNum() {
        return this.colNum;
    }
    public void setBoardSize(int row, int col) {
        this.rowNum = row;
        this.colNum = col;
        removeAll();
        revalidate();
        repaint();
        initPanelView();
    }

    public GameBoard(MainWindow father) {
        this.father = father;
    }

    private void initPanelView() {
        this.setLayout(new GridLayout(this.rowNum, this.colNum, 0, 0));
        initCells();
        this.setVisible(true);
    }

    private void initCells() {
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        mines = new MineCell[rowNum][];
        for (int i = 0; i < rowNum; i++) {
            mines[i] = new MineCell[colNum];
            for (int j = 0; j < colNum; j++) {
                mines[i][j] = new MineCell(i, j);
                mines[i][j].setSize(BUTTON_SIZE, BUTTON_SIZE);
                mines[i][j].setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
                mines[i][j].setVisible(true);
                mines[i][j].setFont(font);
                this.add(mines[i][j]);
            }
        }
    }

    void updateMap(String strMap) {
        for (int i = 0; i < this.rowNum; i++) {
            for (int j = 0; j < this.colNum; j++) {
                int cur = i * (this.colNum + 1) + j;
                this.mines[i][j].update(strMap.charAt(cur));
            }
        }
    }

    void endGame(boolean win, int clicks, int time) {
        if (win) {
            JOptionPane.showMessageDialog(null,
                    "恭喜，你赢了！一共左键点击" + clicks + "次。"
                            + "游戏时间：" + time + "秒。");
            // JOptionPane.showMessageDialog(null, "恭喜，你赢了！");
        }
        else {
            JOptionPane.showMessageDialog(null, "抱歉，你输了！");
        }
        controller.restartGame();
        // game.restart();
        // update();
    }
}
