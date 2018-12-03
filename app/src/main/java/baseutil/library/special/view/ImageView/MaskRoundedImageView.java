package baseutil.library.special.view.ImageView;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.AttributeSet;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * 给图片加滤镜
 * 
 * @author Administrator
 * 
 */
public class MaskRoundedImageView extends RoundedImageView {

	public MaskRoundedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);

		setColorFilter(new PorterDuffColorFilter(0x33000000,
				PorterDuff.Mode.SRC_ATOP));
	}

}
