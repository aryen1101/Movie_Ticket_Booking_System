package models;

public class City {

    private final String cityId;
    private final String name;

    public City(String cityId, String name) {
        this.cityId = cityId;
        this.name = name;
    }

    public String getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

}
