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
        ArrayList<Work> done = new ArrayList<>();
        if (workList.size() <= 0){
            return done;
        }
        Work temp;
        double remainingTime = workList.get(0).getTime() - time;
        while (remainingTime <= 0.0){
            temp = workList.remove(0);
            done.add(temp);
            Collections.shuffle(workList);
            if (workList.size() <= 0){
                return done;
            }
            remainingTime += workList.get(0).getTime();
        }
        workList.get(0).setTime(remainingTime);
        workCompleted += done.size();
        return done;
    }
    @Override
    public double getFirstDoneTime(){
        return workList.get(0).getTime();
    }

}
