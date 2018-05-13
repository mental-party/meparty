package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mehelper.AssertHelper;
import com.teammental.mehelper.ResolutionHelper;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageFactory.class);

  /**
   * Creates an empty image.
   * @param properties Image properties.
   * @return byte array of the created image.
   * @throws IOException exception
   */
  public static byte[] createEmptyImage(EmptyImageProperties properties)
      throws IOException {

    AssertHelper.notNull(properties);

    int bufferedImageType = properties.getFileExtension().equals(FileExtension.JPG)
        ? BufferedImage.TYPE_INT_RGB
        : BufferedImage.TYPE_INT_ARGB;

    String formatType = properties.getFileExtension()
        .getExtensions()[0];

    BufferedImage image = new BufferedImage(properties.getWidth(),
        properties.getHeight(), bufferedImageType);

    Graphics2D graphics2D = image.createGraphics();
    graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    graphics2D.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    graphics2D
        .setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

    graphics2D.setColor(properties.getBackgroundColor());
    graphics2D.fillRect(0, 0, properties.getWidth(), properties.getHeight());
    graphics2D.dispose();

    ByteArrayOutputStream output = new ByteArrayOutputStream();

    for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatType);
        iw.hasNext(); ) {
      ImageWriter writer = iw.next();
      ImageWriteParam writeParam = writer.getDefaultWriteParam();
      ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier
          .createFromBufferedImageType(bufferedImageType);
      IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
      if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
        continue;
      }

      if (FileExtension.JPG.matches(formatType)) {
        setJpgDpi(metadata, properties.getDpi());
      } else {
        setPngDpi(metadata, properties.getDpi());
      }

      try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
        writer.setOutput(stream);
        writer.write(metadata,
            new IIOImage(image, null, metadata), writeParam);

      } catch (Exception ex) {
        LOGGER.error(ex.getLocalizedMessage());
        throw ex;
      }
      break;
    }

    return output.toByteArray();

  }

  private static void setJpgDpi(IIOMetadata metadata, int dpi)
      throws IIOInvalidTreeException {

    String metadataFormat = "javax_imageio_jpeg_image_1.0";
    IIOMetadataNode root = new IIOMetadataNode(metadataFormat);
    IIOMetadataNode jpegVariety = new IIOMetadataNode("JPEGvariety");
    IIOMetadataNode markerSequence = new IIOMetadataNode("markerSequence");

    IIOMetadataNode app0JFIF = new IIOMetadataNode("app0JFIF");
    app0JFIF.setAttribute("majorVersion", "1");
    app0JFIF.setAttribute("minorVersion", "2");
    app0JFIF.setAttribute("thumbWidth", "0");
    app0JFIF.setAttribute("thumbHeight", "0");
    app0JFIF.setAttribute("resUnits", "01");
    app0JFIF.setAttribute("Xdensity", String.valueOf(dpi));
    app0JFIF.setAttribute("Ydensity", String.valueOf(dpi));

    root.appendChild(jpegVariety);
    root.appendChild(markerSequence);
    jpegVariety.appendChild(app0JFIF);

    metadata.mergeTree(metadataFormat, root);
  }


  private static void setPngDpi(IIOMetadata metadata, int dpi)
      throws IIOInvalidTreeException {

    double dotsPerMilli = 1.0 * dpi / 10 / ResolutionHelper.INCH_PER_CM;

    IIOMetadataNode horiz = new IIOMetadataNode("HorizontalPixelSize");
    horiz.setAttribute("value", Double.toString(dotsPerMilli));

    IIOMetadataNode vert = new IIOMetadataNode("VerticalPixelSize");
    vert.setAttribute("value", Double.toString(dotsPerMilli));

    IIOMetadataNode dim = new IIOMetadataNode("Dimension");
    dim.appendChild(horiz);
    dim.appendChild(vert);

    IIOMetadataNode root = new IIOMetadataNode("javax_imageio_1.0");
    root.appendChild(dim);

    metadata.mergeTree("javax_imageio_1.0", root);
  }
}
