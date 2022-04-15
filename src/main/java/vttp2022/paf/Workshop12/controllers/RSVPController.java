package vttp2022.paf.Workshop12.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import vttp2022.paf.Workshop12.models.RSVP;
import vttp2022.paf.Workshop12.services.RSVPService;

import static vttp2022.paf.Workshop12.models.ConversionUtils.*;

@RestController
@RequestMapping(path="/api")
public class RSVPController {

    @Autowired
    private RSVPService rsvpSvc;

//     @GetMapping(path="/rsvps", produces = MediaType.APPLICATION_JSON_VALUE)
//     public ResponseEntity<String> allRSVPS() {

//         List<RSVP> rsvpList = rsvpSvc.getAllRSVPs();

//         JsonObjectBuilder builder = Json.createObjectBuilder();
//         if (rsvpList.isEmpty()) {
//             return ResponseEntity.status(404)
//                 .body(
//                     builder.add("error", "not found: %s".formatted(rsvpList))
//                         .build().toString()
//                 );
//         }

//         JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
//         JsonObjectBuilder individualRSVP = Json.createObjectBuilder();

//         for (int i=0; i < rsvpList.size(); i++) {
//             individualRSVP.add("name", rsvpList.get(i).getName());
//             individualRSVP.add("email", rsvpList.get(i).getEmail());
//             individualRSVP.add("phone", rsvpList.get(i).getPhone());
//             individualRSVP.add("confirmation date", rsvpList.get(i).getDate().toString());
//             individualRSVP.add("comments", rsvpList.get(i).getComments());

//             arrBuilder.add(individualRSVP.build());
//         }
            
//         builder.add("RSVP", arrBuilder.build());

//         return ResponseEntity.ok(builder.build().toString());
//     }

    // HTTP Method:

    @GetMapping("/rsvps")
    public ModelAndView allRSVPs() {

        ModelAndView mvc = new ModelAndView();

        List<RSVP> rsvpList = rsvpSvc.getAllRSVPs();

        mvc.addObject("rsvpList", rsvpList);
        mvc.setViewName("RSVPList");

        return mvc;

    }

    // @GetMapping("rsvp")
    // public ResponseEntity<String> getRSVP(@RequestParam String q) {

    //     List<RSVP> rsvpNameList = rsvpSvc.searchNames(q);

    //     JsonObjectBuilder builder = Json.createObjectBuilder();
    //     if (rsvpNameList.isEmpty()) {
    //         return ResponseEntity.status(404)
    //             .body(
    //                 builder.add("error", "not found: %s".formatted(q))
    //                 .build().toString()
    //             );
    //     }

    //     JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    //     JsonObjectBuilder nameRSVP = Json.createObjectBuilder();

    //     for (int i=0; i < rsvpNameList.size(); i++) {
    //         nameRSVP.add("name", rsvpNameList.get(i).getName());
    //         nameRSVP.add("email", rsvpNameList.get(i).getEmail());
    //         nameRSVP.add("phone", rsvpNameList.get(i).getPhone());
    //         nameRSVP.add("confirmation date", rsvpNameList.get(i).getDate().toString());
    //         nameRSVP.add("comments", rsvpNameList.get(i).getComments());

    //         arrayBuilder.add(nameRSVP.build());
    //     }

    //     builder.add("RSVP", arrayBuilder);

    //     return ResponseEntity.ok(builder.build().toString());

    // }

    // HTTP Method:

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

    // @PostMapping(path="/rsvp", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // public ResponseEntity<String> addRSVP(@RequestBody MultiValueMap<String, String> form) {

    //     Optional<RSVP> optRSVP = convert(form);
    //     JsonObjectBuilder builder = Json.createObjectBuilder();

    //     if (optRSVP.isEmpty()) {
    //         return ResponseEntity.status(404)
    //             .body(
    //                 builder.add("error", "can't be converted: %s".formatted(optRSVP))
    //                     .build().toString()

    //             );
    //     }

    //     RSVP newRSVP = optRSVP.get();

    //     Optional<RSVP> rsvpCreated = rsvpSvc.addRSVP(newRSVP);

    //     if (rsvpCreated.isEmpty()) {
    //         return ResponseEntity.status(404)
    //             .body(
    //                 builder.add("error", "can't be added: %s".formatted(rsvpCreated))
    //                     .build().toString()

    //             );
    //     }

    //     RSVP addedRsvp = rsvpCreated.get();

    //     JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
    //     JsonObjectBuilder addedRSVP = Json.createObjectBuilder();

    //     addedRSVP.add("name", addedRsvp.getName());
    //     addedRSVP.add("email", addedRsvp.getEmail());
    //     addedRSVP.add("phone", addedRsvp.getPhone());
    //     addedRSVP.add("date", addedRsvp.getDate().toString());
    //     addedRSVP.add("comments", addedRsvp.getComments());

    //     arrBuilder.add(addedRSVP);
    //     builder.add("New RSVP", arrBuilder);

    //     return ResponseEntity.status(201)
    //         .body(
    //             builder.build().toString()
    //         );
    // }

    // HTTP Method:

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

    // @GetMapping("/rsvps/count")
    // public ResponseEntity<String> getRSVPCount() {
    //     int count =rsvpSvc.countAllRSVP();

    //     JsonObjectBuilder builder = Json.createObjectBuilder();
    //     builder.add("Total number of RSVPs", count);
    //     return ResponseEntity.status(201)
    //         .body(
    //             builder.build().toString()
    //         );
    // }

    // HTTP Method:

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
