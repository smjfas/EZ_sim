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

    Server(int mu, int capacity) {
        this.mu = mu;
        this.capacity = capacity;
    }

    abstract int doWork(double time);

    public int getMu() {
        return mu;
    }

    public boolean addWork(Work work){
        if (workList.size() >= capacity){
            return false;
        } else {
            workList.add(work);
            workCount ++;
            return true;
        }
    }

    public double getFirstDoneTime(){
        double answer = Double.MAX_VALUE;
        for (int i = 0; i < workList.size(); i++) {
            if (workList.get(i).getTime() < answer){
                answer = workList.get(i).getTime();
            }
        }
        return answer;
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
