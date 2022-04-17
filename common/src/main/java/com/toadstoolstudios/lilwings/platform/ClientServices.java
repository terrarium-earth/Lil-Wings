package com.toadstoolstudios.lilwings.platform;

import com.toadstoolstudios.lilwings.platform.services.IClientHelper;

public class ClientServices {
    public static final IClientHelper CLIENT = CommonServices.load(IClientHelper.class);
}
