import java.io.File;

public class PathReaderThread extends Thread{

    ConsistentFilePathBuffer pathBuffer;
    FileCrawler2 testCrawler;

    public PathReaderThread(ConsistentFilePathBuffer newPathBuffer, FileCrawler2 newTestCrawler){
        this.pathBuffer = newPathBuffer;
        this.testCrawler = newTestCrawler;
    }


    public void run(){

        while(pathBuffer.getSize() > 0 || testCrawler.isAlive()) {
            String targetFile = pathBuffer.getPath();
            File fileToOpen = new File(targetFile);
            if (!fileToOpen.isDirectory())
                throw new IllegalArgumentException("File non valido: non è una directory.");
            File[] filesList = fileToOpen.listFiles();
            for (File currentFile : filesList)
                System.out.println(currentFile.getName());
        }



    }
}
