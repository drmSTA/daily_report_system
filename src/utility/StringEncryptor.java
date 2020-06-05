package utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class StringEncryptor {
  private static final long serialVersionUID = 20200601L;
  private static String SALT = null;
  private static String ENCRYPTION_TYPE = "SHA-256";

  public static void setSalt(String salt){
    StringEncryptor.SALT = salt;
  }

  public static String makeEncryptedPassword(String plainPassword){
    String result = null;
    plainPassword = (plainPassword == null ) ? "" : plainPassword;
    try{
      if(StringValidator.isEmpty(SALT))      throw new IllegalArgumentException("invalid parameter detected on plainPassword and salt");
      byte[] bytes ;
      String processing = plainPassword + SALT;
      bytes = MessageDigest.getInstance(ENCRYPTION_TYPE).digest(processing.getBytes());
      result = DatatypeConverter.printHexBinary(bytes);
    }catch (IllegalArgumentException | NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return result;
  }
}
