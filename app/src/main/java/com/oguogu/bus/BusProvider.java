package com.oguogu.bus;

import com.squareup.otto.Bus;

/**
 * Created by Administrator on 2016-12-23.
 */

public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
