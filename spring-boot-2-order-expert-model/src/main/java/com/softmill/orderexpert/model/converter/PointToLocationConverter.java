package com.softmill.orderexpert.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.geo.Point;
import org.springframework.lang.Nullable;

import com.softmill.orderexpert.model.dto.request.LocationDTO;

public class PointToLocationConverter implements Converter<Point, LocationDTO> {

    @Nullable
    @Override
    public LocationDTO convert(Point point) {
        if (point == null) {
            return null;
        }
        return new LocationDTO(point.getY(), point.getX(), null);
    }

}
