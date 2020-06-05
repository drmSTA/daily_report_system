package utility;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class Dumper {
  private static final long serialVersionUID = 20200601L;

  public static void dump(HttpServletRequest request){
    Map<String, String[]> parameters = request.getParameterMap();
    for (String key : parameters.keySet()) {
      System.out.print("key : " + key + " | values : ");
      for (Object value : parameters.values().toArray()) {
        if(value instanceof String){
          System.out.print(" " + value);
          continue;
        }

        if(value instanceof Integer){
          System.out.print(" " + value);
          continue;
        }

        System.out.print(" " + value.toString());
      }
      System.out.println();
    }
  }

}
