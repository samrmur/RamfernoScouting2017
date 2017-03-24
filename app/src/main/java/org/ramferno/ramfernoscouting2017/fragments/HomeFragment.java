package org.ramferno.ramfernoscouting2017.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.ramferno.ramfernoscouting2017.R;

// Start of HomeFragment
public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    } // End of constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Keep instance of fragment
        setRetainInstance(true);

        // Create view
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        image.setImageBitmap(decodeImage(R.mipmap.ic_home_image));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    } // End of method

    /**
     * Decodes an image
     * @param resourceId is the id of the image
     * @return a bitmap of the image
     */
    public Bitmap decodeImage(int resourceId) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), resourceId, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 100; // you are free to modify size as your requirement

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2
                    >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeResource(getResources(), resourceId, o2);
        }
        catch (Throwable e) {
            e.printStackTrace();
        } // End of try statement

        // Return null if nothing ir orginally returned
        return null;
    } // End of method
} // End of class
