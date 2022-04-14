package vttp2022.paf.Workshop12.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.paf.Workshop12.models.RSVP;
import vttp2022.paf.Workshop12.repositories.RSVPRepository;

@Service
public class RSVPService {

    @Autowired
    private RSVPRepository rsvpRepo;

    public List<RSVP> getAllRSVPs() {
        return rsvpRepo.getAllRSVP();
    }

    public List<RSVP> searchNames(String name) {
        return rsvpRepo.searchRSVPByName(name);
    }

    public Optional<RSVP> addRSVP(RSVP rsvp) {

        if(!rsvpRepo.searchRSVPByPhone(rsvp.getPhone())) { // if there is no RSVP corresponding to this phone number
            if(rsvpRepo.addNewRSVP(rsvp)) { // if added new RSVP
                return Optional.of(rsvp); // return RSVP
            } else {
                return Optional.empty(); // return empty
            }
        } else { // if there is a corresponding RSVP
            if(rsvpRepo.deleteOldRSVP(rsvp)) { // if deleted old RSVP
                if(rsvpRepo.addNewRSVP(rsvp)) { // add new RSVP
                    return Optional.of(rsvp); // return RSVP
                } else {
                    return Optional.empty(); // return empty
                }
            } else {
                return Optional.empty();
            }
        }
    }

    public int countAllRSVP () {
        return rsvpRepo.countAllRSVP();
    }
    
}
