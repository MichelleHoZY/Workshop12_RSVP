package vttp2022.paf.Workshop12.repositories;

public class Queries {

    public static final String SQL_GET_ALL_RSVP = 
        "select * from RSVP_table";

    public static final String SQL_SEARCH_RSVP_NAME =
        "select * from RSVP_table where Name like concat('%', ?, '%')";
    
    public static final String SQL_ADD_NEW_RSVP = 
        "insert into RSVP_table(Name, Email, Phone, Confirmation_date, Comments) values(?, ?, ?, ?, ?)";

    public static final String SQL_DELETE_OLD_RSVP = 
        "delete from RSVP_table where Phone = ?";  

    public static final String SQL_SEARCH_RSVP_PHONE = 
        "select * from RSVP_table where Phone = ?";

    public static final String SQL_COUNT_RSVP = 
        "select count(distinct Phone) as RSVP_count from RSVP_table";
}
