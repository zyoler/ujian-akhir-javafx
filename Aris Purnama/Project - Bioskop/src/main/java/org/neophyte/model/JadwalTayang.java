package org.neophyte.model;

import sun.util.calendar.BaseCalendar;

import java.sql.Date;
import java.sql.Time;

public class JadwalTayang {
    private String KodeTayang;
    private String tanggal;
    private String jamTayang;
    private  String judulMovie;
    private String genre;
    private String poster;

    public JadwalTayang(String kodeTayang, String tanggal, String jamTayang, String judulMovie, String genre, String poster) {
        this.KodeTayang = kodeTayang;
        this.tanggal = tanggal;
        this.jamTayang = jamTayang;
        this.judulMovie = judulMovie;
        this.genre = genre;
        this.poster = poster;
    }

    public String getKodeTayang() {
        return KodeTayang;
    }

    public void setKodeTayang(String kodeTayang) {
        KodeTayang = kodeTayang;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJamTayang() {
        return jamTayang;
    }

    public void setJamTayang(String jamTayang) {
        this.jamTayang = jamTayang;
    }

    public String getJudulMovie() {
        return judulMovie;
    }

    public void setJudulMovie(String judulMovie) {
        this.judulMovie = judulMovie;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
