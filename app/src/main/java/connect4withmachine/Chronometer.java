package connect4withmachine;

public class Chronometer {
    private long startTime;
    private long stopTime;
    private boolean running;

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    public long getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }
        
        // transform to seconds 
        elapsed = elapsed / 1000;

        return elapsed;
    }

    public void reset() {
        this.startTime = 0;
        this.stopTime = 0;
        this.running = false;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean isRunning() {
        return running;
    }


}
