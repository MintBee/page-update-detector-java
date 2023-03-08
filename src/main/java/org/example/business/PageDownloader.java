package org.example.business;

import java.util.concurrent.CompletableFuture;

public interface PageDownloader {


    CompletableFuture<String> download(String url);
}
