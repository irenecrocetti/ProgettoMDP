package it.unicam.cs.mpgc.rpg130573.persistence;

import it.unicam.cs.mpgc.rpg130573.application.session.GameSession;

import java.util.Optional;

public class SaveService {
    private final SaveRepository repository;
    private final GameSessionMapper mapper;

    public SaveService(SaveRepository repository, GameSessionMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    public void save(GameSession session){
        if(session.hasActiveBattle())
            throw new IllegalStateException("Non si può salvare durante una battaglia");
        repository.save(mapper.toSnapshot(session));
    }

    public Optional<GameSession> load(){
        return repository.load().map(mapper::fromSnapshot);
    }

    public boolean hasSave() {
        return repository.exists();
    }
}
