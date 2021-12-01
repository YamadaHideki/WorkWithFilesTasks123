import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private final File logFile;
    private final String path = System.getProperty("user.dir") + "\\Games\\temp";
    private final Date date = new Date();
    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy H:m:s");

    public Logger() throws IOException {

        File logDir = new File(path);
        logFile = new File(logDir + "\\temp.txt");

        if (!logDir.exists()) {
            if (!logDir.mkdir()) {
                throw new IOException("Can't create \\temp in " + path);
            }

            if (!logFile.createNewFile()) {
                throw new IOException("Can't create 'temp.txt' in " + path);
            }
        } else {
            if (!logFile.exists()) {
                if (!logFile.createNewFile()) {
                    throw new IOException("Can't create 'temp.txt' in " + path);
                }
            }
        }
    }

    public void addLog(String info, boolean status) {
        if(logFile.exists()) {
            try (FileWriter fw = new FileWriter(logFile, true)) {
                if(logFile.length() > 0) {
                    fw.append("\n");
                }
                fw.append(dateFormat.format(date)).append(" - ");
                if (status) {
                    fw.append("Успех: ");
                } else {
                    fw.append("Ошибка: ");
                }
                fw.write(info);
                fw.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getPath() {
        return path;
    }
}
