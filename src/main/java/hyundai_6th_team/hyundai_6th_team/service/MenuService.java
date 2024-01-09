


package hyundai_6th_team.hyundai_6th_team.service;

import hyundai_6th_team.hyundai_6th_team.apiPayload.code.statusEnums.ErrorStatus;
import hyundai_6th_team.hyundai_6th_team.apiPayload.exception.handler.GeneralHandler;
import hyundai_6th_team.hyundai_6th_team.dto.response.RestaurantResponse;
import hyundai_6th_team.hyundai_6th_team.entity.Menu;
import hyundai_6th_team.hyundai_6th_team.entity.Rating;
import hyundai_6th_team.hyundai_6th_team.entity.Restaurant;
import hyundai_6th_team.hyundai_6th_team.repository.MenuRepository;
import hyundai_6th_team.hyundai_6th_team.repository.RestAreaRepository;
import hyundai_6th_team.hyundai_6th_team.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.max;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    //private final AmazonS3Service amazonS3Service;
    private final MenuRepository menuRepository;

    private final RestaurantRepository restaurantRepository;


    public Float getAvg(Menu menu){

        Float sum = 0.0F;

        for(Rating rating : menu.getRatingList()){
            sum += rating.getRating();
        }

        if (!menu.getRatingList().isEmpty()) {
            return sum / menu.getRatingList().size();
        } else {
            return 0.0F; // or handle the case where there are no ratings
        }

    }
    public List<RestaurantResponse.MenuDTO> findTop3MenuByRestArea(Long restAreaId) {
        List<Restaurant> restaurants = restaurantRepository.findRestaurantByIdFromRestArea(restAreaId);
        List<RestaurantResponse.MenuDTO> top3Menus = new ArrayList<>();

        List<Menu> total = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            List<Menu> menusPerHouse = menuRepository.findByRestaurant(restaurant);

            total.addAll(menusPerHouse);
            // Calculate average ratings for each menu and sort them


            // Get the top 3 menus

        }

        List<Menu> sortedMenus = total.stream()
                .sorted(Comparator.comparingDouble(menu -> getAvg(menu)))
                .collect(Collectors.toList());

        int size = sortedMenus.size();
        int endIndex = size >= 3 ? 3 : size;
        List<Menu> top3 = sortedMenus.subList(max(0,sortedMenus.size()-endIndex), sortedMenus.size());

        // Convert to DTO and add to the result list
        List<RestaurantResponse.MenuDTO> top3MenuDTOs = top3.stream()
                .map(menu -> new RestaurantResponse.MenuDTO(menu.getName(), getAvg(menu).toString(), menu.getPrice().toString()))
                .collect(Collectors.toList());

        top3Menus.addAll(top3MenuDTOs);
        //int endIndexFinal = Math.min(top3Menus.size(), 3);


// ...

        return top3Menus;
    }



    @Transactional
    public String uploadImage(MultipartFile image, Long menuId) throws IOException {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() ->  new GeneralHandler(ErrorStatus.MENU_NOT_FOUND));
        String imageUrl = amazonS3Service.upload(image, "menu");
        menu.setImageUrl(imageUrl);
        menuRepository.save(menu);
        return imageUrl;
    }


}


