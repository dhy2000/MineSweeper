package org.minesweeper.Controller;

import org.minesweeper.Game.GameDifficulty;
import org.minesweeper.Game.MineSweeperGame;

public class GameController {

    /* Single Instance Mode */
    private static class GameControllerInstance {
        private static final GameController instance = new GameController();
    }
    private GameController() {

    }

    public static GameController getInstance() {
        return GameControllerInstance.instance;
    }

    public void newGame() {


    }


}
