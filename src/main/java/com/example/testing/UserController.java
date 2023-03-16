package com.example.testing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

@Controller
public class UserController {

    @RequestMapping("/main")
    //@ResponseBody
    private String main() {

        return "main";
    }

    @RequestMapping("/user/{id}")
    private ModelAndView getUser(@PathVariable Integer id, Model model)   {
        String uri = "https://jsonplaceholder.typicode.com/users/" + id;
        RestTemplate restTemplate = new RestTemplate();

        User user = restTemplate.getForObject(uri, User.class);
        Address address = user.getAddress();
        Geo geo = address.getGeo();
        Company company = user.getCompany();

        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("user1", user);
        modelAndView.addObject("address1", address);
        modelAndView.addObject("geo1", geo);
        modelAndView.addObject("company1", company);

        return modelAndView;
    }

    @RequestMapping("/albums/{id}")    //DZIALA MADA FAKA
    private ModelAndView  getAlbums(@PathVariable Integer id, Model model) {
        String uri = "https://jsonplaceholder.typicode.com/users/"+id+"/albums?_start=0&_limit=5";
        RestTemplate restTemplate = new RestTemplate();

        Album[] album= restTemplate.getForObject(uri, Album[].class);
        System.out.println("User: " + album);
        Album al1 = album[0];
        Album al2 = album[1];
        Album al3 = album[2];
        Album al4 = album[3];
        Album al5 = album[4];
        ModelAndView modelAndView=new ModelAndView("album");
        modelAndView.addObject("album1",al1);
        modelAndView.addObject("album2",al2);
        modelAndView.addObject("album3",al3);
        modelAndView.addObject("album4",al4);
        modelAndView.addObject("album5",al5);

        System.out.println(album[0].getUserId());
        System.out.println(al1.getTitle());
        return modelAndView;
    }

/*
    @RequestMapping("/albums/{id}")
    private ModelAndView getAlbum(@PathVariable Integer id)   {
        String uri = "https://jsonplaceholder.typicode.com/albums/"+id ;
        RestTemplate restTemplate = new RestTemplate();

        Album album = restTemplate.getForObject(uri, Album.class);

        ModelAndView modelAndView = new ModelAndView("album");
        modelAndView.addObject("album",album);

        return modelAndView;
    }

    @RequestMapping("user/{id}/albums")
    private ModelAndView getAlbums(@PathVariable Integer id)   {
        // https://jsonplaceholder.typicode.com/users/1/albums?_start=0&_limit=5
        String uri = "https://jsonplaceholder.typicode.com/users/"+id+"/albums?_start=0&_limit=5" ;
        RestTemplate restTemplate = new RestTemplate();

        Albums albums = restTemplate.getForObject(uri, Albums.class);

        ModelAndView modelAndView = new ModelAndView("albums");
        modelAndView.addObject("albums",albums);

        return modelAndView;
    }
*/




    @RequestMapping("/")
    @ResponseBody
    private String test2() {
        String uri = "https://jsonplaceholder.typicode.com/users/1/albums?_start=0&_limit=5";
        RestTemplate restTemplate = new RestTemplate();

        Albums[] albums = restTemplate.getForObject(uri, Albums[].class);
        System.out.println("Albums: " + albums);

        return "Albums2 detail page.";
    }

    @RequestMapping("/hello")
//@ResponseBody
    private String hello() {
        return "main";
    }
    @RequestMapping("/test")    //dziala
    @ResponseBody
    private String test() {
        String uri = "https://jsonplaceholder.typicode.com/users/1/albums?_start=0&_limit=5";
        RestTemplate restTemplate = new RestTemplate();

        Album[] album= restTemplate.getForObject(uri, Album[].class);
        System.out.println("User: " + album);

        System.out.println(album[0].getUserId());

        return "albums detail page.";
    }
}