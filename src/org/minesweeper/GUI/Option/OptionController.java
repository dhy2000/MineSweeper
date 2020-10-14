package org.minesweeper.GUI.Option;

import org.minesweeper.Game.GameDifficulty;
import org.minesweeper.Options.GameOptions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionController {
    private final OptionBox master;
    public OptionController(OptionBox master) {
        this.master = master;
    }

    public OptionBox getMaster() {
        return master;
    }

    public void loadConfig() {

    }
    public void saveDifficultyConfig(String selCmd) {
        GameDifficulty difficulty = null;
        switch (selCmd) {
            case "Easy":
                difficulty = GameDifficulty.EASY;
                break;
            case "Medium":
                difficulty = GameDifficulty.MEDIUM;
                break;
            case "Hard":
                difficulty = GameDifficulty.HARD;
                break;
            default:
        }
        if (null != difficulty) {
            GameOptions.getInstance().setGameDifficulty(difficulty);
            master.getFather().getGameBoard().getController().restartGame();
        }
    }


}
