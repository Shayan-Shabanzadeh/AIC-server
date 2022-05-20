package ir.sharif.aic.hideandseek.core.watchers;

import ir.sharif.aic.hideandseek.core.app.GameService;
import ir.sharif.aic.hideandseek.core.events.GameEvent;
import ir.sharif.aic.hideandseek.core.events.GameTurnChangedEvent;
import ir.sharif.aic.hideandseek.core.models.GameConfig;
import ir.sharif.aic.hideandseek.core.models.Team;
import ir.sharif.aic.hideandseek.lib.channel.Channel;
import ir.sharif.aic.hideandseek.lib.channel.Watcher;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NextTurnWatcher implements Watcher<GameEvent> {
  private final Channel<GameEvent> eventChannel;
  private final GameConfig gameConfig;
  private final GameService gameService;

  @Override
  public void watch(GameEvent event) {
    if (event instanceof GameTurnChangedEvent) {
      this.arrestThieves();
      this.chargeBalances();
      this.figureOutGameResult();
    }
  }

  public void arrestThieves() {
    var nodes = this.gameConfig.getAllNodes();

    for (var node : nodes) {
      for (var team : Team.values()) {
        this.gameService.arrestThieves(node, team);
      }
    }
  }

  public void chargeBalances() {
    var allPolice = this.gameConfig.findAllPolice();
    var policeIncome = this.gameConfig.getIncomeSettings().getPoliceIncomeEachTurn();
    allPolice.forEach(police -> police.chargeBalance(policeIncome, this.eventChannel));

    var aliveThieves = this.gameConfig.findAliveThieves();
    var thievesIncome = this.gameConfig.getIncomeSettings().getThiefIncomeEachTurn();
    aliveThieves.forEach(thief -> thief.chargeBalance(thievesIncome, this.eventChannel));
  }

  public void figureOutGameResult() {}
}