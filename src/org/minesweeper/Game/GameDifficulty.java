package org.minesweeper.Game;

public final class GameDifficulty {
    private final int sizeX;
    private final int sizeY;
    private final int mineNumber;

    public GameDifficulty(int x, int y, int mineNumber) {
        sizeX = x;
        sizeY = y;
        this.mineNumber = mineNumber;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getMineNumber() {
        return mineNumber;
    }

    @Override
    public String toString() {
        return "X = " + sizeX + ", Y = " + sizeY + ", Mine: " + mineNumber;
    }

    public static final GameDifficulty EASY;
    public static final GameDifficulty MEDIUM;
    public static final GameDifficulty HARD;

    static {
        EASY = new GameDifficulty(9, 9, 10);
        MEDIUM = new GameDifficulty(16, 16, 40);
        HARD = new GameDifficulty(16, 30, 99);
    }
}
