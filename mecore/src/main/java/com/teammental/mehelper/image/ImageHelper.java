package com.teammental.mehelper.image;

import com.teammental.mecore.enums.FileExtension;
import com.teammental.mecore.enums.FileType;
import com.teammental.mehelper.AssertHelper;
import com.teammental.mehelper.ResolutionHelper;
import com.teammental.mehelper.StringHelper;
import com.teammental.mehelper.image.exception.TextToImageException;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
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

public class ImageHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageHelper.class);

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
    setGraphics2dProperties(graphics2D);
    graphics2D.setColor(properties.getBackgroundColor());
    graphics2D.fillRect(0, 0, properties.getWidth(), properties.getHeight());
    graphics2D.dispose();

    return setDpi(properties.getFileExtension(),
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

    String text = properties.getText();
    List<String> lines = Arrays.stream(text.split(" "))
        .filter(s -> !StringHelper.isNullOrEmpty(s))
        .collect(Collectors.toList());

    Font font = getSuitableFont(lines, properties, bufferedImageType);

    BufferedImage img = new BufferedImage(properties.getMaxWidth(),
        properties.getMaxHeight(), bufferedImageType);

    Graphics2D g2d = img.createGraphics();
    setGraphics2dProperties(g2d);
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

      int topMargin = (properties.getMaxHeight()
          - (fm.getAscent() * lines.size() + (fm.getAscent() / 2) * (lines.size() - 1))) / 2;

      int lineY = topMargin + (fm.getAscent() / 2) * (lineIndex - 1) + fm.getAscent() * lineIndex;
      g2d.drawString(line, lineX, lineY);

      lineIndex++;
    }
    g2d.dispose();

    return setDpi(properties.getFileExtension(),
        properties.getDpi(), img);
  }

  /**
   * Changes format of given image data.
   *
   * @param originalImage       original image
   * @param outputFileExtension wanted format
   * @return formatted image data
   * @throws IOException exception
   */
  public static byte[] changeFormat(final byte[] originalImage,
                                    FileExtension outputFileExtension)
      throws IOException {

    AssertHelper.notNull(originalImage, outputFileExtension);

    if (!outputFileExtension.getFileType().equals(FileType.IMAGE)) {
      throw new IllegalArgumentException("outputFileExtension is not an image type");
    }

    BufferedImage bufferedImage = bytesToBufferedImage(originalImage);

    return bufferedImageToBytes(bufferedImage, outputFileExtension);

  }

  /**
   * Resizes given image.
   *
   * @param originalImageData original image.
   * @param imageResolution   desired resoltion
   * @param fileExtension     file extension
   * @return resized image data
   * @throws IOException exception
   */
  public static byte[] resize(final byte[] originalImageData,
                              ImageResolution imageResolution,
                              FileExtension fileExtension)
      throws IOException {

    AssertHelper.notNull(originalImageData, imageResolution);

    BufferedImage originalImage = bytesToBufferedImage(originalImageData);

    BufferedImage resizedImage = new BufferedImage(imageResolution.getWidth(),
        imageResolution.getHeight(), originalImage.getType());

    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(originalImage, 0, 0, imageResolution.getWidth(),
        imageResolution.getHeight(), null);
    g.dispose();

    return bufferedImageToBytes(resizedImage, fileExtension);
  }

  /**
   * Converts byte array of an image to BufferedImage.
   *
   * @param originalImage original data
   * @return buffered image
   * @throws IOException exception
   */
  public static BufferedImage bytesToBufferedImage(final byte[] originalImage)
      throws IOException {

    AssertHelper.notNull(originalImage);

    ByteArrayInputStream byteArrayInputStream
        = new ByteArrayInputStream(originalImage);

    BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

    return bufferedImage;
  }

  /**
   * Converts BufferedImage to byte array.
   *
   * @param bufferedImage original image
   * @param fileExtension file extension
   * @return byte array
   * @throws IOException exception
   */
  public static byte[] bufferedImageToBytes(BufferedImage bufferedImage,
                                            FileExtension fileExtension)
      throws IOException {

    ByteArrayOutputStream byteArrayOutputStream
        = new ByteArrayOutputStream();
    ImageOutputStream imageOutputStream
        = ImageIO.createImageOutputStream(byteArrayOutputStream);

    ImageIO.write(bufferedImage, fileExtension
        .getExtensions()[0], imageOutputStream);

    return byteArrayOutputStream.toByteArray();
  }

  /**
   * Sets DPI value of given image.
   *
   * @param fileExtension file extension
   * @param dpi           dpi value
   * @param image         image
   * @return byte array;
   * @throws IOException exception
   */
  public static byte[] setDpi(FileExtension fileExtension,
                              int dpi,
                              byte[] image)
      throws IOException {

    BufferedImage bufferedImage = bytesToBufferedImage(image);

    return setDpi(fileExtension, dpi, bufferedImage);
  }

  /**
   * Sets DPI value of given image.
   *
   * @param fileExtension file extension
   * @param dpi           dpi value
   * @param image         image
   * @return byte array;
   * @throws IOException exception
   */
  public static byte[] setDpi(FileExtension fileExtension,
                              int dpi,
                              BufferedImage image)
      throws IOException {

    AssertHelper.notNull(fileExtension, image);

    ByteArrayOutputStream output = new ByteArrayOutputStream();

    String formatType = fileExtension.getExtensions()[0];

    for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatType);
        iw.hasNext(); ) {
      ImageWriter writer = iw.next();
      ImageWriteParam writeParam = writer.getDefaultWriteParam();
      ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier
          .createFromBufferedImageType(image.getType());
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

  private static Font getSuitableFont(List<String> lines,
                                      TextToImageProperties properties,
                                      int bufferedImageType) throws TextToImageException {
    /*Because font metrics is based on a graphics context, we need to create
           a small, temporary image so we can ascertain the width and height
           of the final image.
         */
    BufferedImage img = new BufferedImage(1, 1, bufferedImageType);
    Graphics2D g2d = img.createGraphics();

    int fontSize = properties.getMaxFontSize();


    int height = 0;
    int width = 0;

    Font font;
    while (true) {

      if (fontSize < properties.getMinFontSize()) {
        throw new TextToImageException("Can not create image with given properties."
            + " Please increase width or height.");
      }

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

      height = fm.getAscent() * lines.size() + (fm.getAscent() / 2) * (lines.size() + 1);

      if (height >= properties.getMaxHeight()) {
        valid = false;
      }

      if (valid) {
        break;
      }
      fontSize = fontSize - 1;
    }

    g2d.dispose();
    return font;
  }

  private static void setGraphics2dProperties(Graphics2D g2d) {

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
