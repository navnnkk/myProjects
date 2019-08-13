package com.naveen;

import java.awt.*;
import java.io.Serializable;

public class FontModel implements Serializable {

    private Font font;
    private int fontIndex;
    private int fontStyleIndex;
    private int sizeIndex;

    private static final long serialVersionUID = 1L;

    public FontModel(Font font, int fontIndex, int fontStyleIndex, int sizeIndex) {
        this.font = font;
        this.fontIndex = fontIndex;
        this.fontStyleIndex = fontStyleIndex;
        this.sizeIndex = sizeIndex;
    }

    public Font getFont() {
        return font;
    }
    int getFontIndex() {
        return fontIndex;
    }
    int getFontStyleIndex() { return fontStyleIndex; }
    int getFontSizeIndex() { return sizeIndex; }
}