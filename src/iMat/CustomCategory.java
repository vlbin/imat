package iMat;

import se.chalmers.cse.dat216.project.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class CustomCategory {

    private List<ProductCategory> subCategories = new ArrayList<>();
    private ProductCategory[] categories;
    private String name;

    public CustomCategory(String name, String... categories) {
        this.name = name;
        for (String s: categories) {
            s = s.toUpperCase();
            subCategories.add(ProductCategory.valueOf(s));
        }
    }

    public String getName() {
        return name;
    }

    public List<ProductCategory> getSubCategories() {
        return subCategories;
    }
}
