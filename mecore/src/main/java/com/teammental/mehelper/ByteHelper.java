package com.teammental.mehelper;

import java.util.Random;

public class ByteHelper {

  /**
   * Generates a random byte array.
   * @param size array size
   * @return random byte array
   */
  public static byte[] generateRandomByteArray(int size) {

    if (size < 1) {
      size = 1;
    }

    Random random = new Random();
    byte[] bytes = new byte[size];
    random.nextBytes(new byte[size]);
    return bytes;
  }
}
