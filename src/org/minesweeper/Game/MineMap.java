package org.minesweeper.Game;

import org.jetbrains.annotations.NotNull;

import java.util.*;

class MineMap {

    private int sizeX; // x rows
    private int sizeY; // y cols
    private int mineNumber;
    private MineCell[][] map;

    private boolean mineClicked;
    private int discoverCount;

    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
    public int getMineNumber() {
        return mineNumber;
    }

    public boolean isMineClicked() {
        return mineClicked;
    }
    public int getDiscoverCount() {
        return discoverCount;
    }

    private void resetPlayedLog() {
        this.mineClicked = false;
        this.discoverCount = 0;
    }

    enum OperationStatus {
        SUCCESS("Click Succeed"),
        OUT_OF_RANGE("Click Position Out of Range"),
        REJECTED("Click Rejected"),
        IGNORED("Click Ignored"),
        MINE_FOUND("You clicked Mine");

        private String Message;
        OperationStatus(String message) {
            this.Message = message;
        }

        @Override
        public String toString() {
            return Message;
        }
    }

    // Generate An Empty Map
    private void initEmptyMap(int x, int y) {
        // sizeX = x;
        // sizeY = y;
        map = new MineCell[x][];
        for (int i = 0; i < x; i++) {
            map[i] = new MineCell[y];
            for (int j = 0; j < y; j++) {
                map[i][j] = new MineCell(i, j);
            }
        }
    }

    private void placeMine(int mineNumber) {
        if (mineNumber >= sizeX * sizeY)
            return ;
        // this.mineNumber = mineNumber;
        Random rand = new Random();
        for (int i = 0; i < mineNumber; i++) {
            int posX = Math.abs(rand.nextInt()) % sizeX;
            int posY = Math.abs(rand.nextInt()) % sizeY;
            while (map[posX][posY].isMine()) {
                posX = Math.abs(rand.nextInt()) % sizeX;
                posY = Math.abs(rand.nextInt()) % sizeY;
            }
            map[posX][posY].setMine();
        }
    }

    private boolean isNotValid(int x, int y) {
        return x < 0 || x >= sizeX ||
                y < 0 || y >= sizeY;
    }

    @NotNull
    private List<MineCell> getNeighbors(int x, int y) {
        ArrayList<MineCell> neighbors = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                if (isNotValid(x + dx, y + dy))continue;
                neighbors.add(map[x + dx][y + dy]);
            }
        }
        return neighbors;
    }

    private int countNearMines(int x, int y) {
        if (isNotValid(x, y))
            return 0;
        if (map[x][y].isMine())
            return -1;
        int count = 0;
        for (MineCell cell: getNeighbors(x, y)) {
            if (cell.isMine())
                count++;
        }
        return count;
    }

    private void setNumbers() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (!map[i][j].isMine())
                    map[i][j].setValue(countNearMines(i, j));
            }
        }
    }

    private void startMap() {
        resetPlayedLog();
        initEmptyMap(sizeX, sizeY);
        placeMine(mineNumber);
        setNumbers();
    }

    public boolean start(@NotNull GameDifficulty level) {
        int mineNumber = level.getMineNumber();
        int x = level.getSizeX();
        int y = level.getSizeY();
        if (mineNumber >= x * y) {
            // System.out.println("Parameters not valid, Game Start Failed");
            return false;
        }
        sizeX = x;
        sizeY = y;
        this.mineNumber = mineNumber;
        startMap();
        return true;
    }

    private void expand(int x, int y) {
        assert !isNotValid(x, y);
        assert map[x][y].getNumber() == 0;
        Queue<MineCell> queue = new LinkedList<>();
        queue.offer(map[x][y]);
        map[x][y].discover();
        this.discoverCount++;
        while (!queue.isEmpty()) {
            MineCell cur = queue.poll();
            List<MineCell> neighbors = getNeighbors(cur.getPosX(), cur.getPosY());
            for (MineCell cell: neighbors) {
                if (!cell.isDiscovered()) {
                    cell.discover();
                    this.discoverCount++;
                    if (cell.isBlank())
                        queue.offer(cell);
                }
            }
        }

    }

    public OperationStatus click(int x, int y) {
        if (isNotValid(x, y)) {
            // System.out.println("Click Position is Invalid");
            return OperationStatus.OUT_OF_RANGE;
        }
        if (map[x][y].hasFlag()) {
            // System.out.println("Cannot Open: Flagged!");
            return OperationStatus.REJECTED;
        }
        if (map[x][y].isDiscovered()) {
            // System.out.println("Click: Already Discovered");
            return OperationStatus.IGNORED;
        }
        if (map[x][y].isMine()) {
            if (discoverCount == 0) {
                // The Map should be reset
                do {
                    startMap();
                } while (map[x][y].isMine());
                click(x, y);
            }
            else {
                // System.out.println("You Lose!!!");
                mineClicked = true;
                for (int i = 0; i < sizeX; i++) {
                    for (int j = 0; j < sizeX; j++) {
                        map[i][j].discover();
                    }
                }
            }
            return OperationStatus.MINE_FOUND;
        }
        else {
            if (map[x][y].getNumber() != 0) {
                map[x][y].discover();
                discoverCount++;
            }
            else {
                expand(x, y);
            }
            // System.out.println("Click Success!");
            return OperationStatus.SUCCESS;
        }
    }

    public OperationStatus flag(int x, int y) {
        if (discoverCount == 0) {
            // System.out.println("Right Click: Please open at least 1 cell.");
            return OperationStatus.REJECTED;
        }
        if (isNotValid(x, y))
            return OperationStatus.OUT_OF_RANGE;
        if (map[x][y].isDiscovered()) {
            // System.out.println("Right Click: Already Discovered");
            return OperationStatus.IGNORED;
        }
        map[x][y].toggleFlag();
        // System.out.println("Right Click Succeed");
        return OperationStatus.SUCCESS;
    }

    public OperationStatus center(int x, int y) {
        // Double Click
        if (isNotValid(x, y))
            return OperationStatus.OUT_OF_RANGE;
        if (map[x][y].isDiscovered()) {
            List<MineCell> neighbors = getNeighbors(x, y);
            int flagCount = 0;
            for (MineCell cell: neighbors) {
                if (cell.hasFlag())
                    flagCount++;
            }
            if (flagCount == map[x][y].getNumber()) {
                for (MineCell cell: neighbors) {
                    if (!cell.isDiscovered() && !cell.hasFlag()) {
                        click(cell.getPosX(), cell.getPosY());
                    }
                }
            }
            return OperationStatus.SUCCESS;
        }
        return OperationStatus.IGNORED;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (mineClicked)
                    sb.append(map[i][j].show());
                else
                    sb.append(map[i][j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public MineMap() {}

}
