package repository;

import domain.NetworkEntity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public abstract class FileRepository<E extends NetworkEntity> extends MemoryRepository<E>{
    private String dataFile;

    public FileRepository(String file) {
        this.dataFile = file;
        readFromFile();
    }

    public void readFromFile() {
        Path path = Paths.get(dataFile);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(line -> {
                E entity=extractEntity(Arrays.asList(line.split(";")));
                super.addOne(entity);
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile() {
        List<E> entities = super.getAll();
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(dataFile,false))) {
            for(E entity : entities) {
                bW.write(createEntityAsString(entity));
                bW.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract E extractEntity(List<String> toExtract);

    protected abstract String createEntityAsString(E entity);

}
