package com.springinaction.spitter.persistence;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class JpaSpitterDAO implements SpitterDAO {
    private static final String SPITTER_FOR_USERNAME = "SELECT s FROM Spitter s WHERE s.username = :username";
    private static final String ALL_SPITTER = "SELECT s FROM Spitter s";
    private static final String SPITTLES_FROM_SPITTER = "SELECT s FROM Spittle s WHERE s.spitter.id = :spitterId";
    private static final String RECENT_SPITTLES = "SELECT s FROM Spittle s WHERE s.when = :postedTime";

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addSpitter(Spitter spitter) {
        em.persist(spitter);
    }

    @Override
    public void saveSpitter(Spitter spitter) {
        em.merge(spitter);
    }

    @Override
    public Spitter getSpitterById(long id) {
        return em.find(Spitter.class, id);
    }

    @Override
    public Spitter getSpitterByUsername(String username) {
        return em.createQuery(SPITTER_FOR_USERNAME, Spitter.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    @Override
    public List<Spitter> findAllSpitters() {
        return em.createQuery(ALL_SPITTER, Spitter.class).getResultList();
    }

    @Override
    public void saveSpittle(Spittle spittle) {
        em.persist(spittle);
    }

    @Override
    public void deleteSpittle(Spittle spittle) {
        em.remove(spittle);
    }

    @Override
    public Spittle getSpittleById(long id) {
        return em.find(Spittle.class, id);
    }

    @Override
    public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
        return em.createQuery(SPITTLES_FROM_SPITTER, Spittle.class)
                .setParameter("spitterId", spitter.getId())
                .getResultList();
    }

    @Override
    public List<Spittle> getRecentSpittle() {
        return em.createQuery(RECENT_SPITTLES, Spittle.class)
                .setParameter("postedTime", LocalDate.now())
                .getResultList();
    }
}
