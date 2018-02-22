package pasu.vadivasal.photographyInsta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 21/11/17.
 */

public class Model {
    public String name;
    public int itemPositon;
    private boolean isSelected;

    public Model() {
    }

    public Model(String name) {
        this.name = name;
    }

    public static List<Model> createMovies(int itemCount) {
        List<Model> movies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Model model = new Model("Item " + (itemCount == 0 ?
                    (itemCount + 1 + i) : (itemCount + i)));
            movies.add(model);
        }
        return movies;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
