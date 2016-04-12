package network.cooking.sonle.grocery;

/**
 * Created by sonle on 4/6/16.
 */
public class Shop {
    public Shop(String name, String address, double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.address = address;
    }

    private double lat;

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    private double lng;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String name;


    private String address;

}
