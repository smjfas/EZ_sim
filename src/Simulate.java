import java.util.ArrayList;

public class Simulate {
    public static final int LAMBDA1 = 7;
    public static final int LAMBDA2 = 2;
    public static final int MU1 = 5;
    public static final int MU2 = 3;
    public static final int MU3 = 1;
    public static final int K1 = 100;
    public static final int K2 = 12;
    public static final int K3MIN = 8;
    public static final int K3MAX = 16;
    public static final int FIRSTPHASETHRESHOLD = 5000;
    public static final int SECONDPHASETHRESHOLD = 50005000;

    public static final int MAXITERATION = 50;

    public static double exponentialRandomGenerator(int lambda){
        return -1 * (Math.log(1- Math.random()) / lambda);
    }

    private void simulate(int k3){
        for (int R = 1; R < MAXITERATION; R++) {
            double time = 0;
            double TTotalSum = 0;
            double preProcess1AddTime = exponentialRandomGenerator(LAMBDA1);
            double preProcess2AddTime = exponentialRandomGenerator(LAMBDA2);
            Server preProcessServer1 = new SRJFServer(MU1, K1);
            Server preProcessServer2 = new RandomServer(MU2, K2);
            Server mainProcessServer = new PSServer(MU3, k3);
            //WARMUP PHASE
            while (mainProcessServer.getWorkCompleted()<SECONDPHASETHRESHOLD){
                //Calculate timeStep
                double timeStep = Math.min(Math.min(preProcess1AddTime, preProcess2AddTime), Math.min(preProcessServer1.getFirstDoneTime(), preProcessServer2.getFirstDoneTime()));

                //Do work
                ArrayList<Work> finished1 = preProcessServer1.doWork(timeStep);
                ArrayList<Work> finished2 = preProcessServer2.doWork(timeStep);
                ArrayList<Work> finished = mainProcessServer.doWork(timeStep);
                time += timeStep;

                //Pass timeStep
                preProcess1AddTime -= timeStep;
                preProcess2AddTime -= timeStep;
                //-- Add to Main Server
                for (Work work : finished1) {
                    work.setLength(exponentialRandomGenerator(MU3));
                    mainProcessServer.addWork(work);
                }
                for (Work work : finished2) {
                    work.setLength(exponentialRandomGenerator(MU3));
                    mainProcessServer.addWork(work);
                }
                //-- Add to Pre Process Server
                if(preProcess1AddTime<=0){
                    preProcessServer1.addWork(new Work(exponentialRandomGenerator(MU1), time));
                    preProcess1AddTime += exponentialRandomGenerator(LAMBDA1);
                }
                if(preProcess2AddTime<=0){
                    preProcessServer1.addWork(new Work(exponentialRandomGenerator(MU2), time));
                    preProcess2AddTime += exponentialRandomGenerator(LAMBDA2);
                }

                // Calculate statistics
                for(Work work : finished){
                    TTotalSum += time - work.getStartTime();
                }
            }
            double PB1 = preProcessServer1.getWorkCompleted() / (double) preProcessServer2.getWorkCount();
            double LQ1 = preProcessServer1.getIntegral() / time;
            double WQ1 = preProcessServer1.getIntegral() / (double) preProcessServer1.getWorkCompleted();
            double PB3 = mainProcessServer.getWorkCompleted() / (double) mainProcessServer.getWorkCount();
            double TTotal = TTotalSum / time;
            double LQ3 = mainProcessServer.getIntegral() / time;
        }
    }

    public static void main(String[] args) {
        Simulate simualtor = new Simulate();
        for(int k3 = K3MIN; k3<=K3MAX; k3++) {
            simualtor.simulate(k3);
        }
    }
}
