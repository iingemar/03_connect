package connect.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import connect.core.Connect;

public class ConnectJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assets().setPathPrefix("connect/resources");
    PlayN.run(new Connect());
  }
}
