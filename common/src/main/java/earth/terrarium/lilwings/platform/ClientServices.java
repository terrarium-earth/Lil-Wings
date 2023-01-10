package earth.terrarium.lilwings.platform;

import earth.terrarium.lilwings.platform.services.IClientHelper;

public class ClientServices {
    public static final IClientHelper CLIENT = CommonServices.load(IClientHelper.class);
}
