package me.angelovdejan.contacts.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {

    @GetMapping(path = {"", "/"})
    public Map<String, String> index() {
        Map<String, String> output = new HashMap<>();

        output.put("message", "Hello world!");

        return output;
    }
}
