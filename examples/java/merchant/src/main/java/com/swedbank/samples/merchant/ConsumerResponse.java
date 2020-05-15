package com.swedbank.samples.merchant;

import java.util.List;

public class ConsumerResponse {
    private String token;
    private List<Operation> operations;
    public static class Operation {
        private String method;
        private String rel;
        private String href;
        private String contentType;
    }
}
