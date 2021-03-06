package us.poe2017.controller;

import us.poe2017.dto.*;
import us.poe2017.service.RsvpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
public class BasicController {

    @Autowired
    RsvpService rsvpService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void heartbeat(HttpServletRequest request) {

    }

    @RequestMapping(value = "/code/{code}", method = RequestMethod.GET)
    public Group getRsvpInfo(@PathVariable String code) {
        return rsvpService.findGroupByCode(code);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/responses", method = RequestMethod.GET)
    public List<CompletedResponse> getResponses(){
        return rsvpService.findResponses();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "admin/groups", method = RequestMethod.GET)
    public List<GroupAdmin> findGroupsForAdmin(){
        return rsvpService.findGroupsForAdmin();
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
