class dataQueue {
    private final int []queue;
    private final Object FULL_STATE = new Object();
    private final Object EMPTY_STATE = new Object();
    public final int BUFFERING_SIZE, N;
    public int lastPosition, numOfPrimes, maxPrime = -1;
    private int curIndex, prevIndex, counter;

    public dataQueue(int n, int bufferSize) {
        N = n;
        BUFFERING_SIZE = bufferSize;
        queue = new int[BUFFERING_SIZE];
        curIndex = -1;
        prevIndex = 0;
        counter = 0;
    }

    public int getMaxPrime()
    {
        return maxPrime;
    }
    public int getNumOfPrimes()
    {
        return numOfPrimes;
    }
    public int getLastPosition()
    {
        return lastPosition;
    }
    public void add(int value){
        synchronized (queue){
            // Assigning the prime to next empty position
            curIndex++;

            // Handling curIndex exceeds the limit of queue so it should start again from zero
            curIndex %= BUFFERING_SIZE;

            // Counting the number of primes currently in the queue
            counter++;

            queue[curIndex] = value;
        }
    }
    public int remove() {
        synchronized (queue) {
            // Removing the element at index prevIndex which is the next element to remove sequentially
            int prime = queue[prevIndex];
            prevIndex++;

            // Handling curIndex exceeds the limit of queue so it should start again from zero
            prevIndex %= BUFFERING_SIZE;

            // Counting the number of primes currently in the queue
            counter--;

            return prime;
        }
    }
    boolean isFull(){
        return counter == BUFFERING_SIZE;
    }
    boolean isEmpty(){
        return counter == 0;
    }
    public void waitingFullState() throws InterruptedException {
        synchronized (FULL_STATE) {
            FULL_STATE.wait();
        }
    }
    public void notifyFullState() {
        synchronized (FULL_STATE) {
            FULL_STATE.notifyAll();
        }
    }
    public void waitEmptyState() throws InterruptedException {
        synchronized (EMPTY_STATE) {
            EMPTY_STATE.wait();
        }
    }
    public void notifyEmptyState() {
        synchronized (EMPTY_STATE) {
            EMPTY_STATE.notify();
        }
    }

    void setPrimes(int primes) {
        this.numOfPrimes = primes;
    }
}