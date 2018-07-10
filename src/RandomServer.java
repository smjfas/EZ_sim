import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ali on 6/25/18.
 */
public class RandomServer extends Server {
    public RandomServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    ArrayList<Work> doWork(double time) {
        addIntegral(time);
        serviceIntegral += time;
        ArrayList<Work> done = new ArrayList<>();
        if (workList.size() <= 0){
            return done;
        }
        Work temp;
        double remainingTime = workList.get(0).getLength() - time;
        while (remainingTime <= 0.0){
            temp = workList.remove(0);
            done.add(temp);
            Collections.shuffle(workList);
            if (workList.size() <= 0){
                return done;
            }
            remainingTime += workList.get(0).getLength();
        }
        workList.get(0).setLength(remainingTime);
        workCompleted += done.size();
        return done;
    }
    @Override
    public double getFirstDoneTime(){
        if (workList.size() <= 0){
            return Double.MAX_VALUE;
        }
        return workList.get(0).getLength();
    }

}
