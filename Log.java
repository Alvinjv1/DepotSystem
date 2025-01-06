import java.util.ArrayList;
import java.util.List;

public class Log {
    private static Log instance;
    private List<String> logEntries;

    private Log() {
        logEntries = new ArrayList<>();
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void addLog(String logEntry) {
        logEntries.add(logEntry);
    }

    public List<String> getLogEntries() {
        return logEntries;
    }
}
