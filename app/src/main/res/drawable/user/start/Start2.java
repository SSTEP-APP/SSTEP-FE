package drawable.user.start;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sstep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Start2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Start2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.start2_fragment, container, false);

        return v;
    }
}