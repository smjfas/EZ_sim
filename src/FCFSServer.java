import java.util.ArrayList;

/**
 * Created by ali on 6/25/18.
 */
public class FCFSServer extends Server {
    public FCFSServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    ArrayList<Work> doWork(double time) {
        return null;
    }

    @Override
    public boolean addWork(Work work) {
        return false;
    }

    @Override
    public double getFirstDoneTime() {
        return 0;
    }
}
