package unimas.fcsit.foodieroute;

/**
 * Created by Elliot on 19-Aug-16.
 */
class FoodListingObject {
    final String date_time;
    final String username;
    final String image_file_name;
    final String food_name;
    final String food_price;
    final String seller_location_lat;
    final String seller_location_lng;
    final String seller_name;
    final String is_seller;
    final String food_comment;
    final double lat;
    final double lng;


    FoodListingObject(String date_time, String username, String image_file_name, String food_name, String food_price, String seller_location_lat, String seller_location_lng, String is_seller, String seller_name, String food_comment) {

        this.date_time = date_time;
        this.username = username;
        this.image_file_name = image_file_name;
        this.food_name = food_name;
        this.food_price = food_price;
        this.seller_name = seller_name;
        this.is_seller = is_seller;
        this.food_comment = food_comment;

        this.seller_location_lat = seller_location_lat;
        this.seller_location_lng = seller_location_lng;

        lat = doubleOf(seller_location_lat);
        lng = doubleOf(seller_location_lng);
    }

    private double doubleOf(String s){
        return Double.parseDouble(s);
    }
}