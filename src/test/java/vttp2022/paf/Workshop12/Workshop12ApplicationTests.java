package vttp2022.paf.Workshop12;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.paf.Workshop12.models.RSVP;
import vttp2022.paf.Workshop12.repositories.RSVPRepository;
import vttp2022.paf.Workshop12.services.RSVPService;

@SpringBootTest
class Workshop12ApplicationTests {

	@Autowired
	private RSVPService rsvpSvc;

	@Autowired
	private RSVPRepository rsvpRepo;

	private RSVP Webster;
	private RSVP Shifty;

	public Workshop12ApplicationTests() {
		Webster = new RSVP();
		Webster.setName("David Webster");
		Webster.setEmail("litmajor@easy.company");
		Webster.setComments("They got me!");
		Webster.setPhone("12346789");

		Calendar calW = Calendar.getInstance();
		calW.set(Calendar.MONTH, Calendar.SEPTEMBER);
		calW.set(Calendar.DATE, 17);
		calW.set(Calendar.YEAR, 1944);
		Webster.setDate(calW.getTime());

		Shifty = new RSVP();
		Shifty.setName("Shifty Powers");
		Shifty.setEmail("DCPowers@easy.company");
		Shifty.setPhone("98764321");
		Shifty.setComments("Time to go home.");

		Calendar calS = Calendar.getInstance();
		calS.set(Calendar.MONTH, Calendar.MAY);
		calS.set(Calendar.DATE, 8);
		calS.set(Calendar.YEAR, 1945);
		Shifty.setDate(calS.getTime());
	}

	@BeforeEach
	public void setup() {
		rsvpRepo.addNewRSVP(Webster);
		rsvpRepo.addNewRSVP(Shifty);
	}

	@AfterEach
	public void tearDown() {
		rsvpRepo.deleteOldRSVP(Webster);
		rsvpRepo.deleteOldRSVP(Shifty);
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void insertWebsterShouldPass() {
		try {
			assertTrue(rsvpSvc.addRSVP(Webster).isPresent());
		} catch (Exception ex) {
			return;
		}

	}

	@Test
	public void insertShiftyShouldPass() {
		try {
			assertTrue(rsvpSvc.addRSVP(Shifty).isPresent());
		} catch (Exception ex) {
			return;
		}

	}

	@Test
	public void shouldReturn9() {
		int count = rsvpSvc.countAllRSVP();
		assertEquals(9, count);
	}

	@Test
	public void shouldReturnNotEmptyList() {
		List<RSVP> testingList = rsvpSvc.getAllRSVPs();
		assertNotNull(testingList);
	}

	@Test
	public void shouldReturnEmptyList() {
		List<RSVP> testingList = new LinkedList<>();
		assertEquals(testingList, rsvpSvc.searchNames("Winters"));
	}
}
