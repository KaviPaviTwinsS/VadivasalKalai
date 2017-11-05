package pasu.vadivasal.android.logger;

public interface LogStrategy {

  void log(int priority, String tag, String message);
}
