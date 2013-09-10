package connect.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import connect.core.Connect;

public class ConnectActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("connect/resources");
    PlayN.run(new Connect());
  }
}
