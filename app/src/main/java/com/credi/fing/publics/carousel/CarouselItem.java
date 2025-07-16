package com.credi.fing.publics.carousel;

import java.io.Serializable;

public class CarouselItem implements Serializable {
    public String title;
    public int imageRes;

    public CarouselItem(String title, int imageRes) {
        this.title = title;
        this.imageRes = imageRes;
    }
}
