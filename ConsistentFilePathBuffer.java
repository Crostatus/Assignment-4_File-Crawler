import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//ConsistentFilePathBuffer is a thread-safe data structure to store paths.
public class ConsistentFilePathBuffer {
    public LinkedList<String> filePathBuffer;
    public ReentrantLock lockQueue;
    public Condition isNotEmpty;

    public ConsistentFilePathBuffer() {
        filePathBuffer = new LinkedList<>();
        lockQueue = new ReentrantLock();
        isNotEmpty = lockQueue.newCondition();
    }

    public void insertPath(String newFilePath){
        lockQueue.lock();
        try {
            filePathBuffer.addLast(newFilePath);
            isNotEmpty.signalAll();
        }
        finally {
            lockQueue.unlock();
        }
    }

    public String getPath(){
        lockQueue.lock();
        try {
            while (filePathBuffer.size() == 0) {
                try {
                    isNotEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            String result = filePathBuffer.pollFirst();
            return result;
        }
        finally {
            lockQueue.unlock();
        }

    }
}
