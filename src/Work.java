/**
 * Created by ali on 6/25/18.
 */
public class Work {
    private double length;
    private double startTime;

    public Work(double Length, double startTime) {
        this.length = Length;
        this.startTime = startTime;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
}
