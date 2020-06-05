package utility;

public class StringValidator {
  private static final long serialVersionUID = 20200601L;

  public static boolean isEmpty(String input){
    if(input == null || input.length() == 0){
      return true;
    }
    return false;
  }

  public static boolean areEmpty(String input1, String input2){
    if(isEmpty(input1) || isEmpty(input2)){
      return true;
    }
    return false;
  }

  public static boolean isUnderValidSession(String previousToken, String currentToken){
    if(previousToken != null && previousToken.equals(currentToken)) {
      return true;
    }
    return false;
  }
}
