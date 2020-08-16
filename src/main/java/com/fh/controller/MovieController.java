package com.fh.controller;

import com.fh.entity.Movie;
import com.fh.entity.MovieParam;
import com.fh.entity.Projection;
import com.fh.service.MovieService;
import com.fh.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("movie")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @RequestMapping("findProjectionRedis")
    public Map findProjectionRedis(){
        Map map =new HashMap();
        List<Projection> projectionList =movieService.findProjectionRedis();
        map.put("data",projectionList);
        return map;
    }

    @RequestMapping("findMovie")
    public MovieParam finMovie(MovieParam movieParam){

        return movieService.findMovie(movieParam);
    }

    @RequestMapping("updateOneNum")
    public Map updateOneNum(Integer mid){
        movieService.updateOneNum(mid);
        Map map = new HashMap();
        map.put("code",200);
        return map;
    }

    //文件上传
    @RequestMapping("OSSFileUpload")
    public Map OSSFileUpload( @RequestParam("img") MultipartFile img) {
        Map map = new HashMap();
        try {
            File file = OSSUtil.readFiles(img);
            String filePath = OSSUtil.uploadFile(file);
            map.put("filePath",filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("addMovie")
    public Map addMovie(Movie movie){
        movieService.addMovie(movie);
        Map map = new HashMap();
        map.put("code",200);
        return map;
    }

}
