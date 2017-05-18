package us.poe2017.controller;

import us.poe2017.dto.Group;
import us.poe2017.dto.Response;
import us.poe2017.service.RsvpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class BasicController {

    @Autowired
    RsvpService rsvpService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showIndex() {
        return "The server is running!";
    }

    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public Group getRsvpInfo(@PathVariable String code) {
        return rsvpService.findGroupByCode(code);
    }

    @RequestMapping(value = "/code/", method = RequestMethod.POST)
    public void saveResponse(@RequestBody Response response) {
        rsvpService.saveResponse(response);
    }


    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String testAuthenticated() {
        return "Authentication successful";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String test() {
        return "this endpoint is for admins only!";
    }
}
