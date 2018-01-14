package com.springinaction.spitter.persistence;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;

import java.util.List;

public interface SpitterDAO {
    void addSpitter(Spitter spitter);
    void saveSpitter(Spitter spitter);
    Spitter getSpitterById(long id);
    Spitter getSpitterByUsername(String username);
    List<Spitter> findAllSpitters();

    void saveSpittle(Spittle spittle);
    void deleteSpittle(Spittle spittle);
    Spittle getSpittleById(long id);
    List<Spittle> getSpittlesForSpitter(Spitter spitter);
    List<Spittle> getRecentSpittle();
}
