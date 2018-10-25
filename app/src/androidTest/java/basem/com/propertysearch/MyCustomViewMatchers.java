package basem.com.propertysearch;


import android.view.View;

import org.hamcrest.Matcher;


public final class MyCustomViewMatchers {


    public static Matcher<View> withDrawable(final int resourceId) {
        return new DrawableMatcher(resourceId);
    }

}
