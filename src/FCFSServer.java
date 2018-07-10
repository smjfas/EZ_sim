import java.util.ArrayList;

/**
 * Created by ali on 6/25/18.
 */
public class FCFSServer extends Server {
    public FCFSServer(int mu, int capacity) {
        super(mu, capacity);
    }

    @Override
    ArrayList<Work> doWork(double time) {
        addIntegral(time);
        serviceIntegral += time;
        ArrayList<Work> done = new ArrayList<>();
        while (time > 0 && workList.size()>0){
            Work tempWork = workList.get(0);
            if(tempWork.getLength() <= time){
                time -= tempWork.getLength();
                workList.remove(0);
                done.add(tempWork);
            }
            else{
                tempWork.setLength(tempWork.getLength() - time);
                time = 0;
            }
        }
        workCompleted += done.size();
        return done;
    }

    @Override
    public double getFirstDoneTime() {
        return workList.get(0).getLength();
    }
}
