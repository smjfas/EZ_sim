/**
 * Created by ali on 6/25/18.
 */
public class RandomServer extends Server {
    public RandomServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    int doWork(double time) {
        return 0;
    }
    @Override
    public double getFirstDoneTime(){
        return workList.get(0).getTime();
    }

}
