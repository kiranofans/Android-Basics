package Utils;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static Utils.ViewTypeConstants.ViewTypes.HORIZONTAL_TYPE;
import static Utils.ViewTypeConstants.ViewTypes.VERTICAL_TYPE;
public class ViewTypeConstants {
    @IntDef ({HORIZONTAL_TYPE, VERTICAL_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewTypes{
        int VERTICAL_TYPE = 200;
        int HORIZONTAL_TYPE =100;
    }
}
