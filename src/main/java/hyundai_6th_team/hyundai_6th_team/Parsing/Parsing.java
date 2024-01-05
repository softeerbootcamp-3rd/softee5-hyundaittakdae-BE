package hyundai_6th_team.hyundai_6th_team.Parsing;
//import ch.qos.logback.core.joran.sanity.Pair;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hyundai_6th_team.hyundai_6th_team.Repository.RoadRepository;
import hyundai_6th_team.hyundai_6th_team.entity.RestArea;
import hyundai_6th_team.hyundai_6th_team.Repository.RestAreaRepository;
import hyundai_6th_team.hyundai_6th_team.entity.Road;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class Parsing {

    @Autowired
    private final RestAreaRepository restAreaRepository;
    private final RoadRepository roadRepository;

    public Parsing(RestAreaRepository restAreaRepository, RoadRepository roadRepository){
        this.restAreaRepository = restAreaRepository;
        this.roadRepository = roadRepository;
        parseJsonFile();
    }


    private void parseJsonFile(){

        restAreaRepository.deleteAll();
        try{


            File jsonFile = new File("./src/main/resources/static/data.json");


            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonFile);

            // Access and process JSON data as needed
            //System.out.println(jsonNode.get("records").get(0).toString());

            Set<String> set = new HashSet<>();



            int size = jsonNode.get("records").size();
            for(int i=0;i<size;i++){
                JsonNode latitudeNode = jsonNode.get("records").get(i).get("경도");
                JsonNode longitudeNode = jsonNode.get("records").get(i).get("위도");
                JsonNode telNode = jsonNode.get("records").get(i).get("휴게소전화번호");
                String roadName = jsonNode.get("records").get(i).get("도로노선명").asText();

                Road road = null;
                if(!set.contains(roadName)){
                    set.add(roadName);
                    road = Road.builder()
                            .name(roadName)
                            .build();
                    roadRepository.save(road);
                }else{
                    road = roadRepository.findByName(roadName);
                }


                if (latitudeNode != null && !latitudeNode.asText().isEmpty() &&
                        longitudeNode != null && !longitudeNode.asText().isEmpty()
                && telNode != null && !telNode.asText().isEmpty()) {
                    RestArea restArea = RestArea.builder()
                            .name(jsonNode.get("records").get(i).get("휴게소명").asText())
                            .xpos(jsonNode.get("records").get(i).get("경도").asDouble())
                            .ypos(jsonNode.get("records").get(i).get("위도").asDouble())
                            .tel(jsonNode.get("records").get(i).get("휴게소전화번호").asText())
                            .road(road)
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
