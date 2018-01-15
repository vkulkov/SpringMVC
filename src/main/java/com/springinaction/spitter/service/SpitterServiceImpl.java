package com.springinaction.spitter.service;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;
import com.springinaction.spitter.persistence.SpitterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service("spitterService")
@Transactional(propagation = Propagation.REQUIRED)
public class SpitterServiceImpl implements SpitterService {
    private final SpitterDAO spitterDAO;

    @Autowired
    public SpitterServiceImpl(SpitterDAO spitterDAO) {
        this.spitterDAO = spitterDAO;
    }

    @Override
    public void saveSpitter(Spitter spitter) {
        if (spitter.getId() == null) {
            spitterDAO.addSpitter(spitter);
        } else {
            spitterDAO.saveSpitter(spitter);
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Spitter getSpitter(long id) {
        return spitterDAO.getSpitterById(id);
    }

    @Override
    public Spitter getSpitter(String username) {
        return spitterDAO.getSpitterByUsername(username);
    }

    @Override
    public List<Spitter> getAllSpitters() {
        return spitterDAO.findAllSpitters();
    }

    @Override
    public void saveSpittle(Spittle spittle) {
        spittle.setWhen(LocalDate.now());
        spitterDAO.saveSpittle(spittle);
    }

    @Override
    public void deleteSpittle(Spittle spittle) {
        spitterDAO.deleteSpittle(spittle);
    }

    @Override
    public Spittle getSpittleById(long id) {
        return spitterDAO.getSpittleById(id);
    }

    @Override
    public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
        return spitterDAO.getSpittlesForSpitter(spitter);
    }

    @Override
    public List<Spittle> getSpittlesForSpitter(String username) {
        Spitter spitter = spitterDAO.getSpitterByUsername(username);
        return getSpittlesForSpitter(spitter);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Spittle> getRecentSpittles(int count) {
        List<Spittle> recentSpittles = spitterDAO.getRecentSpittle();
        Collections.reverse(recentSpittles);
        return recentSpittles.subList(0, Math.min(count, recentSpittles.size()));
    }
}
