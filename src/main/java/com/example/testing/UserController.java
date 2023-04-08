package com.example.testing;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import java.util.Collections;

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

    @PostMapping("/addUser") //dodajmy urzytkownika
    public String addUser(@ModelAttribute("user") User user) {
        RestTemplate restTemplate = new RestTemplate();

        /*
        User myuser = new User();
        myuser=user;
*/

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> request = new HttpEntity<>(user, headers);

        String uri = "https://jsonplaceholder.typicode.com/users";
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        //restTemplate.postForEntity(uri, request, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Request Successful");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
        return "redirect:/users";
    }
/*
    @PostMapping("/users/{id}")
    public String editUser(@PathVariable Integer id, User user) {
        String uri = "https://jsonplaceholder.typicode.com/users/" + id;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, user);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<User> request = new HttpEntity<>(user, headers);
        if (request. HttpStatus.OK) {
            System.out.println("Request Successful");
            System.out.println(request.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(restTemplate.getClass());
        }
        return "redirect:/users";
    }
    */
/**/
    @PostMapping("/users/{id}")
    public String editUser(@PathVariable("id") int id, @ModelAttribute User user) {
        System.out.println("zmiana dotyczy urzytkownika o id: "+id);
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://jsonplaceholder.typicode.com/users/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<User> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, User.class);
        System.out.println("Status code: "+responseEntity.getStatusCode());
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Request Successful");
            //System.out.println(requestEntity.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(responseEntity.getStatusCode());
        }
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUsersList(Model model) {
        String uri = "https://jsonplaceholder.typicode.com/users";
        RestTemplate restTemplate = new RestTemplate();
        User[] users = restTemplate.getForObject(uri, User[].class);
        model.addAttribute("users", users);
        return "users";
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