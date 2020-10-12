package org.minesweeper.Test;

import org.minesweeper.Game.GameDifficulty;
import org.minesweeper.Game.MineSweeperGame;

import java.util.Scanner;

public class MineSweeperGameTester {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        MineSweeperGame game = null;
        System.out.println(" ------ Welcome to play Mine Sweeper Game ----");
        System.out.println("[Select Difficulty]");
        System.out.println("    1 for Easy, 2 for Medium, 3 for Hard, 4 for Custom");
        System.out.print("Enter Your Choice: ");
        int ch = cin.nextInt();
        switch (ch) {
            case 1:
                System.out.println("[Easy Mode]");
                game = new MineSweeperGame(GameDifficulty.EASY);
                break;
            case 2:
                System.out.println("[Medium Mode]");
                game = new MineSweeperGame(GameDifficulty.MEDIUM);
                break;
            case 3:
                System.out.println("[Hard Mode]");
                game = new MineSweeperGame(GameDifficulty.HARD);
                break;
            case 4:
                System.out.println("[Custom Mode]");
                System.out.println("Please Input X, Y, and the number of Mines");
                int x = cin.nextInt(), y = cin.nextInt(), nMine = cin.nextInt();
                game = new MineSweeperGame(new GameDifficulty(x, y, nMine));
                break;
            default:
                System.out.println("Illegal Input!");
        }
        if (null == game) {
            cin.close();
            return ;
        }
        System.out.println(game.getDifficulty());
        System.out.println("[Game Start]");
        char op;
        int x, y;
        System.out.println(game.getStringMap());
        System.out.print("> ");
        while (!game.isEnd() && cin.hasNext()) {
            op = cin.next().charAt(0);
            x = cin.nextInt();
            y = cin.nextInt();
            if (op == 'c') {
                game.leftClick(x, y);
            }
            else if (op == 'r') {
                game.rightClick(x, y);
            }
            else if (op == 'd') {
                game.leftRightClick(x, y);
            }
            else {
                System.out.println("Illegal Input");
            }
            System.out.println(game.getStringMap());
            System.out.print("> ");
        }
        if (game.isEnd()) {
            if (game.isWin()) {
                System.out.println("YOU WIN !!!");
            }
            else {
                System.out.println("YOU LOSE >_<");
            }
        }
        else {
            System.out.println("Terminated.");
        }
        cin.close();
    }
}

/*
  1P1
  111
1211  111
P2P1  1P1
121212221
1112P4P31
1P12P4PP1
111112221

(Last Line is completely blank)

    111
  112*211
222*212*1
**2121211
331 1*1
*1  111
1111211
112*2*1
1*21211

 */