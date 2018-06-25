import java.util.ArrayList;

/**
 * Created by ali on 6/25/18.
 */
public class PSServer extends Server {
    PSServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    ArrayList<Work> doWork(double time) {
        ArrayList<Work> done = new ArrayList<>();
        if (workList.size() <= 0){
            return done;
        }
        double reduceAmount = time / workList.size();
        double remainingTime;
        Work temp;

        for (Work aWorkList : workList) {
            remainingTime = aWorkList.getTime() - reduceAmount;
            aWorkList.setTime(remainingTime);
        }

        for (int i = workList.size() - 1; i >=0 ; i--) {
            if (workList.get(i).getTime() <= 0.0){
                temp = workList.remove(i);
                done.add(temp);
            }
        }

        workCompleted += done.size();

        return done;
    }

    @Override
    public double getFirstDoneTime(){
        double answer = Double.MAX_VALUE;
        for (int i = 0; i < workList.size(); i++) {
            if (workList.get(i).getTime() < answer){
                answer = workList.get(i).getTime();
            }
        }
        return answer * workList.size();
    }
}
