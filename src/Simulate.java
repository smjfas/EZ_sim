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
    public static final int FIRSTPHASETHRESHOLD = 500;
    public static final int SECONDPHASETHRESHOLD = 500500;
    public static final double PRECISIONTHRESHOLD = 0.05;

    public static final int MAXITERATION = 2    ;

    public static double exponentialRandomGenerator(int lambda){
        return -1 * (Math.log(1- Math.random()) / lambda);
    }

    public static double sampleMean(ArrayList<Double> Y){
        double sum = 0;
        for (int i=0; i<Y.size(); i++)
            sum+=Y.get(i);
        return sum/Y.size();
    }

    public static double sampleStd(ArrayList<Double> Y){
        double sampleMean = sampleMean(Y);
        if (Y.size() <= 1)
            return sampleMean;
        double sum = 0;
        for (int i = 0; i < Y.size(); i++)
            sum += (Y.get(i) - sampleMean) * (Y.get(i) - sampleMean);
        return Math.sqrt(sum / (Y.size()-1));
    }

    public static double computePrecision(ArrayList<Double> Y, int R){
        return 1.96 * sampleStd(Y) / (Math.sqrt(R) * sampleMean(Y));
    }


    private void simulate(int k3){
        ArrayList<Double> PB1 = new ArrayList<>();
        ArrayList<Double> LQ1 = new ArrayList<>();
        ArrayList<Double> WQ1 = new ArrayList<>();
        ArrayList<Double> PB3 = new ArrayList<>();
        ArrayList<Double> TTotal = new ArrayList<>();
        ArrayList<Double> LQ3 = new ArrayList<>();
        boolean PB1Finished = false;
        boolean LQ1Finished = false;
        boolean WQ1Finished = false;
        boolean PB3Finished = false;
        boolean TTotalFinished = false;
        boolean LQ3Finished = false;
        int PB1R = 0, LQ1R = 0, WQ1R = 0, PB3R = 0, TTotalR = 0, LQ3R = 0;
        double PB1Precision = 0, LQ1Precision = 0, WQ1Precision = 0, PB3Precision = 0, TTotalPrecision = 0, LQ3Precision = 0;
        double PB1Mean = 0, LQ1Mean = 0, WQ1Mean = 0, PB3Mean = 0, TTotalMean = 0, LQ3Mean = 0;
        for (int R = 1; R < MAXITERATION; R++) {
            double time = 0, mainStart = 0;
            double TTotalSum = 0;
            double preProcess1AddTime = exponentialRandomGenerator(LAMBDA1);
            double preProcess2AddTime = exponentialRandomGenerator(LAMBDA2);
            boolean isWarmup = true;
            Server preProcessServer1 = new SRJFServer(MU1, K1);
            Server preProcessServer2 = new RandomServer(MU2, K2);
            Server mainProcessServer = new PSServer(MU3, k3);
            //WARMUP PHASE
            while (mainProcessServer.getWorkCompleted()<SECONDPHASETHRESHOLD){
                if(isWarmup && mainProcessServer.getWorkCompleted() > FIRSTPHASETHRESHOLD){
                    isWarmup = false;
                    mainStart = time;
                    preProcessServer1.resetStatistics();
                    preProcessServer2.resetStatistics();
                    mainProcessServer.resetStatistics();
                }
                //Calculate timeStep
                double timeStep = Math.min(Math.min(Math.min(preProcess1AddTime, preProcess2AddTime), Math.min(preProcessServer1.getFirstDoneTime(), preProcessServer2.getFirstDoneTime())), mainProcessServer.getFirstDoneTime());

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
                    preProcessServer2.addWork(new Work(exponentialRandomGenerator(MU2), time));
                    preProcess2AddTime += exponentialRandomGenerator(LAMBDA2);
                }

                // Calculate statistics
                for(Work work : finished){
                    TTotalSum += time - work.getStartTime();
                }
            }
            time -= mainStart;
            PB1.add(1 - preProcessServer1.getWorkCompleted() / (double) preProcessServer1.getWorkCount());
            LQ1.add((preProcessServer1.getIntegral() - preProcessServer1.getServiceIntegral()) / time);
            WQ1.add((preProcessServer1.getIntegral() - preProcessServer1.getServiceIntegral()) / (double) preProcessServer1.getWorkCompleted());
            PB3.add(1 - mainProcessServer.getWorkCompleted() / (double) mainProcessServer.getWorkCount());
            TTotal.add(TTotalSum / time);
            LQ3.add(mainProcessServer.getIntegral() / time);
            if(!PB1Finished && computePrecision(PB1, R)< PRECISIONTHRESHOLD){
                PB1Finished = true;
                PB1Precision = computePrecision(PB1, R);
                PB1R = R;
                PB1Mean = sampleMean(PB1);
            }

            if(!LQ1Finished && computePrecision(LQ1, R)< PRECISIONTHRESHOLD){
                LQ1Finished = true;
                LQ1Precision = computePrecision(LQ1, R);
                LQ1R = R;
                LQ1Mean = sampleMean(LQ1);
            }

            if(!WQ1Finished && computePrecision(WQ1, R)< PRECISIONTHRESHOLD){
                WQ1Finished = true;
                WQ1Precision = computePrecision(WQ1, R);
                WQ1R = R;
                WQ1Mean = sampleMean(WQ1);
            }

            if(!PB3Finished && computePrecision(PB3, R)< PRECISIONTHRESHOLD){
                PB3Finished = true;
                PB3Precision = computePrecision(PB3, R);
                PB3R = R;
                PB3Mean = sampleMean(PB3);
            }

            if(!TTotalFinished && computePrecision(TTotal, R)< PRECISIONTHRESHOLD){
                TTotalFinished = true;
                TTotalPrecision = computePrecision(TTotal, R);
                TTotalR = R;
                TTotalMean = sampleMean(TTotal);
            }

            if(!LQ3Finished && computePrecision(LQ3, R)< PRECISIONTHRESHOLD){
                LQ3Finished = true;
                LQ3Precision = computePrecision(LQ3, R);
                LQ3R = R;
                LQ3Mean = sampleMean(LQ3);
            }

            if(PB1Finished && LQ1Finished && WQ1Finished && PB3Finished && TTotalFinished && LQ3Finished)
                break;

        }
        // Compute for non computed variables
        if(!PB1Finished){
            PB1Precision = computePrecision(PB1, MAXITERATION);
            PB1R = MAXITERATION;
            PB1Mean = sampleMean(PB1);
        }

        if(!LQ1Finished){
            LQ1Precision = computePrecision(LQ1, MAXITERATION);
            LQ1R = MAXITERATION;
            LQ1Mean = sampleMean(LQ1);
        }

        if(!WQ1Finished){
            WQ1Precision = computePrecision(WQ1, MAXITERATION);
            WQ1R = MAXITERATION;
            WQ1Mean = sampleMean(WQ1);
        }

        if(!PB3Finished){
            PB3Precision = computePrecision(PB3, MAXITERATION);
            PB3R = MAXITERATION;
            PB3Mean = sampleMean(PB3);
        }

        if(!TTotalFinished){
            TTotalPrecision = computePrecision(TTotal, MAXITERATION);
            TTotalR = MAXITERATION;
            TTotalMean = sampleMean(TTotal);
        }

        if(!LQ3Finished){
            LQ3Precision = computePrecision(LQ3, MAXITERATION);
            LQ3R = MAXITERATION;
            LQ3Mean = sampleMean(LQ3);
        }

        // Print results
        System.out.println("Results for K3 = " + k3 + ":\n");

        System.out.println("******* PB1 *******");
        System.out.println("Y: " + PB1.toString());
        System.out.println("Sample Mean: " + PB1Mean);
        System.out.println("Min R: " + PB1R);
        System.out.println("Precision: " + PB1Precision);

        System.out.println("******* LQ1 *******");
        System.out.println("Y: " + LQ1.toString());
        System.out.println("Sample Mean: " + LQ1Mean);
        System.out.println("Min R: " + LQ1R);
        System.out.println("Precision: " + LQ1Precision);

        System.out.println("******* WQ1 *******");
        System.out.println("Y: " + WQ1.toString());
        System.out.println("Sample Mean: " + WQ1Mean);
        System.out.println("Min R: " + WQ1R);
        System.out.println("Precision: " + WQ1Precision);

        System.out.println("******* PB3 *******");
        System.out.println("Y: " + PB3.toString());
        System.out.println("Sample Mean: " + PB3Mean);
        System.out.println("Min R: " + PB3R);
        System.out.println("Precision: " + PB3Precision);

        System.out.println("******* TTotal *******");
        System.out.println("Y: " + TTotal.toString());
        System.out.println("Sample Mean: " + TTotalMean);
        System.out.println("Min R: " + TTotalR);
        System.out.println("Precision: " + TTotalPrecision);

        System.out.println("******* LQ3 *******");
        System.out.println("Y: " + LQ3.toString());
        System.out.println("Sample Mean: " + LQ3Mean);
        System.out.println("Min R: " + LQ3R);
        System.out.println("Precision: " + LQ3Precision);

        System.out.println("---------------------------------");
    }

    public static void main(String[] args) {
        Simulate simualtor = new Simulate();
        for(int k3 = K3MIN; k3<=K3MAX; k3++) {
            simualtor.simulate(k3);
        }
    }
}
