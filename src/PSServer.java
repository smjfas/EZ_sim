/**
 * Created by ali on 6/25/18.
 */
public class PSServer extends Server {
    public PSServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    int doWork() {
        double reduceAmount = 1 / workList.size();
        double remainingTime = 0.0;
        int numOfDone = 0;
        for (Work aWorkList : workList) {
            remainingTime = aWorkList.getTime() - reduceAmount;
            aWorkList.setTime(remainingTime);
        }
        for (int i = workList.size() - 1; i >=0 ; i--) {
            if (workList.get(i).getTime() <= 0.0){
                workList.remove(i);
                numOfDone ++;
            }
        }
        return numOfDone;
    }

    @Override
    public boolean addWork(Work work) {
        return false;
    }
}
