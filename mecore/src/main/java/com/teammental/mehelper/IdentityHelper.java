package com.teammental.mehelper;

/**
 * client-application
 * Created by coskun.deniz on 26.10.2016.
 */
public class IdentityHelper {

  /**
   * checkIdentityNumber.
   *
   * @param identityNumber identity number
   * @return boolean
   */
  public static boolean checkIdentityNumber(String identityNumber) {

    int tekHaneler = 0;
    int ciftHaneler = 0;
    int toplam = 0;

    // 11 haneden küçük olamaz
    if (identityNumber.length() < 11) {
      return false;
    }

    // 0 rakamı ile başlayamaz
    if (identityNumber.startsWith("0")) {
      return false;
    }

    // Char array to int array.
    char[] arrChar = identityNumber.toCharArray();
    int[] arr = new int[arrChar.length];
    for (int i = 0; i < arrChar.length; i++) {
      arr[i] = Integer.parseInt(arrChar[i] + "");
    }

    //Son rakam tek sayı olamaz
    if (arr[10] % 2 != 0) {
      return false;
    }

    //Tek haneliler ile çiftlerin farkının mode 10 u 10. haneyi verir
    for (int i = 0; i < 9; i++) {
      // System.out.println("Data : " + i + " : " + arr[i]);
      toplam += arr[i];
      if (i % 2 == 0) {
        tekHaneler += arr[i];
      } else {
        ciftHaneler += arr[i];
      }
    }

    toplam += arr[9];
    // 1,3,5,7,9 hanelerinin toplamından 7 katından 2,4,6,8 haneleri
    // toplamının çıkarırsak mod 10 a göre 10. rakamı vermesi  gerekir.
    int kural;
    kural = tekHaneler * 7 - ciftHaneler;

    if (kural % 10 != arr[9]) {
      return false;
    }

    // İlk 10 rakam toplamının mode 10'u 11. haneyi verir.
    if (toplam % 10 != arr[10]) {
      return false;
    }

    return true;
  }

  /**
   * Check tax number is true.
   *
   * @param taxNumber tax number.
   * @return boolean.
   */
  public static boolean checkTaxNumber(Long taxNumber) {

    String taxNumberStr = taxNumber.toString();
    taxNumberStr = getTenLengthTaxNumber(taxNumberStr);
    if (taxNumberStr.length() != 10) {
      return false;
    }
    int[] haneMod10 = new int[10];
    int[] haneMod9 = new int[10];
    int toplam = 0;
    for (int i = 0; i < 9; i++) {
      haneMod10[i] = (Integer.parseInt(String.valueOf(taxNumberStr.charAt(i))) + (9 - i)) % 10;
      haneMod9[i] = (int) (haneMod10[i] * Math.pow(2, 9 - i) % 9);
      if (haneMod10[i] != 0 && haneMod9[i] == 0) {
        haneMod9[i] = 9;
      }
      toplam += haneMod9[i];
    }
    haneMod10[9] = Integer.parseInt(String.valueOf(taxNumberStr.charAt(9)));
    if (toplam % 10 == 0) {
      toplam = 0;
    } else {
      toplam = 10 - (toplam % 10);
    }
    if (haneMod10[9] == toplam) {
      return true;
    }
    return false;
  }

  /**
   * Get tax number to 10 chracter.
   *
   * @param taxNumberStr tax number
   * @return tax number with zero
   */
  public static String getTenLengthTaxNumber(String taxNumberStr) {

    if (taxNumberStr.length() < 10) {
      int minus = 10 - taxNumberStr.length();

      StringBuilder taxNumberStrBuilder = new StringBuilder(taxNumberStr);
      for (int i = 0; i < minus; i++) {
        taxNumberStrBuilder.insert(0, "0");
      }
      taxNumberStr = taxNumberStrBuilder.toString();
    }

    return taxNumberStr;
  }
}
