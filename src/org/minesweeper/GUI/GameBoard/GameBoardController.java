package org.minesweeper.GUI.GameBoard;

import org.jetbrains.annotations.NotNull;
import org.minesweeper.Game.GameDifficulty;
import org.minesweeper.Game.GameUpdater;
import org.minesweeper.Game.MineSweeperGame;
import org.minesweeper.Options.GameOptions;

public class GameBoardController implements GameUpdater {
    private final GameBoard master;
    private @NotNull MineSweeperGame game;
    public GameBoardController(GameBoard parent) {
        this.master = parent;
        GameDifficulty difficulty = GameOptions.getInstance().getGameDifficulty();
        this.master.setBoardSize(difficulty.getSizeX(), difficulty.getSizeY());
        // start Game at last
        this.game = new MineSweeperGame(GameOptions.getInstance().getGameDifficulty());
        this.game.setUpdater(this);
        this.game.enableTimer();
    }
    public void loadGame(MineSweeperGame game) {

        this.game = game;
        updateMap(game.getStringMap());
    }

    public void restartGame() {
        game.restart(GameOptions.getInstance().getGameDifficulty());
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
    }

    @Override
    public void updateMap(String strMap) {
        this.master.updateMap(strMap);
    }

    @Override
    public void updateTimer(int count) {
        // @TODO: add Timer
    }

    @Override
    public void endGame(boolean win, int clicks, int time) {
        this.master.endGame(win, clicks, time);
    }
}
