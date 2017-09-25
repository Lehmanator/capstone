public class APIs {
    public static void train(String filename) {
        Process process = Runtime.getRuntime().exec("python3 train.py" + filename);
    }
    public static void classify(String filename) {
        Process process = Runtime.getRuntime().exec("python3 " + filename);
    }
    public static void scrape_web(String query, Integer count) {
        Process process = Runtime.getRuntime().exec("python3 scraper.py -s " + query + "-n " + Integer.toString(int));
    }

}
