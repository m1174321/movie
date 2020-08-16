package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Movie;
import com.fh.entity.MovieParam;
import com.fh.entity.Projection;
import com.fh.mapper.MovieMapper;
import com.fh.service.MovieService;
import com.fh.utils.RedisPool;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;


    @Override
    public List<Projection> findProjectionRedis() {
        List<String> movie_wxwu = RedisPool.hvals("movie_wxwu");
        if(movie_wxwu.size() == 0){
            List<Projection> projectionList = movieMapper.findProjectionRedis();
            for (int i = 0; i < projectionList.size(); i++) {
                Projection projection = projectionList.get(i);
                RedisPool.hsetRedis("movie_wxwu",projection.getHid()+"",projection);
            }
        return projectionList;
        }
        List<Projection> projectionList = new ArrayList<>();
        for (int i = 0; i < movie_wxwu.size(); i++) {
            String s = movie_wxwu.get(i);
            Projection projection = JSONObject.parseObject(s, Projection.class);
            projectionList.add(projection);
        }
        return projectionList;
    }

    @Override
    public MovieParam findMovie(MovieParam movieParam) {
        long count =  movieMapper.findMovieCount(movieParam);
        movieParam.setRecordsFiltered(count);
        movieParam.setRecordsTotal(count);
        List<Movie> movieList = movieMapper.findMovie(movieParam);
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieList.size(); i++) {
            Movie movie1 = movieList.get(i);
            String hgetRedis = RedisPool.hgetRedis("movie_wxwu", movie1.getShowHome() + "");
            Projection projection = JSONObject.parseObject(hgetRedis, Projection.class);
            if(projection == null){
                movie1.setHname("放映室异常");
                movies.add(movie1);
            }else{
                movie1.setHname(projection.getHname());
                movies.add(movie1);
            }
        }
        movieParam.setData(movies);
        return movieParam;
    }


    @Override
    public void updateOneNum(Integer mid) {
        movieMapper.updateOneNum(mid);
    }

    @Override
    public void addMovie(Movie movie) {
        movie.setShowTime(new Date());
        movieMapper.addMovie(movie);
    }


}
