package com.fh.service;

import com.fh.entity.Movie;
import com.fh.entity.MovieParam;
import com.fh.entity.Projection;

import java.util.List;

public interface MovieService {
    List<Projection> findProjectionRedis();




    void updateOneNum(Integer mid);

    void addMovie(Movie movie);

    MovieParam findMovie(MovieParam movieParam);
}
