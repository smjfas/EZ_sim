import java.util.ArrayList;

/**
 * Created by ali on 6/25/18.
 */
public abstract class Server {
    private ArrayList<Work> workList;
    int mu;

    public Server(int mu) {
        workList = new ArrayList<>();
        this.mu = mu;
    }

    abstract void doWork();

    public int getMu() {
        return mu;
    }

    public void setMu(int mu) {
        this.mu = mu;
    }
}
