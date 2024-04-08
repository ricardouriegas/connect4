package connect4withmachine;

public class Chronometer {
    private long startTime;
    private long stopTime;
    private boolean running;

    public void start() {
        this.startTime = System.nanoTime();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.nanoTime();
        this.running = false;
    }

    public String getElapsedTime() {
        long elapsed;
        if (running) {
            elapsed = (System.nanoTime() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }

        long minutes = (elapsed / 1_000_000_000) / 60;
        long seconds = (elapsed / 1_000_000_000) % 60;
        long microSeconds = (elapsed % 1_000_000);

        return String.format("%d min, %d sec, %d microsec", minutes, seconds, microSeconds);
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

    public long getMicroSeconds() {
        long elapsed;
        if (running) {
            elapsed = (System.nanoTime() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }

        return elapsed % 1_000_000;
    }

    public long getSeconds() {
        long elapsed;
        if (running) {
            elapsed = (System.nanoTime() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }

        return (elapsed / 1_000_000_000) % 60;
    }

    public long getMinutes() {
        long elapsed;
        if (running) {
            elapsed = (System.nanoTime() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }

        return (elapsed / 1_000_000_000) / 60;
    }

    public void setTime(long minutes, long seconds, long microSeconds) {
        long elapsed = (minutes * 60 * 1_000_000_000) + (seconds * 1_000_000_000) + microSeconds;
        this.startTime = System.nanoTime() - elapsed;
    }
}