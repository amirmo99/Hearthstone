package logic;

import Util.Monitor;

import java.awt.*;

public class MyTimer extends Thread {

    private Runnable staticRunnable;
    private Runnable usualRunnable;
    private final int initialCountDown;
    private int count;
    private int warningTime;
    private boolean isAlive;
    private boolean isPaused;

    private final Object monitorObject;
    private final Monitor monitor;

    public MyTimer(int initialCountDown, int warningTime) {
        this.initialCountDown = initialCountDown;
        this.warningTime = warningTime;
        usualRunnable = () -> { };
        staticRunnable = () -> { };

        this.monitorObject = new Object();
        this.monitor = new Monitor();
    }

    public MyTimer(int initialCountDown) {
        this(initialCountDown, 0);
    }

    @Override
    public void run() {
        init();

        while (isAlive) {
            try {
                sleep(1000);

                if (isPaused) monitor.doWait(monitorObject);

                tick();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        this.isAlive = true;
        this.isPaused = false;
        this.count = initialCountDown;
    }


    private void tick() {
        count--;
        if (count == warningTime) warn();
        staticRunnable.run();

        if (count <= 0) {
            usualRunnable.run();
            count = initialCountDown;
        }
    }

    private void warn() {
        if (count >= 0)
            Toolkit.getDefaultToolkit().beep();
    }

    public void stopCounting() {
        this.isAlive = false;
    }

    public void pauseCounting() {
        isPaused = true;
    }

    public void resumeCounting() {
        isPaused = false;
        monitor.doNotify(monitorObject);
    }

    public void reset() {
        count = initialCountDown;
    }

    public int getCount() {
        return count;
    }

    public void setStaticRunnable(Runnable staticRunnable) {
        this.staticRunnable = staticRunnable;
    }

    public void setUsualRunnable(Runnable usualRunnable) {
        this.usualRunnable = usualRunnable;
    }
}
