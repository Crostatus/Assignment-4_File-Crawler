import java.io.File;
import java.io.IOException;

public class FileCrawler2 extends Thread {

    ConsistentFilePathBuffer pathBuffer;
    String initialPath;

    public FileCrawler2(String newInitialPath, ConsistentFilePathBuffer newPathBuffer){
        this.pathBuffer = newPathBuffer;
        this.initialPath = newInitialPath;
    }

    public void run() throws IllegalArgumentException{
        File initialFile = new File(initialPath);
        if(!initialFile.isDirectory())
            throw new IllegalArgumentException("Il path iniziale non corrisponde ad una directory.");
        pathBuffer.insertPath(initialPath);
        crawl(initialPath);

    }

    private void crawl(String pathName){
        File currentFile = new File(pathName);
        File[] fileList;
        String currentCanonicalPath;
        if(!currentFile.isDirectory())
            throw new IllegalArgumentException("Parametro invalido: non è una directory.");
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




}