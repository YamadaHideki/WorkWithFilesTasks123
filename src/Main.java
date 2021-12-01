import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String mainPath = System.getProperty("user.dir") + "\\Games";
        InstallGame installGame = new InstallGame(mainPath);

        GameProgress player = new GameProgress(100, 3, 23, 163.45);
        GameProgress player2 = new GameProgress(93, 2, 11, 1543.98);
        GameProgress player3 = new GameProgress(65, 6, 54, 122.75);

        String savePathPlayer = mainPath + "\\savegames\\player.dat";
        String savePathPlayer2 = mainPath + "\\savegames\\player2.dat";
        String savePathPlayer3 = mainPath + "\\savegames\\player3.dat";

        player.saveGame(savePathPlayer);
        player2.saveGame(savePathPlayer2);
        player3.saveGame(savePathPlayer3);
    }
}
