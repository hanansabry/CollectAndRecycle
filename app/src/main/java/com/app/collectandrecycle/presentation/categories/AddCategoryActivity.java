package com.app.collectandrecycle.presentation.categories;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.app.collectandrecycle.data.Category;
import com.app.collectandrecycle.databinding.ActivityAddCategoryBinding;
import com.app.collectandrecycle.di.ViewModelProviderFactory;
import com.app.collectandrecycle.presentation.BaseActivity;
import com.app.collectandrecycle.utils.Constants;

import java.io.InputStream;

import javax.inject.Inject;

public class AddCategoryActivity extends BaseActivity {

    @Inject
    ViewModelProviderFactory providerFactory;
    private ActivityAddCategoryBinding binding;
    private CategoriesViewModel categoriesViewModel;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri selectedImageUri;

    @Override
    public View getDataBindingView() {
        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPickImageResultLauncher();
        categoriesViewModel = new ViewModelProvider(getViewModelStore(), providerFactory).get(CategoriesViewModel.class);
        categoriesViewModel.getAddCategoryStateLiveData().observe(this, category -> {
            if (category != null) {
                Intent intent = new Intent();
                intent.putExtra(Constants.CATEGORY, category);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Can't add category, please try again", Toast.LENGTH_SHORT).show();
            }
        });

        categoriesViewModel.getErrorState().observe(this, error -> {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        });
    }

    private void registerPickImageResultLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if (data != null){
                            selectedImageUri = data.getData();
                            if (selectedImageUri != null){
                                try {
                                    binding.categoryImage.setImageURI(selectedImageUri);
                                }catch (Exception e){
                                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
    }

    public void onSelectImageClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncher.launch(intent);
    }

    public void onSaveClicked(View view) {
        String categoryName = binding.categoryEditText.getText().toString();
        if (categoryName.isEmpty()) {
            Toast.makeText(this, "You must add category name", Toast.LENGTH_SHORT).show();
        } else {
            Category category = new Category();
            category.setName(categoryName);
            if (selectedImageUri != null) {
                category.setImage(selectedImageUri.getPath());
            }
            categoriesViewModel.addCategory(category);
        }
    }

}