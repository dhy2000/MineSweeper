package org.minesweeper.AI;

import javafx.scene.control.RadioMenuItem;
import org.minesweeper.Game.MineSweeperGame;

import java.util.*;

public class AIOne implements AutoSweeper
{

    private MineSweeperGame game;
    private int rowNum;
    private int colNum;
    private char[][] charMap;
    private int[][] discoveredCount;
    private int[][] flagCount;
    private boolean isAlive;

    static class Position
    {
        private final int x;
        private final int y;
        public Position(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public int getX()
        {
            return x;
        }

        public int getY()
        {
            return y;
        }
    }

    private Queue<Position> clickQueue;
    private Queue<Position> flagQueue;

    private void loadCharMap(String strMap)
    {
        this.charMap = new char[this.rowNum][];
        for (int i = 0; i < rowNum; i++) {
            this.charMap[i] = new char[this.colNum];
            for (int j = 0; j < colNum; j++) {
                int cur = i * (this.colNum + 1) + j;
                char chr = strMap.charAt(cur);
                this.charMap[i][j] = chr;
            }
        }
    }

    private int countNeighbors(int x, int y)
    {
        int cnt = 0;
        for (int ii = -1; ii <= 1; ii++)
        {
            for (int jj = -1; jj <= 1; jj++)
            {
                if (ii == 0 && jj == 0)
                    continue;
                int ni ;
                ni = x + ii;
                int nj ;
                nj = y + jj;
                if (ni < 0 || ni >= this.rowNum)
                {
                    continue;
                }
                if (nj < 0 || nj >= this.colNum)
                {
                    continue;
                }
                cnt++;
            }
        }
        return cnt;
    }

    private void countDiscover()
    {
        for (int i = 0; i < rowNum; i++)
        {
            for (int j = 0; j < colNum; j++)
            {
                int cnt = 0;
                for (int ii = -1; ii <= 1; ii++)
                {
                    for (int jj = -1; jj <= 1; jj++)
                    {
                        int ni = i + ii;
                        int nj = j + jj;
                        if (ni < 0 || ni >= rowNum)
                        {
                            continue;
                        }

                        if (nj < 0 || nj >= colNum)
                        {
                            continue;
                        }
                        if (ii == 0 && jj == 0) {
                            continue;
                        }
                        if (this.charMap[ni][nj] == '-'
                                || this.charMap[ni][nj] == 'P')
                        {
                            cnt++;
                        }
                    }
                }
                this.discoveredCount[i][j] = countNeighbors(i, j) - cnt;
            }
        }
    }

    private void countFlag()
    {
        for (int i = 0; i < rowNum; i++)
        {
            for (int j = 0; j < colNum; j++)
            {
                int cnt = 0;
                for (int ii = -1; ii <= 1; ii++)
                {
                    for (int jj = -1; jj <= 1; jj++)
                    {
                        int ni = i + ii;
                        int nj = j + jj;
                        if (ni < 0 || ni >= rowNum)
                        {
                            continue;
                        }
                        if (nj < 0 || nj >= colNum)
                        {
                            continue;
                        }
                        if (ii == 0 && jj == 0)
                        {
                            continue;
                        }
                        if (this.charMap[ni][nj] == 'P')
                        {
                            cnt++;
                        }
                    }
                }
                this.flagCount[i][j] = cnt;
            }
        }
    }

    private boolean selectCanClick()
    {
        boolean flag = false;
        for (int i = 0; i < rowNum; i++)
        {
            for (int j = 0; j < colNum; j++)
            {
                if (charMap[i][j] >= '0' && charMap[i][j] <= '9')
                {
                    int mineNum = charMap[i][j] - '0';
//
                    if (flagCount[i][j] == mineNum
                            && flagCount[i][j] + discoveredCount[i][j] < countNeighbors(i, j))
                    {
                        flag = true;
                        clickQueue.offer(new Position(i, j));
                    }
                }
            }
        }
        return flag;
    }

    private boolean selectCanFlag()
    {
        boolean flag = false;
        for (int i = 0; i < rowNum; i++)
        {
            for (int j = 0; j < colNum; j++)
            {
                if (charMap[i][j] >= '0' && charMap[i][j] <= '9')
                {
                    int mineNum = charMap[i][j] - '0';
                    if (mineNum > flagCount[i][j]
                            && mineNum + discoveredCount[i][j] == countNeighbors(i, j)) {
                        flag = true;
                        flagQueue.offer(new Position(i, j));
                    }
                }
            }
        }
        return flag;
    }

    private void doClick()
    {
        while (!clickQueue.isEmpty())
        {
            Position pos = clickQueue.peek();
            clickQueue.poll();
            if (!isAlive)
            {
                return;
            }
            int clickX;
            int clickY;
            clickX = pos.getX();
            clickY = pos.getY();
            game.leftRightClick(clickX, clickY);
            if (game.isEnd()) {
                return;
            }
            loadCharMap(game.getStringMap());
            try
            {
                Thread.sleep(SLEEP_INTERVAL);
            }
            catch (InterruptedException e)
            {

            }
        }
    }

    private void doFlag()
    {
        while (!flagQueue.isEmpty())
        {
            Position pos = flagQueue.peek();
            flagQueue.poll();
            for (int dx = -1; dx <= 1; dx++)
            {
                for (int dy = -1; dy <= 1; dy++)
                {
                    int nxtx = dx + pos.getX();
                    int nxty = dy + pos.getY();
                    if (dx == 0 && dy == 0)
                    {
                        continue;
                    }
                    if (nxtx < 0 || nxtx >= this.rowNum)
                    {
                        continue;
                    }
                    if (nxty < 0 || nxty >= this.colNum)
                    {
                        continue;
                    }
                    if (charMap[nxtx][nxty] == '-')
                    {
                        if (!isAlive)
                            return;
                        game.rightClick(nxtx, nxty);
                        loadCharMap(game.getStringMap());
//                        System.out.println(game.getStringMap());
                        try
                        {
                            Thread.sleep(SLEEP_INTERVAL);
                        }
                        catch (InterruptedException e)
                        {

                        }
                    }

                }
            }
        }
    }

    private boolean autoPlayOne()
    {
        boolean flgFlag;
        boolean flgClick;
        // loaded map
        countFlag();
        countDiscover();
        flgFlag = selectCanFlag();
        doFlag();
        countFlag();
        countDiscover();
        flgClick = selectCanClick();
        if (!flgFlag && !flgClick)
        {
            return false;
        }
        doClick();
        return true;
    }

    private void displayCharMap() {
        for (int i = 0; i < rowNum; i++)
        {
            for (int j = 0; j < colNum; j++)
            {
                char ch = charMap[i][j];
                System.out.print(ch);
            }
            System.out.println();
        }
    }

    public AIOne(MineSweeperGame game)
    {
        clickQueue = new ArrayDeque<>();
        flagQueue = new ArrayDeque<>();
        this.game = game;
        this.rowNum = game.getDifficulty().getSizeX();
        this.colNum = game.getDifficulty().getSizeY();
        String strMap = game.getStringMap();
        this.loadCharMap(strMap);
        this.discoveredCount = new int[this.rowNum][];
        for (int i = 0; i < rowNum; i++)
        {
            this.discoveredCount[i] = new int [this.colNum];
        }
        this.flagCount = new int[this.rowNum][];
        for (int i = 0; i < rowNum; i++)
        {
            this.flagCount[i] = new int [this.colNum];
        }
    }

    @Override
    public boolean playGame()
    {
        boolean flag = false;
        isAlive = true;
        while (isAlive)
        {
            if (game.isEnd())
            {
                flag = true;
                break;
            }
            // countFlag();
            // countDiscover();
            flag = autoPlayOne();
            if (!flag)
            {
                break;
            }
            flag = true;
            loadCharMap(game.getStringMap());
        }
        System.out.println("AI One finished!");
        return flag;
    }

    @Override
    public void stop()
    {
        this.isAlive = false;
    }

    @Override
    public boolean isGameEnd()
    {
        return game.isEnd();
    }

    @Override
    public void nextAction(int strategy)
    {
        System.out.println("AI Continue!");
        this.isAlive = true;
        // refresh the states
        clickQueue.clear();
        flagQueue.clear();
        this.loadCharMap(game.getStringMap());
        for (int i = 0; i < rowNum; i++)
        {
            for (int j = 0; j < colNum; j++)
            {
                discoveredCount[i][j] = 0;
                flagCount[i][j] = 0;
            }
        }
//        randClick();
        switch (strategy)
        {
            case 1:
                randClick();
                break;
            case 2:
                randClickBorder();
                break;
            case 3:
//                System.out.println("Not developed next(3) yet.");
                clickMostProbably();
                break;
            default:
                break;
        }
    }

    private List<Position> listNotClicked()
    {
        List<Position> notvis = new ArrayList<Position>();
        for (int i = 0; i < rowNum; i++)
        {
            for (int j = 0; j < colNum; j++)
            {
                if (charMap[i][j] == '-') {
                    notvis.add(new Position(i, j));
                }
            }
        }
        return notvis;
    }

    private void randClick()
    {
        List<Position> notvis;
        notvis = listNotClicked();
        int n ;
        n = notvis.size();
        Random rnd ;
        rnd = new Random();
        int idx;
        idx = rnd.nextInt(n);
        Position pos;
        pos = notvis.get(idx);
        int clickX;
        int clickY;
        clickX = pos.getX();
        clickY = pos.getY();
        game.leftClick(clickX, clickY);
    }

    private List<Position> listBorder()
    {
        List<Position> borders = new ArrayList<>();
        for (int i = 0; i < rowNum; i++)
        {
            for (int j = 0; j < colNum; j++)
            {
                // border:
                int cnt;
                cnt = 0;
                if (charMap[i][j] == '-')
                {
                    for (int ii = -1; ii <= 1; ii++)
                    {
                        for (int jj = -1; jj <= 1; jj++)
                        {
                            if (ii == 0 && jj == 0)
                            {
                                continue;
                            }
                            int nxti;
                            int nxtj;
                            nxti = i + ii;
                            nxtj = j + jj;
                            if (nxti < 0 || nxti >= rowNum)
                            {
                                continue;
                            }
                            if (nxtj < 0 || nxtj >= colNum)
                            {
                                continue;
                            }
                            if (charMap[nxti][nxtj] == '-'
                                    || charMap[nxti][nxtj] == 'P')
                            {
                                cnt = cnt + 1;
                            }
                        }
                    }
                    if (cnt < countNeighbors(i, j))
                    {
                        borders.add(new Position(i, j));
                    }
                }
            }
        }
        return borders;
    }

    private void randClickBorder()
    {
        List<Position> borderList = listBorder();
        if (borderList.isEmpty())
        {
            randClick();
            return;
        }
        int n;
        n = borderList.size();
        Random rnd;
        rnd = new Random();
        int idx;
        idx = rnd.nextInt(n);
        Position pos;
        pos = borderList.get(idx);
        int clickX;
        int clickY;
        clickX = pos.getX();
        clickY = pos.getY();
        game.leftClick(clickX, clickY);
    }

    private Position minUncertain()
    {
        Position retPos = null;
        List<Position> borderList = listBorder();
        int minUncertain = 9;
        for (Position pos : borderList)
        {
            // calc Uncertainty
            int i;
            i = pos.getX();
            int j;
            j = pos.getY();
            int flgcnt;
            flgcnt = flagCount[i][j];
            int discovercnt;
            discovercnt = discoveredCount[i][j];
            int neibourcnt;
            neibourcnt = countNeighbors(i, j);
            int uncertain;
            uncertain = neibourcnt - flgcnt - discovercnt;
            if (uncertain < 0)
                uncertain = -uncertain;
            if (uncertain < minUncertain)
            {
                minUncertain = uncertain;
                retPos = pos;
            }
        }
        return retPos;
    }

    private void clickMostProbably()
    {
        List<Position> clickList = new ArrayList<>();
        Position minUncPos = minUncertain();
        if (minUncPos == null) {
            randClick();
            return;
        }
        int i;
        int j;
        i = minUncPos.getX();
        j = minUncPos.getY();
        for (int ii = -1; ii <= 1; ii++)
        {
            for (int jj = -1; jj <= 1; jj++)
            {
                if (ii == 0 && jj == 0)
                {
                    continue;
                }
                int nxti;
                int nxtj;
                nxti = i + ii;
                nxtj = j + jj;
                if (nxti < 0 || nxti >= rowNum)
                {
                    continue;
                }
                if (nxtj < 0 || nxtj >= colNum)
                {
                    continue;
                }
                if (charMap[nxti][nxtj] == '-')
                {
                    clickList.add(new Position(nxti, nxtj));
                }
            }
        }
        int n;
        n = clickList.size();
        Random rnd ;
        rnd = new Random();
        int idx;
        idx = rnd.nextInt(n);
        Position posToClick;
        posToClick = clickList.get(idx);
        int clickX;
        int clickY;
        clickX = posToClick.getX();
        clickY = posToClick.getY();
        game.leftClick(clickX, clickY);
    }

}
