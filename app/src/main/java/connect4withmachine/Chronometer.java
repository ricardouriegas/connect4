/**
 * @Author: Ricardo Uriegas
 * @Date: 09/04/2024
 * @Version: 1
 * @Description: This class represents the chronometer
 */
package connect4withmachine;

public class Chronometer {
    private long startTime;
    private long stopTime;
    private boolean running;

    /**
     * Function to start the chronometer
     * @return void
     */
    public void start() {
        this.startTime = System.nanoTime();
        this.running = true;
    }

    /**
     * Function to stop the chronometer
     * @return void
     */
    public void stop() {
        this.stopTime = System.nanoTime();
        this.running = false;
    }

    /**
     * Function to get the elapsed time
     * @return String
     */
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

    /**
     * Function to reset the chronometer
     * @return void
     */
    public void reset() {
        this.startTime = 0;
        this.stopTime = 0;
        this.running = false;
    }

    /**
     * Function to set the start time
     * @param startTime
     * @return void
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * Function to set the stop time
     * @return long (startTime)
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Function to set the stop time
     * @return long (stopTime)
     */
    public long getEndTime() {
        return stopTime;
    }

    /**
     * Function to set the stop time
     * @param stopTime
     * @return boolean (if the chronometer is running or not)
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Function to get the elapsed time in seconds
     * @return long (elapsed microseconds)
     */
    public long getMicroSeconds() {
        long elapsed;
        if (running) {
            elapsed = (System.nanoTime() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }

        return elapsed % 1_000_000;
    }

    /**
     * Function to get the elapsed time in seconds
     * @return long (elapsed seconds)
     */
    public long getSeconds() {
        long elapsed;
        if (running) {
            elapsed = (System.nanoTime() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }

        return (elapsed / 1_000_000_000) % 60;
    }

    /**
     * Function to get the elapsed time in minutes
     * @return long (elapsed minutes)
     */
    public long getMinutes() {
        long elapsed;
        if (running) {
            elapsed = (System.nanoTime() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }

        return (elapsed / 1_000_000_000) / 60;
    }

    /**
     * Function to set the time
     * @param minutes
     * @param seconds
     * @param microSeconds
     */
    public void setTime(long minutes, long seconds, long microSeconds) {
        long elapsed = (minutes * 60 * 1_000_000_000) + (seconds * 1_000_000_000) + microSeconds;
        this.startTime = System.nanoTime() - elapsed;
    }
}