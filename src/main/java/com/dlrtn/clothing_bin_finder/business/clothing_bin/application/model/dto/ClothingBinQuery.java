package com.dlrtn.clothing_bin_finder.business.clothing_bin.application.model.dto;

public record ClothingBinQuery(
        double latitude,
        double longitude,
        double distance
) {
}