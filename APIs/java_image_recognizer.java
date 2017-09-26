public class APIs {
    public static String getCurrDir() {
        File file = new File("java_image_recognizer.java");
        String parentDirName = file.getAbsoluteFile().getParent();
        String ml_dir = parent_dir + "/ml_backend/";
    }
    public static void train(String filename) {
        Process process = Runtime.getRuntime().exec("python3 " + ml_dir + "train.py " + filename);
    }
    public static void classify(String filename) {
        // Which file is the main one for classification
        Process process = Runtime.getRuntime().exec("python3 " + ml_dir + "classify.py " + filename);
    }
    public static void scrape_web(String query, Integer count) {
        Process process = Runtime.getRuntime().exec("python3" + ml_dir + "scraper.py " + "-s " + query + " -n " + Integer.toString(int));
    }

}
