package work.newproject.asus.as.swadeshiebazaar.network.model_res;

import java.io.Serializable;

public class PojoModel  implements Serializable {
    String title;
    int Images;

    public PojoModel(String title, int images) {
        this.title = title;
        Images = images;
    }



    public Integer getImages() {
        return Images;
    }
    public void setImages(Integer images) {
        Images = images;
    }

    public PojoModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
