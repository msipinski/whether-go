package pl.zzpj.djsr.whethergo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class LocationDTO {
    @Nullable
    Long id;
    String name;
    @Nullable
    boolean importing;
}
