package hyundai_6th_team.hyundai_6th_team;

import hyundai_6th_team.hyundai_6th_team.Parsing.Parsing;
import hyundai_6th_team.hyundai_6th_team.Repository.RestAreaRepository;
import hyundai_6th_team.hyundai_6th_team.Repository.RoadRepository;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class Hyundai6thTeamApplicationTests {



	private final Parsing parsing;
	private final RestAreaRepository restAreaRepository;
	private final RoadRepository roadRepository;


	@Autowired
	Hyundai6thTeamApplicationTests(Parsing parsing, RestAreaRepository restAreaRepository, RoadRepository roadRepository) {
		this.parsing = parsing;
		this.restAreaRepository = restAreaRepository;
		this.roadRepository = roadRepository;
	}

	@BeforeEach
	public void mockMvcSetup(){

		restAreaRepository.deleteAll();
		roadRepository.deleteAll();

	}
	@DisplayName("parsing from json")
	@Test
	void contextLoads() throws Exception{
		//given


		//when



		//then
		String name = "대천(목포)";
		RestArea restArea = restAreaRepository.findByName(name);
		int num = restAreaRepository.findAll().size();
		assertThat(num).isEqualTo(240);
		assertThat(restArea.getTel()).isEqualTo("041-931-6901");

	}

}
