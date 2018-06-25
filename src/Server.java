import java.util.ArrayList;

/**
 * Created by ali on 6/25/18.
 */
public abstract class Server {
    ArrayList<Work> workList = new ArrayList<>();
    private int mu;
    int capacity;
    int workCount;
    int workCompleted;

    Server(int mu, int capacity) {
        this.mu = mu;
        this.capacity = capacity;
    }

    abstract int doWork();

    public int getMu() {
        return mu;
    }

    public boolean addWork(Work work){
        if (workList.size() >= capacity){
            return false;
        } else {
            workList.add(work);
            return true;
        }
    }

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
