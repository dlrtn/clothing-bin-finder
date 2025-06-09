package com.dlrtn.clothing_bin_finder.business.clothing_bin.api.dto.request;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.application.model.dto.ClothingBinQuery;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GetClothingBinRequest(
        @NotNull @Min(1) Integer distance,
        @NotNull @DecimalMin("-90.0") @DecimalMax("90.0") Double latitude,
        @NotNull @DecimalMin("-180.0") @DecimalMax("180.0") Double longitude
) {
    public ClothingBinQuery toQuery() {
        return new ClothingBinQuery(latitude, longitude, distance);
    }
}
