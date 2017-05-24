package unimas.fcsit.foodieroute;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by Elliot on 19-Aug-16.
 */
class AdapterFoodMenu extends BaseAdapter {

    private Context context;
    private ArrayList<FoodListingObject> data;

    private static final String url_read_image = ResFR.URL_read_small_image + "?image_name=" ;

    static double[] myLocation = new double[]{ResFR.DEFAULT_EMPTY_LOCATION , ResFR.DEFAULT_EMPTY_LOCATION};

    public AdapterFoodMenu(Context context, ArrayList<FoodListingObject> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("FoodListView", "position = "+position+" is viewed");

        ItemHolder holder;
        View view = convertView;

        if (view == null) {
            holder = new ItemHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_view_single_item_layout, null/* parent */);

            holder.textDateTime = (TextView) view.findViewById(R.id.textview_adpt_date_time);
            holder.textFoodName = (TextView) view.findViewById(R.id.textview_adpt_food_name);
            holder.textUsername = (TextView) view.findViewById(R.id.textview_adpt_food_username);
            holder.textFoodPrice = (TextView) view.findViewById(R.id.textview_adpt_food_price);
            holder.textSellerName = (TextView) view.findViewById(R.id.textview_adpt_seller_name);
            holder.textFoodComment = (TextView) view.findViewById(R.id.textview_adpt_food_comment);
            holder.textDistance = (TextView) view.findViewById(R.id.textview_adpt_distance);
            holder.imageView = (ImageView) view.findViewById(R.id.imageview_adapter_list_view);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.imageView.getLayoutParams();
            Log.d("AdapterImageView", "LinearLayout.LayoutParams params.width = "+params.width);
            int screenWidth = Screen.getWidth(context);
            int imageViewNewWidth = screenWidth / 5 * 2;
            int imageViewNewHeight = screenWidth / 5 * 2 / 4 * 3;

            Log.d("AdapterImageView", "imageViewNewWidth = "+imageViewNewWidth);
            Log.d("AdapterImageView", "imageViewNewHeight = "+imageViewNewHeight);
            params.width = imageViewNewWidth;
            params.height = imageViewNewHeight;
            holder.imageView.setLayoutParams(params);

            view.setTag(holder);
        } else {
            holder = (ItemHolder) view.getTag();
        }



        holder.textDateTime.setText(data.get(position).date_time);
        holder.textFoodName.setText(data.get(position).food_name);


            /* Shop name / Seller name */
//        String shopname = ResFR.string(context, R.string.s_listview_shopname);
//        shopname += " " + data.get(position).seller_name;
        holder.textSellerName.setText(data.get(position).seller_name);


        /* Price of food. */
        String price = ResFR.string(context, R.string.s_listview_price);
        double priceD = ResFR.doubleOf(data.get(position).food_price);
        String priceDS = String.format("%.2f",priceD);
        price = price.replace("$money$", priceDS);
        holder.textFoodPrice.setText(price);

        if(data.get(position).is_seller.equals("0")) {
            /* username who posted/shared this food */
            String poster = ResFR.string(context, R.string.s_listview_username);
            poster = poster.replace("$user$", data.get(position).username);
            holder.textUsername.setText(poster);

            /* Comment: */
            String comment = ResFR.string(context, R.string.s_listview_comment);
            comment = comment + " " + data.get(position).food_comment;
            holder.textFoodComment.setText(comment);
        }

        String locationunknown = ResFR.string(context, R.string.s_label_locationunknown);
        holder.textDistance.setText(data.get(position).distanceString);

        // load image from web.
        String imgURL = url_read_image + data.get(position).image_file_name;

        Ion.with(context)
                .load(imgURL)
                .withBitmap()
                .placeholder(R.mipmap.ic_loading)
                .intoImageView(holder.imageView);

        return view;
    }

    private class ItemHolder {
        TextView textDateTime;
        TextView textUsername;
        TextView textFoodName;
        TextView textFoodPrice;
        TextView textSellerName;
        TextView textFoodComment;
        TextView textDistance;
        ImageView imageView;
    }

    void setTextDistance(int position){

    }




    String stringOf(double value){
        return String.format("%.0f", value);
    }
}