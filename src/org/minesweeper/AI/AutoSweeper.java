package org.minesweeper.AI;

import org.minesweeper.Game.MineSweeperGame;
import org.minesweeper.Options.GameOptions;

public interface AutoSweeper
{
    int SLEEP_INTERVAL = GameOptions.getInstance().getAiInterval();
    boolean playGame();
    void stop();
    boolean isGameEnd();
    void nextAction(int strategy);
}
