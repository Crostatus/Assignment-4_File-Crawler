import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
//A PathReaderThread is a consumer type of thread, waiting for directories paths to be added in the ConsistentFileBuffer,
//open them and print all of the content.
public class PathReaderThread extends Thread{

    private ConsistentFilePathBuffer pathBuffer;
    private String endSearch;
    private static SimpleDateFormat formatter = new SimpleDateFormat("[HH:mm:ss");

    public PathReaderThread(ConsistentFilePathBuffer newPathBuffer, String newEndSearch){
        this.pathBuffer = newPathBuffer;
        this.endSearch = newEndSearch;
    }

    public void run() {
        String targetFilePath;
        File fileToOpen;
        while ((targetFilePath = pathBuffer.getPath()) != endSearch) {
            fileToOpen = new File(targetFilePath);
            if (!fileToOpen.isDirectory())
                throw new IllegalArgumentException("Invalid File: not a directory.");
            File[] filesList = fileToOpen.listFiles();
            printDirectoryContent(targetFilePath, filesList);
        }
    }

    private static synchronized void printDirectoryContent(String parentDirectory, File [] filesList){
        Date date = new Date();
        System.out.println(formatter.format(date) + "-INFO] " + Thread.currentThread().getName() + " opened directory located at " + parentDirectory);
        System.out.println("[DIRECTORY CONTENT]");
        for(File currentFile : filesList){
            System.out.println(currentFile.getName());
        }
        System.out.println("[END CONTENT]");
        System.out.flush();
    }
}
