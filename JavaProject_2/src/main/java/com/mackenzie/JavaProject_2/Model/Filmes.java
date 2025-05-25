package com.mackenzie.JavaProject_2.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Filmes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("Title")
    private String title;
    @JsonProperty("Plot")
    private String plot;
    @JsonProperty("Year")
    @Column(name = "ano")
    private int year;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Poster")
    private String poster;
    @JsonProperty("imdbRating")
    private String imdbRating;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }
    public void setPlot(String plot) {
        this.plot = plot;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }
    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getImdbRating()
    { return imdbRating; }
    public void setImdbRating(String imdbRating)
    { this.imdbRating = imdbRating; }

    public Double getImdbRatingAsDouble() {
        try {
            return imdbRating != null && !imdbRating.equals("N/A") ? Double.parseDouble(imdbRating) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

}
