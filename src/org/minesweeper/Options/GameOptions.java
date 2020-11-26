package org.minesweeper.Options;

import org.minesweeper.Game.GameDifficulty;

public class GameOptions {

    /* Single Instance Mode */
    private static class GameOptionsInstance {
        private static final GameOptions instance = new GameOptions();
    }
    private GameOptions() {
        this.debugMessageConfig = new DebugMessageConfig();

    }

    public static GameOptions getInstance() {
        return GameOptionsInstance.instance;
    }

    /* Configurations of Debug Messages */
    public static class DebugMessageConfig {
        private boolean showPlayerBehavior = true;
        private boolean showPlayerBehaviorFeedback = true;
        private boolean showMouseEvent = false;
        private boolean showGameBehavior = true;
        public boolean isShowPlayerBehavior() {
            return showPlayerBehavior;
        }
        public boolean isShowMouseEvent() {
            return showMouseEvent;
        }
        public boolean isShowGameBehavior() {
            return showGameBehavior;
        }
        public boolean isShowPlayerBehaviorFeedback() {
            return showPlayerBehaviorFeedback;
        }

        public void setShowPlayerBehavior(boolean option) {
            this.showPlayerBehavior = option;
        }
        public void setShowMouseEvent(boolean option) {
            this.showMouseEvent = option;
        }
        public void setShowGameBehavior(boolean option) {
            this.showGameBehavior = option;
        }
        public void setShowPlayerBehaviorFeedback(boolean option) {
            this.showPlayerBehaviorFeedback = option;
        }
    }
    private final DebugMessageConfig debugMessageConfig;
    public DebugMessageConfig getDebugMessageConfig() {
        return debugMessageConfig;
    }

    private GameDifficulty gameDifficulty = GameDifficulty.MEDIUM;
    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }
    public void setGameDifficulty(GameDifficulty difficulty) {
        gameDifficulty = difficulty;
    }

    private int aiInterval = 100;

    public int getAiInterval() {
        return aiInterval;
    }

    public void setAiInterval(int interval) {
        this.aiInterval = interval;
    }
}
