package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mehelper.AssertHelper;
import com.teammental.mehelper.ResolutionHelper;
import com.teammental.mehelper.StringHelper;
import com.teammental.mehelper.image.exception.TextToImageException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
   *
   * @param properties Image properties.
   * @return byte array of the created image.
   * @throws IOException exception
   */
  public static byte[] createEmptyImage(EmptyImageProperties properties)
      throws IOException {

    AssertHelper.notNull(properties);

    int bufferedImageType = getBufferedImageType(properties.getFileExtension());

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

    String formatType = properties.getFileExtension()
        .getExtensions()[0];

    return setDpi(formatType, bufferedImageType,
        properties.getDpi(),
        image);

  }

  /**
   * Creates image from text. Text is splitted to words, and each word is a line in the image.
   *
   * @param properties properties
   * @return byte array of the image
   * @throws TextToImageException exception
   * @throws IOException          exception
   */
  public static byte[] createImageFromText(TextToImageProperties properties)
      throws TextToImageException, IOException {

    AssertHelper.notNull(properties);
    int bufferedImageType = getBufferedImageType(properties.getFileExtension());

    /*Because font metrics is based on a graphics context, we need to create
           a small, temporary image so we can ascertain the width and height
           of the final image.
         */
    BufferedImage img = new BufferedImage(1, 1, bufferedImageType);
    Graphics2D g2d = img.createGraphics();

    int fontSize = properties.getMaxFontSize();
    String text = properties.getText();
    List<String> lines = Arrays.asList(text.split(" "));

    int height = 0;
    int width = 0;

    Font font;
    while (true) {

      boolean valid = true;

      font = new Font(properties.getFontType().toString(), Font.PLAIN, fontSize);
      g2d.setFont(font);
      FontMetrics fm = g2d.getFontMetrics();

      for (String line :
          lines) {
        width = fm.stringWidth(line);
        if (width >= properties.getMaxWidth()) {
          valid = false;
          break;
        }
      }

      if (valid) {
        height = (fm.getAscent() * lines.size()) + ((fm.getAscent() / 2) * (lines.size() + 1));

        if (height >= properties.getMaxHeight()) {
          valid = false;
        }
      }

      if (valid) {
        break;

      } else {
        fontSize = fontSize - 1;
        if (fontSize == 0) {
          throw new TextToImageException("Can not create image with given properties."
              + " Please increase width or height.");
        }
      }


    }

    if (height == 0 || width == 0) {
      throw new TextToImageException("Can not create image with given properties."
          + " Please increase width or height.");
    }

    g2d.dispose();

    img = new BufferedImage(width, height, bufferedImageType);

    g2d = img.createGraphics();
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
    g2d.setFont(font);

    FontMetrics fm = g2d.getFontMetrics();

    g2d.setColor(properties.getBackgroundColor());
    g2d.fillRect(0, 0, properties.getMaxWidth(),
        properties.getMaxHeight());

    g2d.setColor(properties.getFontColor());

    int lineIndex = 1;
    for (String line :
        lines) {

      int lineWidth = fm.stringWidth(line);
      int lineX = (properties.getMaxWidth() - lineWidth) / 2;

      int lineY = ((fm.getAscent() / 2) * lineIndex) + (fm.getAscent() * lineIndex);
      g2d.drawString(line, lineX, lineY);

      lineIndex++;
    }
    g2d.dispose();

    String formatType = properties.getFileExtension()
        .getExtensions()[0];

    return setDpi(formatType, bufferedImageType,
        properties.getDpi(), img);
  }


  private static byte[] setDpi(String formatType,
                               int bufferedImageType,
                               int dpi,
                               BufferedImage image)
      throws IOException {

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
        setJpgDpi(metadata, dpi);
      } else {
        setPngDpi(metadata, dpi);
      }

      try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
        writer.setOutput(stream);
        writer.write(metadata,
            new IIOImage(image, null, metadata), writeParam);

        break;

      } catch (Exception ex) {
        LOGGER.error(ex.getLocalizedMessage());
        throw ex;
      }
    }

    return output.toByteArray();
  }

  private static int getBufferedImageType(FileExtension fileExtension) {

    int bufferedImageType = fileExtension.equals(FileExtension.JPG)
        ? BufferedImage.TYPE_INT_RGB
        : BufferedImage.TYPE_INT_ARGB;

    return bufferedImageType;
  }

  private static void setJpgDpi(IIOMetadata metadata, int dpi)
      throws IIOInvalidTreeException {

    String metadataFormat = "javax_imageio_jpeg_image_1.0";

    IIOMetadataNode app0Jfif = new IIOMetadataNode("app0JFIF");
    app0Jfif.setAttribute("majorVersion", "1");
    app0Jfif.setAttribute("minorVersion", "2");
    app0Jfif.setAttribute("thumbWidth", "0");
    app0Jfif.setAttribute("thumbHeight", "0");
    app0Jfif.setAttribute("resUnits", "01");
    app0Jfif.setAttribute("Xdensity", String.valueOf(dpi));
    app0Jfif.setAttribute("Ydensity", String.valueOf(dpi));

    IIOMetadataNode root = new IIOMetadataNode(metadataFormat);

    IIOMetadataNode jpegVariety = new IIOMetadataNode("JPEGvariety");
    IIOMetadataNode markerSequence = new IIOMetadataNode("markerSequence");

    root.appendChild(jpegVariety);
    root.appendChild(markerSequence);
    jpegVariety.appendChild(app0Jfif);

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
