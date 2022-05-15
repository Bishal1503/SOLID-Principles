package com.design.patterns.lsp;

import java.util.Set;
import java.util.HashSet;

class GenericUser implements UserAccess {
    private Set<String> protectedRights;

    public GenericUser(HashSet<String> protectedRights) {
        this.protectedRights = protectedRights;
    }

    @Override
    public void setupAccessRight(String right, boolean value) {
        if (!getValueOfAccessRight(right)) {
            protectedRights.add(right);
        }
    }

    @Override
    public boolean getValueOfAccessRight(String right) {
        return false;
    }

}
