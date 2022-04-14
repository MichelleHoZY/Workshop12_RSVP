package vttp2022.paf.Workshop12.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.MultiValueMap;

public class ConversionUtils {

    public static RSVP convert(SqlRowSet rs) {
        RSVP rsvp = new RSVP();

        rsvp.setName(rs.getString("Name"));
        rsvp.setPhone(rs.getString("Phone"));
        rsvp.setEmail(rs.getString("Email"));
        rsvp.setComments(rs.getString("Comments"));
        rsvp.setDate(rs.getDate("Confirmation_date"));

        return rsvp;
    }

    public static Optional<RSVP> convert(MultiValueMap<String, String> form) {
        RSVP rsvp = new RSVP();

        rsvp.setName(form.getFirst("name"));
        rsvp.setEmail(form.getFirst("email"));
        rsvp.setPhone(form.getFirst("phone"));
        rsvp.setComments(form.getFirst("comments"));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = sdf.parse(form.getFirst("date"));
            rsvp.setDate(newDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Optional.of(rsvp);
    }
    
}
