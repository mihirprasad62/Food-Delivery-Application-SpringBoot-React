package com.kodewala.controller;

import com.kodewala.io.FoodRequest;
import com.kodewala.io.FoodResponse;
import com.kodewala.service.FoodService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.thirdparty.jackson.core.JsonProcessingException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@AllArgsConstructor
@CrossOrigin("*")

public class FoodController {
    @Autowired
    FoodService foodService;

    @PostMapping
    public FoodResponse addFood(@RequestPart("food")String foodString, @RequestPart("file")MultipartFile file){
        ObjectMapper objectMapper=new ObjectMapper();
        FoodRequest request=null;

        try {
          request=  objectMapper.readValue(foodString,FoodRequest.class);
        } catch (Exception ex) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid JSON format");
        }
        FoodResponse response=foodService.addFood(request,file);
        return response;
    }

    @GetMapping
    public List<FoodResponse> readFoods(){
       return foodService.readFoods();
    }

    @GetMapping("/{id}")
    public FoodResponse readFood(@PathVariable String id){
        return foodService.readFood(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFood(@PathVariable String id){
        foodService.deleteFood(id);
    }
}
