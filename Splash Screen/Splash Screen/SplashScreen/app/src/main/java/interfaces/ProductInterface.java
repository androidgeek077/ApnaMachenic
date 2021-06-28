package interfaces;

import android.view.View;

import com.example.splashscreen.ModelProduct;

public class ProductInterface {
    public interface onProductClickListener {
        void OnAddToCartClick(View view, ModelProduct modelProduct, int position);
    }
}
