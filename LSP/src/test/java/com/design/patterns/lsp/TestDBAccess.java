package com.design.patterns.lsp;

import org.junit.Assert;
import org.junit.Test;

public class TestDBAccess {

    @Test
    public void testPowerUser() {

        PowerUser powerUser = new PowerUser();

        DatabaseGateway.writeToDBForce(powerUser, "payload");
        Assert.assertEquals(DatabaseGateway.readFromDB(), "payload");
    }

}
