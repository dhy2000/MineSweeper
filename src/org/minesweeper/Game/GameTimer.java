package org.minesweeper.Game;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private int count;
    private boolean isRunning;
    private class Task extends TimerTask {
        @Override
        public void run() {
            count = count + 1;
            update();
        }
    }
    private Timer timer;

    private GameUpdater updater;
    public void setUpdater(GameUpdater updater) {
        this.updater = updater;
    }

    public GameTimer() {

    }

    protected void update() {
        if (null != updater)
            updater.updateTimer(getCount());
    }

    public int getCount() {
        return count;
    }
    public boolean isRunning() {
        return isRunning;
    }

    public void start() {
        if (isRunning)
            return ;
        this.isRunning = true;
        // System.out.println("Timer Start !");
        // this.schedule(this.task, 1);
        this.timer = new Timer();
        timer.schedule(new Task(), 0, 1000);
    }
    public void stop() {
        if (!isRunning)
            return ;
        this.timer.cancel();
        this.timer = null;
        // System.out.println("Timer Stop !");
        this.isRunning = false;
    }
    public synchronized void reset() {
        if (!isRunning) {
            count = 0;
        }
    }

    public static void main(String[] args) {
        // Test GameTimer
        GameTimer gt = new GameTimer() {
            @Override
            protected void update() {
                System.out.println("@timer count = " + getCount());
            }
        };
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            String cmd = cin.next();
            switch (cmd.charAt(0)) {
                case 's':
                    gt.start();
                    break;
                case 't':
                    gt.stop();
                    break;
                case 'r':
                    gt.reset();
                    break;
                case 'd':
                    System.out.println("count = " + gt.getCount());
                    break;
                default:
            }
        }
        gt.stop();
        cin.close();
    }

}
