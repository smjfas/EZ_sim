/**
 * Created by ali on 6/25/18.
 */
public class RandomServer extends Server {
    public RandomServer(int mu, int capacity) {
        super(mu, capacity);
    }
    @Override
    int doWork() {
        //TODO
        return 0;
    }

    @Override
    public boolean addWork(Work work) {
        return false;
    }

}
