/**
 * Created by ali on 6/25/18.
 */
public class SRJFServer extends Server {

    public SRJFServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    int doWork(double time) {
        int numOfDone = 0;
        double remainingTime = workList.get(0).getTime() - time;
        while (remainingTime < 0.0){
            workList.remove(0);
            numOfDone++;
            remainingTime += workList.get(0).getTime();
        }
        workList.get(0).setTime(remainingTime);
        return numOfDone;
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

    @Override
    public double getFirstDoneTime(){
        double answer = Double.MAX_VALUE;
        for (int i = 0; i < workList.size(); i++) {
            if (workList.get(i).getTime() < answer){
                answer = workList.get(i).getTime();
            }
        }
        return answer;
    }
}
