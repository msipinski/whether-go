package pl.zzpj.djsr.whethergo.utils.converters;

import pl.zzpj.djsr.whethergo.dtos.LocationDTO;
import pl.zzpj.djsr.whethergo.entities.LocationEntity;

public class LocationConverter {
    public static LocationDTO locationDTOFromEntity(LocationEntity locationEntity){
        return new LocationDTO(locationEntity.getId(), locationEntity.getName(), locationEntity.isImporting());
    }
    public static LocationEntity locationEntityFromDTO(LocationDTO locationDTO){
        return new LocationEntity(locationDTO.getName());
    }
}
