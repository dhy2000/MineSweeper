package org.minesweeper.Game;

import org.minesweeper.Options.GameOptions;

public class MineSweeperGame {
    private GameDifficulty difficulty;
    private MineMap map;

    private boolean end;
    private boolean win;

    public GameDifficulty getDifficulty() {
        return difficulty;
    }

    public int getLeftClickTimes() {
        return this.map.getLeftClickTimes();
    }

    private GameTimer timer;

    public boolean hasTimer() {
        return this.timer != null;
    }
    public void enableTimer() {
        this.timer = new GameTimer();
    }
    public void disableTimer() {
        this.timer.stop();
        this.timer = null;
    }
    public int getTime() {
        if (timer == null)
            return -1;
        return timer.getCount();
    }
    private void startTimer() {
        if (hasTimer()) {
            timer.stop();
            timer.reset();
            timer.start();
            if (GameOptions.getInstance().getDebugMessageConfig().isShowGameBehavior()) {
                System.out.println("Timer Reset and Start");
            }
        }
    }
    private void stopTimer() {
        if (hasTimer()) {
            timer.stop();
            if (GameOptions.getInstance().getDebugMessageConfig().isShowGameBehavior()) {
                System.out.println("Timer Stop at " + timer.getCount());
            }
        }
    }

    private void start() {
        if (map == null)
            map = new MineMap();
        if (difficulty == null)
            difficulty = GameDifficulty.EASY;
        boolean result = map.start(difficulty);
        if (!result) {
            System.out.println("EASY Mode is set as default");
            difficulty = GameDifficulty.EASY;
            map.start(difficulty);
        }
        initBoard();
        updateMap();
        if (GameOptions.getInstance().getDebugMessageConfig().isShowGameBehavior())
            System.out.println("Game Start - " + difficulty.toString());
        startTimer();
        end = false;
        win = false;
    }

    public void restart() {
        start();
    }
    public void restart(GameDifficulty difficulty) {
        this.difficulty = difficulty;
        start();
    }

    public String getStringMap() {
        return map.toString();
    }

    private void update() {
        if (map.isMineClicked()) {
            stopTimer();
            end = true;
            win = false;
            updateMap();
            endGame();
        }
        if (map.getDiscoverCount() + map.getMineNumber() == map.getSizeX() * map.getSizeY()) {
            stopTimer();
            end = true;
            win = true;
            endGame();
        }
    }

    public boolean isEnd() {
        return end;
    }
    public boolean isWin() {
        return win;
    }

    enum OperationStatus {
        SUCCESS,
        MAP_CLICK_FAILURE,
        GAME_ENDED
    }

    public OperationStatus leftClick(int x, int y) {
        boolean displayMessage = GameOptions.getInstance()
                .getDebugMessageConfig().isShowPlayerBehavior();
        boolean displayReturn = GameOptions.getInstance()
                .getDebugMessageConfig().isShowPlayerBehaviorFeedback();
        if (displayMessage)
            System.out.println("Left Click @(" + x + ", " + y + ")");
        if (end) {
            return OperationStatus.GAME_ENDED;
        }
        /* Check Timer */
        boolean isInitial = map.getDiscoverCount() == 0;
        MineMap.OperationStatus status = map.click(x, y);
        if (displayReturn)
            System.out.println(status);

        if (!status.isSuccess()) {
            return OperationStatus.MAP_CLICK_FAILURE;
        }
        else {
            // click success
            updateMap();
            update();
            if (isInitial)
                startTimer();
            return OperationStatus.SUCCESS;
        }
    }
    public OperationStatus rightClick(int x, int y) {
        boolean displayMessage = GameOptions.getInstance()
                .getDebugMessageConfig().isShowPlayerBehavior();
        boolean displayReturn = GameOptions.getInstance()
                .getDebugMessageConfig().isShowPlayerBehaviorFeedback();
        if (displayMessage)
            System.out.println("Right Click @(" + x + ", " + y + ")");
        if (end) {
            return OperationStatus.GAME_ENDED;
        }
        MineMap.OperationStatus status = map.flag(x, y);
        if (displayReturn)
            System.out.println(status);
        if (!status.isSuccess()) {
            return OperationStatus.MAP_CLICK_FAILURE;
        }
        else {
            updateMap();
            return OperationStatus.SUCCESS;
        }
    }
    public OperationStatus leftRightClick(int x, int y) {
        boolean displayMessage = GameOptions.getInstance()
                .getDebugMessageConfig().isShowPlayerBehavior();
        boolean displayReturn = GameOptions.getInstance()
                .getDebugMessageConfig().isShowPlayerBehaviorFeedback();
        if (displayMessage)
            System.out.println("Left+Right Click @(" + x + ", " + y + ")");
        if (end) {
            return OperationStatus.GAME_ENDED;
        }
        MineMap.OperationStatus status = map.center(x, y);
        if (displayReturn)
            System.out.println(status);
        if (!status.isSuccess()) {
            return OperationStatus.MAP_CLICK_FAILURE;
        }
        else {
            updateMap();
            update();
            return OperationStatus.SUCCESS;
        }
    }

    public MineSweeperGame(GameDifficulty difficulty) {
        this.difficulty = difficulty;
        start();
    }

    /* Implements Controller */
    private GameUpdater updater; // default null
    public void setUpdater(GameUpdater updater) {
        this.updater = updater;
        updateMap();
    }
    private void initBoard() {
        if (null != updater) {
            updater.initBoard(getDifficulty().getSizeX(), getDifficulty().getSizeY());
        }
    }
    private void updateMap() {
        if (null != updater) {
            updater.updateMap(getStringMap());
        }
    }
    private void endGame() {
        if (null != updater) {
            updater.endGame(isWin(), getLeftClickTimes(), getTime());
        }
    }
}
