package battleLogic.log;

import battleLogic.IBattle;

@FunctionalInterface
public interface LogSupplier {

    Logger get(IBattle battle);

}
