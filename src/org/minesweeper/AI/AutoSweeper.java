package org.minesweeper.AI;

import org.minesweeper.Game.MineSweeperGame;

public interface AutoSweeper {
    int SLEEP_INTERVAL = 100;
    boolean playGame();
    void stop();
    boolean isGameEnd();
}
