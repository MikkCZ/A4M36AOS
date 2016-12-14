package cz.cvut.fel.aos.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import cz.cvut.fel.aos.dao.GenericEntityDao;
import cz.cvut.fel.aos.entities.DestinationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
public class DestinationService extends GenericService<DestinationEntity> {

    private final GeoApiContext geoApiContext;

    public DestinationService(GenericEntityDao<DestinationEntity> entityDao, GeoApiContext geoApiContext) {
        super(entityDao);
        this.geoApiContext = geoApiContext;
    }

    public int create(DestinationEntity destinationEntity) {
        enhanceByGeoApi(destinationEntity);
        entityDao.create(destinationEntity);
        return destinationEntity.getId();
    }

    public void update(int id, DestinationEntity destinationEntity) {
        destinationEntity.setId(id);
        enhanceByGeoApi(destinationEntity);
        entityDao.update(destinationEntity);
    }

    private void enhanceByGeoApi(DestinationEntity destinationEntity) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, destinationEntity.getName()).await();
            if (results.length > 0) {
                LatLng location = results[0].geometry.location;
                destinationEntity.setLatitude((float)location.lat);
                destinationEntity.setLongitude((float)location.lng);
            }
        } catch (Exception e) {
            log.error("Could nor t retrieve info from Google API.", e);
        }
    }
}
