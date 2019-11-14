package animationModule.framePackage;

import lombok.Getter;

@Getter
public class Frame {
    private int frameWidth;
    private int frameHeight;

    public Frame(int frameWidth, int frameHeight) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
    }
}
