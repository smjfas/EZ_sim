import java.util.ArrayList;

/**
 * Created by ali on 6/25/18.
 */
public abstract class Server {
    private ArrayList<Work> workList = new ArrayList<>();
    private int mu;
    private int capacity;

    public Server(int mu, int capacity) {
        this.mu = mu;
        this.capacity = capacity;
    }

    abstract void doWork();

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
}
