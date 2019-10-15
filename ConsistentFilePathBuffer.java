import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


//Il produttore potrebbe non fregarsene di tutta la struttura, può accedere all' ultimo elemento
//senza dar noia agli altri threads
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

    public void printTest(){
        for(String current : filePathBuffer)
            System.out.println(current);


    }

    public synchronized int getSize(){
        return filePathBuffer.size();
    }


}
