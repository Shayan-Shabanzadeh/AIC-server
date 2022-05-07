package ir.sharif.aic.hideandseek.core.events;

import lombok.Getter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public abstract class GameEvent {
  protected final String id;
  protected final Date timeStamp;
  protected final GameEventType type;
  protected String message;
  protected Map<String, String> context;

  protected GameEvent(GameEventType type) {
    this.id = UUID.randomUUID().toString();
    this.message = "";
    this.timeStamp = new Date();
    this.context = new HashMap<>();
    this.type = type;
  }

  public void addContext(String key, Object value) {
    this.context.put(key, value.toString());
  }

  public boolean isOfType(GameEventType type) {
    return this.type != null && this.type.equals(type);
  }
}