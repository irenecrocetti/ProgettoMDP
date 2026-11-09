package it.unicam.cs.mpgc.rpg130573.persistence;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;


public class JsonSaveRepository implements SaveRepository{
    private final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    private final Path file;

    public JsonSaveRepository(Path file){
        this.file = file;
    }

    @Override
    public void save(GameSnapshot snapshot) {
        try{
            Path parent = file.toAbsolutePath().getParent();
            Files.createDirectories(parent);
            Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
            mapper.writeValue(tmp.toFile(), snapshot);
            Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e){
            throw new PersistenceException("Impossibile salvare la partita", e);
        }
    }

    @Override
    public Optional<GameSnapshot> load() {
        if(!exists()) return Optional.empty();
        try{
            return Optional.of(mapper.readValue(file.toFile(), GameSnapshot.class));
        } catch (JacksonException e){
            throw new PersistenceException("File di salvataggio corrotto o non valido", e);
        } catch (IOException e){
            throw new PersistenceException("Impossibile leggere il salvataggio", e);
        }
    }

    @Override
    public boolean exists() {
        return Files.exists(file);
    }

    @Override
    public void delete() {
        try{ Files.deleteIfExists(file); }
        catch (IOException e) {
            throw new PersistenceException("Impossibile eliminare il salvataggio", e);
        }
    }
}
