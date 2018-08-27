package com.softmill.orderexpert.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.geo.Point;
import org.springframework.lang.Nullable;

import com.softmill.orderexpert.model.dto.request.LocationDTO;

public class LocationToPointConverter implements Converter<LocationDTO, Point> {

    @Nullable
    @Override
    public Point convert(LocationDTO locationDTO) {
        if (locationDTO == null) {
            return null;
        }
        return new Point(locationDTO.getLongitude(), locationDTO.getLatitude());
    }

}
