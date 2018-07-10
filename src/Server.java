import java.util.ArrayList;

/**
 * Created by ali on 6/25/18.
 */
public abstract class Server {
    ArrayList<Work> workList = new ArrayList<>();
    private int mu;
    int capacity;
    int workCount = 0;
    int workCompleted= 0;
    double integral = 0;
    double serviceIntegral = 0;


    public double getIntegral() {
        return integral;
    }

    Server(int mu, int capacity) {
        this.mu = mu;
        this.capacity = capacity;
    }

    abstract ArrayList<Work> doWork(double time);

    public int getMu() {
        return mu;
    }

    void addIntegral(double time){
        integral += time * workList.size();
    }

    public boolean addWork(Work work){
        workCount ++;
        serviceIntegral += work.getLength();
        if (workList.size() >= capacity)
            return false;
        workList.add(work);
        return true;
    }

    abstract public double getFirstDoneTime();

    public void setMu(int mu) {
        this.mu = mu;
    }

    public int getWorkCount() {
        return workCount;
    }

    public int getWorkCompleted() {
        return workCompleted;
    }

    public void resetStatistics(){
        workCount = 0;
        workCompleted = 0;
        integral = 0.0;
    }

    public double getServiceIntegral() {
        return serviceIntegral;
    }
}
