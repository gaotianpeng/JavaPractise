package com.javapractise.unittest;

public class SomeService {
    private DependencyService dependencyService;

    public SomeService() {
        this.dependencyService = new DependencyService();
    }

    public String callDependencyServiceMethod() {
        return dependencyService.someMethod();
    }
}

