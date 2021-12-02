import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class GameProgress implements Serializable {
    private static final long serialVersionUID = 1L;

    private int health;
    private int weapons;
    private int lvl;
    private double distance;
    private transient final Logger logger = new Logger();

    public GameProgress(int health, int weapons, int lvl, double distance) throws IOException {
        this.health = health;
        this.weapons = weapons;
        this.lvl = lvl;
        this.distance = distance;
    }

    public void saveGame(String path) {
        try (
                FileOutputStream fos = new FileOutputStream(new File(path));
                ObjectOutputStream oos = new ObjectOutputStream(fos)
            )
        {
            oos.writeObject(this);
            oos.flush();
            logger.addLog("состояние игры сохранено в " + path, true);
        } catch (IOException e) {
            logger.addLog("состояние игры в " + path + " сохранить не удалось", false);
        }
    }

    public void zipFiles(String zipPath, String... listObj) {

        HashMap<String, String> listObjNames = new HashMap<>();

        for (String name : listObj) {
            String[] splitName = name.split(Pattern.quote("\\"));
            Collections.reverse(Arrays.asList(splitName));
            listObjNames.put(splitName[0], name);
        }

        // запись в архив
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {

            for (Map.Entry<String, String> entry : listObjNames.entrySet()) {
                File file = new File(entry.getValue());
                FileInputStream fis = new FileInputStream(file);

                ZipEntry ze = new ZipEntry(entry.getKey());
                zos.putNextEntry(ze);

                if (file.exists() && file.isFile()) {
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    logger.addLog("файл (" + entry.getValue() + ") заархивирован в " + zipPath, true);
                } else {
                    logger.addLog("ошибка при архивации, файл (" + entry.getValue() + ") не существует или не является файлом", false);
                }
                zos.closeEntry();
                fis.close();
            }

        } catch (IOException e) {
            logger.addLog("ошибка при архивации - " + e.getMessage(), false);
        }

        // удаление архивированных файлов
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {

            ZipEntry entry;
            String fileName;

            while ((entry = zis.getNextEntry()) != null) {
                fileName = entry.getName();
                if (listObjNames.containsKey(fileName)) {
                    File delFile = new File(listObjNames.get(fileName));
                    if (delFile.delete()) {
                        logger.addLog("файл (" + listObjNames.get(fileName) + ") удален", true);
                    } else {
                        logger.addLog("файл (" + listObjNames.get(fileName) + ") не удален", false);
                    }
                }

                zis.closeEntry();
            }
        } catch (IOException e) {
            logger.addLog("удаление файлов вне архива", false);
        }
    }

    public void openZip(String zipPath, String path) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String fileName;
            while ((entry = zis.getNextEntry()) != null) {
                fileName = entry.getName();
                FileOutputStream fos = new FileOutputStream(path + "\\" + fileName);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
                logger.addLog("файл (" + fileName + ") разархивирован в " + path, true);
            }
        } catch (IOException e) {
            logger.addLog("архив (" + zipPath + ") не удалось прочитать", false);
        }
    }

    public GameProgress openProgress(String objPath) {
        GameProgress obj = null;
        try (
                FileInputStream fis = new FileInputStream(objPath);
                ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
            obj = (GameProgress) ois.readObject();
            logger.addLog("файл (" + objPath + ") дессерилизован", true);
        } catch (IOException | ClassNotFoundException e) {
            logger.addLog("не удалось дессерилиазовать (" + objPath + ")", false);
        }

        return obj;
    }

    @Override
    public String toString() {
        return "GameProgress{" +
                "health=" + health +
                ", weapons=" + weapons +
                ", lvl=" + lvl +
                ", distance=" + distance +
                '}';
    }
}