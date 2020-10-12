package org.minesweeper.Game;

public class MineSweeperGame {

    private GameDifficulty difficulty;
    private MineMap map;

    private boolean end;
    private boolean win;

    public GameDifficulty getDifficulty() {
        return difficulty;
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
            end = true;
            win = false;
        }
        if (map.getDiscoverCount() + map.getMineNumber() == map.getSizeX() * map.getSizeY()) {
            end = true;
            win = true;
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
        if (end) {
            // System.out.println("Game: Ended.");
            return OperationStatus.GAME_ENDED;
        }
        MineMap.OperationStatus status = map.click(x, y);
        // System.out.println(status);
        update();
        if (status != MineMap.OperationStatus.SUCCESS)
            return OperationStatus.MAP_CLICK_FAILURE;
        else
            return OperationStatus.SUCCESS;
    }
    public OperationStatus rightClick(int x, int y) {
        if (end) {
            System.out.println("Game: Ended");
            return OperationStatus.GAME_ENDED;
        }
        MineMap.OperationStatus status = map.flag(x, y);
        if (status != MineMap.OperationStatus.SUCCESS)
            return OperationStatus.MAP_CLICK_FAILURE;
        else
            return OperationStatus.SUCCESS;
    }
    public OperationStatus leftRightClick(int x, int y) {
        if (end) {
            // System.out.println("Game: Ended");
            return OperationStatus.GAME_ENDED;
        }
        MineMap.OperationStatus status = map.center(x, y);
        update();
        if (status != MineMap.OperationStatus.SUCCESS)
            return OperationStatus.MAP_CLICK_FAILURE;
        else
            return OperationStatus.SUCCESS;
    }

    public MineSweeperGame(GameDifficulty difficulty) {
        this.difficulty = difficulty;
        start();
    }
}
