package com.design.patterns.lsp;

public interface UserAccess {

    void setupAccessRight(String right, boolean value);

    boolean getValueOfAccessRight(String right);
}
