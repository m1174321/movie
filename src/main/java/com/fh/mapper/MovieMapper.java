package com.fh.mapper;

import com.fh.entity.Movie;
import com.fh.entity.MovieParam;
import com.fh.entity.Projection;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieMapper {
    List<Projection> findProjectionRedis();

    void updateOneNum(Integer mid);

    void addMovie(Movie movie);

    long findMovieCount(MovieParam movieParam);

    List<Movie> findMovie(MovieParam movieParam);

}
