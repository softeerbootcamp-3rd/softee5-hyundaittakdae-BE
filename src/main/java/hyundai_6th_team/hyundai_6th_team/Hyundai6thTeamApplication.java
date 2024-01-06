package hyundai_6th_team.hyundai_6th_team;

import hyundai_6th_team.hyundai_6th_team.Parsing.Parsing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hyundai6thTeamApplication {

	@Autowired
	private Parsing parsing;

	public Hyundai6thTeamApplication(Parsing parsing) {
		this.parsing = parsing;
	}
	public static <parsing> void main(String[] args) {


		SpringApplication.run(Hyundai6thTeamApplication.class, args);
	}

}
