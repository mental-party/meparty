package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mecore.enums.HorizontalAlignment;
import com.teammental.mecore.enums.VerticalAlignment;
import java.awt.Color;

@SuppressWarnings("PMD")
public class ModifyImageProperties {
  private final Color backgroundColor;
  private final byte[] originalImageData;
  private final ImageResolution imageResolution;
  private final HorizontalAlignment horizontalAlignment;
  private final VerticalAlignment verticalAlignment;
  private final FileExtension fileExtension;
  private final boolean scaleDisabled;

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public byte[] getOriginalImageData() {
    return originalImageData;
  }

  public ImageResolution getImageResolution() {
    return imageResolution;
  }

  public HorizontalAlignment getHorizontalAlignment() {
    return horizontalAlignment;
  }

  public VerticalAlignment getVerticalAlignment() {
    return verticalAlignment;
  }

  public FileExtension getFileExtension() {
    return fileExtension;
  }

  public boolean isScaleDisabled() {
    return scaleDisabled;
  }

  private ModifyImageProperties(Color backgroundColor,
                                byte[] originalImageData,
                                ImageResolution imageResolution,
                                HorizontalAlignment horizontalAlignment,
                                VerticalAlignment verticalAlignment,
                                FileExtension fileExtension,
                                boolean scaleDisabled) {
    this.backgroundColor = backgroundColor;
    this.originalImageData = originalImageData;
    this.imageResolution = imageResolution;
    this.horizontalAlignment = horizontalAlignment;
    this.verticalAlignment = verticalAlignment;
    this.fileExtension = fileExtension;
    this.scaleDisabled = scaleDisabled;
  }

  public static Builder getBuilder() {
    return new DefaultBuilderImpl();
  }


  public interface Builder {

    Builder backgroundColor(Color backgroundColor);

    Builder originalImageData(byte[] originalImageData);

    Builder imageResolution(ImageResolution imageResolution);

    Builder horizontalAlignment(HorizontalAlignment horizontalAlignment);

    Builder verticalAlignment(VerticalAlignment verticalAlignment);

    Builder fileExtension(FileExtension fileExtension);

    Builder scaleDisabled(boolean scaleDisabled);

    ModifyImageProperties build();
  }


  public static class DefaultBuilderImpl
      implements Builder {

    private Color backgroundColor;
    private byte[] originalImageData;
    private ImageResolution imageResolution;
    private HorizontalAlignment horizontalAlignment;
    private VerticalAlignment verticalAlignment;
    private FileExtension fileExtension;
    private boolean scaleDisabled;

    @Override
    public Builder backgroundColor(Color backgroundColor) {
      this.backgroundColor = backgroundColor;
      return this;
    }

    @Override
    public Builder originalImageData(byte[] originalImageData) {
      this.originalImageData = originalImageData;
      return this;
    }

    @Override
    public Builder imageResolution(ImageResolution imageResolution) {
      this.imageResolution = imageResolution;
      return this;
    }

    @Override
    public Builder horizontalAlignment(HorizontalAlignment horizontalAlignment) {
      this.horizontalAlignment = horizontalAlignment;
      return this;
    }

    @Override
    public Builder verticalAlignment(VerticalAlignment verticalAlignment) {
      this.verticalAlignment = verticalAlignment;
      return this;
    }

    @Override
    public Builder fileExtension(FileExtension fileExtension) {
      this.fileExtension = fileExtension;
      return this;
    }

    @Override
    public Builder scaleDisabled(boolean scaleDisabled) {
      this.scaleDisabled = scaleDisabled;
      return this;
    }

    @Override
    public ModifyImageProperties build() {

      return new ModifyImageProperties(backgroundColor,
          originalImageData,
          imageResolution,
          horizontalAlignment,
          verticalAlignment,
          fileExtension,
          scaleDisabled);
    }
  }
}
