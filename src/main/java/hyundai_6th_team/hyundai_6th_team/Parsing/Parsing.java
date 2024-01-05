package hyundai_6th_team.hyundai_6th_team.Parsing;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.Repository.RestAreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
public class Parsing {

    @Autowired
    private final RestAreaRepository restAreaRepository;

    public Parsing(RestAreaRepository restAreaRepository){
        this.restAreaRepository = restAreaRepository;
        parseJsonFile();
    }


    private void parseJsonFile(){


        try{


            File jsonFile = new File("./src/main/resources/static/data.json");


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            // Access and process JSON data as needed
            //System.out.println(jsonNode.get("records").get(0).toString());

            int size = jsonNode.get("records").size();
            for(int i=0;i<size;i++){
                JsonNode latitudeNode = jsonNode.get("records").get(i).get("경도");
                JsonNode longitudeNode = jsonNode.get("records").get(i).get("위도");

                if (latitudeNode != null && !latitudeNode.asText().isEmpty() &&
                        longitudeNode != null && !longitudeNode.asText().isEmpty()) {
                    RestArea restArea = RestArea.builder()
                            .name(jsonNode.get("records").get(i).get("휴게소명").asText())
                            .xpos(Double.parseDouble(latitudeNode.asText()))
                            .ypos(Double.parseDouble(longitudeNode.asText()))
                            .build();
                    restAreaRepository.save(restArea);
                } else {
                    // Handle the case where latitude or longitude is missing or empty
                    System.err.println("Latitude or longitude is missing or empty for record " + i);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }




}
