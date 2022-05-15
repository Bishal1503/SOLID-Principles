package com.design.patterns.lsp;

import java.util.*;

class PowerUser implements UserAccess {
    Map<String, Boolean> accessRights = new HashMap<>();

    @Override
    public void setupAccessRight(String right, boolean value) {
        accessRights.put(right, value);
    }

    @Override
    public boolean getValueOfAccessRight(String right) {
        return accessRights.getOrDefault(right, false);
    }

}
