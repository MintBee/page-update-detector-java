package org.example.business.factory;

import org.example.business.PageDiffDetector;

public interface PageDiffDetectorFactory {
    PageDiffDetector create(String htmlFromLastVisit, String page);
}
