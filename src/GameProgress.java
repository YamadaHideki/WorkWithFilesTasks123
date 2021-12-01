import java.io.*;

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

    public void

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