public class MainClass {

    public static void main(String[] args){

        ConsistentFilePathBuffer testBuffer = new ConsistentFilePathBuffer();
        FileCrawler2 testCrawler = new FileCrawler2("/home/crostatus/Scrivania/Andrea", testBuffer);
        PathReaderThread testReader = new PathReaderThread(testBuffer, testCrawler);
        testCrawler.start();
        testReader.start();
        try {
            testCrawler.join();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //testBuffer.printTest();




    }


}
