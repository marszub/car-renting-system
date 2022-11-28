package pl.edu.agh.carManager.carManager.service;

public record Position(
        Double latitude,
        Double longitude
) {
    public Position(String latitude, String longitude) {
        this(Double.valueOf(latitude), Double.valueOf(latitude));
    }
}
