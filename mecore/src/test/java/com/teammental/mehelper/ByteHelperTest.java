package com.teammental.mehelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ByteHelperTest {

  public static class GenerateRandom {

    @Test
    public void shouldReturnSizeOne_whenGivenSizeIsNegative() {
      final int size = -5;
      final byte[] bytes = ByteHelper.generateRandomByteArray(size);

      assertEquals(1, bytes.length);
    }

    @Test
    public void shouldNotReturnNull() {
      final int size = 965;
      final byte[] bytes = ByteHelper.generateRandomByteArray(size);

      assertNotNull(bytes);
    }

    @Test
    public void shouldGenerateArrayWithRequestedSize() {
      final int size = 965;
      final byte[] bytes = ByteHelper.generateRandomByteArray(size);

      assertEquals(size, bytes.length);
    }
  }
}