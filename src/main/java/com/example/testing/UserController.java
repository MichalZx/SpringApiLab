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

    @RequestMapping("/albums/{id}")
    private ModelAndView  getAlbums(@PathVariable Integer id, Model model) {
        String uri = "https://jsonplaceholder.typicode.com/users/"+id+"/albums?_start=0&_limit=5";
        RestTemplate restTemplate = new RestTemplate();
        Album[] album= restTemplate.getForObject(uri, Album[].class);
        System.out.println("Album: " + album);
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

    @PostMapping("/addUser") //adding user
    public String addUser(@ModelAttribute("user") User user) {
        RestTemplate restTemplate = new RestTemplate();
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

    @GetMapping("/users/{id}/posts")
    private ModelAndView  getPost(@PathVariable Integer id, Model model) {
        String uri = "https://jsonplaceholder.typicode.com/users/"+id+"/posts?_start=0&_limit=5";
        RestTemplate restTemplate = new RestTemplate();
        Post[] post= restTemplate.getForObject(uri, Post[].class);
        System.out.println("Post: " + post);
        Post po1 = post[0];
        Post po2 = post[1];
        Post po3 = post[2];
        Post po4 = post[3];
        Post po5 = post[4];
        ModelAndView modelAndView=new ModelAndView("post");
        modelAndView.addObject("post1",po1);
        modelAndView.addObject("post2",po2);
        modelAndView.addObject("post3",po3);
        modelAndView.addObject("post4",po4);
        modelAndView.addObject("post5",po5);
        System.out.println(post[0].getUserId());
        return modelAndView;
    }

    @GetMapping("/users/{id}/todos")
    private ModelAndView  getTodos(@PathVariable Integer id, Model model) {
        String uri = "https://jsonplaceholder.typicode.com/users/"+id+"/todos?_start=0&_limit=5";
        RestTemplate restTemplate = new RestTemplate();
        Todos[] todos= restTemplate.getForObject(uri, Todos[].class);
        System.out.println("Todos: " + todos);
        Todos td1 = todos[0];
        Todos td2 = todos[1];
        Todos td3 = todos[2];
        Todos td4 = todos[3];
        Todos td5 = todos[4];
        ModelAndView modelAndView=new ModelAndView("todos");
        modelAndView.addObject("todos1",td1);
        modelAndView.addObject("todos2",td2);
        modelAndView.addObject("todos3",td3);
        modelAndView.addObject("todos4",td4);
        modelAndView.addObject("todos5",td5);
        System.out.println(todos[0].getUserId());
        return modelAndView;
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
        System.out.println("editing user with id: "+id);
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

    @GetMapping("/users/del/{id}") // deleting
    public String deleteUser(@PathVariable Integer id) {
        String uri = "https://jsonplaceholder.typicode.com/users/" + id;
        RestTemplate restTemplate = new RestTemplate();
        //User user = restTemplate.delete(uri);

        try {
            restTemplate.delete(uri);
            System.out.println("Request Successful");
        } catch (Exception e) {
            System.out.println("Request Failed");
            //System.out.println(responseEntity.getStatusCode());
        }
        return "redirect:/users";
    }

    @RequestMapping("/")
    @ResponseBody
    private String test2() {
        main();
        return "Hello world! but go to /main :)";
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