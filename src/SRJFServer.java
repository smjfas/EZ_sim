import java.util.ArrayList;

/**
 * Created by ali on 6/25/18.
 */
public class SRJFServer extends Server {

    public SRJFServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    ArrayList<Work> doWork(double time) {
        addIntegral(time);
        ArrayList<Work> done = new ArrayList<>();
        if (workList.size() <= 0){
            return done;
        }
        Work temp;
        double remainingTime = workList.get(0).getLength() - time;

        while (remainingTime <= 0.0){
            temp = workList.remove(0);
            done.add(temp);
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
    public boolean addWork(Work work) {
        workCount++;
        if (workList.size() >= capacity)
            return false;

        for (int i = 0; i < workList.size(); i++) {
            // Keep the tasks sorted
            if (workList.get(i).getLength() >= work.getLength()){
                workList.add(i, work);
                return true;
            }
        }
        // Task is bigger than all the others.
        workList.add(work);
        return true;

    }

    @Override
    public double getFirstDoneTime(){
        double answer = Double.MAX_VALUE;
        for (int i = 0; i < workList.size(); i++) {
            if (workList.get(i).getLength() < answer){
                answer = workList.get(i).getLength();
            }
        }
        return answer;
    }
}
