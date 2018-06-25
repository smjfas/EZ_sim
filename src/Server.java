import java.util.ArrayList;

/**
 * Created by ali on 6/25/18.
 */
public abstract class Server {
    protected ArrayList<Work> workList = new ArrayList<>();
    protected int mu;
    protected int capacity;
    protected int workCount = 0;
    protected int workCompleted = 0;

    Server(int mu, int capacity) {
        this.mu = mu;
        this.capacity = capacity;
    }

    abstract void doWork();

    public int getMu() {
        return mu;
    }

    abstract public boolean addWork(Work work);

    public void setMu(int mu) {
        this.mu = mu;
    }

    public int getWorkCount() {
        return workCount;
    }

    public int getWorkCompleted() {
        return workCompleted;
    }
}
