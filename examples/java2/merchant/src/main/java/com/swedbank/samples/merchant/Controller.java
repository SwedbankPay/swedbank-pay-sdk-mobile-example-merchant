package com.swedbank.samples.merchant;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class Controller {

    @RequestMapping(path = "/healthcheck", method = RequestMethod.GET)
    public String healthCheck() {
        return "Healthcheck OK\n";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = "application/json")
    public String emptyJson(HttpServletResponse servletResponse) {
        return "{}";
    }

}
