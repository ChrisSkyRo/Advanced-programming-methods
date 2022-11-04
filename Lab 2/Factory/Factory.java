package Factory;

import Container.Container;
import Container.Strategy;

public interface Factory {
    Container createContainer(Strategy strategy);
}
