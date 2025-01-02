public class Log {
    private static Log instance = null;
    private StringBuilder log;

    private Log() {
        log = new StringBuilder();
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void addEvent(String event) {
        log.append(event).append("\n");
    }

    public String getLog() {
        return log.toString();
    }

    public void writeToFile(String filename) {
        try (java.io.FileWriter writer = new java.io.FileWriter(filename)) {
            writer.write(log.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
