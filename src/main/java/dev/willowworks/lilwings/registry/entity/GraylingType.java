package dev.willowworks.lilwings.registry.entity;

public enum GraylingType {
    NORMAL(""),
    AZALEA("_azalea"),
    SPORE_BLOSSOM("_spore_blossom");

    private final String textureColor;

    GraylingType(String textureColor) {
        this.textureColor = textureColor;
    }

    public String getTextureColor() {
        return textureColor;
    }
}
