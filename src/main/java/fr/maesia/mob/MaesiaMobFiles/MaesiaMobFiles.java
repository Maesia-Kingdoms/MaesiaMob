package fr.maesia.mob.MaesiaMobFiles;

import fr.maesia.mob.MaesiaMob;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Logger;

public enum MaesiaMobFiles {
    Messages("Messages.yml");

    private final String filename;
    private final File dataFolder;

    MaesiaMobFiles(String filename) {
        this.filename = filename;
        this.dataFolder = MaesiaMob.getInstance().getDataFolder();
    }

    public void Create(Logger logger) {

        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        InputStream in = MaesiaMob.getInstance().getResource(filename);

        if (in == null) {
            throw new IllegalArgumentException("The reource '" + filename + "' cannot be found in plugin jar");
        }

        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            logger.severe("Failled to make directory");
        }

        File outFile = getFile();

        if (!outFile.exists()) {
            logger.info("The " + filename + " was not found, creation in progress ...");

            try {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int n;
                while ((n = in.read(buf)) >= 0) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();

                if (!outFile.exists()) {
                    logger.severe("Unable to copy file");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public File getFile(){
        return new File(dataFolder, filename);
    }

    public FileConfiguration getConfig(){
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(FileConfiguration config){
        try {
            config.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
