import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String mainPath = System.getProperty("user.dir") + "\\Games";
        String saveGamePath = mainPath + "\\savegames";
        String zipPath = mainPath + "\\savegames\\zip.zip";
        String savePathPlayer = mainPath + "\\savegames\\player.dat";
        String savePathPlayer2 = mainPath + "\\savegames\\player2.dat";
        String savePathPlayer3 = mainPath + "\\savegames\\player3.dat";

        InstallGame installGame = new InstallGame(mainPath);

        GameProgress player = new GameProgress(100, 3, 23, 163.45);
        GameProgress player2 = new GameProgress(93, 2, 11, 1543.98);
        GameProgress player3 = new GameProgress(65, 6, 54, 122.75);

        player.saveGame(savePathPlayer);
        player2.saveGame(savePathPlayer2);
        player3.saveGame(savePathPlayer3);

        player.zipFiles(zipPath, savePathPlayer, savePathPlayer2, savePathPlayer3);
        player.openZip(zipPath, saveGamePath);

        GameProgress save = player.openProgress(savePathPlayer3);
        GameProgress save2 = player.openProgress(savePathPlayer2);
        GameProgress save3 = player.openProgress(savePathPlayer);

        System.out.println(save);
        System.out.println(save2);
        System.out.println(save3);
    }
}
