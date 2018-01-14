package com.springinaction.spitter.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "spittle")
public class Spittle implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Spitter spitter;
    private String text;

    @DateTimeFormat(pattern="yyyy/MM/dd")
    private LocalDate when;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "spitter_id")
    public Spitter getSpitter() {
        return spitter;
    }

    public void setSpitter(Spitter spitter) {
        this.spitter = spitter;
    }

    @Column(name = "spittle_text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "posted_time")
    public LocalDate getWhen() {
        return when;
    }

    public void setWhen(LocalDate when) {
        this.when = when;
    }
}
