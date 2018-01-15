package com.springinaction.spitter.service;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;

import java.util.List;

public interface SpitterService {
    void saveSpitter(Spitter spitter);
    Spitter getSpitter(long id);
    Spitter getSpitter(String username);
    List<Spitter> getAllSpitters();

    void saveSpittle(Spittle spittle);
    void deleteSpittle(Spittle spittle);
    Spittle getSpittleById(long id);
    List<Spittle> getSpittlesForSpitter(Spitter spitter);
    List<Spittle> getSpittlesForSpitter(String username);
    List<Spittle> getRecentSpittles(int count);
}
