package org.minesweeper.Game;

import org.jetbrains.annotations.NotNull;

class MineCell {
    /* Attributes */
    private int posX;
    private int posY;
    private int value;
    private boolean discovered;
    private boolean flag;
    /* Constructor Methods */
    public MineCell() {
        posX = 0;
        posY = 0;
        value = 0;
        discovered = false;
        flag = false;
    }
    public MineCell(int x, int y) {
        posX = x;
        posY = y;
        value = 0;
        discovered = false;
        flag = false;
    }
    public MineCell(int x, int y, int value) {
        posX = x;
        posY = y;
        this.value = value;
        discovered = false;
        flag = false;
    }

    /* Methods of Position */
    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public void setPosX(int x) {
        posX = x;
    }
    public void setPosY(int y) {
        posY = y;
    }
    public void setPos(int x, int y) {
        posX = x;
        posY = y;
    }

    /* Methods of Value and Type */
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public boolean isMine() {
        return this.value == -1;
    }
    public void setMine() {
        this.value = -1;
    }
    public boolean isNumber() {
        return this.value >= 1 && this.value <= 8;
    }
    public int getNumber() {
        if (isNumber())
            return this.value;
        else
            return 0;
    }
    public boolean isBlank() {
        return this.value == 0;
    }
    /* Methods of Flag */
    public void addFlag() {
        flag = true;
    }
    public void removeFlag() {
        flag = false;
    }
    public void toggleFlag() {
        flag = !flag;
    }
    public boolean hasFlag() {
        return flag;
    }
    /* Methods of Discover */
    public void discover() {
        // if (!isMine())
            discovered = true;
        // else game failed
    }
    public boolean isDiscovered() {
        return discovered;
    }

    /* Output */

    @NotNull
    private String getString(boolean show) {
        StringBuilder sb = new StringBuilder();
        // sb.append("[");
        char content = '-';
        if (flag)
            content = 'P';
        if (discovered || show) {
            if (isNumber())
                content = (char) (getNumber() + '0');
            else if (isMine())
                content = '*';
            else
                content = ' ';
        }
        sb.append(content);
        // sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        return getString(false);
    }
    public String show() {
        return getString(true);
    }

}
