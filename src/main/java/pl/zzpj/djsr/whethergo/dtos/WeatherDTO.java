package pl.zzpj.djsr.whethergo.dtos;

import lombok.Data;

@Data
public class WeatherDTO {
    Main main;

    @Data
    public static class Main {
        double temp;
        double feelsLike;
        double tempMin;
        double tempMax;
        double pressure;
        double humidity;
    }
}
