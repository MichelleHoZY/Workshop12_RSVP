package vttp2022.paf.Workshop12.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.Workshop12.models.RSVP;

import static vttp2022.paf.Workshop12.repositories.Queries.*;
import static vttp2022.paf.Workshop12.models.ConversionUtils.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class RSVPRepository {

    @Autowired
    private JdbcTemplate template;

    public List<RSVP> getAllRSVP() {
        List<RSVP> RSVPList = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(
            SQL_GET_ALL_RSVP
        );

        while (rs.next()) {
            RSVP rsvp = convert(rs);
            RSVPList.add(rsvp);
        }

        return RSVPList;        
    }
    
    public List<RSVP> searchRSVPByName(String name) {
        List<RSVP> RSVPnames = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(
            SQL_SEARCH_RSVP_NAME, name
        );

        while (rs.next()) {
            RSVP rsvp = convert(rs);
            RSVPnames.add(rsvp);
        }
        
        return RSVPnames;
    }

    public boolean addNewRSVP(RSVP rsvp) {
        int count = template.update(
            SQL_ADD_NEW_RSVP, rsvp.getName(), rsvp.getEmail(), rsvp.getPhone(), rsvp.getDate(), rsvp.getComments()
        );

        return 1 == count;
    }

    public boolean deleteOldRSVP(RSVP rsvp) {
        int count = template.update(
            SQL_DELETE_OLD_RSVP, rsvp.getPhone()
        );

        return 1 == count;
    }

    public boolean searchRSVPByPhone(String phone) {
        SqlRowSet rs = template.queryForRowSet(
            SQL_SEARCH_RSVP_PHONE, phone
        );

        if (!rs.next())
            return false;
        
        return true;
    }

    public int countAllRSVP() {
        SqlRowSet rs = template.queryForRowSet(
            SQL_COUNT_RSVP
        );

        if(!rs.next())
            return 0;

        return rs.getInt("RSVP_count");
    }

}
