package com.yoelglus.presentation.patterns.inject;

import com.yoelglus.presentation.patterns.data.DummyItemsRepository;
import com.yoelglus.presentation.patterns.domain.interfaces.ItemsRepository;

public class RepositoryFactory {
    public static ItemsRepository createItemsRepo() {
        return new DummyItemsRepository();
    }
}
