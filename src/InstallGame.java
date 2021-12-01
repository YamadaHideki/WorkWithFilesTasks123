import java.io.File;
import java.io.IOException;

public class InstallGame {
    private final Logger logger = new Logger();

    public InstallGame(String path) throws IOException {
        createFolders(path, "src", "res", "savegames");
        createFolders(path + "\\src", "main", "test");
        createFolders(path + "\\res", "drawables", "vectors", "icons");
        createFiles(path + "\\src\\main", "Main.java", "Utils.java");
    }

    public void createFolders(String path, String... names) {
        File fileDir = new File(path);
        if (fileDir.isDirectory()) {
            for (String name : names) {
                File newDir = new File(path + "\\" + name);
                logger.addLog("cоздание директории (" + newDir.getName() + ") в " + path, newDir.mkdir());
            }
        }
    }

    public void createFiles(String path, String... names) {
        File fileDir = new File(path);
        if (fileDir.isDirectory()) {
            for (String name : names) {
                File newFile = new File(path + "\\" + name);
                try {
                    logger.addLog("создание файла (" + newFile.getName() + ") в " + path, newFile.createNewFile());
                } catch (IOException e) {
                    logger.addLog("создание файла (" + newFile.getName() + ") в " + path + " | " + e.getMessage(), false);
                }
            }
        }
    }
}
