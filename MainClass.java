import java.io.*;
import java.util.Scanner;

public class MainClass {

    public static void main(String[] args){
        //Parameters needed: testDirectory consumerNumberContainer.txt
        // (File)       testDirectory : A directory that needs to be explored by the producer thread.
        // (File.txt)   consumerNumberContainer.txt : A file that contains the number >= 1 of consumer
        //                                            threads that MainClass will start.
        File inputFile = new File(args[0]);
        if(!inputFile.isDirectory())
            throw new IllegalArgumentException("main: input parameter is not a directory.");

        File[] inputFileList = inputFile.listFiles();
        File testDirectory = inputFileList[1];
        int consumersNumber = 0;
        if(inputFileList[0].isDirectory()) {
            testDirectory = inputFileList[0];
            consumersNumber = getFromInputFileList(inputFileList[1]);
        }
        else {
            testDirectory = inputFileList[1];
            consumersNumber = getFromInputFileList(inputFileList[0]);
        }
        //Consumer and producers thread share the same special String to let the producer alert that no more output
        //will be produced from him.
        String endOfSearchString = "EOS";
        ConsistentFilePathBuffer testBuffer = new ConsistentFilePathBuffer();
        FileCrawler producerThread = new FileCrawler(testDirectory.getPath(), testBuffer, consumersNumber, endOfSearchString);
        producerThread.start();

        PathReaderThread newReader;
        for(int i = 0; i < consumersNumber; i++){
            newReader = new PathReaderThread(testBuffer, endOfSearchString);
            newReader.start();
        }


    }

    //Method to open the file.txt containing the number of consumers requested.
    //Throws IllegalArgumentException if a < 1 number is given.
    private static int getFromInputFileList(File consumerNumberContainer){
        int result = 0;
        try {
            Scanner scanner = new Scanner(consumerNumberContainer);
            result = scanner.nextInt();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(result < 1)
            throw new IllegalArgumentException("Invalid input parameter: this program must have at least 1 consumer.");
        return result;
    }


}
