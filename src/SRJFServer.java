/**
 * Created by ali on 6/25/18.
 */
public class SRJFServer extends Server {

    public SRJFServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    void doWork() {

    }

    @Override
    public boolean addWork(Work work) {
        if (workList.size() >= capacity){
            return false;
        } else {
            for (int i = 0; i < workList.size(); i++) {
                
            }
        }
    }
}
