package vttp2022.paf.Workshop12.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.paf.Workshop12.models.RSVP;
import vttp2022.paf.Workshop12.services.RSVPService;

import static vttp2022.paf.Workshop12.models.ConversionUtils.*;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RSVPController {

    @Autowired
    private RSVPService rsvpSvc;

    @GetMapping("/rsvps")
    public ModelAndView allRSVPs() {

        ModelAndView mvc = new ModelAndView();

        List<RSVP> rsvpList = rsvpSvc.getAllRSVPs();

        mvc.addObject("rsvpList", rsvpList);
        mvc.setViewName("RSVPList");

        return mvc;

    }

    @GetMapping("/rsvp")
    public ModelAndView getRSVP(@RequestParam String q) {

        ModelAndView mvc = new ModelAndView();

        List<RSVP> rsvpNameList = rsvpSvc.searchNames(q);

        if (rsvpNameList.isEmpty()) {
            mvc.setStatus(HttpStatus.NOT_FOUND);
            mvc.setViewName("error");
        } else {
            mvc.addObject("rsvpNameList", rsvpNameList);
            mvc.setStatus(HttpStatus.OK);
            mvc.setViewName("RSVPSearchName");
        }

        return mvc;
    }

    @PostMapping("/rsvp")
    public ModelAndView addRSVP(@RequestBody MultiValueMap<String, String> form) {

        ModelAndView mvc = new ModelAndView();
        Optional<RSVP> optRSVP = convert(form);

        if (optRSVP.isEmpty()) {
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
        }

        RSVP newRSVP = optRSVP.get();

        Optional<RSVP> rsvpCreated = rsvpSvc.addRSVP(newRSVP);

        if (rsvpCreated.isEmpty()) {
            mvc.setStatus(HttpStatus.BAD_REQUEST);
            mvc.setViewName("error");
        } else {
            mvc.setStatus(HttpStatus.CREATED);
            mvc.setViewName("RSVPNewName");
            mvc.addObject("newRsvp", rsvpCreated.get());
        }

        return mvc;
    }

    @GetMapping("/rsvps/count")
    public ModelAndView getRSVPCount() {
        ModelAndView mvc = new ModelAndView();
        int count = rsvpSvc.countAllRSVP();

        mvc.addObject("rsvpCount", count);
        mvc.setViewName("RSVPCount");
        mvc.setStatus(HttpStatus.CREATED);

        return mvc;
    }
    
}
