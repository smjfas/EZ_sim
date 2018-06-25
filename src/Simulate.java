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
            double preProcess1AddTime = exponentialRandomGenerator(LAMBDA1);
            double preProcess2AddTime = exponentialRandomGenerator(LAMBDA2);
            Server preProcessServer1 = new SRJFServer(MU1, K1);
            Server preProcessServer2 = new RandomServer(MU2, K2);
            Server mainProcessServer = new PSServer(MU3, k3);
            //WARMUP PHASE
            while (mainProcessServer.getWorkCompleted()<FIRSTPHASETHRESHOLD){
                if(preProcess1AddTime<=0){
                    preProcessServer1.addWork(new Work(exponentialRandomGenerator(MU1)));
                    preProcess1AddTime += exponentialRandomGenerator()
                }
            }
        }
    }

    public static void main(String[] args) {
        Simulate simualtor = new Simulate();
        for(int k3 = K3MIN; k3<=K3MAX; k3++) {
            simualtor.simulate(k3);
        }
    }
}
