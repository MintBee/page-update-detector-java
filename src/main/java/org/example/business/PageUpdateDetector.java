package org.example.business;

import org.example.business.factory.PageDiffDetectorFactory;
import org.example.domain.PageUpdateDetectionResult;
import org.example.domain.TrackedPage;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PageUpdateDetector {
    @Nonnull
    private final TrackedPage targetPage;
    @Nonnull
    private final List<String> keywords;

    @Nonnull
    private final PageDiffDetectorFactory diffDetectorFactory;
    @Nonnull
    private final PageDownloader pageDownloader;


    public PageUpdateDetector(@Nonnull TrackedPage targetPage,
                              @Nonnull List<String> keywords,
                              @Nonnull PageDiffDetectorFactory diffDetectorFactory,
                              @Nonnull PageDownloader pageDownloader) {
        this.targetPage = targetPage;
        this.keywords = keywords;
        this.diffDetectorFactory = diffDetectorFactory;
        this.pageDownloader = pageDownloader;
    }


    public CompletableFuture<PageUpdateDetectionResult> detect() {
        return pageDownloader.download(targetPage.getUrl())
                .thenApplyAsync(page -> {
                    var diffDetector = diffDetectorFactory.create(targetPage.getHtmlFromLastVisit(), page);
                    var isChangeMade = diffDetector.isChangeMade();
                    List<String> keywordsDetected;
                    if (isChangeMade) {
                        keywordsDetected = diffDetector.calculateKeywordsInChange(keywords);
                    } else {
                        keywordsDetected = null;
                    }
                    return new PageUpdateDetectionResult(isChangeMade, keywordsDetected);
                });
    }
}
