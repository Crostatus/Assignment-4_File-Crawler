import java.io.File;
//The FileCrawler is a producer thread that searches in every sub-directory found from the starting one, given at construction time.
//He puts every dicrectory he finds in a ConsistentFilePathBuffer.
public class FileCrawler extends Thread {

    private ConsistentFilePathBuffer pathBuffer;
    private String initialPath;
    private int consumersNumber;
    private String endSearch;

    public FileCrawler(String newInitialPath, ConsistentFilePathBuffer newPathBuffer, int newConsumersNumber, String newEndSearch){
        this.pathBuffer = newPathBuffer;
        this.initialPath = newInitialPath;
        this.consumersNumber = newConsumersNumber;
        this.endSearch = newEndSearch;
    }

    public void run() throws IllegalArgumentException{
        File initialFile = new File(initialPath);
        if(!initialFile.isDirectory())
            throw new IllegalArgumentException("The initial path does not address a directory.");
        pathBuffer.insertPath(initialPath);
        crawl(initialPath);
        stopConsumers();
    }

    //Method that recursively open every directory he finds inside the one located
    // using the argument path.
    //Throws IllegalArgumentException if a path given as argument is not a directory.
    private void crawl(String pathName){
        File currentFile = new File(pathName);
        File[] fileList;
        String currentCanonicalPath;
        if(!currentFile.isDirectory())
            throw new IllegalArgumentException("FileCrawler.crawl-Invalid file: not a directory.");
        try {
            fileList = currentFile.listFiles();
            for (File thisFile : fileList) {
                currentCanonicalPath = thisFile.getCanonicalPath();
                if (thisFile.isDirectory()) {
                    pathBuffer.insertPath(currentCanonicalPath);
                    crawl(currentCanonicalPath);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //Method that inserts in the ConsistentPathBuffer many "endSearch" Strings as the consumers number,
    //to let them know that this thread has ended his execution and no more output will be provided from him.
    private void stopConsumers(){
        int i;
        for(i = 0; i < consumersNumber; i++){
            pathBuffer.insertPath(endSearch);
        }
    }


}
