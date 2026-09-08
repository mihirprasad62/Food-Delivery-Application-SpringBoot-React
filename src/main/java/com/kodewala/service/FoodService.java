package com.kodewala.service;

import com.kodewala.io.FoodRequest;
import com.kodewala.io.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {

    String uploadFile(MultipartFile file);

    FoodResponse addFood(FoodRequest request,MultipartFile file);

    List<FoodResponse> readFoods();

    
}
