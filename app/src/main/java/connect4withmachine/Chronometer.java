package connect4withmachine;

public class Chronometer {
    private long startTime;
    private long endTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long getElapsedTimeInMilliseconds() {
        if (startTime == 0) {
            throw new IllegalStateException("Chronometer has not been started");
        }
        if (endTime == 0) {
            throw new IllegalStateException("Chronometer has not been stopped");
        }
        return (endTime - startTime) / 1000000; // Convert nanoseconds to milliseconds
    }

    public void reset() {
        startTime = 0;
        endTime = 0;
    }

    public boolean isRunning() {
        return startTime != 0 && endTime == 0;
    }

    // get time
    public long getTime() {
        return getElapsedTimeInMilliseconds();
    }
}

