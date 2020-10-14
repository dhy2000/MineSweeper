package org.minesweeper.Game;

public interface GameUpdater {
    /* Methods of this Interface will be called by MineSweeperGame instance. */
    void initBoard(int rowNum, int colNum);
    void updateMap(String strMap);
    void updateTimer(int count);
    void endGame(boolean win, int clicks, int time);
}
