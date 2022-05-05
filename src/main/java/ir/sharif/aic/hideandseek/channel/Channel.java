package ir.sharif.aic.hideandseek.channel;

public interface Channel<T> {
  /**
   * push a message through channel so all watchers are notified.
   *
   * @param msg the message to send to all watchers.
   */
  void push(T msg);

  /**
   * add a watcher to notify later on.
   *
   * @param watcher the watcher to add.
   */
  void addWatcher(Watcher<T> watcher);
}
