/**
 * Created by ali on 6/25/18.
 */
public class FCFSServer extends Server {
    public FCFSServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    int doWork() {
        return 0;
    }

    @Override
    public boolean addWork(Work work) {
        return false;
    }
}
