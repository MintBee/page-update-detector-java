package org.example.business;

import java.util.List;

abstract public class PageDiffDetector {
        protected String pageBefore;
        protected String pageAfter;

        abstract public boolean isChangeMade();
        abstract public List<String> calculateKeywordsInChange(List<String> keywords);
}
