package concurrency.CPU;

public class SequentialPrimeFinder extends AbstractPrimeFinder {

    /*
    Let’s exercise the code with 10000000 (10 million) as an argument and observe
    the time taken for this sequential run.
    Number of primes under 10000000 is 664579
    Time (seconds) taken is 5.791894776
     */
    public int countPrimes(final int number) {
        return countPrimesInRange(1, number);
    }
    public static void main(final String[] args) throws InterruptedException {
        new SequentialPrimeFinder().timeAndCompute(Integer.parseInt(args[0]));
    }
}
