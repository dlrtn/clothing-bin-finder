package com.dlrtn.clothing_bin_finder.business.clothing_bin.api.request;

public record GetClothingBinRequest(
        Integer distance,
        Double latitude,
        Double longitude
) {
    public GetClothingBinRequest {
        if (distance == null || distance <= 0) {
            throw new IllegalArgumentException("Distance must be a positive integer.");
        }
        if (latitude == null || latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        }
        if (longitude == null || longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180.");
        }
    }
}
