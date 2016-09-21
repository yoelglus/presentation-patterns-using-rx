package net.skyscanner.cleanarchitecture.inject;

import net.skyscanner.cleanarchitecture.data.DummyItemsRepository;
import net.skyscanner.cleanarchitecture.domain.interfaces.ItemsRepository;

public class RepositoryFactory {
    public static ItemsRepository createItemsRepo() {
        return new DummyItemsRepository();
    }
}
