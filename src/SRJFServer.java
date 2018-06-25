/**
 * Created by ali on 6/25/18.
 */
public class SRJFServer extends Server {

    public SRJFServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    int doWork() {
        int numOfDone = 0;
        double remainingTime = workList.get(0).getTime() - 1.0;
        while (remainingTime < 0.0){
        }
        return 0;
    }

    @Override
    public boolean addWork(Work work) {
        if (workList.size() >= capacity){
            return false;
        } else {
            for (int i = 0; i < workList.size(); i++) {
                // Keep the tasks sorted
                if (workList.get(i).getTime() >= work.getTime()){
                    workList.add(i, work);
                    return true;
                }
            }
            // Task is bigger than all the others.
            workList.add(work);
            return true;
        }
    }
}
