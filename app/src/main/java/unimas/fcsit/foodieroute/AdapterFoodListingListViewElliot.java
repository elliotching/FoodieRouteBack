package unimas.fcsit.foodieroute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by Elliot on 19-Aug-16.
 */
class AdapterFoodListingListViewElliot extends BaseAdapter {

    private Context context;
    private ArrayList<FoodListingObject> data;

    private static final String url_read_image = ResFR.URL_read_image + "?image_name=" ;

    static double[] myLocation = null;

    public AdapterFoodListingListViewElliot(Context context, ArrayList<FoodListingObject> data) {
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
            view.setTag(holder);
        } else {
            holder = (ItemHolder) view.getTag();
        }

        double lat = data.get(position).lat;
        double lng = data.get(position).lng;
        double mylat = myLocation[0];
        double mylng = myLocation[1];

        double distance = distanceOf(mylat, mylng, lat, lng, 0.0, 0.0);



        holder.textDateTime.setText(data.get(position).date_time);
        holder.textFoodName.setText(data.get(position).food_name);
        holder.textUsername.setText(data.get(position).username);
        holder.textFoodPrice.setText("RM "+data.get(position).food_price);
        holder.textSellerName.setText(data.get(position).seller_name);
        holder.textFoodComment.setText(data.get(position).food_comment);
        holder.textDistance.setText(((MyCustomActivity)context).stringOf(distance));

        // load image from web.
        String imgURL = url_read_image + data.get(position).image_file_name;

        Ion.with(context)
                .load(imgURL)
                .withBitmap()
                .placeholder(R.mipmap.loading)
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

    private double distanceOf(double lat1, double lon1,
                              double lat2, double lon2,
                              double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}

//    final String date_time;
//    final String username;
//    final String image_file_name;
//    final String food_name;
//    final String food_price;
//    final String seller_location_lat;
//    final String seller_location_lng;
//    final String seller_name;
//    final String is_seller;
//    final String food_comment;
//        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    Context context;
//    ArrayList<FoodListingObject> mDataArrayList;
//
//    public AdapterFoodListingListViewElliot(Context c, ArrayList<FoodListingObject> data) {
//        context = c;
//        mDataArrayList = data;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        final View view = LayoutInflater.from(context).inflate(R.layout.list_view_single_item_layout, parent, false);
//        ElliotHolder h = new ElliotHolder(view);
//        return new ElliotHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ElliotHolder elliotHolder = (ElliotHolder) holder;
//        elliotHolder.thisTextView = (TextView) elliotHolder.thisItemView.findViewById(R.id.m_list_view_item_text_view);
//        elliotHolder.thisI = (ImageView) elliotHolder.thisItemView.findViewById(R.id.image);
//        elliotHolder.thisTextView.setText(mDataArrayList.get(position).text);
////        elliotHolder.thisTextView.setBackgroundResource(mDataArrayList.get(position).colorID);
//        elliotHolder.thisI.setImageResource(mDataArrayList.get(position).photoRes);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDataArrayList.size();
//    }
//
//    private class ElliotHolder extends RecyclerView.ViewHolder {
//        TextView thisTextView;
//        ImageView thisI;
//        View thisItemView;
//
//        public ElliotHolder(View itemView) {
//            super(itemView);
//            thisItemView = itemView;
//        }
//    }
//
//}